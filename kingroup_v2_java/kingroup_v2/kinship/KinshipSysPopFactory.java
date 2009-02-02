package kingroup_v2.kinship;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;

import javax.utilx.pair.IntPair2;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 20/03/2006, Time: 16:46:01
 */
public class KinshipSysPopFactory extends SysPopFactory
{
  public static SysPop makePair(KinshipIBD ibd, SysAlleleFreq freq)
  {
    final int PAIR_SIZE = 2;
    SysPop pair = new SysPop(PAIR_SIZE, freq.getNumLoci());
    pair.setFreq(freq);
    pair.setSize(PAIR_SIZE);
    int x = 0;
    int y = 1;
    makeRandomDiploid(x, pair);
    makeDiploidYFromX(ibd, y, x, pair);
    return pair;
  }
  public static void makeDiploidYFromX(KinshipIBD ibd
    , int yIdx, int fromIdx, SysPop pop)
  {
    SysAlleleFreq freq = pop.getFreq();
    for (int L = 0; L < pop.getNumLoci(); L++) {
      IntPair2 p = pop.getAllelePair(fromIdx, L);
      int x = SysPopFactory.getRandomAllele(p.a,  p.b);
      int x2 = p.pair(x);

      int y = getIBDAllele(ibd.getRm(), x, L, freq);
      pop.setAllele(yIdx, L, pop.MAT, y);

      y = getIBDAllele(ibd.getRp(), x2, L, freq);
      pop.setAllele(yIdx, L, pop.PAT, y);
    }
//    // log.info("pair=\n" + pair);
  }
}
