package fr.dralagen.groupDiv.services.exception;

import java.util.Map;

/**
 * Created on 6/5/15.
 *
 * @author dralagen
 */
public class InvalidFormException extends Throwable {


  private final Map<String, String> errors;

  public InvalidFormException(Map<String, String> errors) {
    this.errors = errors;
  }

  @Override
  public String toString() {

    String errorsFormat = "{";
    boolean hasError = false;
    for(Map.Entry<String, String> error : errors.entrySet()) {
      if (hasError) {
        errorsFormat += ",";
      }
      errorsFormat += "\"" + error.getKey() + "\":\"" + error.getValue() + "\"";
      hasError = true;
    }
    errorsFormat += "}";

    return errorsFormat;
  }
}
