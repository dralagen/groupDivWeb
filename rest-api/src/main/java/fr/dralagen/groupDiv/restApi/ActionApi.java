package fr.dralagen.groupDiv.restApi;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import fr.dralagen.groupDiv.bean.*;
import fr.dralagen.groupDiv.model.Action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
  public LogBean commitUe(CommitUeBean ue) {

    //TODO dralagen 6/4/15 : Add new version of UE
    //TODO dralagen 6/4/15 : Log Commit UE
    //TODO dralagen 6/4/15 : Update divergence

    LogBean result = new LogBean();
    result.setAction(Action.COMMIT);
    result.setMessage("Commit UE on " + ue.getUeId());
    result.setUserId(ue.getUserId());
    result.setDate(new Date());
    return result;
  }

  @ApiMethod(name = "action.commit.review", httpMethod = ApiMethod.HttpMethod.POST, path = "action/commit/review")
  public LogBean commitReview(CommitReviewBean review) {

    //TODO dralagen 6/4/15 : Add new review of UE
    //TODO dralagen 6/4/15 : Log Commit review
    //TODO dralagen 6/4/15 : Update divergence

    LogBean result = new LogBean();
    result.setAction(Action.COMMIT);
    result.setMessage("Commit Review ");
    result.setUserId(review.getUserId());
    result.setDate(new Date());
    return result;
  }

  @ApiMethod(name = "action.pull", httpMethod = ApiMethod.HttpMethod.GET, path = "action/pull/{userId}")
  public PullBean pull(@Named("userId") String userId) {

    //TODO dralagen 6/4/15 : Update position of user
    //TODO dralagen 6/4/15 : Log Pull
    //TODO dralagen 6/4/15 : Update divergence

    PullBean result = new PullBean();
    List<ReviewBean> newReview = new ArrayList<>();
    for (int i = 1; i < 10; i++) {
      ReviewBean reviewBean = new ReviewBean();
      reviewBean.setUserId(i % 5 + 1);
      reviewBean.setUeId((long) (i + (Math.random()*15) % 5 + 8));
      reviewBean.setId((long) (Math.random()*1000000));
      reviewBean.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec tempor massa mi, ac laoreet orci mattis vel.");
      reviewBean.setPostDate(new Date());

      newReview.add(reviewBean);
    }
    result.setReview(newReview);

    List<UeBean> newUe = new ArrayList<>();
    UeBean ue = new UeBean();
    ue.setId((long) (Math.random()*1000000));
    ue.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec tempor massa mi, ac laoreet orci mattis vel. Morbi tempus efficitur risus et pretium. Suspendisse et enim arcu. Curabitur gravida tortor ut auctor mattis. Quisque augue dui, aliquam in dui vel, porttitor suscipit tortor. Integer imperdiet felis ante, sit amet mattis nulla fermentum et. Mauris sollicitudin ipsum sagittis, scelerisque neque in, vestibulum leo. Suspendisse egestas dui in arcu molestie, a pharetra ex congue. Donec sollicitudin molestie nisi vel pellentesque. Donec et diam eros. Nulla eget finibus arcu. Curabitur ac leo vitae dui ullamcorper vehicula nec sit amet tortor. Curabitur aliquam rutrum magna.");
    ue.setTitle("Lorem ipsum");
    ue.setVersion((int) (Math.random()*25));
    newUe.add(ue);
    result.setUe(newUe);


    return result;
  }


}
