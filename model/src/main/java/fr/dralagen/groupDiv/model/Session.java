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
  private Date createDate;

  @Persistent(mappedBy = "session")
  @Element(dependent = "true")
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  @Order(extensions = @Extension(vendorName="datanucleus",key="list-ordering", value="time asc"))
  private List<LogDivergence> divergences;

  @Persistent(mappedBy = "session")
  @Element(dependent = "true")
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  @Order(extensions = @Extension(vendorName="datanucleus",key="list-ordering", value="time asc"))
  private List<LogAction> actions;

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private int GDtot;

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private Boolean withGroupDiv;

  @Persistent(mappedBy = "session")
  @Element(dependent = "true")
  private List<Ue> ues;

  @Persistent(mappedBy = "session")
  @Element(dependent = "true")
  private List<User> users;

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

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
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

  public List<Ue> getUes () {
    return ues;
  }

  public void setUes (List<Ue> ues) {
    this.ues = ues;
  }

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }
}
