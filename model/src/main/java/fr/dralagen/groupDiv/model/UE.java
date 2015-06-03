package fr.dralagen.groupDiv.model;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.*;
import java.util.List;

/**
 * Created on 6/2/15.
 *
 * @author dralagen
 */
@PersistenceCapable
public class UE {

  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  @PrimaryKey
  private Key key;

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private String title;

  @Persistent(mappedBy = "ue")
  @Element(dependent = "true")
  private List<UEContent> contents;

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
  private Session session;

}
