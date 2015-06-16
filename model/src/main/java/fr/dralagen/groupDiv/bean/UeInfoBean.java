package fr.dralagen.groupDiv.bean;

import fr.dralagen.groupDiv.model.Ue;

/**
 * Created on 6/16/15.
 *
 * @author dralagen
 */
public class UeInfoBean {

  private long id;

  private String title;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public static UeInfoBean toBean(Ue one) {
    UeInfoBean bean = new UeInfoBean();

    if (one.getKey() != null) {
      bean.setId(one.getKey().getId());
    }

    bean.setTitle(one.getTitle());

    return bean;
  }
}
