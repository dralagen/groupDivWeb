package fr.dralagen.groupDiv.persistence;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import fr.dralagen.groupDiv.model.Session;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.Collection;

/**
 * Created on 6/5/15.
 *
 * @author dralagen
 */
public class SessionRepository {

  private static SessionRepository repo = null;

  private SessionRepository() {
  }

  public static SessionRepository getInstance() {
    if (repo == null) {
      repo = new SessionRepository();
    }
    return repo;
  }

  public Collection<Session> findAll () {
    PersistenceManager pm = PMF.get().getPersistenceManager();

    Query q = pm.newQuery(Session.class);

    try {
      return (Collection<Session>) q.execute();
    } catch (JDOObjectNotFoundException e) {
      return null;
    }
  }

  public Session findOne(long sessionId) {
    return findOne(forgeKey(sessionId));
  }

  public Session findOne(Key sessionId) {
    PersistenceManager pm = PMF.get().getPersistenceManager();

    try {
      return pm.getObjectById(Session.class, sessionId);
    } catch (JDOObjectNotFoundException e) {
      return null;
    }

  }

  public Session create(Session session) {
    PersistenceManager pm = PMF.get().getPersistenceManager();

    try {
      pm.makePersistent(session);
    } finally {
      pm.close();
    }

    return session;
  }

  private static Key forgeKey(long id) {
    return new KeyFactory.Builder(Session.class.getSimpleName(), id).getKey();
  }
}
