package fr.dralagen.groupDiv.persistence;

import com.google.appengine.api.datastore.Key;
import fr.dralagen.groupDiv.model.Review;
import fr.dralagen.groupDiv.model.Ue;
import fr.dralagen.groupDiv.model.User;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.Collection;
import java.util.List;

/**
 * Created on 6/5/15.
 *
 * @author dralagen
 */
public class ReviewRepository {

  private static ReviewRepository repo = null;

  private ReviewRepository() {

  }

  private static ReviewRepository getInstance() {
    if (repo == null) {
      repo = new ReviewRepository();
    }

    return repo;
  }

  public Collection<Review> findAllByUser(User user) {
    PersistenceManager pm = PMF.get().getPersistenceManager();

    List<Review> result;
    Query q = pm.newQuery(Review.class);
    q.setFilter("author == paramUser");
    q.declareParameters("User paramUser");

    try {
      result = (List<Review>) q.execute(user);
    } finally {
      q.closeAll();
    }

    return result;
  }

  public Collection<Review> findAllByUe(Ue ue) {
    PersistenceManager pm = PMF.get().getPersistenceManager();

    List<Review> result;
    Query q = pm.newQuery(Review.class);
    q.setFilter("ue == paramUe");
    q.declareParameters("UE paramUe");

    try {
      result = (List<Review>) q.execute(ue);
    } finally {
      q.closeAll();
    }

    return result;
  }

  public Review findOne(Key reviewId) {
    PersistenceManager pm = PMF.get().getPersistenceManager();

    try {
      return pm.getObjectById(Review.class, reviewId);
    } catch (JDOObjectNotFoundException e) {
      return null;
    }
  }

  public Review add(Review review) {

    PersistenceManager pm = PMF.get().getPersistenceManager();

    try {
      pm.makePersistent(review);
    } finally {
      pm.close();
    }

    return review;
  }
}
