package fr.dralagen.groupDiv.bean;

import fr.dralagen.groupDiv.model.Session;
import fr.dralagen.groupDiv.model.Ue;

import java.util.ArrayList;
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

  public static SessionBean toBean(Session one) {
    SessionBean bean = new SessionBean();

    if (one.getKey() != null) {
      bean.setId(one.getKey().getId());
    }

    bean.setCreateDate(one.getCreateDate());
    bean.setName(one.getName());
    bean.setWithGroupDiv(one.getWithGroupDiv());

    List<UeInfoBean> ue = new ArrayList<>();
    List<UserBean> user = new ArrayList<>();
    for (Ue u: one.getUes()) {
      ue.add(UeInfoBean.toBean(u));
      user.add(UserBean.toBean(u.getAuthor()));
    }
    bean.setUe(ue);
    bean.setUser(user);

    return bean;
  }
}
