package kingroup.algorithm.window;
import kingroup_v2.partition.ms.MSAlgGroups;
import kingroup_v2.partition.simpson.MSAlgGroup;
import tsvlib.project.ProjectLogger;

import javax.utilx.bitset.CompBitSet;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/01/2006, Time: 15:29:18
 */
public class AlgAccessViaGroups extends AlgWinAccessOrder {
  private static ProjectLogger log = ProjectLogger.getLogger(AlgAccessViaGroups.class.getName());
  private final MSAlgGroups groups;
  private MSAlgGroup currGroup;
  public AlgAccessViaGroups(int size, MSAlgGroups groups) {
    super(size);
    this.groups = groups;
    pool = new CompBitSet();
    pool.set(0, size, true);
  }
  public boolean hasNext() {
    return (pool.cardinality() > 0 );
  }
  public int nextIdx() {
    int res = 0;
    if (currGroup == null) {
      currGroup = groups.removeFirst();
    }
    for (; ; )
    {
      res = currGroup.nextSetBit(res); // first available in this group
      if (res == -1) {
        currGroup = null;
        break; // empty group
      }
      currGroup.set(res, false); // remove
      if (pool.get(res)) {// available in the pool
        pool.set(res, false);
//        log.info("\nres=" + res + ", currGroup=" + currGroup);
        return res;
      }
    }
    return nextIdx();
  }
}

