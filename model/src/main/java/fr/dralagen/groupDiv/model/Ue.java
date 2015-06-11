package fr.dralagen.groupDiv.model;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.*;

/**
 * Created on 6/2/15.
 *
 * @author dralagen
 */
@PersistenceCapable
public class Ue {

  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  @PrimaryKey
  private Key key;

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private String title;

  @Persistent
  private long sessionId;

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private User author;


  public Key getKey () {
    return key;
  }

  public void setKey (Key key) {
    this.key = key;
  }

  public String getTitle () {
    return title;
  }

  public void setTitle (String title) {
    this.title = title;
  }

  public long getSessionId () {
    return sessionId;
  }

  public void setSessionId (long sessionId) {
    this.sessionId = sessionId;
  }

  public User getAuthor () {
    return author;
  }

  public void setAuthor (User author) {
    this.author = author;
  }
}