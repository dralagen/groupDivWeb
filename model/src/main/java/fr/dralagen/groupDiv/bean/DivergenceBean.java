package fr.dralagen.groupDiv.bean;

import fr.dralagen.groupDiv.model.LogDivergence;
import fr.dralagen.groupDiv.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;

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
    for( Map.Entry<User, Long> e: divergence.getUserDivegence().entrySet()) {
      userDivergence.put(e.getKey().getKey().getId(), e.getValue());
    }
    bean.setUserDivergence(userDivergence);
    bean.setGlobalDivergence(divergence.getGDtot());
    bean.setTime(divergence.getTime());

    return bean;
  }
}
