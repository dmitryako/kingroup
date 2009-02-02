package kingroup.papers.butler2004.family;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 28, 2004, Time: 12:31:28 PM
 */
final public class ButlerFamily extends ArrayList {
  private int id_;
  private String name_;
  public ButlerFamily(int id, String name, Integer[] va) {
    super(Arrays.asList(va));
    id_ = id;
    name_ = name;
  }
  final public int getId() {
    return id_;
  }
  final public String toString() {
    return name_;
  }
  public int[] toIntArray() {
    int[] res = new int[size()];
    for (int i = 0; i < res.length; i++) {
      res[i] = getFamilySizeAt(i);
    }
    return res;
  }
  final public int getFamilySizeAt(int i) {
    if (i < 0 || i >= size())
      return 0;
    Integer res = (Integer) get(i);
    return res.intValue();
  }
}
