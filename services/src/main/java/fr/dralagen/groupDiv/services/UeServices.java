package fr.dralagen.groupDiv.services;

import fr.dralagen.groupDiv.bean.UeBean;
import fr.dralagen.groupDiv.model.Ue;
import fr.dralagen.groupDiv.model.UeContent;
import fr.dralagen.groupDiv.model.User;
import fr.dralagen.groupDiv.persistence.UeContentRepository;
import fr.dralagen.groupDiv.persistence.UeRepository;
import fr.dralagen.groupDiv.persistence.UserRepository;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created on 6/10/15.
 *
 * @author dralagen
 */
public class UeServices {
  private static UeServices service = null;

  private UeServices() {}

  public static synchronized UeServices getInstance() {
    if (null == service) {
      service = new UeServices();
    }
    return service;
  }

  public UeBean getUe (long sessionId, long ueId, User user) {

    Integer ueVersion = user.getVersionUE().get(ueId);
    if (ueVersion == null) {
      User persistedUser = UserRepository.getInstance().findOne(user.getKey());
      ueVersion = persistedUser.getVersionUE().get(ueId);
    }

    UeBean result = UeBean.toBean(UeRepository.getInstance().findOne(ueId));

    UeContent content = UeContentRepository.getInstance().findOne(ueId, ueVersion);

    result.setContent(content.getContent());
    result.setVersion(content.getVersion());

    return result;

  }

  public Collection<UeBean> getAllUe(long sessionId) {

    Collection<UeBean> result = new ArrayList<>();
    Collection<Ue> listUe = UeRepository.getInstance().findAll(sessionId);

    for (Ue one: listUe) {
      result.add(UeBean.toBean(one));
    }

    return result;

  }
}
