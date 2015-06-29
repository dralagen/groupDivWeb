package fr.dralagen.groupDiv.services;

import fr.dralagen.groupDiv.bean.*;
import fr.dralagen.groupDiv.model.*;
import fr.dralagen.groupDiv.persistence.SessionRepository;
import fr.dralagen.groupDiv.persistence.UeRepository;
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

    Map<User, Long> usersDivergence = new HashMap<>();

    for (User oneUser: newSession.getUsers()) {
      oneUser.setVersionUE(versionUE);
      usersDivergence.put(oneUser, 0l);
    }

    LogDivergence initDivergence = new LogDivergence();
    initDivergence.setGDtot(0);
    initDivergence.setUserDivegence(usersDivergence);
    initDivergence.setTime(new Date());
    initDivergence.setSession(newSession);

    newSession.getDivergences().add(initDivergence);

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
      errors.put("name", "ERROR_MAND_NAME_SESSION");
    }

    if (session.getUe().isEmpty()) {
      errors.put("ue", "ERROR_MAND_UE_SESSION");
    }

    for (NewUeBean ue : session.getUe()) {
      String ueTitle = ue.getTitle();
      if (ueTitle == null || ueTitle.equals("")) {
        errors.put("ueTitle", "ERROR_MAND_UETITLE");
      }

      String userName = ue.getUser();
      if (userName == null || userName.equals("")) {
        errors.put("userName", "ERROR_MAND_USERNAME");
      }

    }

    if (!errors.isEmpty()) {
      throw new InvalidFormException(errors);
    }
  }

  public SessionBean updateSessionName (Long sessionId, NewSessionBean session) {

    Session persistedSession = SessionRepository.getInstance().findOne(sessionId);

    persistedSession.setName(session.getName());
    persistedSession.setWithGroupDiv(session.getWithGroupDiv());

    return SessionBean.toBean(persistedSession);

  }

  public UeInfoBean updateUe (Long sessionId, Long ueId, NewUeBean ue) {

    Ue persistedUe = UeRepository.getInstance().findOne(sessionId, ueId);

    User persistedUser = persistedUe.getAuthor();

    if (ue.getTitle() != null && !"".equals(ue.getTitle())) {
      persistedUe.setTitle(ue.getTitle());
    }

    if (ue.getUser() != null && !"".equals(ue.getUser())) {
      persistedUser.setName(ue.getUser());
    }

    return UeInfoBean.toBean(persistedUe);
  }

  public SessionBean deleteUe (Long sessionId, Long ueId) {
    Session session = SessionRepository.getInstance().findOne(sessionId);

    Ue deleteUe = null;
    for (Ue ue : session.getUes()) {
      if (ue.getKey().getId() == ueId) {
        deleteUe = ue;
        break;
      }
    }

    if (deleteUe != null) {
      session.getUes().remove(deleteUe);
    }

    return SessionBean.toBean(session);
  }
}
