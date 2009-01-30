package javax.utilx;
import java.util.TreeSet;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 4/03/2005, Time: 15:12:13
 */
public class IntTreeSet extends TreeSet {
  public boolean addInt(int i) {
    return add(new Integer(i));
  }
  public boolean removeInt(int i) {
    return remove(new Integer(i));
  }
  public boolean containsInt(int i) {
    return contains(new Integer(i));
  }
  public int firstInt() {
    return ((Integer) first()).intValue();
  }
  public int lastInt() {
    return ((Integer) last()).intValue();
  }
}
