package fr.dralagen.groupDiv.bean;

/**
 * Created on 6/16/15.
 *
 * @author dralagen
 */
public class CommitUeBean {

  private Long ueId;

  private Long authorId;

  private String content;

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
}
