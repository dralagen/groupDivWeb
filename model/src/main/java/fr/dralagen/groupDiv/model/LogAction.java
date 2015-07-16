package fr.dralagen.groupDiv.model;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.datanucleus.annotations.Unowned;

import javax.jdo.annotations.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created on 6/4/15.
 *
 * @author dralagen
 */
@PersistenceCapable
public class LogAction implements Serializable{

  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  @PrimaryKey
  private Key key;

  @Persistent
  private Long author;

  @Persistent
  private Action action;

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private String result;

  @Persistent
  private Date time;

  @Persistent
  @Unowned
  private Session session;

  public Key getKey() {
    return key;
  }

  public void setKey(Key key) {
    this.key = key;
  }

  public Long getAuthor () {
    return author;
  }

  public void setAuthor(Long author) {
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

  public Date getTime () {
    return time;
  }

  public void setTime (Date time) {
    this.time = time;
  }

  public Session getSession () {
    return session;
  }

  public void setSession (Session session) {
    this.session = session;
  }
}
