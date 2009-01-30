package javax.utilx.pair;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Nov 5, 2004, Time: 4:10:56 PM
 */
public class SumByInt extends TreeMap {
  final public void sum(int key, int added) {
    Integer keyObj = new Integer(key);
    Object valObj = get(keyObj);
    if (valObj == null) {
      put(keyObj, new Integer(added));
      return;
    }
    if (!(valObj instanceof Number))
      return;
    int old_value = ((Number) valObj).intValue();
    put(keyObj, new Integer(old_value + added));
  }
  final public void sum(int key, double added) {
    Integer keyObj = new Integer(key);
    Object valObj = get(keyObj);
    if (valObj == null) {
      put(keyObj, new Double(added));
      return;
    }
    if (!(valObj instanceof Number))
      return;
    double old_value = ((Number) valObj).doubleValue();
    put(keyObj, new Double(old_value + added));
  }
  final public double getDouble(int key) {
    return getDouble(new Integer(key));
  }
  final public int getInt(int key) {
    return getInt(new Integer(key));
  }
  final public double getDouble(Object key) {
    Object valObj = get(key);
    if (valObj == null) {
      return 0;
    }
    if (!(valObj instanceof Number))
      return 0;
    return ((Number) valObj).doubleValue();
  }
  final public int getInt(Object key) {
    Object valObj = get(key);
    if (valObj == null) {
      return 0;
    }
    if (!(valObj instanceof Number))
      return 0;
    return ((Number) valObj).intValue();
  }
  final public boolean divide(SumByInt by) {
    Set keys = keySet();
    boolean res = true;
    for (Iterator it = keys.iterator(); it.hasNext();) {
      Object key = it.next();
      double top = getDouble(key);
      double bottom = by.getDouble(key);
      if (bottom == 0)
        res = false;
      else {
        put(key, new Double(top / bottom));
      }
    }
    return res;
  }
  final public boolean divide(double byVal) {
    Set keys = keySet();
    boolean res = true;
    for (Iterator it = keys.iterator(); it.hasNext();) {
      Object key = it.next();
      double top = getDouble(key);
      if (byVal == 0)
        res = false;
      else {
        put(key, new Double(top / byVal));
      }
    }
    return res;
  }
  public double[] toArray() {
    double[] arr = new double[size()];
    Set keys = keySet();
    int idx = 0;
    for (Iterator it = keys.iterator(); it.hasNext();) {
      arr[idx++] = getDouble(it.next());
    }
    return arr;
  }
}
