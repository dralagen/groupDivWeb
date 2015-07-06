package fr.dralagen.groupDiv.persistence;

import com.google.appengine.api.datastore.Key;
import fr.dralagen.groupDiv.model.Review;
import fr.dralagen.groupDiv.model.GroupDivUser;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created on 6/5/15.
 *
 * @author dralagen
 */
public class ReviewRepository {

  private static ReviewRepository repo = null;

  private ReviewRepository() {

  }

  public static ReviewRepository getInstance() {
    if (repo == null) {
      repo = new ReviewRepository();
    }

    return repo;
  }

  public Collection<Review> findAllByUser(GroupDivUser user) {
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

  public Collection<Review> findAllByUe(long ueId) {
    PersistenceManager pm = PMF.get().getPersistenceManager();

    List<Review> result;
    Query q = pm.newQuery(Review.class);
    q.setFilter("ue == paramUe");
    q.declareParameters("long paramUe");

    try {
      result = (List<Review>) q.execute(ueId);
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

    Review persistedReview;
    PersistenceManager pm = PMF.get().getPersistenceManager();

    try {
      persistedReview = pm.makePersistent(review);
    } finally {
      pm.close();
    }

    return persistedReview;
  }

  public Set<Review> findAll(Set<Key> ids) {

    PersistenceManager pm = PMF.get().getPersistenceManager();

    HashSet<Review> allReview = new HashSet<>();
    for (Key oneId:ids) {
      allReview.add(pm.getObjectById(Review.class, oneId));
    }

    return allReview;
  }
}
