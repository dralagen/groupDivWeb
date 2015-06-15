package fr.dralagen.groupDiv.persistence;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import fr.dralagen.groupDiv.model.Ue;
import fr.dralagen.groupDiv.model.UeContent;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

/**
 * Created on 6/15/15.
 *
 * @author dralagen
 */
public class UeContentRepository {

  private static UeContentRepository repo = null;

  private UeContentRepository () {

  }

  public static UeContentRepository getInstance() {
    if (repo == null) {
      repo = new UeContentRepository();
    }

    return repo;
  }

  public UeContent findOne(long ueId, int version) {
    PersistenceManager pm = PMF.get().getPersistenceManager();

    try {
      return pm.getObjectById(UeContent.class, forgeKey(ueId, version));
    } catch (JDOObjectNotFoundException e) {
      return null;
    }
  }

  public UeContent create(UeContent content) {
    if (content.getKey() != null) {
      return null;
    }

    content.setKey(forgeKey(content.getUeId(), content.getVersion()));

    PersistenceManager pm = PMF.get().getPersistenceManager();

    try {
      pm.makePersistent(content);
    } finally {
      pm.close();
    }

    return content;
  }

  private Key forgeKey (long ueId, int version) {
    return  new KeyFactory.Builder(Ue.class.getSimpleName(), ueId)
        .addChild("version", ((long) version))
        .getKey();
  }
}
