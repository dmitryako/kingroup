package javax.utilx;
import java.util.HashMap;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 19/09/2005, Time: 16:55:06
 */
public class HashMapToIdx {
  private final HashMap map = new HashMap();
  public void put(Object obj, int i) {
    map.put(obj, new Integer(i));
  }
  public int getIdx(Object key) {
    Integer res = (Integer) map.get(key);
    if (res == null)
      return -1;
    return res.intValue();
  }
  public int size() {
    return map.size();
  }
}
