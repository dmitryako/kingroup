package papers.kingroup2007_relatedness.before_0709;
import kingroup_v2.kinship.KinshipRMtrxBiasPair;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;
import kingroup_v2.relatedness.PairwiseRMtrx;

/**
 * Created by: jc1386591
 * Date: 27/06/2006. Time: 14:41:03
 */
public class RareFreqKinshipOuterPairs extends RareFreqKinship
{
  public void testByNL() {
    N_ALLELES = 10;
    calcByNL("_QG_OUTPAIRS_TRI_NA" + N_ALLELES + "_FGS" + GROUP_SIZE + "_NG" + N_GROUPS);
  }
  public void testByNA() {
    calcByNA("_QG_OUTPAIRS_TRI_NL" + N_LOCI + "_FGS" + GROUP_SIZE + "_NG" + N_GROUPS);
  }

  public void testDeirkes() {
    N_LOCI = 5;
    POP_BUILDER_MODEL.setNumLoci(N_LOCI);
    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_DIERKES_2005);
    calcByNG("_QG_OUTPAIRS_DEI_NL" + N_LOCI + "_FGS" + GROUP_SIZE);
  }
  public void testByNG() {
    N_ALLELES = 10;
    N_LOCI = 10;
    calcByNG("_QG_OUTPAIRS_TRI_NL" + N_LOCI + "_FGS" + GROUP_SIZE + "_NA" + N_ALLELES);
  }
  public void testSingleFamilyV2() {
//    N_TRIALS = 1000;  // 10,000 paper
    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.SINGLE_FULL_SIB_FAMILY);
    N_ALLELES = 10;
    N_LOCI = 10;
    GROUP_SIZE = 50;
//    MIN_PROP = 10;
    calcByProportion("_QG_OUTPAIRS_TRI_NL" + N_LOCI + "_GS" + GROUP_SIZE  + "_NA" + N_ALLELES);
  }
  public void testSingleFamilyV3(int nTrials) {
    N_TRIALS = nTrials;  // 10,000 paper
    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.SINGLE_FULL_SIB_FAMILY);
    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
    N_ALLELES = 10;
    N_LOCI = 10;
    GROUP_SIZE = 100;

    MIN_PROP = 10;
    MAX_PROP = 80;
    PROP_STEP = 10;

    calcByProportion("_QG_OUTPAIRS_EQ_NL" + N_LOCI + "_GS" + GROUP_SIZE  + "_NA" + N_ALLELES);
  }

  protected void calcRMatrix(PopBuilderModel builderModel) {
    SysPop sysPop = SysPopFactory.makeSysPopFrom(builderModel);
//    log.info("\nsysPop=\n" + sysPop);

    PairwiseRMtrx mtrx = new KinshipRMtrxBiasPair(sysPop, sysPop, KINSHIP);
    mtrx.calc();
    loadKin(FS, 0.5, SysPopFactory.getFS(mtrx, sysPop));
    loadKin(HS, 0.25, SysPopFactory.getHS(mtrx, sysPop));
    loadKin(PO, 0.5, SysPopFactory.getPO(mtrx, sysPop));
    loadKin(NR, 0.0, SysPopFactory.getNR(mtrx, sysPop));
  }
}

