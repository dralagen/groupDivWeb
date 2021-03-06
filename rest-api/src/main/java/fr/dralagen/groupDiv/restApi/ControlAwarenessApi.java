package fr.dralagen.groupDiv.restApi;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.NotFoundException;
import fr.dralagen.groupDiv.bean.DivergenceBean;
import fr.dralagen.groupDiv.services.SessionServices;
import fr.dralagen.groupDiv.services.exception.ObjectNotFoundException;

import java.util.Collection;

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

  @ApiMethod(name = "divergence.get", httpMethod = ApiMethod.HttpMethod.GET, path = "session/{sessionId}/divergence")
  public DivergenceBean getDivergence(@Named("sessionId") long sessionId) throws NotFoundException {

    try {
      return SessionServices.getInstance().getDivergence(sessionId);
    } catch (ObjectNotFoundException e) {
      throw new NotFoundException("ERROR_"+e.getObjectName().toUpperCase()+"_NOT_FOUND");
    }
  }

  @ApiMethod(name = "divergence.list", httpMethod = ApiMethod.HttpMethod.GET, path = "session/{sessionId}/divergence/all")
  public Collection<DivergenceBean> getAllDivergence(@Named("sessionId") long sessionId) throws NotFoundException {

    try {
      return SessionServices.getInstance().getAllDivergence(sessionId);
    } catch (ObjectNotFoundException e) {
      throw new NotFoundException("ERROR_"+e.getObjectName().toUpperCase()+"_NOT_FOUND");
    }
  }
}
