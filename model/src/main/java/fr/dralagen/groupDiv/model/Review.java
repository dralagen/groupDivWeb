package fr.dralagen.groupDiv.model;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.*;
import java.util.Date;

/**
 * Created on 6/2/15.
 *
 * @author dralagen
 */
@PersistenceCapable
public class Review  {

  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  @PrimaryKey
  private Key key;

  @Persistent
  private User author;

  @Persistent
  private Ue ue;

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private Date time;

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private String content;

  public Key getKey () {
    return key;
  }

  public void setKey (Key key) {
    this.key = key;
  }

  public User getAuthor () {
    return author;
  }

  public void setAuthor (User author) {
    this.author = author;
  }

  public Ue getUe () {
    return ue;
  }

  public void setUe (Ue ue) {
    this.ue = ue;
  }

  public Date getTime () {
    return time;
  }

  public void setTime (Date time) {
    this.time = time;
  }

  public String getContent () {
    return content;
  }

  public void setContent (String content) {
    this.content = content;
  }
}
