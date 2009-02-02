package kingroup.util;
/* Copyright (C) 2003-2004  Dr. Dmitry Konovalov.
* This code is licensed under the GPL license (see www.gnu.org) for academic,
* not-for-profit use or for use within other Open Source software (see www.opensource.org).
* See www.kingroup.org for more details.
*/
import java.util.TreeMap;
public class FastId {
  public final static int FAST_ID_NONE = -1;
  private static FastIdManager manageId_ = FastIdManager.getInstance();
  private int id_ = FAST_ID_NONE;
  public FastId() {
  }
  public FastId(String from) {
    setId(from);
  }
  public FastId(FastId from) {
    setId(from);
  }
  final public String getId() {
    return manageId_.getStringId(id_);
  }
  final public void setId(FastId from) {
    id_ = from.id_;
  }
  final public void setId(String from) {
    id_ = manageId_.getId(from);
  }
  final public boolean equals(FastId to) {
    return to.id_ == id_;
  }
  final public boolean equals(String to) {
    return id_ == manageId_.getId(to);
  }
  public String toString() {
    return getId();
  }
  final protected void copyFastIdFrom(FastId from) {
    id_ = from.id_;
  }
  public FastId[] add(FastId[] to) {
    FastId[] newArray = null;
    if (to == null)
      newArray = new FastId[1];
    else
      newArray = new FastId[to.length + 1];
    int i = 0;
    for (; i < to.length; i++)
      newArray[i] = to[i];
    newArray[i] = this;
    return newArray;
  }
}
class FastIdManager {
  static private int nextId_ = 0;
  private TreeMap mapStrToInt_ = new TreeMap();
  private TreeMap mapIntToStr_ = new TreeMap();
  public synchronized int getId(String s) {
    if (s == null || s.length() < 1)
      return FastId.FAST_ID_NONE;
    Integer id = (Integer) mapStrToInt_.get(s);
    if (id == null) {
      id = new Integer(nextId_++);
      mapStrToInt_.put(s, id);
      mapIntToStr_.put(id, s);
    }
    return id.intValue();
  }
  public synchronized String getStringId(int i) {
    return (String) mapIntToStr_.get(new Integer(i));
  }
  //Singleton
  private static FastIdManager instance_ = null;
  private FastIdManager() {
  }
  final public static FastIdManager getInstance() {
    if (instance_ == null)
      instance_ = new FastIdManager();
    return instance_;
  }
}
