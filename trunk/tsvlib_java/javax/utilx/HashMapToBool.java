package javax.utilx;
import java.util.HashMap;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Oct 27, 2004, Time: 11:32:45 AM
 */
public class HashMapToBool extends HashMap {
  public boolean getBool(Object key) {
    Boolean res = (Boolean) get(key);
    if (res == null)
      return false;
    return res.booleanValue();
  }
  public boolean put(Object key, boolean b) {
    put(key, new Boolean(b));
    return b;
  }
}
