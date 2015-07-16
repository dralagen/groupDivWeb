package fr.dralagen.groupDiv.restApi;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.NotFoundException;
import fr.dralagen.groupDiv.bean.NewSessionBean;
import fr.dralagen.groupDiv.bean.NewUeBean;
import fr.dralagen.groupDiv.bean.SessionBean;
import fr.dralagen.groupDiv.bean.UeInfoBean;
import fr.dralagen.groupDiv.services.SessionServices;
import fr.dralagen.groupDiv.services.exception.InvalidFormException;
import fr.dralagen.groupDiv.services.exception.ObjectNotFoundException;

import java.util.Collection;


/**
 * Created on 6/3/15.
 *
 * Rest API to get and saveAction Session XP
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
  public Collection<SessionBean> getAllSession () throws NotFoundException {

    try {
      return SessionServices.getInstance().getAll();
    } catch (ObjectNotFoundException e) {
      throw new NotFoundException("ERROR_"+e.getObjectName().toUpperCase()+"_NOT_FOUND");
    }
  }

  @ApiMethod(name = "session.get", httpMethod = ApiMethod.HttpMethod.GET, path = "session/{sessionId}")
  public SessionBean getSession(@Named("sessionId") long id) throws NotFoundException {

    try {
      return SessionServices.getInstance().get(id);
    } catch (ObjectNotFoundException e) {
      throw new NotFoundException("ERROR_"+e.getObjectName().toUpperCase()+"_NOT_FOUND");
    }
  }
  
  @ApiMethod(name = "session.post", httpMethod = ApiMethod.HttpMethod.POST, path = "session")
  public SessionBean create(NewSessionBean session) throws BadRequestException, NotFoundException {

    if (session == null) {
      throw new NotFoundException("ERROR_NEW_SESSION_NOT_FOUND");
    }

    try {

      return SessionServices.getInstance().create(session);

    } catch (InvalidFormException e) {
      throw new BadRequestException(e);
    }
  }

  @ApiMethod(name = "session.edit", httpMethod = ApiMethod.HttpMethod.PUT, path = "session/{sessionId}")
  public SessionBean update(@Named("sessionId") Long sessionId, NewSessionBean session) throws BadRequestException, NotFoundException {

    if ( session.getName().equals("") ) {
      throw new BadRequestException("ERROR_NULL_SESSION_NAME");
    }

    try {
      return SessionServices.getInstance().updateSessionName(sessionId, session);
    } catch (ObjectNotFoundException e) {
      throw new NotFoundException("ERROR_"+e.getObjectName().toUpperCase()+"_NOT_FOUND");
    }
  }

  @ApiMethod(name = "session.ue.edit", httpMethod = ApiMethod.HttpMethod.PUT, path = "session/{sessionId}/ue/{ueId}")
  public UeInfoBean updateUe(@Named("sessionId") Long sessionId, @Named("ueId") Long ueId, NewUeBean ue) throws BadRequestException, NotFoundException {

    if (ue == null) {
      throw new BadRequestException("ERROR_UE_NOT_FOUND");
    }

    try {
      return SessionServices.getInstance().updateUe(sessionId, ueId, ue);
    } catch (InvalidFormException e) {
      throw new BadRequestException(e);
    } catch (ObjectNotFoundException e) {
      throw new NotFoundException("ERROR_"+e.getObjectName().toUpperCase()+"_NOT_FOUND");
    }

  }

  @ApiMethod(name = "session.ue.delete", httpMethod = ApiMethod.HttpMethod.DELETE, path = "session/{sessionId}/ue/{ueId}")
  public SessionBean deleteUe(@Named("sessionId") Long sessionId, @Named("ueId") Long ueId) throws NotFoundException {

    try {
      return SessionServices.getInstance().deleteUe(sessionId, ueId);
    } catch (ObjectNotFoundException e) {
      throw new NotFoundException("ERROR_"+e.getObjectName().toUpperCase()+"_NOT_FOUND");
    }

  }

  @ApiMethod(name = "session.ue.add", httpMethod = ApiMethod.HttpMethod.PUT, path = "session/{sessionId}/ue")
  public UeInfoBean addUe(@Named("sessionId") Long sessionId, NewUeBean ue) throws BadRequestException, NotFoundException {

    if ( ue.getUser().equals("") ) {
      throw new BadRequestException("User can't be null");
    }
    if (ue.getTitle().equals("")){
      throw new BadRequestException("Ue title can't be null");
    }
    try {
      return SessionServices.getInstance().addUe(sessionId, ue);
    } catch (ObjectNotFoundException e) {
      throw new NotFoundException("ERROR_"+e.getObjectName().toUpperCase()+"_NOT_FOUND");
    }

  }
}
