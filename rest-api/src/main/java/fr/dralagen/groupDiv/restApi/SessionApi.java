package fr.dralagen.groupDiv.restApi;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.BadRequestException;
import fr.dralagen.groupDiv.model.Session;
import fr.dralagen.groupDiv.model.SessionBean;
import fr.dralagen.groupDiv.model.UE;
import fr.dralagen.groupDiv.persistence.UERepository;
import fr.dralagen.groupDiv.services.SessionServices;
import fr.dralagen.groupDiv.services.exception.InvalidFormException;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


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
  public Session getSession(@Named("sessionId") long id) {

    return SessionServices.getInstance().get(id);
  }
  
  @ApiMethod(name = "session.post", httpMethod = ApiMethod.HttpMethod.POST, path = "session")
  public SessionBean postSession (SessionBean session) throws BadRequestException {
    try {

      checkSession(session);

      Session newSession = new Session();
      newSession.setName(session.getName());
      newSession.setWithGroupDiv(session.getWithGroupDiv());
      newSession.setLastLog(null);
      newSession.setBeginDate(new Date());
      newSession.setGDtot(0);

      newSession = SessionServices.getInstance().create(newSession);
      session.setBeginDate(newSession.getBeginDate());
      session.setKey(newSession.getKey());

      for (UE ue: session.getUes()) {
        ue.setSessionId(newSession.getKey());
        ue = UERepository.getInstance().create(ue);
      }

    } catch (InvalidFormException e) {
      throw new BadRequestException(e);
    }

    return session;
  }

  private void checkSession(SessionBean session) throws InvalidFormException {
    Map<String, String> errors = new HashMap<>();

    if (session.getName() == null || session.getName().equals("")) {
      errors.put("name", "Name is mandatory for a session");
    }

    if (session.getUes().isEmpty()) {
      errors.put("ue", "At least one UE is mandatory for a session");
    }

    for (UE ue : session.getUes()) {
      String ueTitle = ue.getTitle();
      if (ueTitle == null || ueTitle.equals("")) {
        errors.put("ueTitle", "UE title is mandatory");
      } else {
        if (ue.getAuthor() == null || ue.getAuthor().getName() == null || ue.getAuthor().getName().equals("")) {
          errors.put(ue.getTitle(), "User is mandatory for an UE");
        }
      }
    }

    if (!errors.isEmpty()) {
      throw new InvalidFormException(errors);
    }
  }
}
