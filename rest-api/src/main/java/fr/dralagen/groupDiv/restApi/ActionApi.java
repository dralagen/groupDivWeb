package fr.dralagen.groupDiv.restApi;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import fr.dralagen.groupDiv.model.Action;
import fr.dralagen.groupDiv.model.LogAction;
import fr.dralagen.groupDiv.model.UE;

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

  @ApiMethod(name = "action.commit.ue", httpMethod = ApiMethod.HttpMethod.POST, path = "action/commit/ue")
  public LogAction commitUe(@Named("content") String content, UE ue) {

    //TODO dralagen 6/4/15 : Add new version of UE
    //TODO dralagen 6/4/15 : Log Commit UE
    //TODO dralagen 6/4/15 : Update divergence

    LogAction result = new LogAction();
    result.setAction(Action.COMMIT);
    result.setResult("Commit UE");
    return result;
  }

  @ApiMethod(name = "action.commit.review", httpMethod = ApiMethod.HttpMethod.POST, path = "action/commit/review")
  public LogAction commitReview(@Named("content") String content, UE ue) {

    //TODO dralagen 6/4/15 : Add new review of UE
    //TODO dralagen 6/4/15 : Log Commit review
    //TODO dralagen 6/4/15 : Update divergence

    LogAction result = new LogAction();
    result.setAction(Action.COMMIT);
    result.setResult("Commit Review");
    return result;
  }

  @ApiMethod(name = "action.commit.ue", httpMethod = ApiMethod.HttpMethod.GET, path = "action/pull/{userId}")
  public LogAction pull(@Named("userId") String userId) {

    //TODO dralagen 6/4/15 : Add new version of UE
    //TODO dralagen 6/4/15 : Log Commit UE
    //TODO dralagen 6/4/15 : Update divergence

    LogAction result = new LogAction();
    result.setAction(Action.PULL);
    result.setResult("Pull on user " + userId);
    return result;
  }


}
