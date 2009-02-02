package kingroup_v2.partition.simpson.exact;
import javax.langx.SysProp;
import javax.utilx.bitset.CompBitSet;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 2/07/2005, Time: 15:36:48
 */
public class SimpsPhenoGroup extends CompBitSet {
  private int groupSize;
  public SimpsPhenoGroup() {
  }
  public SimpsPhenoGroup(SimpsPhenoGroup g3) {
    super(g3);
    this.groupSize = g3.groupSize;
  }
  public void setGroupSize(int groupSize) {
    this.groupSize = groupSize;
  }
  public int getGroupSize() {
    return groupSize;
  }
  public String toString() {
    return "#" + groupSize + ", " + super.toString();
  }
  public static String toString(SimpsPhenoGroup[] arr) {
    StringBuffer res = new StringBuffer();
    for (short i = 0; i < arr.length; i++) {
      res.append("[" + i + "] ");
      res.append(arr[i]);
      res.append(SysProp.EOL);
    }
    return res.toString();
  }
}
