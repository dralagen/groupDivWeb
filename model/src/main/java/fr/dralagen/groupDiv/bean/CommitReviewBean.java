package fr.dralagen.groupDiv.bean;

/**
 * Created on 6/16/15.
 *
 * @author dralagen
 */
public class CommitReviewBean {
  private Long ueId;

  private Long userId;

  private String content;

  public Long getUeId() {
    return ueId;
  }

  public void setUeId(Long ueId) {
    this.ueId = ueId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
