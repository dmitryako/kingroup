package kingroup.util;
/* Copyright (C) 2003-2004  Dr. Dmitry Konovalov.
* This code is licensed under the GPL license (see www.gnu.org) for academic,
* not-for-profit use or for use within other Open Source software (see www.opensource.org).
* See www.kingroup.org for more details.
*/
public class FastIdArray {
  private FastId[] v_ = null; // array of ids
  // PUBLIC+
  public FastIdArray() {
    v_ = new FastId[0];
  }
  public FastIdArray(int _size) {
    v_ = new FastId[_size];
  }
  public FastIdArray(String[] ids) {
    if (ids == null || ids.length == 0) {
      v_ = new FastId[0];
      return;
    }
    v_ = new FastId[ids.length];
    for (int i = 0; i < v_.length; i++)
      v_[i] = new FastId(ids[i]);
  }
  // HIGHLY-USED
  final public FastId get(int i) {
    return v_[i];
  }
  final public void add(FastId a) {
    v_ = a.add(v_);
  }
  final public void add(String s) {
    v_ = new FastId(s).add(v_);
  }
  final public int size() {
    return v_.length;
  }
  final public void set(int i, FastId a) {
    v_[i] = a;
  }
  public String toString() {
    StringBuffer res = new StringBuffer();
    if (v_.length == 0)
      return "empty";
    int i = 0;
    for (; i < v_.length - 1; i++) // all but last
      res.append(v_[i].toString()).append(", ");
    res.append(v_[i].toString());     // last is without ","
    return res.toString();
  }
  public String toStringIds() {
    StringBuffer res = new StringBuffer();
    if (v_.length == 0)
      return "empty";
    int i = 0;
    for (; i < v_.length - 1; i++) // all but last
      res.append(v_[i].getId()).append(", ");
    res.append(v_[i].getId());     // last is without ","
    return res.toString();
  }
  public boolean find(FastId id) {
    for (int i = 0; i < v_.length; i++) {
      if (id.equals(v_[i]))
        return true;
    }
    return false;
  }
}


