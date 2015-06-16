package fr.dralagen.groupDiv.persistence;

import fr.dralagen.groupDiv.model.LogAction;
import fr.dralagen.groupDiv.model.LogDivergence;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.Collection;
import java.util.List;

/**
 * Created on 6/16/15.
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

  public Collection<LogAction> findLogAction(long sessionId) {

    PersistenceManager pm = PMF.get().getPersistenceManager();

    List<LogAction> result;
    Query q = pm.newQuery(LogAction.class);
    q.setFilter("sessionId == paramSessionId");
    q.declareParameters("long paramSessionId");

    try {
      result = (List<LogAction>) q.execute(sessionId);
    } finally {
      q.closeAll();
    }

    return result;
  }

  public Collection<LogDivergence> findLogDivergence(long sessionId) {

    PersistenceManager pm = PMF.get().getPersistenceManager();

    List<LogDivergence> result;
    Query q = pm.newQuery(LogDivergence.class);
    q.setFilter("sessionId == paramSessionId");
    q.declareParameters("long paramSessionId");

    try {
      result = (List<LogDivergence>) q.execute(sessionId);
    } finally {
      q.closeAll();
    }

    return result;
  }

  public LogAction addLog(LogAction log) {
    PersistenceManager pm = PMF.get().getPersistenceManager();

    try {
      pm.makePersistent(log);
    } finally {
      pm.close();
    }

    return log;
  }

  public LogDivergence addLog(LogDivergence log) {
    PersistenceManager pm = PMF.get().getPersistenceManager();

    try {
      pm.makePersistent(log);
    } finally {
      pm.close();
    }

    return log;
  }
}
