package fr.dralagen.groupDiv.persistence;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import fr.dralagen.groupDiv.model.Session;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.ArrayList;
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

    Collection<Session> allSession = (Collection<Session>) MemcacheRepository.getInstance().get(Session.class.getSimpleName(), "allSession");

    if (allSession == null) {
      PersistenceManager pm = PMF.get().getPersistenceManager();

      Query q = pm.newQuery(Session.class);

      allSession = (Collection<Session>) q.execute();

      MemcacheRepository.getInstance().add(Session.class.getSimpleName(), "allSession", new ArrayList<>(allSession));
    }

    return allSession;
  }

  public Session findOne(long sessionId) {
    return findOne(forgeKey(sessionId));
  }

  public Session findOne(Key sessionId) {

    Session session = (Session) MemcacheRepository.getInstance().get(Session.class.getSimpleName(), sessionId);

    if (session == null) {

      PersistenceManager pm = PMF.get().getPersistenceManager();

      session = pm.getObjectById(Session.class, sessionId);

      MemcacheRepository.getInstance().add(Session.class.getSimpleName(), sessionId, session);
    }

    return session;
  }

  public Session save(Session session) {
    PersistenceManager pm = PMF.get().getPersistenceManager();

    session = pm.makePersistent(session);

    MemcacheRepository.getInstance().clean(Session.class.getSimpleName(), "allSession");
    MemcacheRepository.getInstance().clean(Session.class.getSimpleName(), session.getKey());
    MemcacheRepository.getInstance().add(Session.class.getSimpleName(), session.getKey(), session);

    return session;
  }

  private static Key forgeKey(long id) {
    return new KeyFactory.Builder(Session.class.getSimpleName(), id).getKey();
  }
}
