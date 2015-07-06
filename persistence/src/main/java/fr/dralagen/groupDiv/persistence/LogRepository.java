package fr.dralagen.groupDiv.persistence;

import fr.dralagen.groupDiv.model.Action;
import fr.dralagen.groupDiv.model.LogAction;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.*;

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


  public Collection<LogAction> findAll(Long sessionId, String action) {
    PersistenceManager pm = PMF.get().getPersistenceManager();

    List<LogAction> result;
    Query q = pm.newQuery(LogAction.class);

    String filter = "session == paramSession";
    String parameters = "long paramSession";

    Map<String, Object> values = new HashMap<>();
    values.put("paramSession", sessionId);

    if (action != null) {
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

    return result;
  }

  public LogAction save(LogAction log) {
    PersistenceManager pm = JDOHelper.getPersistenceManager(log.getSession());

    try {
      log = pm.makePersistent(log);
    } finally {
      pm.close();
    }

    return log;
  }
}
