package fr.dralagen.groupDiv.persistence;

import com.google.appengine.api.datastore.Key;
import fr.dralagen.groupDiv.model.User;

import javax.jdo.JDOObjectNotFoundException;
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

  public User findOne(Key userId) {
    PersistenceManager pm = PMF.get().getPersistenceManager();

    try {
      return pm.getObjectById(User.class, userId);
    } catch (JDOObjectNotFoundException e) {
      return null;
    }
  }
}
