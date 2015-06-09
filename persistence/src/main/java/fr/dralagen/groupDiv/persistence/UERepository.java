package fr.dralagen.groupDiv.persistence;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import fr.dralagen.groupDiv.model.Session;
import fr.dralagen.groupDiv.model.UE;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.Collection;

/**
 * Created on 6/5/15.
 *
 * @author dralagen
 */
public class UERepository {

  private static UERepository repo = null;

  private UERepository() {

  }

  public static UERepository getInstance() {
    if (repo == null) {
      repo = new UERepository();
    }

    return repo;
  }

  public Collection<UE> findAll(long sessionId) {
    PersistenceManager pm = PMF.get().getPersistenceManager();

    Query q = pm.newQuery(UE.class);
    q.setFilter("sessionId == sessionIdParam");
    q.declareParameters("long sessionIdParam");

    try {
      return (Collection<UE>) q.execute(sessionId);
    } catch (JDOObjectNotFoundException e) {
      return null;
    }

  }

  public UE findOne(long sessionId, String ueId) {
    return findOne(forgeKey(sessionId, ueId));
  }

  public UE findOne(Key id) {
    PersistenceManager pm = PMF.get().getPersistenceManager();

    try {
      return pm.getObjectById(UE.class, id);
    } catch (JDOObjectNotFoundException e) {
      return null;
    }
  }

  static Key forgeKey(long sessionId, String ueId) {
    return new KeyFactory.Builder(Session.class.getSimpleName(), sessionId).addChild(UE.class.getSimpleName(), ueId).getKey();
  }

  public UE create (UE ue) {
    PersistenceManager pm = PMF.get().getPersistenceManager();

    try {
      pm.makePersistent(ue);
    } finally {
      pm.close();
    }

    return ue;
  }
}
