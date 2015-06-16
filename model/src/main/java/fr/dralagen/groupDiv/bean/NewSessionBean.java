package fr.dralagen.groupDiv.bean;

import java.util.List;

/**
 * Created on 6/16/15.
 *
 * @author dralagen
 */
public class NewSessionBean {

  private String name;

  private Boolean withGroupDiv;

  private List<NewUeBean> ue;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getWithGroupDiv() {
    return withGroupDiv;
  }

  public void setWithGroupDiv(Boolean withGroupDiv) {
    this.withGroupDiv = withGroupDiv;
  }

  public List<NewUeBean> getUe() {
    return ue;
  }

  public void setUe(List<NewUeBean> ue) {
    this.ue = ue;
  }
}
