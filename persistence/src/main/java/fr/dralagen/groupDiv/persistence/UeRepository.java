package fr.dralagen.groupDiv.persistence;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import fr.dralagen.groupDiv.model.Session;
import fr.dralagen.groupDiv.model.Ue;

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

  public Ue findOne(long sessionId, long ueId) {
    return findOne(forgeKey(sessionId, ueId));
  }

  public Ue findOne(Key id) {

    Ue ue = (Ue) MemcacheRepository.getInstance().get(Ue.class.getSimpleName(), id);

    if (ue == null) {
      PersistenceManager pm = PMF.get().getPersistenceManager();
      ue = pm.getObjectById(Ue.class, id);

      MemcacheRepository.getInstance().add(Ue.class.getSimpleName(), id, ue);
    }

    return ue;
  }

  private static Key forgeKey(long sessionId, long ueId) {
    return new KeyFactory.Builder(Session.class.getSimpleName(), sessionId).addChild(Ue.class.getSimpleName(), ueId).getKey();
  }
}
