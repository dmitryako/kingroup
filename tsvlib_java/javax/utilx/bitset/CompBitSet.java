package javax.utilx.bitset;
import tsvlib.project.ProjectLogger;

import java.util.BitSet;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Oct 14, 2004, Time: 5:05:41 PM
 */
public class CompBitSet extends BitSet
  implements Comparable
{
  private final static ProjectLogger log = ProjectLogger.getLogger(CompBitSet.class);
  private boolean isLocked = false;
  public CompBitSet() {
  }
  public CompBitSet(BitSet from) {
    this.or(from);
  }
  public int[] toArray()
  {
    int[] res = new int[this.cardinality()];
    int count = 0;
    for (int i = nextSetBit(0); i >= 0; i = nextSetBit(i+1))
    {
      res[count++] = i;
    }
    return res;
  }
  public int compareTo(Object ob) {
    if (this == ob || equals(ob))
      return 0;
    if (!(ob instanceof BitSet))
      return -1;
    //return o.hashCode() - hashCode();
    BitSet set = (BitSet) ob;
    int diff = length() - set.length();
    if (diff != 0)
      return diff;
    BitSet xorSet = (BitSet) clone();
    xorSet.xor(set); // find the most significan bit
    if (get(xorSet.length() - 1))
      return 1;
    return -1;
  }
  public void set(int bitIdx) {
    if (isLocked) {
      RuntimeException e = new RuntimeException("trying to change a locked bit set.");
      log.error("error", e);
      throw e;
    }
    super.set(bitIdx);
  }
  public void flip(int bitIdx) {
    if (isLocked) {
      RuntimeException e = new RuntimeException("trying to change a locked bit set.");
      log.error("error", e);
      throw e;
    }
    super.flip(bitIdx);
  }
  public void set(int bitIdx, boolean flag) {
    if (isLocked) {
      RuntimeException e = new RuntimeException("trying to change a locked bit set.");
      log.error("error", e);
      throw e;
    }
    super.set(bitIdx, flag);
  }
  public void swapFirstDifferentSetBit(CompBitSet with) {
    if (this.isEmpty()
      || with.isEmpty()
      || equals(with))
      return;
    int bitA = this.nextSetBit(0);
    int bitB = with.nextSetBit(0);
    while (bitA == bitB) {
      bitA = this.nextSetBit(bitA + 1);
      bitB = with.nextSetBit(bitB + 1);
      if (bitA == -1 || bitB == -1)
        return;
    }
    this.set(bitA, false);
    with.set(bitB, false);
    this.set(bitB);
    with.set(bitA);
  }
  public void setLocked(boolean b) {
    isLocked = b;
  }
}
