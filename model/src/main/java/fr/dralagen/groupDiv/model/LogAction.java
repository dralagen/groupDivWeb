package fr.dralagen.groupDiv.model;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.*;

/**
 * Created on 6/4/15.
 *
 * @author dralagen
 */
@PersistenceCapable
public class LogAction {

  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  @PrimaryKey
  private Key key;

  @Persistent
  private User author;

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private Action action;

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private String result;

  @Persistent
  private long sessionId;

  public Key getKey() {
    return key;
  }

  public void setKey(Key key) {
    this.key = key;
  }

  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public Action getAction() {
    return action;
  }

  public void setAction(Action action) {
    this.action = action;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public long getSessionId () {
    return sessionId;
  }

  public void setSessionId (long sessionId) {
    this.sessionId = sessionId;
  }
}
