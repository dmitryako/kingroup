package kingroup_v2.partition.simpson;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.utilx.bitset.CompBitSet;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 10/05/2005, Time: 11:33:18
 */
public class SimpsPartitionFactory {
  private final SysPop pop;
  public SimpsPartitionFactory(SysPop pop) {
    this.pop = pop;
  }
  public SimpsPartition makeOnePerGroup() {
    SimpsPartition res = new SimpsPartition(pop.size());
    for (int i = 0; i < pop.size(); i++) {
//         SimpsGroup set = new SimpsGroup(pop.getNumLoci());
      CompBitSet set = new CompBitSet();
      set.set(i);
      res.add(set);
    }
    return res;
  }
  public SimpsPartition makeOneGroup() {
    SimpsPartition res = new SimpsPartition(pop.size());
//      SimpsGroup set = new SimpsGroup(pop.getNumLoci());
    CompBitSet set = new CompBitSet();
    for (int i = 0; i < pop.size(); i++) {
      set.set(i);
    }
    res.add(set);
    return res;
  }
}
