package fr.dralagen.groupDiv.bean;

import java.util.Date;

/**
 * Created on 6/16/15.
 *
 * @author dralagen
 */
public class ReviewBean {
  private long id;

  private long ueId;

  private long authorId;

  private String content;

  private Date postDate;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

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

  public Date getPostDate() {
    return postDate;
  }

  public void setPostDate(Date postDate) {
    this.postDate = postDate;
  }
}
