package fr.dralagen.groupDiv.persistence;

import fr.dralagen.groupDiv.model.LogAction;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.Collection;
import java.util.List;

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


  public Collection<LogAction> findAll(Long sessionId) {
    PersistenceManager pm = PMF.get().getPersistenceManager();

    List<LogAction> result;
    Query q = pm.newQuery(LogAction.class);
    q.setFilter("session == paramSession");
    q.declareParameters("long paramSession");

    try {
      result = (List<LogAction>) q.execute(sessionId);
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
