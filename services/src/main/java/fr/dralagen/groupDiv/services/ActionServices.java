package fr.dralagen.groupDiv.services;

import com.google.appengine.api.datastore.Key;
import fr.dralagen.groupDiv.bean.*;
import fr.dralagen.groupDiv.model.*;
import fr.dralagen.groupDiv.persistence.*;
import fr.dralagen.groupDiv.services.exception.InvalidFormException;
import fr.dralagen.groupDiv.services.exception.ObjectNotFoundException;

import javax.jdo.JDOObjectNotFoundException;
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

  public LogBean commitUe(long sessionId, CommitUeBean ue) throws InvalidFormException, IllegalAccessException, ObjectNotFoundException {

    checkCommitUe(ue);

    Ue persistedUe;
    try {
      persistedUe = UeRepository.getInstance().findOne(sessionId, ue.getUeId());
    } catch (JDOObjectNotFoundException e) {
      throw new ObjectNotFoundException(Ue.class);
    }

    if (persistedUe.getAuthor().getKey().getId() != ue.getAuthorId()) {
      throw new IllegalAccessException("ERROR_USER_UE");
    }

    UeContent content = new UeContent();
    content.setContent(ue.getContent());
    content.setVersion(persistedUe.getContents().size());

    persistedUe.getContents().add(content);

    GroupDivUser user;
    try {
      user = UserRepository.getInstance().findOne(sessionId, ue.getAuthorId());
    } catch (JDOObjectNotFoundException e) {
      throw new ObjectNotFoundException(GroupDivUser.class);
    }
    user.getVersionUE().put(ue.getUeId(), content.getVersion());

    Session session;
    try {
      session = SessionRepository.getInstance().findOne(sessionId);
    } catch (JDOObjectNotFoundException e) {
      throw new ObjectNotFoundException(Session.class);
    }

    updateDivergenceOnCommit(user, session);

    LogAction action = new LogAction();
    action.setAction(Action.COMMIT);
    action.setAuthor(user.getKey().getId());
    action.setSession(session);
    action.setTime(new Date());
    action.setResult("Commit UE on " + ue.getUeId() + " at version " + content.getVersion() + ":" + content.getKey().getId());

    LogRepository.getInstance().saveAction(action);

    return LogBean.toBean(action);
  }

  private void checkCommitUe(CommitUeBean ue) throws InvalidFormException {
    Map<String, String> errors = new HashMap<>();

    if (ue.getUeId() == null) {
      errors.put("ueId", "ERROR_MAND_UEID");
    }

    if (ue.getAuthorId() == null) {
      errors.put("userId", "ERROR_MAND_USERID");
    }

    if (ue.getContent() == null || ue.getContent().equals("")) {
      errors.put("content", "ERROR_MAND_CONTENT");
    }

    if (!errors.isEmpty()) {
      throw new InvalidFormException(errors);
    }
  }

  public LogBean commitReview(long sessionId, CommitReviewBean review) throws InvalidFormException, IllegalAccessException, ObjectNotFoundException {

    checkCommitReview(review);

    Ue ue;
    try {
      ue = UeRepository.getInstance().findOne(sessionId, review.getUeId());
    } catch (JDOObjectNotFoundException e) {
      throw new ObjectNotFoundException(Ue.class);
    }

    if (ue.getAuthor().getKey().getId() == review.getAuthorId()) {
      throw new IllegalAccessException("ERROR_REVIEW_UE");
    }

    GroupDivUser user;
    try {
      user = UserRepository.getInstance().findOne(sessionId, review.getAuthorId());
    } catch (JDOObjectNotFoundException e) {
      throw new ObjectNotFoundException(GroupDivUser.class);
    }

    Review rev = new Review();
    rev.setAuthor(user.getKey());
    rev.setUe(ue.getKey());
    rev.setContent(review.getContent());
    rev.setTime(new Date());

    ReviewRepository.getInstance().add(rev);

    user.getReview().add(rev.getKey());

    Session session;
    try {
      session = SessionRepository.getInstance().findOne(sessionId);
    } catch (JDOObjectNotFoundException e) {
      throw new ObjectNotFoundException(Session.class);
    }

    updateDivergenceOnCommit(user, session);

    LogAction action = new LogAction();
    action.setAction(Action.COMMIT);
    action.setAuthor(user.getKey().getId());
    action.setSession(session);
    action.setTime(new Date());
    action.setResult("Commit review on " + ue.getKey().getId() + " at version :" + rev.getKey().getId());

    LogRepository.getInstance().saveAction(action);

    return LogBean.toBean(action);
  }

  private void checkCommitReview (CommitReviewBean review) throws InvalidFormException {
    Map<String, String> errors = new HashMap<>();

    if (review.getContent() == null || review.getContent().equals("")) {
      errors.put("content", "ERROR_MAND_CONTENT");
    }

    if (review.getUeId() == null) {
      errors.put("ueId", "ERROR_MAND_UEID");
    }

    if (review.getAuthorId() == null) {
      errors.put("userId", "ERROR_MAND_USERID");
    }

    if (!errors.isEmpty()) {
      throw new InvalidFormException(errors);
    }
  }

  public PullBean pull(long sessionId, long fromUserId, long toUserId) throws ObjectNotFoundException {

    boolean log = true;
    boolean uselessPull = true;

    GroupDivUser toUser;
    GroupDivUser fromUser;
    try {
      fromUser = UserRepository.getInstance().findOne(sessionId, fromUserId);
    } catch (JDOObjectNotFoundException e) {
      throw new ObjectNotFoundException(GroupDivUser.class);
    }

    Session session;
    try {
      session = SessionRepository.getInstance().findOne(sessionId);
    } catch (JDOObjectNotFoundException e) {
      throw new ObjectNotFoundException(Session.class);
    }

    LogAction logAction = new LogAction();
    logAction.setAuthor(fromUserId);
    logAction.setSession(session);
    logAction.setTime(new Date());

    Set<Key> newReviewId;

    if (fromUserId == toUserId) {

      newReviewId = fromUser.getReview();
      log = false;

    } else {
      try {
        toUser = UserRepository.getInstance().findOne(sessionId, toUserId);
      } catch (JDOObjectNotFoundException e) {
        throw new ObjectNotFoundException(GroupDivUser.class);
      }

      logAction.setResult(fromUser.getName() + " pull on " + toUser.getName());

      int diffDivergence = 0;

      { // update UE version
        Map<Long, Integer> toUserVersion = toUser.getVersionUE();
        for ( Map.Entry<Long, Integer> one : fromUser.getVersionUE().entrySet() ) {
          if ( one.getValue().compareTo(toUserVersion.get(one.getKey())) < 0 ) {
            uselessPull = false;
            diffDivergence += (toUserVersion.get(one.getKey()) - one.getValue());
            one.setValue(toUserVersion.get(one.getKey()));
          }
        }
      }

      // find new review
      newReviewId = new HashSet<>(toUser.getReview());
      newReviewId.removeAll(fromUser.getReview());

      diffDivergence += newReviewId.size();

      // merge Review
      fromUser.getReview().addAll(toUser.getReview());

      UserRepository.getInstance().save(fromUser);

      LogDivergence lastDivergence = LogRepository.getInstance().getLastDivergence(session.getKey().getId());

      if (diffDivergence > 0) {

        uselessPull = false;

        Map<Long, Long> newUsersDivergence = new HashMap<>();
        for ( Map.Entry<Long, Long> e : lastDivergence.getUserDivegence().entrySet() ) {
          if ( e.getKey().equals(fromUser.getKey().getId()) ) {
            newUsersDivergence.put(e.getKey(), Math.max(e.getValue() - diffDivergence, 0));
          } else {
            newUsersDivergence.put(e.getKey(), e.getValue());
          }
        }

        LogDivergence divergence = new LogDivergence();
        divergence.setSession(session);
        divergence.setGDtot(Math.max(lastDivergence.getGDtot() - diffDivergence, 0));
        divergence.setTime(new Date());
        divergence.setUserDivegence(newUsersDivergence);

        //Update last Divergence
        LogRepository.getInstance().saveDivergence(divergence);
      }
    }

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

    Set<Review> allReview;
    try {
      allReview = ReviewRepository.getInstance().findAll(newReviewId);
    } catch (JDOObjectNotFoundException e) {
      throw new ObjectNotFoundException(Review.class);
    }
    Set<ReviewBean> reviewList = new HashSet<>();
    for(Review rev:allReview) {
      reviewList.add(ReviewBean.toBean(rev));
    }

    PullBean result = new PullBean();
    result.setUe(ueList);
    result.setReview(reviewList);

    if (log) {
      logAction.setAction((uselessPull)?Action.USELESS_PULL:Action.PULL);
      LogRepository.getInstance().saveAction(logAction);
    }

    return result;
  }

  private void updateDivergenceOnCommit (GroupDivUser user, Session session) {
    LogDivergence lastDivergence = LogRepository.getInstance().getLastDivergence(session.getKey().getId());
    Map<Long, Long> newUsersDivergence = new HashMap<>();
    for ( Map.Entry<Long, Long> e: lastDivergence.getUserDivegence().entrySet()) {

      Long value = (!e.getKey().equals(user.getKey().getId())) ? e.getValue() + 1 : e.getValue();

      newUsersDivergence.put(e.getKey(), value);
    }

    LogDivergence divergence = new LogDivergence();
    divergence.setSession(session);
    divergence.setGDtot(lastDivergence.getGDtot() + session.getUsers().size() - 1);
    divergence.setTime(new Date());
    divergence.setUserDivegence(newUsersDivergence);

    //Update last Divergence
    LogRepository.getInstance().saveDivergence(divergence);
  }
}
