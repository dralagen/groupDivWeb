package fr.dralagen.groupDiv.persistence;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import fr.dralagen.groupDiv.model.Ue;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.Collection;

/**
 * Created on 6/5/15.
 *
 * @author dralagen
 */
public class UeRepository {

  private static UeRepository repo = null;

  private UeRepository () {

  }

  public static UeRepository getInstance() {
    if (repo == null) {
      repo = new UeRepository();
    }

    return repo;
  }

  public Collection<Ue> findAll(long sessionId) {
    PersistenceManager pm = PMF.get().getPersistenceManager();

    Query q = pm.newQuery(Ue.class);
    q.setFilter("sessionId == sessionIdParam");
    q.declareParameters("long sessionIdParam");

    try {
      return (Collection<Ue>) q.execute(sessionId);
    } catch (JDOObjectNotFoundException e) {
      return null;
    }

  }

  public Ue findOne(long ueId) {
    return findOne(forgeKey(ueId));
  }

  public Ue findOne(Key id) {
    PersistenceManager pm = PMF.get().getPersistenceManager();

    try {
      return pm.getObjectById(Ue.class, id);
    } catch (JDOObjectNotFoundException e) {
      return null;
    }
  }

  static Key forgeKey(long ueId) {
    return new KeyFactory.Builder(Ue.class.getSimpleName(), ueId).getKey();
  }

  public Ue create (Ue ue) {
    PersistenceManager pm = PMF.get().getPersistenceManager();

    try {
      pm.makePersistent(ue);
    } finally {
      pm.close();
    }

    return ue;
  }
}
