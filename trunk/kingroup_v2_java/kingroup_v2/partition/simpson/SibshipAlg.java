package kingroup_v2.partition.simpson;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.utilx.bitset.CompBitSet;
import javax.utilx.pair.IntPairSymmKey;
import java.util.TreeSet;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 28/11/2005, Time: 17:06:02
 */
public abstract class SibshipAlg
{
  public static final int NOT_SET = -1;
  public abstract boolean isSibGroupSLOW(SysPop pop, CompBitSet group);

  protected void loadAlleleAndLocusSetsSLOW(SysPop pop, TreeSet locusSet, TreeSet alleleSet
    , CompBitSet group, int locusIdx) {
    short idx = -1;
    for (; ;) {
      idx = (short) group.nextSetBit(idx + 1); // for all members of this group
      if (idx == -1)
        break;
      int pi = pop.getAllele(idx, locusIdx, pop.PAT); // idx of paternal getAllele
      int mi = pop.getAllele(idx, locusIdx, pop.MAT); // idx of maternal getAllele
//         LOG.report(this, "group( "+new IntPairSymmKey(pi, mi));

      //[dak080214] missing allele is -1
//      assert(pi != NOT_SET && mi != NOT_SET);
//      if (pi == NOT_SET || mi == NOT_SET)
//        throw new KinGroupError("pi == NOT_SET  ||  mi == NOT_SET");

      if (pi != NOT_SET)
        alleleSet.add(new Integer(pi));

      if (mi != NOT_SET)
        alleleSet.add(new Integer(mi));

      if (pi != NOT_SET  &&  mi != NOT_SET)
        locusSet.add(new IntPairSymmKey(pi, mi)); 
    }
  }
}
