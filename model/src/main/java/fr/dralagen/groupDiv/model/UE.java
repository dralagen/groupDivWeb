package fr.dralagen.groupDiv.model;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.*;
import java.util.List;

/**
 * Created on 6/2/15.
 *
 * @author dralagen
 */
@PersistenceCapable
public class UE {

  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  @PrimaryKey
  private Key key;

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private String title;

  @Persistent(mappedBy = "ue")
  @Element(dependent = "true")
  private List<UEContent> contents;

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private Session session;

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

  public List<UEContent> getContents () {
    return contents;
  }

  public void setContents (List<UEContent> contents) {
    this.contents = contents;
  }

  public Session getSession () {
    return session;
  }

  public void setSession (Session session) {
    this.session = session;
  }

  public User getAuthor () {
    return author;
  }

  public void setAuthor (User author) {
    this.author = author;
  }
}
