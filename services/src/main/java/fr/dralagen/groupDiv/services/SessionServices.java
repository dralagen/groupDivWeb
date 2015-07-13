package fr.dralagen.groupDiv.services;

import fr.dralagen.groupDiv.bean.*;
import fr.dralagen.groupDiv.model.*;
import fr.dralagen.groupDiv.persistence.LogRepository;
import fr.dralagen.groupDiv.persistence.SessionRepository;
import fr.dralagen.groupDiv.persistence.UeRepository;
import fr.dralagen.groupDiv.services.exception.InvalidFormException;
import fr.dralagen.groupDiv.services.exception.ObjectNotFoundException;

import javax.jdo.JDOObjectNotFoundException;
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
    List<GroupDivUser> userList = new ArrayList<>();
    for (NewUeBean ue : session.getUe()) {
      GroupDivUser newUser = new GroupDivUser();
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

    Map<Long, Long> usersDivergence = new HashMap<>();

    for (GroupDivUser oneUser: newSession.getUsers()) {
      oneUser.setVersionUE(versionUE);
      usersDivergence.put(oneUser.getKey().getId(), 0l);
    }

    LogDivergence initDivergence = new LogDivergence();
    initDivergence.setGDtot(0);
    initDivergence.setUserDivegence(usersDivergence);
    initDivergence.setTime(new Date());
    initDivergence.setSession(newSession);

    LogRepository.getInstance().saveDivergence(initDivergence);

    return SessionBean.toBean(newSession);
  }

  public Collection<SessionBean> getAll() throws ObjectNotFoundException {
    Collection<Session> allSession;
    try {
      allSession = SessionRepository.getInstance().findAll();
    } catch (JDOObjectNotFoundException e) {
      throw new ObjectNotFoundException(Session.class);
    }

    Collection<SessionBean> allBean = new ArrayList<>();
    for(Session oneSession: allSession) {
      allBean.add(SessionBean.toBean(oneSession));
    }

    return allBean;
  }

  public SessionBean get(long sessionId) throws ObjectNotFoundException {
    SessionBean sessionBean;
    try {
      sessionBean = SessionBean.toBean(SessionRepository.getInstance().findOne(sessionId));
    } catch (JDOObjectNotFoundException e) {
      throw new ObjectNotFoundException(Session.class);
    }
    return sessionBean;
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

        GroupDivUser newUsr = new GroupDivUser();
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

  public SessionBean updateSessionName(Long sessionId, NewSessionBean session) throws ObjectNotFoundException {

    Session persistedSession;
    try {
      persistedSession = SessionRepository.getInstance().findOne(sessionId);
    } catch (JDOObjectNotFoundException e) {
      throw new ObjectNotFoundException(Session.class);
    }

    persistedSession.setName(session.getName());
    persistedSession.setWithGroupDiv(session.getWithGroupDiv());

    return SessionBean.toBean(persistedSession);

  }

  public UeInfoBean updateUe(Long sessionId, Long ueId, NewUeBean ue) throws InvalidFormException, ObjectNotFoundException {

    Session persistedSession;
    try {
      persistedSession = SessionRepository.getInstance().findOne(sessionId);
    } catch (JDOObjectNotFoundException e) {
      throw new ObjectNotFoundException(Session.class);
    }

    Ue persistedUe;
    try {
      persistedUe = UeRepository.getInstance().findOne(sessionId, ueId);
    } catch (JDOObjectNotFoundException e) {
      throw new ObjectNotFoundException(Ue.class);
    }

    GroupDivUser persistedUser = persistedUe.getAuthor();

    checkUe(persistedSession, ue);

    persistedUe.setTitle(ue.getTitle());
    persistedUser.setName(ue.getUser());

    return UeInfoBean.toBean(persistedUe);
  }

  public SessionBean deleteUe (Long sessionId, Long ueId) throws ObjectNotFoundException {
    Session session;
    try {
      session = SessionRepository.getInstance().findOne(sessionId);
    } catch (JDOObjectNotFoundException e) {
      throw new ObjectNotFoundException(Session.class);
    }

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

  public UeInfoBean addUe(Long sessionId, NewUeBean ue) throws ObjectNotFoundException {

    Session session;
    try {
      session = SessionRepository.getInstance().findOne(sessionId);
    } catch (JDOObjectNotFoundException e) {
      throw new ObjectNotFoundException(Session.class);
    }

    GroupDivUser newUser = new GroupDivUser();
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

  public DivergenceBean getDivergence(long sessionId) throws ObjectNotFoundException {

    try {
      return DivergenceBean.toBean(LogRepository.getInstance().getLastDivergence(sessionId));
    } catch (JDOObjectNotFoundException e) {
      throw new ObjectNotFoundException(Session.class);
    }

  }

  public Collection<DivergenceBean> getAllDivergence(long sessionId) throws ObjectNotFoundException {

    List<LogDivergence> divergences;
    try {
      divergences = LogRepository.getInstance().findAllDivergence(sessionId);
    } catch (JDOObjectNotFoundException e) {
      throw new ObjectNotFoundException(Session.class);
    }

    List<DivergenceBean> divergencesBean = new ArrayList<>();
    for (LogDivergence one : divergences) {
      divergencesBean.add(DivergenceBean.toBean(one));
    }

    return divergencesBean;
  }

  public Collection<LogBean> getAllAction(long sessionId, String action) throws ObjectNotFoundException {

    Collection<LogAction> persistedLog;
    try {
      persistedLog = LogRepository.getInstance().findAllAction(sessionId, action);
    } catch (JDOObjectNotFoundException e) {
      throw new ObjectNotFoundException(LogAction.class);
    }

    List<LogBean> logs = new ArrayList<>();
    for (LogAction one : persistedLog) {
      logs.add(LogBean.toBean(one));
    }

    return logs;
  }
}
