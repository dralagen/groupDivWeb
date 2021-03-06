package fr.dralagen.groupDiv.restApi;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.ForbiddenException;
import com.google.api.server.spi.response.NotFoundException;
import fr.dralagen.groupDiv.bean.CommitReviewBean;
import fr.dralagen.groupDiv.bean.CommitUeBean;
import fr.dralagen.groupDiv.bean.LogBean;
import fr.dralagen.groupDiv.bean.PullBean;
import fr.dralagen.groupDiv.services.ActionServices;
import fr.dralagen.groupDiv.services.SessionServices;
import fr.dralagen.groupDiv.services.exception.InvalidFormException;
import fr.dralagen.groupDiv.services.exception.ObjectNotFoundException;

import javax.annotation.Nullable;
import java.util.Collection;

/**
 * Created on 6/3/15.
 *
 * Rest API to pull and commit review and UE
 *
 * @author dralagen
 */
@Api(
    name = Constants.NAME,
    version = Constants.VERSION,
    clientIds = {Constants.API_ID},
    scopes = {}
)
public class ActionApi {

  @ApiMethod(name = "action.commit.ue", httpMethod = ApiMethod.HttpMethod.POST, path = "session/{sessionId}/commit/ue")
  public LogBean commitUe(@Named("sessionId") long sessionId, CommitUeBean ue) throws BadRequestException, ForbiddenException, NotFoundException {

    try {
      return ActionServices.getInstance().commitUe(sessionId, ue);
    } catch (InvalidFormException e) {
      throw new BadRequestException(e);
    } catch (IllegalAccessException e) {
      throw new ForbiddenException(e.getMessage());
    } catch (ObjectNotFoundException e) {
      throw new NotFoundException("ERROR_"+e.getObjectName().toUpperCase()+"_NOT_FOUND");
    }
  }

  @ApiMethod(name = "action.commit.review", httpMethod = ApiMethod.HttpMethod.POST, path = "session/{sessionId}/commit/review")
  public LogBean commitReview(@Named("sessionId") long sessionId, CommitReviewBean review) throws BadRequestException, ForbiddenException, NotFoundException {

    try {
      return ActionServices.getInstance().commitReview(sessionId, review);
    } catch (InvalidFormException e) {
      throw new BadRequestException(e);
    } catch (IllegalAccessException e) {
      throw new ForbiddenException(e.getMessage());
    } catch (ObjectNotFoundException e) {
      throw new NotFoundException("ERROR_"+e.getObjectName().toUpperCase()+"_NOT_FOUND");
    }
  }

  @ApiMethod(name = "action.pull", httpMethod = ApiMethod.HttpMethod.GET, path = "session/{sessionId}/{fromUserId}/pull/{toUserId}/")
  public PullBean pull(@Named("sessionId") long sessionId, @Named("fromUserId") long fromUserId, @Named("toUserId") long toUserId) throws NotFoundException {

    try {
      return ActionServices.getInstance().pull(sessionId, fromUserId, toUserId);
    } catch (ObjectNotFoundException e) {
      throw new NotFoundException("ERROR_"+e.getObjectName().toUpperCase()+"_NOT_FOUND");
    }
  }


  @ApiMethod(name = "action.list", httpMethod = ApiMethod.HttpMethod.GET, path = "session/{sessionId}/log")
  public Collection<LogBean> getAllAction(@Named("sessionId") long sessionId, @Nullable @Named("action") String action) throws NotFoundException {

    try {
      return SessionServices.getInstance().getAllAction(sessionId, action);
    } catch (ObjectNotFoundException e) {
      throw new NotFoundException("ERROR_"+e.getObjectName().toUpperCase()+"_NOT_FOUND");
    }
  }

}
