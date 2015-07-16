package fr.dralagen.groupDiv.persistence;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 * Created on 6/5/15.
 *
 * @author dralagen
 */
public final class PMF {
  private static PersistenceManagerFactory pmfInstance = null;

  private PMF() {}

  public static PersistenceManagerFactory get() {
    if (pmfInstance == null) {
      pmfInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional");
    }
    return pmfInstance;
  }
}
