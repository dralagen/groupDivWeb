package fr.dralagen.groupDiv.bean;

import com.google.appengine.api.datastore.Key;
import fr.dralagen.groupDiv.model.Ue;
import fr.dralagen.groupDiv.model.User;

/**
 * Created on 6/10/15.
 *
 * @author dralagen
 */
public class UeBean {

  private Key key;

  private String title;

  private long sessionId;

  private User author;

  private String content;

  private int version;

  public Key getKey () {
    return key;
  }

  public void setKey (Key key) {
    this.key = key;
  }

  public String getTitle () {
    return title;
  }

  public void setTitle (String title) {
    this.title = title;
  }

  public long getSessionId () {
    return sessionId;
  }

  public void setSessionId (long sessionId) {
    this.sessionId = sessionId;
  }

  public User getAuthor () {
    return author;
  }

  public void setAuthor (User author) {
    this.author = author;
  }

  public String getContent () {
    return content;
  }

  public void setContent (String content) {
    this.content = content;
  }

  public int getVersion () {
    return version;
  }

  public void setVersion (int version) {
    this.version = version;
  }

  public static UeBean toBean (Ue one) {

    UeBean bean = new UeBean();

    if (one.getKey() != null) {
      bean.setKey(one.getKey());
    }

    bean.setAuthor(one.getAuthor());
    bean.setSessionId(one.getSessionId());
    bean.setTitle(one.getTitle());

    return bean;
  }
}
