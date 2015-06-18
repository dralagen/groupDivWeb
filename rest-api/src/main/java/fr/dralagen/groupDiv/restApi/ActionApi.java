package fr.dralagen.groupDiv.restApi;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.BadRequestException;
import fr.dralagen.groupDiv.bean.CommitReviewBean;
import fr.dralagen.groupDiv.bean.CommitUeBean;
import fr.dralagen.groupDiv.bean.LogBean;
import fr.dralagen.groupDiv.bean.PullBean;
import fr.dralagen.groupDiv.services.ActionServices;
import fr.dralagen.groupDiv.services.exception.InvalidFormException;

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
  public LogBean commitUe(@Named("sessionId") long sessionId, CommitUeBean ue) throws BadRequestException {

    try {
      return ActionServices.getInstance().commitUe(sessionId, ue);
    } catch (InvalidFormException e) {
        throw new BadRequestException(e);
    }
  }

  @ApiMethod(name = "action.commit.review", httpMethod = ApiMethod.HttpMethod.POST, path = "session/{sessionId}/commit/review")
  public LogBean commitReview(@Named("sessionId") long sessionId, CommitReviewBean review) throws BadRequestException {

    try {
      return ActionServices.getInstance().commitReview(sessionId, review);
    } catch (InvalidFormException e) {
      throw new BadRequestException(e);
    }
  }

  @ApiMethod(name = "action.pull", httpMethod = ApiMethod.HttpMethod.GET, path = "session/{sessionId}/{fromUserId}/pull/{toUserId}/")
  public PullBean pull(@Named("sessionId") long sessionId, @Named("fromUserId") long fromUserId, @Named("toUserId") long toUserId) {

    //TODO dralagen 6/4/15 : Update position of user
    //TODO dralagen 6/4/15 : Log Pull
    //TODO dralagen 6/4/15 : Update divergence

    return ActionServices.getInstance().pull(sessionId, fromUserId, toUserId);
  }


}
