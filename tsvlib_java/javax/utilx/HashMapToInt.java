package javax.utilx;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Oct 27, 2004, Time: 9:40:56 AM
 */
public class HashMapToInt extends HashMap {
  public void put(Object obj, int i) {
    put(obj, new Integer(i));
  }
  public int getInt(Object key) {
    Integer res = (Integer) get(key);
    if (res == null)
      return 0;
    return res.intValue();
  }
  public void deepClone(HashMap to) {
    Set set = keySet();
    for (Iterator it = set.iterator(); it.hasNext();) {
      Object key = it.next();
      int val = ((Integer) get(key)).intValue();
      to.put(key, new Integer(val));
    }
  }
  public void mult(int scale) {
    Set set = keySet();
    for (Iterator it = set.iterator(); it.hasNext();) {
      Object key = it.next();
      int val = ((Integer) get(key)).intValue();
      put(key, new Integer(val * scale));
    }
  }
}

