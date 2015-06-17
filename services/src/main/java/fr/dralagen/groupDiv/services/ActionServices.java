package fr.dralagen.groupDiv.services;

import fr.dralagen.groupDiv.bean.*;
import fr.dralagen.groupDiv.model.*;
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

  public LogBean commitUe(long sessionId, CommitUeBean ue) throws InvalidFormException {

    checkCommitUe(ue);

    Ue persistedUe = UeRepository.getInstance().findOne(sessionId, ue.getUeId());

    UeContent content = new UeContent();
    content.setContent(ue.getContent());
    content.setVersion(persistedUe.getContents().size());

    persistedUe.getContents().add(content);

    User user = UserRepository.getInstance().findOne(sessionId, ue.getUserId());
    user.getVersionUE().put(ue.getUeId(), content.getVersion());

    LogBean result = new LogBean();
    result.setAction(Action.COMMIT);
    result.setMessage("Commit UE on " + ue.getUeId() + " at version " + content.getVersion() + ":"+content.getKey().getId());
    result.setUserId(ue.getUserId());
    result.setDate(new Date());
    return result;
  }

  private void checkCommitUe(CommitUeBean ue) throws InvalidFormException {
    Map<String, String> errors = new HashMap<>();

    if (ue.getUeId() == null) {
      errors.put("ueId", "UE id is mandatory for an ue");
    }

    if (ue.getUserId() == null) {
      errors.put("userId", "User id is mandatory for an ue");
    }

    if (ue.getContent() == null || ue.getContent().equals("")) {
      errors.put("content", "Content is mandatory for an ue");
    }

    if (!errors.isEmpty()) {
      throw new InvalidFormException(errors);
    }
  }

  public PullBean pull(long sessionId, long fromUserId, long toUserId) {
    User fromUser = UserRepository.getInstance().findOne(sessionId, fromUserId);
    User toUser = UserRepository.getInstance().findOne(sessionId, toUserId);

    {
      Map<Long, Integer> toUserVersion = toUser.getVersionUE();
      for (Map.Entry<Long, Integer> one : fromUser.getVersionUE().entrySet()) {
        if (one.getValue().compareTo(toUserVersion.get(one.getKey())) < 0) {
          one.setValue(toUserVersion.get(one.getKey()));
        }


      }
    }

    {
      Map<Long,Integer> toUserVersion = toUser.getVersionReview();
      for (Map.Entry<Long, Integer> one : fromUser.getVersionReview().entrySet()) {
        if (one.getValue().compareTo(toUserVersion.get(one.getKey())) < 0) {
          one.setValue(toUserVersion.get(one.getKey()));
        }
      }
    }

    UserRepository.getInstance().save(fromUser);

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

    List<ReviewBean> reviewList = new ArrayList<>();

    PullBean result = new PullBean();
    result.setUe(ueList);
    result.setReview(reviewList);

    return result;
  }
}
