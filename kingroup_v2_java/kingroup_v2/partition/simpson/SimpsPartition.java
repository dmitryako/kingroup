package kingroup_v2.partition.simpson;
import kingroup_v2.partition.ms.MSAlgPartition;

import javax.utilx.bitset.CompBitSet;
import java.util.Iterator;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 10/05/2005, Time: 11:31:02
 */
public class SimpsPartition extends MSAlgPartition {
  private final CompBitSet[] mapIdxToSet;
  public SimpsPartition(int univSize) {
    super(univSize);
    mapIdxToSet = new CompBitSet[univSize];
  }
  public SimpsPartition(SimpsPartition from) {
    super(from.getSampleSize());
//      LOG.report(this, "duplicate from=" + from);
    mapIdxToSet = new CompBitSet[from.getSampleSize()];
    for (Iterator it = from.iterator(); it.hasNext();)
      add(new CompBitSet((CompBitSet) it.next()));
  }
  public boolean add(CompBitSet g) {
    assert(g.length() != 0);
//      LOG.report(this, "addLine(" + g);
    int idx = -1;
    for (; ;) {
      idx = g.nextSetBit(idx + 1);
      if (idx == -1)
        break;
      mapIdxToSet[idx] = g;
    }
    return super.add(g);
  }
  public boolean remove(CompBitSet g) {
    int idx = -1;
    for (; ;) {
      idx = g.nextSetBit(idx + 1);
      if (idx == -1)
        break;
      mapIdxToSet[idx] = null;
    }
    return super.remove(g);
  }
  public CompBitSet getGroupByIdIdx(int i) {
    return mapIdxToSet[i];
  }
  public void moveAWithB(int a, int b) {
    CompBitSet A = getGroupByIdIdx(a);
    CompBitSet B = getGroupByIdIdx(b);
    remove(A);     // must remove first to UNLOCK
    remove(B);
    A.set(a, false);
    B.set(a, true);
    if (A.length() != 0)
      add(A);     // addLine() LOCKs the set
    add(B);
  }
}
