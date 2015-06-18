package fr.dralagen.groupDiv.bean;

import fr.dralagen.groupDiv.model.Review;

import java.util.List;
import java.util.Set;

/**
 * Created on 6/16/15.
 *
 * @author dralagen
 */
public class PullBean {

  private List<UeBean> ue;

  private Set<Review> review;

  public List<UeBean> getUe() {
    return ue;
  }

  public void setUe(List<UeBean> ue) {
    this.ue = ue;
  }

  public Set<Review> getReview() {
    return review;
  }

  public void setReview(Set<Review> review) {
    this.review = review;
  }
}
