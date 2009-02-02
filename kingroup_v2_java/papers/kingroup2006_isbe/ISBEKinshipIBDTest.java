package papers.kingroup2006_isbe;
import kingroup_v2.Kingroup;
import kingroup_v2.kinship.KinshipIBD;
import kingroup_v2.kinship.KinshipIBDFactory;
import kingroup_v2.kinship.KinshipSysPopFactory;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.allele.freq.SysAlleleFreqFactory;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 9/05/2006, Time: 10:17:55
 */
public class ISBEKinshipIBDTest  extends ISBERelatednessByL
{
  public ISBEKinshipIBDTest() {
    N_ALLELES = 5;
    N_LOCI = 2;
    N_TRIALS = 1000;  // 10,000 paper
  }

  public void testKinship() {
    N_TRIALS = 10000;
    R_TYPE = Kingroup.PAIRWISE_R_QG;
    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_TRIANGULAR);
    calc(0.5, KinshipIBDFactory.makeParentOffspring()
      , "kinship_FS_TR_NA" + N_ALLELES);
//    calc(0.5, KinshipIBDFactory.makeParentOffspring()
//      , "kinship_PO_TR_NA" + N_ALLELES  + "_FREQ_ERR");
//    calc(0.0, KinshipIBDFactory.makeUnrelated()
//      , "kinship_UN_TR_NA" + N_ALLELES  + "_FREQ_ERR");
//    calc(0.25, KinshipIBDFactory.makeHalfSib()
//      , "kinship_HS_TR_NA" + N_ALLELES  + "_FREQ_ERR");
  }

  public void calc(double trueR, KinshipIBD pairIBD
    , String tag  )
  {
    double[] arr = new double[2 * N_TRIALS];
    POP_BUILDER_MODEL.setNumAlleles(N_ALLELES);
    POP_BUILDER_MODEL.setNumLoci(N_LOCI);
    int count = 0;
    int L = 0;
    for (int i = 0; i < N_TRIALS; i++) {
      SysAlleleFreq freq = SysAlleleFreqFactory.makeSysAlleleFreq(POP_BUILDER_MODEL, null);
      SysPop pair = KinshipSysPopFactory.makePair(pairIBD, freq);

      arr[count++] = pair.getAllele(1, L, 0);
      arr[count++] = pair.getAllele(1, L, 1);
    }
//      log.info("\narr=\n" + DoubleArr.toString(arr));
    LOG.saveToFile(arr, DIR, "alleles_"+tag+".csv");
  }
}