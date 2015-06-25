package fr.dralagen.groupDiv.restApi;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.BadRequestException;
import fr.dralagen.groupDiv.bean.*;
import fr.dralagen.groupDiv.services.SessionServices;
import fr.dralagen.groupDiv.services.exception.InvalidFormException;

import java.util.Collection;


/**
 * Created on 6/3/15.
 *
 * Rest API to get and save Session XP
 *
 * @author dralagen
 */
@Api(
    name = Constants.NAME,
    version = Constants.VERSION,
    clientIds = {Constants.API_ID},
    scopes = {}
)
public class SessionApi {

  @ApiMethod(name = "session.list", httpMethod = ApiMethod.HttpMethod.GET, path = "session")
  public Collection<SessionBean> getAllSession () {

    return SessionServices.getInstance().getAll();
  }

  @ApiMethod(name = "session.get", httpMethod = ApiMethod.HttpMethod.GET, path = "session/{sessionId}")
  public SessionBean getSession(@Named("sessionId") long id) {
    return SessionServices.getInstance().get(id);
  }
  
  @ApiMethod(name = "session.post", httpMethod = ApiMethod.HttpMethod.POST, path = "session")
  public SessionBean create(NewSessionBean session) throws BadRequestException {
    try {

      return SessionServices.getInstance().create(session);

    } catch (InvalidFormException e) {
      throw new BadRequestException(e);
    } catch (NullPointerException e) {
      throw new BadRequestException("JSON malformed");
    }
  }

  @ApiMethod(name = "session.edit", httpMethod = ApiMethod.HttpMethod.PUT, path = "session/{sessionId}")
  public SessionBean update(@Named("sessionId") Long sessionId, NewSessionBean session) throws BadRequestException {

    if ( session.getName().equals("") ) {
      throw new BadRequestException("Session Name can't be null");
    }

    return SessionServices.getInstance().updateSessionName(sessionId, session);
  }

  @ApiMethod(name = "session.ue.edit", httpMethod = ApiMethod.HttpMethod.PUT, path = "session/{sessionId}/ue/{ueId}")
  public UeInfoBean updateUe(@Named("sessionId") Long sessionId, @Named("ueId") Long ueId, NewUeBean ue) {

    return SessionServices.getInstance().updateUe(sessionId, ueId, ue);

  }

  @ApiMethod(name = "session.ue.delete", httpMethod = ApiMethod.HttpMethod.DELETE, path = "session/{sessionId}/ue/{ueId}")
  public SessionBean deleteUe(@Named("sessionId") Long sessionId, @Named("ueId") Long ueId) {

    return SessionServices.getInstance().deleteUe(sessionId, ueId);

  }
}
