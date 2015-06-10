package fr.dralagen.groupDiv.bean;

import com.google.appengine.api.datastore.Key;
import fr.dralagen.groupDiv.model.Ue;

import java.util.Date;
import java.util.List;

/**
 * Created on 6/2/15.
 *
 * @author dralagen
 */
public class SessionBean {

  private Key key;

  private String name;

  private Date beginDate;

  private Date lastLog;

  private int GDtot;

  private Boolean withGroupDiv;

  private List<Ue> ues;

  public Key getKey () {
    return key;
  }

  public void setKey (Key key) {
    this.key = key;
  }

  public String getName () {
    return name;
  }

  public void setName (String name) {
    this.name = name;
  }

  public Date getBeginDate () {
    return beginDate;
  }

  public void setBeginDate (Date beginDate) {
    this.beginDate = beginDate;
  }

  public Date getLastLog () {
    return lastLog;
  }

  public void setLastLog (Date lastLog) {
    this.lastLog = lastLog;
  }

  public int getGDtot () {
    return GDtot;
  }

  public void setGDtot (int GDtot) {
    this.GDtot = GDtot;
  }

  public Boolean getWithGroupDiv () {
    return withGroupDiv;
  }

  public void setWithGroupDiv (Boolean withGroupDiv) {
    this.withGroupDiv = withGroupDiv;
  }

  public List<Ue> getUes () {
    return ues;
  }

  public void setUes (List<Ue> ues) {
    this.ues = ues;
  }
}
