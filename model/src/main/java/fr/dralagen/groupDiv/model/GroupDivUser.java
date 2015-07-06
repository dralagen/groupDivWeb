package fr.dralagen.groupDiv.model;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.*;
import java.util.Map;
import java.util.Set;

/**
 * Created on 6/2/15.
 *
 * @author dralagen
 */
@PersistenceCapable
public class GroupDivUser {

  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  @PrimaryKey
  private Key key;

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private String name;

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private Map<Long, Integer> versionUE; // Key foreign key of ue

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private Set<Key> review; // key foreign key of user

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  @Element(dependent = "true")
  private Session session;

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

  public Map<Long, Integer> getVersionUE () {
    return versionUE;
  }

  public void setVersionUE (Map<Long, Integer> versionUE) {
    this.versionUE = versionUE;
  }

  public Set<Key> getReview() {
    return review;
  }

  public void setReview(Set<Key> review) {
    this.review = review;
  }
}
