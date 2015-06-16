package fr.dralagen.groupDiv.services;

import fr.dralagen.groupDiv.bean.NewSessionBean;
import fr.dralagen.groupDiv.bean.NewUeBean;
import fr.dralagen.groupDiv.bean.SessionBean;
import fr.dralagen.groupDiv.model.Session;
import fr.dralagen.groupDiv.model.Ue;
import fr.dralagen.groupDiv.model.UeContent;
import fr.dralagen.groupDiv.model.User;
import fr.dralagen.groupDiv.persistence.SessionRepository;
import fr.dralagen.groupDiv.services.exception.InvalidFormException;

import java.util.*;

/**
 * Created on 6/5/15.
 *
 * @author dralagen
 */
public class SessionServices {

  private static SessionServices service = null;

  private SessionServices() {}

  public static synchronized SessionServices getInstance() {
    if (null == service) {
      service = new SessionServices();
    }
    return service;
  }

  public SessionBean create(NewSessionBean session) throws InvalidFormException {

    checkSession(session);

    Session newSession = new Session();
    newSession.setName(session.getName());
    newSession.setWithGroupDiv(session.getWithGroupDiv());
    newSession.setCreateDate(new Date());
    newSession.setGDtot(0);

    List<Ue> ueList = new ArrayList<>();
    List<User> userList = new ArrayList<>();
    for (NewUeBean ue: session.getUe()) {
      User newUser = new User();
      newUser.setName(ue.getUser());
      userList.add(newUser);

      UeContent newContent = new UeContent();
      newContent.setVersion(0);
      newContent.setContent("");
      List<UeContent> contents = new ArrayList<>();
      contents.add(newContent);

      Ue newUe = new Ue();
      newUe.setTitle(ue.getTitle());
      newUe.setAuthor(newUser);
      newUe.setContents(contents);

      ueList.add(newUe);
    }
    newSession.setUes(ueList);
    newSession.setUsers(userList);

    newSession = SessionRepository.getInstance().save(newSession);

    // init divergence of all user
    Map<Long, Integer> versionUE = new HashMap<>();
    for (Ue oneUe: newSession.getUes()) {
      versionUE.put(oneUe.getKey().getId(), 0);
    }
    Map<Long, Integer> versionReview = new HashMap<>();
    for (User oneUser: newSession.getUsers()) {
      versionReview.put(oneUser.getKey().getId(), 0);
    }

    for (User oneUser: newSession.getUsers()) {
      oneUser.setVersionUE(versionUE);
      oneUser.setVersionReview(versionReview);
    }

    SessionRepository.getInstance().save(newSession);

    return SessionBean.toBean(newSession);
  }

  public Collection<SessionBean> getAll () {
    Collection<Session> allSession = SessionRepository.getInstance().findAll();

    Collection<SessionBean> allBean = new ArrayList<>();
    for(Session oneSession: allSession) {
      allBean.add(SessionBean.toBean(oneSession));
    }

    return allBean;
  }

  public SessionBean get(long sessionId) {
    return SessionBean.toBean(SessionRepository.getInstance().findOne(sessionId));
  }

  private void checkSession(NewSessionBean session) throws InvalidFormException {
    Map<String, String> errors = new HashMap<>();

    if (session.getName() == null || session.getName().equals("")) {
      errors.put("name", "Name is mandatory for a session");
    }

    if (session.getUe().isEmpty()) {
      errors.put("ue", "At least one UE is mandatory for a session");
    }

    for (NewUeBean ue : session.getUe()) {
      String ueTitle = ue.getTitle();
      if (ueTitle == null || ueTitle.equals("")) {
        errors.put("ueTitle", "UE title is mandatory");
      }

      String userName = ue.getUser();
      if (userName == null || userName.equals("")) {
        errors.put("userName", "User name is mandatory");
      }

    }

    if (!errors.isEmpty()) {
      throw new InvalidFormException(errors);
    }
  }
}
