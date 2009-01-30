package javax.utilx;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/09/2005, Time: 11:17:01
 */
public class MapIntToIdx {
  private final HashMap map = new HashMap();
  public void put(int key, int idx) {
    map.put(new Integer(key), new Integer(idx));
  }
  public int getIdx(int key) {
    Integer res = (Integer) map.get(new Integer(key));
    if (res == null)
      return -1;
    return res.intValue();
  }
  public int size() {
    return map.size();
  }
  public int[] getKeySet() {
    Set set = map.keySet();
    int[] res = new int[set.size()];
    int idx = 0;
    for (Iterator it = set.iterator(); it.hasNext();) {
      res[idx++] = ((Integer) it.next()).intValue();
    }
    return res;
  }
  public String toString() {
    String COMMA = ", ";
    String L = "(";
    String R = ")";
    StringBuffer buff = new StringBuffer();
    buff.append("{");
    int[] keys = getKeySet();
    for (int i = 0; i < keys.length; i++) {
      int key = keys[i];
      buff.append(L).append(Integer.toString(key)).append(COMMA);
      buff.append(Integer.toString(getIdx(key))).append(R);
      if (i != keys.length - 1)
        buff.append(COMMA);
    }
    buff.append("}");
    return buff.toString();
  }
}
