package kingroup_v2.partition.ms.window_only;
import kingroup.algorithm.window.AlgAccessViaPairs;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.utilx.RandomSeed;
import javax.utilx.bitset.CompBitSet;
import java.util.BitSet;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 17/06/2005, Time: 14:51:00
 */
public class SimpsRandomOrder extends AlgAccessViaPairs {
  private RandomSeed random = RandomSeed.getInstance();
  private final BitSet pool;
  public SimpsRandomOrder(SysPop pop) {
    super(pop.size(), null);
    pool = new CompBitSet();
    pool.set(0, pop.size(), true);
  }
  public boolean hasNext() {
    return (pool.size() > 0);
  }
  public int nextIdx() {
    int res = NOT_SET;
    if (pool.cardinality() == 0)
      return res;
    if (pool.cardinality() == 1) {
      res = pool.nextSetBit(0);
      pool.set(res, false);
      return res;
    }
    int size = random.nextInt(pool.cardinality()) + 1;
    for (int i = 0; i < size; i++) {
      res = pool.nextSetBit(res + 1);
    }
    if (res == -1) {
      int dbg = 1;
    }
    pool.set(res, false);
    return res;
  }
}
