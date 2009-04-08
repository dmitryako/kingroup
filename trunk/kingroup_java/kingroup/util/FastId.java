package kingroup.util;
/* Copyright (C) 2003-2004  Dr. Dmitry Konovalov.
* This code is licensed under the GPL license (see www.gnu.org) for academic,
* not-for-profit use or for use within other Open Source software (see www.opensource.org).
* See www.kingroup.org for more details.
*/
import java.util.TreeMap;
public class FastId {
  public final static int FAST_ID_NONE = -1;
  private static FastIdManager idManager = FastIdManager.getInstance();
  private int id = FAST_ID_NONE;

  public FastId() {
  }
  public FastId(String from) {
    setId(from);
  }
  public FastId(FastId from) {
    setId(from);
  }
  final public String getId() {
    return idManager.getStringId(id);
  }
  final public void setId(FastId from) {
    id = from.id;
  }
  final public void setId(String from) {
    id = idManager.getId(from);
  }
  final public boolean equals(FastId to) {
    return to.id == id;
  }
  final public boolean equals(String to) {
    return id == idManager.getId(to);
  }
  public String toString() {
    return getId();
  }
  final protected void copyFastIdFrom(FastId from) {
    id = from.id;
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
  static private int nextId = 0;
  private TreeMap mapStrToInt = new TreeMap();
  private TreeMap mapIntToStr = new TreeMap();

  public synchronized int getId(String s) {
    if (s == null || s.length() < 1)
      return FastId.FAST_ID_NONE;
    Integer id = (Integer) mapStrToInt.get(s);
    if (id == null) {
      id = new Integer(nextId++);
      mapStrToInt.put(s, id);
      mapIntToStr.put(id, s);
    }
    return id.intValue();
  }
  public synchronized String getStringId(int i) {
    return (String) mapIntToStr.get(new Integer(i));
  }
  //Singleton
  private static FastIdManager instance = null;
  private FastIdManager() {
  }
  final public static FastIdManager getInstance() {
    if (instance == null)
      instance = new FastIdManager();
    return instance;
  }
}
