package fr.dralagen.groupDiv.model;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * Created on 6/2/15.
 *
 * @author dralagen
 */
public class UEContent {

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
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private UE ue;

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

  public UE getUe () {
    return ue;
  }

  public void setUe (UE ue) {
    this.ue = ue;
  }
}
