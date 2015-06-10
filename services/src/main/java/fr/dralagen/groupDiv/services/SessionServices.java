package fr.dralagen.groupDiv.services;

import fr.dralagen.groupDiv.bean.SessionBean;
import fr.dralagen.groupDiv.model.Session;
import fr.dralagen.groupDiv.model.Ue;
import fr.dralagen.groupDiv.persistence.SessionRepository;
import fr.dralagen.groupDiv.persistence.UeRepository;
import fr.dralagen.groupDiv.services.exception.InvalidFormException;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 6/5/15.
 *
 * @author dralagen
 */
public class SessionServices {

  private static SessionServices service = null;

  private SessionServices() {}

  public static synchronized SessionServices getInstance() {
    if (null == service) {
      service = new SessionServices();
    }
    return service;
  }

  public Session create(SessionBean session) throws InvalidFormException {

    checkSession(session);

    Session newSession = new Session();
    newSession.setName(session.getName());
    newSession.setWithGroupDiv(session.getWithGroupDiv());
    newSession.setLastLog(null);
    newSession.setBeginDate(new Date());
    newSession.setGDtot(0);

    newSession = SessionRepository.getInstance().create(newSession);

    for (Ue ue: session.getUes()) {
      ue.setSessionId(newSession.getKey().getId());
      UeRepository.getInstance().create(ue);
    }

    return newSession;
  }

  public Collection<Session> getAll () {
    return SessionRepository.getInstance().findAll();
  }

  public Session get(long sessionId) {
    return SessionRepository.getInstance().findOne(sessionId);
  }

  private void checkSession(SessionBean session) throws InvalidFormException {
    Map<String, String> errors = new HashMap<>();

    if (session.getName() == null || session.getName().equals("")) {
      errors.put("name", "Name is mandatory for a session");
    }

    if (session.getUes().isEmpty()) {
      errors.put("ue", "At least one UE is mandatory for a session");
    }

    for (Ue ue : session.getUes()) {
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
