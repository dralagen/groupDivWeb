package fr.dralagen.groupDiv.model;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.*;
import java.util.Date;
import java.util.Map;

/**
 * Created on 6/4/15.
 *
 * @author dralagen
 */
@PersistenceCapable
public class LogDivergence {

  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  @PrimaryKey
  private Key key;

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private int GDtot;

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private Map<User, Integer> userDivegence; // key foreign key of user

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private Date time;

  @Persistent
  private Session session;

  public Key getKey() {
    return key;
  }

  public void setKey(Key key) {
    this.key = key;
  }

  public int getGDtot() {
    return GDtot;
  }

  public void setGDtot(int GDtot) {
    this.GDtot = GDtot;
  }

  public Map<User, Integer> getUserDivegence() {
    return userDivegence;
  }

  public void setUserDivegence(Map<User, Integer> userDivegence) {
    this.userDivegence = userDivegence;
  }

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  public Session getSession() {
    return session;
  }

  public void setSession(Session session) {
    this.session = session;
  }
}
