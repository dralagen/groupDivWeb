package fr.dralagen.groupDiv.bean;

import java.util.Date;
import java.util.List;

/**
 * Created on 6/2/15.
 *
 * @author dralagen
 */
public class SessionBean {

  private long id;

  private String name;

  private Date createDate;

  private Boolean withGroupDiv;

  private List<UeInfoBean> ue;

  private List<UserBean> user;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Boolean getWithGroupDiv() {
    return withGroupDiv;
  }

  public void setWithGroupDiv(Boolean withGroupDiv) {
    this.withGroupDiv = withGroupDiv;
  }

  public List<UeInfoBean> getUe() {
    return ue;
  }

  public void setUe(List<UeInfoBean> ue) {
    this.ue = ue;
  }

  public List<UserBean> getUser() {
    return user;
  }

  public void setUser(List<UserBean> user) {
    this.user = user;
  }
}
