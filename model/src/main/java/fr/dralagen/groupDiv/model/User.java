package fr.dralagen.groupDiv.model;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.*;
import java.util.Map;

/**
 * Created on 6/2/15.
 *
 * @author dralagen
 */
@PersistenceCapable
public class User {

  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  @PrimaryKey
  private Key key;

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private String name;

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private Map<Key, Integer> versionUE; // Key foreign key of ue

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private Map<Key, Integer> versionReview; // key foreign key of user

  public Key getKey () {
    return key;
  }

  public void setKey (Key key) {
    this.key = key;
  }

  public String getName () {
    return name;
  }

  public void setName (String name) {
    this.name = name;
  }

  public Map<Key, Integer> getVersionUE () {
    return versionUE;
  }

  public void setVersionUE (Map<Key, Integer> versionUE) {
    this.versionUE = versionUE;
  }

  public Map<Key, Integer> getVersionReview () {
    return versionReview;
  }

  public void setVersionReview (Map<Key, Integer> versionReview) {
    this.versionReview = versionReview;
  }
}