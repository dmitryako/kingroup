package papers.kingroup2007_relatedness.before_0709;
import kingroup_v2.Kingroup;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;

/**
 * Created by: jc1386591
 * Date: 25/06/2006. Time: 20:49:13
 */
public class RareFreqKinshipTrueFreq extends RareFreqKinship
{
  public void testByNL() {
    N_ALLELES = 10;
    calcByNL("_QG_TRUE_TRI_NA" + N_ALLELES + "_FGS" + GROUP_SIZE + "_NG" + N_GROUPS);
  }
  public void testByNA() {
    calcByNA("_QG_TRUE_TRI_NL" + N_LOCI + "_FGS" + GROUP_SIZE + "_NG" + N_GROUPS);
  }

  public void testDeirkes() {
    N_LOCI = 5;
    POP_BUILDER_MODEL.setNumLoci(N_LOCI);
    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_DIERKES_2005);
    calcByNG("_QG_TRUE_DEI_NL" + N_LOCI + "_FGS" + GROUP_SIZE);
  }
  public void testByNG() {
    N_ALLELES = 10;
    N_LOCI = 10;
    calcByNG("_QG_TRUE_TRI_NL" + N_LOCI + "_FGS" + GROUP_SIZE + "_NA" + N_ALLELES);
  }
  public void testSingleFamilyV2() {
//    N_TRIALS = 1000;  // 10,000 paper
    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.SINGLE_FULL_SIB_FAMILY);
    N_ALLELES = 10;
    N_LOCI = 10;
    GROUP_SIZE = 50;
//    MIN_PROP = 10;
    calcByProportion("_QG_TRUE_TRI_NL" + N_LOCI + "_GS" + GROUP_SIZE  + "_NA" + N_ALLELES);
  }
  public void testSingleFamilyV3(int nTrials) {
    R_MTRX_TYPE = Kingroup.PAIRWISE_R_QG;
    N_TRIALS = nTrials;  // 10,000 paper
    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.SINGLE_FULL_SIB_FAMILY);
    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
    N_ALLELES = 10;
    N_LOCI = 10;
    GROUP_SIZE = 100;
    MIN_PROP = 10;
    MAX_PROP = 80;
    PROP_STEP = 10;
    calcByProportion("_QG_TRUE_EQ_NL" + N_LOCI + "_GS" + GROUP_SIZE  + "_NA" + N_ALLELES);
  }
//  public void testAllSingleFamily_forML(int nTrials) {
//    R_MTRX_TYPE = Kingroup.PAIRWISE_R_KINSHIP;
//    N_TRIALS = nTrials;  // 10,000 paper
//    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.SINGLE_FULL_SIB_FAMILY);
//    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_TRIANGULAR);
//    N_ALLELES = 10;
//    N_LOCI = 5;
//    GROUP_SIZE = 100;
//    MIN_PROP = 10;
//    MAX_PROP = 40;
//    PROP_STEP = 5;
//    calcByProportion("_QG_TRUE_EQ_NL" + N_LOCI + "_GS" + GROUP_SIZE  + "_NA" + N_ALLELES);
//  }


  protected SysPop makeSysPopFrom(PopBuilderModel builderModel) {
    SysPop res = SysPopFactory.makeSysPopFrom(builderModel);   // TRUE FREQS!!!!!!!!!!
//    log.info("\nsysPop=\n" + sysPop);
    return res;
  }
}