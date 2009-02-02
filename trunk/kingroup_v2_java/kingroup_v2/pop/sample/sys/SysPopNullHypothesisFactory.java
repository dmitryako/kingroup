package kingroup_v2.pop.sample.sys;
import tsvlib.project.ProjectLogger;

import javax.utilx.arrays.IntVec;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 28/11/2006, Time: 14:35:38
 */
public class SysPopNullHypothesisFactory
{
  private static ProjectLogger log = ProjectLogger.getLogger(SysPopNullHypothesisFactory.class.getName());
  public static SysPop makeNullHypothesis(SysPop from) {
//    log.info("from sysPop = \n" + from);
    int nLoci = from.getNumLoci();
    int n = from.size();
    SysPop res = new SysPop(n, nLoci);
    res.setGroupIds(from.getGroupIds());
    res.setFreq(from.getFreq());

    for (int L = 0; L < nLoci; L++) {
      int[] allAlleles = SysPopFactory.loadAllAllelels(L, from);  // array od alleles
//      log.info("allAlleles=\n" + ByteArr.toString(allAlleles));
      int[] rndOrder = IntVec.makeRandIdxArr(allAlleles.length);
//      log.info("rndOrder=\n" + IntVec.toString(rndOrder));
      int idx = 0;
      for (int i = 0; i < n; i++) {
        int a = allAlleles[rndOrder[idx++]];
        int b = allAlleles[rndOrder[idx++]];
        res.setAllelePair(i, L, a, b);
      }
    }
    res.setSize(n);
    res.resetIdIdx();
//    log.info("null sysPop = \n" + res);
    return res;
  }
}
