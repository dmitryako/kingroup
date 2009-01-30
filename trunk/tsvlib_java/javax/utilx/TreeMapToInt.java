package javax.utilx;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Nov 3, 2004, Time: 1:18:14 PM
 */
public class TreeMapToInt extends TreeMap {
  public void put(Object obj, int i) {
    put(obj, new Integer(i));
  }
  public int getInt(Object key) {
    Integer res = (Integer) get(key);
    if (res == null)
      return 0;
    return res.intValue();
  }
  public void deepClone(TreeMap to) {
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
