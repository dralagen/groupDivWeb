package fr.dralagen.groupDiv.restApi;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;

import java.util.Date;


/**
 * Created on 6/3/15.
 *
 * @author dralagen
 */
@Api(
    name = Constants.NAME,
    version = Constants.VERSION,
    clientIds = {Constants.API_ID},
    scopes = {}
)
public class SessionApi {

  public class SessionBean {
    private String name;
    private Date beginDate;

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
  }

  @ApiMethod(name = "session.get", httpMethod = ApiMethod.HttpMethod.GET, path = "session/{sessionId}")
  public SessionBean getSession(@Named("sessionId") String id) {
    SessionBean session = new SessionBean();
    session.setName("Test Session");
    session.setBeginDate(new Date());
//    session.setGDtot(0);
//    session.setLastLog(null);
//    session.setWithGroupDiv(true);

    return session;
  }
}
