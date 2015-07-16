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

    Key userKey = forgeKey(sessionId, userId);

    GroupDivUser user = (GroupDivUser) MemcacheRepository.getInstance().get(GroupDivUser.class.getSimpleName(), userKey);
    if (user == null) {
      PersistenceManager pm = PMF.get().getPersistenceManager();

      user = pm.getObjectById(GroupDivUser.class, userKey);

      MemcacheRepository.getInstance().add(GroupDivUser.class.getSimpleName(), userKey, user);
    }

    return user;
  }

  public GroupDivUser save(GroupDivUser user) {

    PersistenceManager pm = JDOHelper.getPersistenceManager(user);

    user = pm.makePersistent(user);

    MemcacheRepository.getInstance().clean(GroupDivUser.class.getSimpleName(), user.getKey());
    MemcacheRepository.getInstance().add(GroupDivUser.class.getSimpleName(), user.getKey(), user);

    return user;
  }

  private Key forgeKey(long sessionId, long userId) {
    return new KeyFactory.Builder(Session.class.getSimpleName(), sessionId)
        .addChild(GroupDivUser.class.getSimpleName(), userId)
        .getKey();
  }
}
