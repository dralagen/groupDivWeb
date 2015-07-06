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
    for (NewUeBean ue : session.getUe()) {
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
    for (Ue oneUe : newSession.getUes()) {
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

  public Collection<SessionBean> getAll() {
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

    List<Ue> existingUe = new ArrayList<>();
    for (int i = 0; i < session.getUe().size(); ++i) {

      NewUeBean ue = session.getUe().get(i);

      Map<String, String> ueErrors = new HashMap<>();

      checkUe(ueErrors, ue, existingUe);

      if (ueErrors.isEmpty()) {

        User newUsr = new User();
        newUsr.setName(ue.getUser());
        Ue newUe = new Ue();
        newUe.setTitle(ue.getTitle());
        newUe.setAuthor(newUsr);

        existingUe.add(newUe);

      } else {

        for( Map.Entry<String, String> e: ueErrors.entrySet()) {
          errors.put(e.getKey() + "_" + i, e.getValue());
        }

      }
    }

    if (!errors.isEmpty()) {
      throw new InvalidFormException(errors);
    }
  }

  private boolean checkUe(Map<String, String> errors, NewUeBean ue, List<Ue> existingUe) {
    boolean error = false;

    String ueTitle = ue.getTitle();
    if (ueTitle == null || ueTitle.equals("")) {
      errors.put("ueTitle", "ERROR_MAND_UETITLE");
      error = true;
    }

    String username = ue.getUser();
    if (username == null || username.equals("")) {
      errors.put("userName", "ERROR_MAND_USERNAME");
      error = true;
    }

    if (!error) {
      for ( Ue exist : existingUe ) {
        if ( exist.getTitle().equals(ueTitle) ) {
          errors.put("ueTitle", "ERROR_UE_TITLE_ALREADY_EXIST");
          error = true;
        }

        if ( exist.getAuthor().getName().equals(username) ) {
          errors.put("ueName", "ERROR_UE_USERNAME_ALREADY_EXIST");
          error = true;
        }
      }
    }

    return error;
  }

  private void checkUe(Session session, NewUeBean ue) throws InvalidFormException {
    Map<String, String> errors = new HashMap<>();

    checkUe(errors, ue, session.getUes());

    if (!errors.isEmpty()) {
      throw new InvalidFormException(errors);
    }
  }

  public SessionBean updateSessionName(Long sessionId, NewSessionBean session) {

    Session persistedSession = SessionRepository.getInstance().findOne(sessionId);

    persistedSession.setName(session.getName());
    persistedSession.setWithGroupDiv(session.getWithGroupDiv());

    return SessionBean.toBean(persistedSession);

  }

  public UeInfoBean updateUe(Long sessionId, Long ueId, NewUeBean ue) throws InvalidFormException {

    Session persistedSession = SessionRepository.getInstance().findOne(sessionId);

    Ue persistedUe = UeRepository.getInstance().findOne(sessionId, ueId);

    User persistedUser = persistedUe.getAuthor();

    checkUe(persistedSession, ue);

    persistedUe.setTitle(ue.getTitle());
    persistedUser.setName(ue.getUser());

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

  public UeInfoBean addUe(Long sessionId, NewUeBean ue) {

    Session session = SessionRepository.getInstance().findOne(sessionId);

    User newUser = new User();
    newUser.setName(ue.getUser());
    session.getUsers().add(newUser);

    UeContent newContent = new UeContent();
    newContent.setVersion(0);
    newContent.setContent("");
    List<UeContent> contents = new ArrayList<>();
    contents.add(newContent);

    Ue newUe = new Ue();
    newUe.setAuthor(newUser);
    newUe.setTitle(ue.getTitle());
    newUe.setContents(contents);
    session.getUes().add(newUe);



    return UeInfoBean.toBean(newUe);
  }

  public DivergenceBean getDivergence(long sessionId) {

    Session session = SessionRepository.getInstance().findOne(sessionId);

    return DivergenceBean.toBean(session.getLastDivergence());
  }

  public Collection<DivergenceBean> getAllDivergence(long sessionId) {

    Session session = SessionRepository.getInstance().findOne(sessionId);

    List<DivergenceBean> divergences = new ArrayList<>();
    for (LogDivergence one : session.getDivergences()) {
      divergences.add(DivergenceBean.toBean(one));
    }

    return divergences;
  }
}
