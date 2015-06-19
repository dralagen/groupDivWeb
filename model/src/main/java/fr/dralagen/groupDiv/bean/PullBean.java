package fr.dralagen.groupDiv.bean;

import java.util.List;
import java.util.Set;

/**
 * Created on 6/16/15.
 *
 * @author dralagen
 */
public class PullBean {

  private List<UeBean> ue;

  private Set<ReviewBean> review;

  public List<UeBean> getUe() {
    return ue;
  }

  public void setUe(List<UeBean> ue) {
    this.ue = ue;
  }

  public Set<ReviewBean> getReview() {
    return review;
  }

  public void setReview(Set<ReviewBean> review) {
    this.review = review;
  }
}
