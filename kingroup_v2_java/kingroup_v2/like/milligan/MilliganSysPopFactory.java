package kingroup_v2.like.milligan;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;

import javax.utilx.RandomSeed;
import javax.utilx.arrays.vec.Vec;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 20/03/2006, Time: 16:45:33
 */
public class MilliganSysPopFactory extends SysPopFactory
{
  public static void makeDiploidYFromX(MilliganIBD ibd
    , int yIdx, int xIdx, SysPop pop)
  {
    SysAlleleFreq freq = pop.getFreq();
    for (int L = 0; L < pop.getNumLoci(); L++)
    {
      // determine type
      double p = RandomSeed.getInstance().nextDouble();
      int type = Vec.findBinL(ibd.getCum(), p);

      int xm = pop.getAllele(xIdx, L, pop.MAT);
      int xp = pop.getAllele(xIdx, L, pop.PAT);
      int y;
      switch (type){
        case 0:
          pop.setAllele(xIdx, L, pop.PAT, xm);   // Xm->Xp
          pop.setAllele(yIdx, L, pop.MAT, xm);   // Xm->Ym
          pop.setAllele(yIdx, L, pop.PAT, xm);   // Xm->Yp
          continue;
        case 1:
          pop.setAllele(yIdx, L, pop.MAT, xm);   // Xm->Xp
          y = freq.getRandomAllele(L);
          pop.setAllele(yIdx, L, pop.MAT, y);
          pop.setAllele(yIdx, L, pop.PAT, y);
          continue;
        case 2:
          pop.setAllele(xIdx, L, pop.PAT, xm);   // Xm->Xp
          pop.setAllele(yIdx, L, pop.MAT, xm);   // Xm->Ym
          y = freq.getRandomAllele(L);
          pop.setAllele(yIdx, L, pop.PAT, y);
          continue;
        case 3:
          pop.setAllele(xIdx, L, pop.PAT, xm);   // Xm->Xp
          y = freq.getRandomAllele(L);
          pop.setAllele(yIdx, L, pop.MAT, y);
          y = freq.getRandomAllele(L);
          pop.setAllele(yIdx, L, pop.PAT, y);
          continue;
        case 4:
          pop.setAllele(yIdx, L, pop.MAT, xm);    // Xm->Ym
          pop.setAllele(yIdx, L, pop.PAT, xm);    // Xm->Yp
          continue;
        case 5:
          y = freq.getRandomAllele(L);
          pop.setAllele(yIdx, L, pop.MAT, y);    // rand Ym
          pop.setAllele(yIdx, L, pop.PAT, y);    // Ym->Yp
          continue;
        case 6:
          pop.setAllele(yIdx, L, pop.MAT, xm);   // Xm->Ym
          pop.setAllele(yIdx, L, pop.PAT, xp);   // Xp->Yp
          continue;
        case 7:
          pop.setAllele(yIdx, L, pop.MAT, xm);   // Xm->Ym
          y = freq.getRandomAllele(L);
          pop.setAllele(yIdx, L, pop.PAT, y);    // rand Yp
          continue;
        default: // case 8
          y = freq.getRandomAllele(L);
          pop.setAllele(yIdx, L, pop.MAT, y);    // rand Ym
          y = freq.getRandomAllele(L);
          pop.setAllele(yIdx, L, pop.PAT, y);    // rand Yp
          continue;
      }
    }
//    // log.info("pair=\n" + pair);
  }
}
