package fr.dralagen.groupDiv.model;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created on 6/2/15.
 *
 * @author dralagen
 */
@PersistenceCapable
public class Review implements Serializable {

  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  @PrimaryKey
  private Key key;

  @Persistent
  private Key author;

  @Persistent
  private Key ue;

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

  public Key getAuthor() {
    return author;
  }

  public void setAuthor (Key author) {
    this.author = author;
  }

  public Key getUe() {
    return ue;
  }

  public void setUe (Key ue) {
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
