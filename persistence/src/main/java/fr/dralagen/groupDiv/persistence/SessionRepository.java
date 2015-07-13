package fr.dralagen.groupDiv.persistence;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import fr.dralagen.groupDiv.model.Session;

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

      return (Collection<Session>) q.execute();
  }

  public Session findOne(long sessionId) {
    return findOne(forgeKey(sessionId));
  }

  public Session findOne(Key sessionId) {
    PersistenceManager pm = PMF.get().getPersistenceManager();

    return pm.getObjectById(Session.class, sessionId);

  }

  public Session save(Session session) {
    PersistenceManager pm = PMF.get().getPersistenceManager();

    session = pm.makePersistent(session);

    return session;
  }

  private static Key forgeKey(long id) {
    return new KeyFactory.Builder(Session.class.getSimpleName(), id).getKey();
  }
}
