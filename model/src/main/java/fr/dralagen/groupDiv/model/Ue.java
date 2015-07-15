package fr.dralagen.groupDiv.model;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.datanucleus.annotations.Unowned;

import javax.jdo.annotations.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created on 6/2/15.
 *
 * @author dralagen
 */
@PersistenceCapable
public class Ue implements Serializable {

  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  @PrimaryKey
  private Key key;

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private String title;

  @Persistent(mappedBy = "ue")
  @Element(dependent = "true")
  @Order(extensions = @Extension(vendorName="datanucleus",key="list-ordering", value="version asc"))
  private List<UeContent> contents;

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  @Element(dependent = "true")
  private Session session;

  @Persistent
  @Unowned
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private GroupDivUser author;


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

  public List<UeContent> getContents () {
    return contents;
  }

  public void setContents (List<UeContent> contents) {
    this.contents = contents;
  }

  public GroupDivUser getAuthor () {
    return author;
  }

  public void setAuthor (GroupDivUser author) {
    this.author = author;
  }
}
