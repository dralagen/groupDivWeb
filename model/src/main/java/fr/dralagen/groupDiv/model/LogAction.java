package fr.dralagen.groupDiv.model;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * Created on 6/4/15.
 *
 * @author dralagen
 */
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
  private Session session;

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

  public Session getSession() {
    return session;
  }

  public void setSession(Session session) {
    this.session = session;
  }
}
