package fr.dralagen.groupDiv.restApi;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import fr.dralagen.groupDiv.bean.UeBean;
import fr.dralagen.groupDiv.model.User;
import fr.dralagen.groupDiv.services.UeServices;

import java.util.Collection;

/**
 * Created on 6/9/15.
 *
 * Rest Api to get and update UE
 *
 * @author dralagen
 */
@Api(
    name = Constants.NAME,
    version = Constants.VERSION,
    clientIds = {Constants.API_ID},
    scopes = {}
)
public class UeApi {


  @ApiMethod(name = "session.ue.get", httpMethod = ApiMethod.HttpMethod.GET, path = "session/{sessionId}/ue/{ueId}")
  public UeBean getUe(@Named("sessionId") long sessionId, @Named("ueId") long ueId, User user) {

    return UeServices.getInstance().getUe(sessionId, ueId, user);
  }

  @ApiMethod(name = "session.ue.list", httpMethod = ApiMethod.HttpMethod.GET, path = "session/{sessionId}/ue")
  public Collection<UeBean> list(@Named("sessionId") long sessionId) {
    return UeServices.getInstance().getAllUe(sessionId);
  }

  @ApiMethod(name = "session.ue.commit", httpMethod = ApiMethod.HttpMethod.POST, path = "session/{sessionId}/ue")
  public UeBean commit(@Named("sessionId") long sessionId, UeBean ue) {
    return UeServices.getInstance().updateUe(ue);
  }
}
