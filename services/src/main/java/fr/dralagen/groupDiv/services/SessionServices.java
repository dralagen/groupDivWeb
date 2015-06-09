package fr.dralagen.groupDiv.services;

import fr.dralagen.groupDiv.model.Session;
import fr.dralagen.groupDiv.persistence.SessionRepository;
import fr.dralagen.groupDiv.services.exception.InvalidFormException;

import java.util.Collection;
import java.util.Date;

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

    session.setBeginDate(new Date());
    session.setGDtot(0);

    return SessionRepository.getInstance().create(session);
  }

  public Collection<Session> getAll () {
    return SessionRepository.getInstance().findAll();
  }

  public Session get(long sessionId) {
    return SessionRepository.getInstance().findOne(sessionId);
  }
}
