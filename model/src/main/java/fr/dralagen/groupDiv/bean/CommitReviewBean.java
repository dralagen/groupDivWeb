package fr.dralagen.groupDiv.bean;

/**
 * Created on 6/16/15.
 *
 * @author dralagen
 */
public class CommitReviewBean {
  private long ueId;

  private long userId;

  private String content;

  public long getUeId() {
    return ueId;
  }

  public void setUeId(long ueId) {
    this.ueId = ueId;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
