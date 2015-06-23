package fr.dralagen.groupDiv.services;

import com.google.appengine.api.datastore.Key;
import fr.dralagen.groupDiv.bean.*;
import fr.dralagen.groupDiv.model.*;
import fr.dralagen.groupDiv.persistence.ReviewRepository;
import fr.dralagen.groupDiv.persistence.SessionRepository;
import fr.dralagen.groupDiv.persistence.UeRepository;
import fr.dralagen.groupDiv.persistence.UserRepository;
import fr.dralagen.groupDiv.services.exception.InvalidFormException;

import java.util.*;

/**
 * Created on 6/17/15.
 *
 * @author dralagen
 */
public class ActionServices {
  private static ActionServices service = null;

  private ActionServices() {}

  public static synchronized ActionServices getInstance() {
    if (null == service) {
      service = new ActionServices();
    }
    return service;
  }

  public LogBean commitUe(long sessionId, CommitUeBean ue) throws InvalidFormException, IllegalAccessException {

    checkCommitUe(ue);

    Ue persistedUe = UeRepository.getInstance().findOne(sessionId, ue.getUeId());

    if (persistedUe.getAuthor().getKey().getId() != ue.getAuthorId()) {
      throw new IllegalAccessException("User must be the UE's author");
    }

    UeContent content = new UeContent();
    content.setContent(ue.getContent());
    content.setVersion(persistedUe.getContents().size());

    persistedUe.getContents().add(content);

    User user = UserRepository.getInstance().findOne(sessionId, ue.getAuthorId());
    user.getVersionUE().put(ue.getUeId(), content.getVersion());

    //TODO dralagen 6/4/15 : Update divergence

    //TODO dralagen 6/4/15 : Log Commit UE
    LogBean result = new LogBean();
    result.setAction(Action.COMMIT);
    result.setMessage("Commit UE on " + ue.getUeId() + " at version " + content.getVersion() + ":"+content.getKey().getId());
    result.setUserId(ue.getAuthorId());
    result.setDate(new Date());
    return result;
  }

  private void checkCommitUe(CommitUeBean ue) throws InvalidFormException {
    Map<String, String> errors = new HashMap<>();

    if (ue.getUeId() == null) {
      errors.put("ueId", "UE id is mandatory for an ue");
    }

    if (ue.getAuthorId() == null) {
      errors.put("userId", "User id is mandatory for an ue");
    }

    if (ue.getContent() == null || ue.getContent().equals("")) {
      errors.put("content", "Content is mandatory for an ue");
    }

    if (!errors.isEmpty()) {
      throw new InvalidFormException(errors);
    }
  }

  public LogBean commitReview(long sessionId, CommitReviewBean review) throws InvalidFormException, IllegalAccessException {

    checkCommitReview(review);

    Ue ue = UeRepository.getInstance().findOne(sessionId, review.getUeId());

    if (ue.getAuthor().getKey().getId() == review.getAuthorId()) {
      throw new IllegalAccessException("User can't write review on his ue");
    }

    User user = UserRepository.getInstance().findOne(sessionId, review.getAuthorId());

    Review rev = new Review();
    rev.setAuthor(user.getKey());
    rev.setUe(ue.getKey());
    rev.setContent(review.getContent());
    rev.setTime(new Date());

    ReviewRepository.getInstance().add(rev);

    user.getReview().add(rev.getKey());

    //TODO dralagen 6/4/15 : Update divergence

    //TODO dralagen 6/4/15 : Log Commit review
    LogBean result = new LogBean();
    result.setAction(Action.COMMIT);
    result.setMessage("Commit Review ");
    result.setUserId(review.getAuthorId());
    result.setDate(new Date());
    return result;
  }

  private void checkCommitReview (CommitReviewBean review) throws InvalidFormException {
    Map<String, String> errors = new HashMap<>();

    if (review.getContent() == null || review.getContent().equals("")) {
      errors.put("content", "Content is mandatory for an ue");
    }

    if (review.getUeId() == null) {
      errors.put("ueId", "UE id is mandatory for an ue");
    }

    if (review.getAuthorId() == null) {
      errors.put("userId", "User id is mandatory for an ue");
    }

    if (!errors.isEmpty()) {
      throw new InvalidFormException(errors);
    }
  }

  public PullBean pull(long sessionId, long fromUserId, long toUserId) {

    User toUser;
    User fromUser = UserRepository.getInstance().findOne(sessionId, fromUserId);


    Set<Key> newReviewId;

    if (fromUserId == toUserId) {

      newReviewId = fromUser.getReview();

    } else {
      toUser = UserRepository.getInstance().findOne(sessionId, toUserId);

      { // update UE version
        Map<Long, Integer> toUserVersion = toUser.getVersionUE();
        for ( Map.Entry<Long, Integer> one : fromUser.getVersionUE().entrySet() ) {
          if ( one.getValue().compareTo(toUserVersion.get(one.getKey())) < 0 ) {
            one.setValue(toUserVersion.get(one.getKey()));
          }
        }
      }

      // find new review
      newReviewId = new HashSet<>(toUser.getReview());
      newReviewId.removeAll(fromUser.getReview());

      // merge Review
      fromUser.getReview().addAll(toUser.getReview());

      UserRepository.getInstance().save(fromUser);

      //TODO dralagen 6/4/15 : Update divergence
    }

    Session session = SessionRepository.getInstance().findOne(sessionId);

    List<UeBean> ueList = new ArrayList<>();
    {
      for (Ue ue : session.getUes()) {

        Integer ueVersion = fromUser.getVersionUE().get(ue.getKey().getId());
        UeBean bean = new UeBean();
        bean.setId(ue.getKey().getId());
        bean.setVersion(ueVersion);
        bean.setTitle(ue.getTitle());
        bean.setContent(ue.getContents().get(ueVersion).getContent());

        ueList.add(bean);
      }
    }

    Set<Review> allReview = ReviewRepository.getInstance().findAll(newReviewId);
    Set<ReviewBean> reviewList = new HashSet<>();
    for(Review rev:allReview) {
      reviewList.add(ReviewBean.toBean(rev));
    }
    PullBean result = new PullBean();
    result.setUe(ueList);
    result.setReview(reviewList);

    //TODO dralagen 6/4/15 : Log Pull

    return result;
  }
}
