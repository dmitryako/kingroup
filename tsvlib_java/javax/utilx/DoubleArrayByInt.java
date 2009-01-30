package javax.utilx;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Nov 18, 2004, Time: 8:10:39 AM
 */
public class DoubleArrayByInt extends TreeMap {
  final public void add(int key, double added) {
    Integer keyObj = new Integer(key);
    LinkedList list = (LinkedList) get(keyObj);
    if (list == null) {
      list = new LinkedList();
      put(keyObj, list);
    }
    list.add(new Double(added));
  }
  final public double[] getArray(int key) {
    Integer keyObj = new Integer(key);
    LinkedList list = (LinkedList) get(keyObj);
    if (list == null || list.size() == 0)
      return null;
    double[] res = new double[list.size()];
    int i = 0;
    for (Iterator it = list.iterator(); it.hasNext();) {
      Double v = (Double) it.next();
      res[i++] = v.doubleValue();
    }
    return res;
  }
  public java.util.List getList(int key) {
    return (java.util.List) get(new Integer(key));
  }
}