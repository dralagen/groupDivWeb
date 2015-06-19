package fr.dralagen.groupDiv.bean;

import fr.dralagen.groupDiv.model.Review;

import java.util.Date;

/**
 * Created on 6/16/15.
 *
 * @author dralagen
 */
public class ReviewBean {
  private Long id;

  private Long ueId;

  private Long authorId;

  private String content;

  private Date postDate;

  public Long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Long getUeId() {
    return ueId;
  }

  public void setUeId(Long ueId) {
    this.ueId = ueId;
  }

  public Long getAuthorId () {
    return authorId;
  }

  public void setAuthorId (Long authorId) {
    this.authorId = authorId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getPostDate() {
    return postDate;
  }

  public void setPostDate(Date postDate) {
    this.postDate = postDate;
  }

  public static ReviewBean toBean(Review one) {
    ReviewBean bean = new ReviewBean();

    if (one.getKey() != null) {
      bean.setId(one.getKey().getId());
    }

    bean.setContent(one.getContent());
    bean.setUeId(one.getUe().getId());
    bean.setAuthorId(one.getAuthor().getId());
    bean.setPostDate(one.getTime());

    return bean;
  }
}
