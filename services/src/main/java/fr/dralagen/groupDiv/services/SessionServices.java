package fr.dralagen.groupDiv.services;

import fr.dralagen.groupDiv.model.Session;
import fr.dralagen.groupDiv.model.UE;
import fr.dralagen.groupDiv.persistence.SessionRepository;
import fr.dralagen.groupDiv.services.exception.InvalidFormException;

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

  public Session create(Session session) throws InvalidFormException {

    checkSession(session);

    session.setBeginDate(new Date());
    session.setGDtot(0);

    return SessionRepository.getInstance().create(session);
  }

  private void checkSession(Session session) throws InvalidFormException {
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
