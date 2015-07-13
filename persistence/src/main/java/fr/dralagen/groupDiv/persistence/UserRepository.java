package fr.dralagen.groupDiv.persistence;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import fr.dralagen.groupDiv.model.GroupDivUser;
import fr.dralagen.groupDiv.model.Session;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;

/**
 * Created on 6/5/15.
 *
 * @author dralagen
 */
public class UserRepository {
  private static UserRepository repo = null;

  private UserRepository() {
  }

  public static UserRepository getInstance() {
    if (repo == null) {
      repo = new UserRepository();
    }
    return repo;
  }

  public GroupDivUser findOne(long sessionId, long userId) {
    PersistenceManager pm = PMF.get().getPersistenceManager();

    return pm.getObjectById(GroupDivUser.class, forgeKey(sessionId, userId));
  }

  public GroupDivUser save(GroupDivUser user) {

    PersistenceManager pm = JDOHelper.getPersistenceManager(user);

    user = pm.makePersistent(user);

    return user;
  }

  private Key forgeKey(long sessionId, long userId) {
    return new KeyFactory.Builder(Session.class.getSimpleName(), sessionId)
        .addChild(GroupDivUser.class.getSimpleName(), userId)
        .getKey();
  }
}
