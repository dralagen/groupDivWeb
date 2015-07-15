package fr.dralagen.groupDiv.persistence;

import fr.dralagen.groupDiv.model.Action;
import fr.dralagen.groupDiv.model.LogAction;
import fr.dralagen.groupDiv.model.LogDivergence;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 7/6/15.
 *
 * @author dralagen
 */
public class LogRepository {

  private static LogRepository repo = null;

  private LogRepository() {
  }

  public static LogRepository getInstance() {
    if (repo == null) {
      repo = new LogRepository();
    }
    return repo;
  }


  public List<LogAction> findAllAction(Long sessionId, String action) {

    List<LogAction> result = (List<LogAction>) MemcacheRepository.getInstance().get(LogAction.class.getSimpleName(), sessionId);


    if (result == null) {

      PersistenceManager pm = PMF.get().getPersistenceManager();

      Query q = pm.newQuery(LogAction.class);

      String filter = "session == paramSession";
      String parameters = "long paramSession";

      Map<String, Object> values = new HashMap<>();
      values.put("paramSession", sessionId);

      if ( action != null ) {
        filter += " && action == paramAction";
        parameters += ", Action paramAction";
        values.put("paramAction", Action.valueOf(action));
      }

      q.setFilter(filter);
      q.declareParameters(parameters);

      try {
        result = (List<LogAction>) q.executeWithMap(values);
      } finally {
        q.closeAll();
      }
      MemcacheRepository.getInstance().add(LogAction.class.getSimpleName(), sessionId, new ArrayList<>(result));
    }

    return result;
  }

  public LogAction saveAction(LogAction log) {
    PersistenceManager pm = JDOHelper.getPersistenceManager(log.getSession());

    log = pm.makePersistent(log);

    MemcacheRepository.getInstance().clean(LogAction.class.getSimpleName(), log.getSession().getKey().getId());

    return log;
  }

  public List<LogDivergence> findAllDivergence(Long sessionId) {
    List<LogDivergence> result = (List<LogDivergence>) MemcacheRepository.getInstance().get(LogDivergence.class.getSimpleName(), sessionId);

    if (result == null) {
      PersistenceManager pm = PMF.get().getPersistenceManager();

      Query q = pm.newQuery(LogDivergence.class);

      q.setFilter("session == paramSession");
      q.declareParameters("long paramSession");
      q.setOrdering("time desc");

      try {
        result = (List<LogDivergence>) q.execute(sessionId);
      } finally {
        q.closeAll();
      }

      MemcacheRepository.getInstance().add(LogDivergence.class.getSimpleName(), sessionId, new ArrayList<>(result));

    }

    return result;
  }

  public LogDivergence saveDivergence(LogDivergence divergence) {
    PersistenceManager pm = JDOHelper.getPersistenceManager(divergence.getSession());

    divergence = pm.makePersistent(divergence);

    MemcacheRepository.getInstance().clean(LogDivergence.class.getSimpleName(), divergence.getSession().getKey().getId());
    MemcacheRepository.getInstance().clean("LastDivergence", divergence.getSession().getKey().getId());

    MemcacheRepository.getInstance().add("LastDivergence", divergence.getSession().getKey().getId(), divergence);

    return divergence;
  }

  public LogDivergence getLastDivergence(Long sessionId) {
    LogDivergence lastDivergence = (LogDivergence) MemcacheRepository.getInstance().get("LastDivergence", sessionId);

    if (lastDivergence == null) {
      List<LogDivergence> divergences = findAllDivergence(sessionId);
      if ( !divergences.isEmpty() ) {
        lastDivergence = divergences.get(0);
        MemcacheRepository.getInstance().add("LastDivergence", sessionId, lastDivergence);
      }
    }

    return lastDivergence;
  }
}
