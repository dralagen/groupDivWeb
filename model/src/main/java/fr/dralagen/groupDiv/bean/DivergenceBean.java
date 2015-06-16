package fr.dralagen.groupDiv.bean;

import java.util.Map;

/**
 * Created on 6/16/15.
 *
 * @author dralagen
 */
public class DivergenceBean {

  private int globalDivergence;

  private Map<Long, Integer> userDivergence; // userId -> value of divergence

  public int getGlobalDivergence() {
    return globalDivergence;
  }

  public void setGlobalDivergence(int globalDivergence) {
    this.globalDivergence = globalDivergence;
  }

  public Map<Long, Integer> getUserDivergence() {
    return userDivergence;
  }

  public void setUserDivergence(Map<Long, Integer> userDivergence) {
    this.userDivergence = userDivergence;
  }
}
