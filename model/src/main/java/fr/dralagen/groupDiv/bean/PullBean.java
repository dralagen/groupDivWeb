package fr.dralagen.groupDiv.bean;

import java.util.List;

/**
 * Created on 6/16/15.
 *
 * @author dralagen
 */
public class PullBean {

  private List<UeBean> ue;

  private List<ReviewBean> review;

  public List<UeBean> getUe() {
    return ue;
  }

  public void setUe(List<UeBean> ue) {
    this.ue = ue;
  }

  public List<ReviewBean> getReview() {
    return review;
  }

  public void setReview(List<ReviewBean> review) {
    this.review = review;
  }
}
