package fr.dralagen.groupDiv.model;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.*;
import java.util.Date;
import java.util.List;

/**
 * Created on 6/2/15.
 *
 * @author dralagen
 */
@PersistenceCapable
public class Session {

  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  @PrimaryKey
  private Key key;

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private String name;

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private Date beginDate;

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private Date lastLog;

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private int GDtot;

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private Boolean withGroupDiv;

  @Persistent(mappedBy = "session")
  @Element(dependent = "true")
  private List<UE> ues;

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

  public Date getBeginDate () {
    return beginDate;
  }

  public void setBeginDate (Date beginDate) {
    this.beginDate = beginDate;
  }

  public Date getLastLog () {
    return lastLog;
  }

  public void setLastLog (Date lastLog) {
    this.lastLog = lastLog;
  }

  public int getGDtot () {
    return GDtot;
  }

  public void setGDtot (int GDtot) {
    this.GDtot = GDtot;
  }

  public Boolean getWithGroupDiv () {
    return withGroupDiv;
  }

  public void setWithGroupDiv (Boolean withGroupDiv) {
    this.withGroupDiv = withGroupDiv;
  }

  public List<UE> getUes () {
    return ues;
  }

  public void setUes (List<UE> ues) {
    this.ues = ues;
  }
}
