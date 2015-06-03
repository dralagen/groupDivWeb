package fr.dralagen.groupDiv.restApi;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import fr.dralagen.groupDiv.model.Session;
import fr.dralagen.groupDiv.model.UE;
import fr.dralagen.groupDiv.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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

  @ApiMethod(name = "session.get", httpMethod = ApiMethod.HttpMethod.GET, path = "session/{sessionId}")
  public Session getSession(@Named("sessionId") String id) {
    Session session = new Session();
    session.setName("Test Session");
    session.setBeginDate(new Date());
    session.setGDtot(0);
    session.setLastLog(null);
    session.setWithGroupDiv(true);

    List<UE> ueList = new ArrayList<UE>();
    for (int i = 0; i <= 5; ++i) {
      User user = new User();
      user.setName("usr" + i);

      UE ue = new UE();
      ue.setTitle("UE " + i);
      ue.setAuthor(user);

      ueList.add(ue);
    }
    session.setUes(ueList);

    return session;
  }
}
