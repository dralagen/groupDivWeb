package fr.dralagen.groupDiv.bean;

import fr.dralagen.groupDiv.model.LogDivergence;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 6/16/15.
 *
 * @author dralagen
 */
public class DivergenceBean {

  private int globalDivergence;

  private Map<Long, Long> userDivergence; // userId -> value of divergence

  private Date time;


  public int getGlobalDivergence() {
    return globalDivergence;
  }

  public void setGlobalDivergence(int globalDivergence) {
    this.globalDivergence = globalDivergence;
  }

  public Map<Long, Long> getUserDivergence() {
    return userDivergence;
  }

  public void setUserDivergence(Map<Long, Long> userDivergence) {
    this.userDivergence = userDivergence;
  }

  public Date getTime(){
    return time;
  }

  public void setTime(Date time){
    this.time = time;
  }

  public static DivergenceBean toBean (LogDivergence divergence) {

    DivergenceBean bean = new DivergenceBean();

    Map<Long, Long> userDivergence = new HashMap<>();
    for( Map.Entry<Long, Long> e: divergence.getUserDivegence().entrySet()) {
      userDivergence.put(e.getKey(), e.getValue());
    }
    bean.setUserDivergence(userDivergence);
    bean.setGlobalDivergence(divergence.getGDtot());
    bean.setTime(divergence.getTime());

    return bean;
  }
}
