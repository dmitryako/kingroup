package kingroup.partition.bitsets;

import java.util.Iterator;
import java.util.BitSet;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 8/10/2005, Time: 16:56:29
 */
public class PartitionGroupFactory {
  public static int[] getGroups(Partition part) {
    int[] res = new int[part.getSampleSize()];
    int groupIdx = 1;
    for (Iterator it = part.iterator(); it.hasNext(); ) {
      BitSet group = (BitSet)it.next();
      for (int idx = -1; ;) {
        idx = group.nextSetBit(idx + 1);
        if (idx == -1)
          break;
        res[idx] = groupIdx;
      }
      groupIdx++;
    }
    return res;
  }

}
