package fr.dralagen.groupDiv.bean;

import fr.dralagen.groupDiv.model.Action;
import fr.dralagen.groupDiv.model.LogAction;

import java.util.Date;

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

  private Date date;

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

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public static LogBean toBean (LogAction action) {

    LogBean bean = new LogBean();

    if (action.getKey() != null) {
      bean.setId(action.getKey().getId());
    }

    bean.setAction(action.getAction());
    bean.setUserId(action.getAuthor());
    bean.setDate(action.getTime());
    bean.setMessage(action.getResult());

    return bean;

  }
}
