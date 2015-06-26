package fr.dralagen.groupDiv.restApi;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import fr.dralagen.groupDiv.bean.DivergenceBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 6/17/15.
 *
 * Rest API to find all divergence value
 *
 * @author dralagen
 */
@Api(
    name = Constants.NAME,
    version = Constants.VERSION,
    clientIds = {Constants.API_ID},
    scopes = {}
)
public class ControlAwarenessApi {

  @ApiMethod(name = "divergence", httpMethod = ApiMethod.HttpMethod.GET, path = "session/{sessionId}/divergence")
  public DivergenceBean getDivergence(@Named("sessionId") long sessionId) {
    DivergenceBean divergence = new DivergenceBean();

    Map<Long, Integer> userDivergence = new HashMap<>();
    int gdtot = 0;
    for (int i = 0; i < 5; i++) {
      int distance = (int) (Math.random()*15);
      userDivergence.put((long) (i+1), distance);
      gdtot+=distance;
    }
    divergence.setGlobalDivergence(gdtot);
    divergence.setUserDivergence(userDivergence);

    return divergence;
  }
}
