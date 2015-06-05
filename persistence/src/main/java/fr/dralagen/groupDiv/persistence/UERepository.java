package fr.dralagen.groupDiv.persistence;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import fr.dralagen.groupDiv.model.Session;
import fr.dralagen.groupDiv.model.UE;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
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

  private static UERepository getInstance() {
    if (repo == null) {
      repo = new UERepository();
    }

    return repo;
  }

  public Collection<UE> findAll(String sessionId) {

    Session session = SessionRepository.getInstance().findOne(sessionId);

    if (session == null) {
      return null;
    }

    return session.getUes();
  }

  public UE findOne(String sessionId, String ueId) {
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

  static Key forgeKey(String sessionId, String ueId) {
    return new KeyFactory.Builder(Session.class.getSimpleName(), sessionId).addChild(UE.class.getSimpleName(), ueId).getKey();
  }

}
