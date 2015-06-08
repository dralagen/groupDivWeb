package fr.dralagen.groupDiv.restApi;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.BadRequestException;
import fr.dralagen.groupDiv.model.Session;
import fr.dralagen.groupDiv.model.UE;
import fr.dralagen.groupDiv.model.User;
import fr.dralagen.groupDiv.services.SessionServices;
import fr.dralagen.groupDiv.services.exception.InvalidFormException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


/**
 * Created on 6/3/15.
 *
 * Rest API to get and create Session XP
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
  public Collection<Session> getAllSession () {

    return SessionServices.getInstance().getAll();
  }

  @ApiMethod(name = "session.get", httpMethod = ApiMethod.HttpMethod.GET, path = "session/{sessionId}")
  public Session getSession(@Named("sessionId") String id) {
    Session session = new Session();
    session.setName("Test Session");
    session.setBeginDate(new Date());
    session.setGDtot(0);
    session.setLastLog(null);
    session.setWithGroupDiv(true);

    List<UE> ueList = new ArrayList<UE>();
    for (int i = 0; i <= 5; ++i) {
      User user = new User();
      user.setName("usr" + i);

      UE ue = new UE();
      ue.setTitle("UE " + i);
      ue.setAuthor(user);

      ueList.add(ue);
    }
    session.setUes(ueList);

    return session;
  }
  
  @ApiMethod(name = "session.post", httpMethod = ApiMethod.HttpMethod.POST, path = "session")
  public Session postSession(Session session) throws BadRequestException {
    try {
      return SessionServices.getInstance().create(session);
    } catch (InvalidFormException e) {
      throw new BadRequestException(e);
    }
  }
}
