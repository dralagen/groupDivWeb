package fr.dralagen.groupDiv.bean;

import fr.dralagen.groupDiv.model.Action;

/**
 * Created on 6/16/15.
 *
 * @author dralagen
 */
public class LogBean {

  private long id;

  private long userId;

  private Action action;

  private String message;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public Action getAction() {
    return action;
  }

  public void setAction(Action action) {
    this.action = action;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
