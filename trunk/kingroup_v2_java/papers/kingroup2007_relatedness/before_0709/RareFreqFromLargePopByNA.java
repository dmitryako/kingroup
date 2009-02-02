package papers.kingroup2007_relatedness.before_0709;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.allele.freq.SysAlleleFreqFactory;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;
import kingroup_v2.relatedness.PairwiseRMtrx;

import javax.utilx.arrays.vec.Vec;

/**
 * Created by: jc1386591
 * Date: 16/06/2006. Time: 21:24:25
 */
public class RareFreqFromLargePopByNA extends RareFreqFromOuterGroupsByNA
{
  public RareFreqFromLargePopByNA() {
    N_TRIALS = 1000;  // 10,000 paper
//    N_TRIALS = 1000; // paper 10,000
    N_LOCI = 4;
    N_GROUPS = 5;
    GROUP_SIZE = 10;
  }
  public void testQG() {
    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.FULL_SIB_BUILDER);
    calc(0.5, "QG_PopFreq_FS_RAND_NL" + N_LOCI + "_GS" + GROUP_SIZE + "_NG" + N_GROUPS);

    //TODO
    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.FULL_SIB_BUILDER);
//    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "kinship_PO_RAND_NL" + N_LOCI);

    //TODO
    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.FULL_SIB_BUILDER);
//    calc(0.0, KinshipIBDFactory.makeUnrelated(), "kinship_UN_RAND_NL" + N_LOCI);

    //TODO
    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.FULL_SIB_BUILDER);
//    calc(0.25, KinshipIBDFactory.makeHalfSib(), "kinship_HS_RAND_NL" + N_LOCI);
  }

  protected double calcGroups(PopBuilderModel builderModel) {
    SysPop sysPop = SysPopFactory.makeSysPopFrom(builderModel);
//    log.info("\nsysPop=\n" + sysPop);
//    log.info("\nfreq=\n" + sysPop.getFreq());

    SysAlleleFreq largePopFreq = SysAlleleFreqFactory.makeFrom(sysPop, true);
//    log.info("largePopFreq=\n" + largePopFreq);

    sysPop.setFreq(largePopFreq);

    SysPop[] groups = SysPopFactory.makeGroupsFrom(sysPop);
    double[] grpAvr = Vec.makeArray(0., groups.length);
    for (int i = 0; i < groups.length; i++) {
      PairwiseRMtrx mtrx = new PairwiseRMtrx(groups[i]);
      mtrx.calc();
      grpAvr[i] = mtrx.calcAvr();
//      log.info("\ngrpAvr[i]=" + (float)grpAvr[i]);
    }
    return Vec.avr(grpAvr);
  }

}