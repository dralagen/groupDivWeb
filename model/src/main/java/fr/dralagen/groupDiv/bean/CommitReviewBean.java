package fr.dralagen.groupDiv.bean;

/**
 * Created on 6/16/15.
 *
 * @author dralagen
 */
public class CommitReviewBean {
  private long ueId;

  private long authorId;

  private String content;

  public long getUeId() {
    return ueId;
  }

  public void setUeId(long ueId) {
    this.ueId = ueId;
  }

  public long getAuthorId () {
    return authorId;
  }

  public void setAuthorId (long authorId) {
    this.authorId = authorId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
