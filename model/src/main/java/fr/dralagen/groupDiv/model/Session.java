package fr.dralagen.groupDiv.model;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created on 6/2/15.
 *
 * @author dralagen
 */
@PersistenceCapable
public class Session implements Serializable{

  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  @PrimaryKey
  private Key key;

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private String name;

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private Date createDate;

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private Boolean withGroupDiv;

  @Persistent(mappedBy = "session")
  @Element(dependent = "true")
  private List<Ue> ues;

  @Persistent(mappedBy = "session")
  @Element(dependent = "true")
  private List<GroupDivUser> users;

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

  public List<GroupDivUser> getUsers() {
    return users;
  }

  public void setUsers(List<GroupDivUser> users) {
    this.users = users;
  }
}
