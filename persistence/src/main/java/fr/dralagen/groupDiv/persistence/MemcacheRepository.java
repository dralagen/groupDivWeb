package fr.dralagen.groupDiv.persistence;

import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import java.util.logging.Level;

/**
 * Created on 7/13/15.
 *
 * @author dralagen
 */
public class MemcacheRepository {

  private static MemcacheRepository instance = null;

  public static MemcacheRepository getInstance() {
    if (instance == null) {
      instance = new MemcacheRepository();
    }
    return instance;
  }

  private MemcacheRepository() {
    instance = this;
  }

  private MemcacheService getService(String namespace) {
    MemcacheService cache = MemcacheServiceFactory.getMemcacheService(namespace);
    cache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.WARNING));
    return cache;
  }

  public void add(String namespace, Object key, Object value) {
    MemcacheService cache = getService(namespace);
    try {
      cache.put(key, value);
    } catch (Exception ignore) {}
  }

  public Object get(String namespace, Object key) {
    try {

      MemcacheService cache = getService(namespace);

      return cache.get(key);

    } catch (Exception ignore) {}


    return null;
  }

  public void clean(String namespace, Object key) {
    getService(namespace).delete(key);
  }
}
