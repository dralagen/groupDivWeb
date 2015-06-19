package fr.dralagen.groupDiv.restApi;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.BadRequestException;
import fr.dralagen.groupDiv.bean.NewSessionBean;
import fr.dralagen.groupDiv.bean.SessionBean;
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
}
