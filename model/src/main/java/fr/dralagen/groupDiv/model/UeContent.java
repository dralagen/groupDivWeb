package fr.dralagen.groupDiv.model;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.*;

/**
 * Created on 6/2/15.
 *
 * @author dralagen
 */
@PersistenceCapable
public class UeContent {

  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  @PrimaryKey
  private Key key;

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private String content;

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private int version;

  @Persistent
  private Key ueId;

  public Key getKey () {
    return key;
  }

  public void setKey (Key key) {
    this.key = key;
  }

  public String getContent () {
    return content;
  }

  public void setContent (String content) {
    this.content = content;
  }

  public int getVersion () {
    return version;
  }

  public void setVersion (int version) {
    this.version = version;
  }

  public Key getUeId () {
    return ueId;
  }

  public void setUeId (Key ueId) {
    this.ueId = ueId;
  }
}
