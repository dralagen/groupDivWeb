package fr.dralagen.groupDiv.bean;

/**
 * Created on 6/16/15.
 *
 * @author dralagen
 */
public class UeBean {

  private long id;

  private String title;

  private String content;

  private int version;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }
}
