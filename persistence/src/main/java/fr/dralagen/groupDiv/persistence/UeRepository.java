package fr.dralagen.groupDiv.persistence;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import fr.dralagen.groupDiv.model.Session;
import fr.dralagen.groupDiv.model.Ue;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
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

    Session session = SessionRepository.getInstance().findOne(sessionId);

    if (session == null) {
      return null;
    }

    return session.getUes();
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
}
