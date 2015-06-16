package fr.dralagen.groupDiv.bean;

import fr.dralagen.groupDiv.model.User;

/**
 * Created on 6/16/15.
 *
 * @author dralagen
 */
public class UserBean {

  private long id;

  private String name;

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

  public static UserBean toBean(User one) {
    UserBean bean = new UserBean();
    if (one.getKey() != null) {
      bean.setId(one.getKey().getId());
    }

    bean.setName(one.getName());

    return bean;
  }
}
