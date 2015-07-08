package fr.dralagen.groupDiv.services.exception;

/**
 * Created on 7/8/15.
 *
 * @author dralagen
 */
public class ObjectNotFoundException extends Exception {

  Class object;

  public ObjectNotFoundException(Class object) {
    this.object = object;
  }

  public String getObjectName() {
    return object.getSimpleName();
  }
}
