package kingroup_v2.partition.ms;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.partition.simpson.MSAlgGroup;

import java.util.Iterator;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 19/05/2005, Time: 08:40:05
 */
public class MSAlgPartition extends Partition {
  private int simpsonIdx = 0;
  public MSAlgPartition(int universeSize) {
    super(universeSize);
  }
  public MSAlgPartition(Partition from, boolean deepCopy) {
    super(from.getSampleSize());
    if (deepCopy)
      deepCopyFrom(from);
  }
  protected void deepCopyFrom(Partition from) {
    for (Iterator itA = from.iterator(); itA.hasNext();) {
      MSAlgGroup set = (MSAlgGroup) itA.next();
      add(new MSAlgGroup(set));
    }
  }
  public void setSimpsonIdx(int v) {
    simpsonIdx = v;
  }
  public int getSimpsonIdx() {
    return simpsonIdx;
  }
  public MSAlgGroup remove(int idx) {
    MSAlgGroup res = getGroup(idx);
    if (res != null)
      remove(res);
    return res;
  }
  public MSAlgGroup getGroup(int idx) {
    int i = 0;
    for (Iterator it = iterator(); it.hasNext(); i++) {
      Object groupObj = it.next();
      if (i == idx)
        return (MSAlgGroup) groupObj;
    }
    return null;
  }
  public String toString()
  {
    return "simps idx=" + simpsonIdx + ", " + super.toString();
  }
}
