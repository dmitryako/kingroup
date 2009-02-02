package papers.kingroup2007_relatedness.before_0709;
import kingroup_v2.Kingroup;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 26/11/2006, Time: 12:37:45
 */
public class RareFreqMilliganML extends RareFreqKinship
{
//  public void testByNG() {
//    N_ALLELES = 10;
//    N_LOCI = 10;
//    calcByNG("_ML_RND_NL" + N_LOCI + "_FGS" + GROUP_SIZE + "_NA" + N_ALLELES);
//  }

//  public void testSingleFamilyV2() {
//    N_TRIALS = 1000;  // 10,000 paper
//    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.SINGLE_FULL_SIB_FAMILY);
//    N_ALLELES = 10;
//    N_LOCI = 5;
//    GROUP_SIZE = 100;
////    MIN_PROP = 10;
//    calcByProportion("_Milligan_TRI_NL" + N_LOCI + "_GS" + GROUP_SIZE  +"_NA" + N_ALLELES);
//  }

//  protected void calcRMatrix(PopBuilderModel builderModel) {
//    SysPop sysPop = SysPopFactory.makeSysPopFrom(builderModel);
////    log.info("\nsysPop=\n" + sysPop);
//
//    SysAlleleFreq observedFreq = SysAlleleFreqFactory.makeFrom(sysPop, true);
////    log.info("observedFreq=\n" + observedFreq);
//    sysPop.setFreq(observedFreq);
//
//    MilliganRMtrx mtrx = new MilliganRMtrx(sysPop);
//    mtrx.calc();
//    loadKin(FS, 0.5, SysPopFactory.getFS(mtrx, sysPop));
//    loadKin(HS, 0.25, SysPopFactory.getHS(mtrx, sysPop));
//    loadKin(PO, 0.5, SysPopFactory.getPO(mtrx, sysPop));
//    loadKin(NR, 0.0, SysPopFactory.getNR(mtrx, sysPop));
//  }
  public void testAllSingleFamily_forML(int nTrials) {
    setupAllSingleFamily_forML();
    R_MTRX_TYPE = Kingroup.PAIRWISE_R_MILLIGAN;
    N_TRIALS = nTrials;
    calcByProportion("_M_TR_NL" + N_LOCI + "_GS" + GROUP_SIZE  + "_NA" + N_ALLELES);
  }
}
