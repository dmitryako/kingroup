package papers.kingroup2007_relatedness.before_0709;
import kingroup_v2.Kingroup;
import kingroup_v2.pop.sample.PopBuilderModel;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 25/09/2006, Time: 11:38:08
 */
public class RareFreqWang  extends RareFreqKinship
{
  public void testByNL() {
    R_MTRX_TYPE = Kingroup.PAIRWISE_R_WANG;
    N_ALLELES = 20;
    MIN_N_LOCI = 2;
    MAX_N_LOCI = 10;
    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_RANDOM);
    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.PEDIGREE_BUILDER);
    GROUP_SIZE = 9;
    N_GROUPS = 3;
    calcByNL("_W_RND_NA" + N_ALLELES + "_FGS" + GROUP_SIZE + "_NG" + N_GROUPS);
  }
  public void testByNA() {
    calcByNA("_W_TRI_NL" + N_LOCI + "_FGS" + GROUP_SIZE + "_NG" + N_GROUPS);
  }
  public void testDeirkes() {
    N_LOCI = 5;
    POP_BUILDER_MODEL.setNumLoci(N_LOCI);
    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_DIERKES_2005);
    calcByNG("_W_DEI_NL" + N_LOCI + "_FGS" + GROUP_SIZE);
  }
  public void testByNG() {
    N_ALLELES = 10;
    N_LOCI = 10;
    calcByNG("_W_TRI_NL" + N_LOCI + "_FGS" + GROUP_SIZE + "_NA" + N_ALLELES);
  }

//  public void testSingleFamilyV2() {
//    N_TRIALS = 10000;  // 10,000 paper
//    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.SINGLE_FULL_SIB_FAMILY);
//    N_ALLELES = 10;
//    N_LOCI = 5;
////    GROUP_SIZE = 50;
//    GROUP_SIZE = 100;
////    MIN_PROP = 10;
//    calcByProportion("_W_TRI_NL" + N_LOCI + "_GS" + GROUP_SIZE  + "_NA" + N_ALLELES);
//  }

  public void testSingleFamilyV3(int nTrials) {
    R_MTRX_TYPE = Kingroup.PAIRWISE_R_WANG;
    N_TRIALS = nTrials;
    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.SINGLE_FULL_SIB_FAMILY);
    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
    N_ALLELES = 10;
    N_LOCI = 10;
    GROUP_SIZE = 100;
    MIN_PROP = 10;
    MAX_PROP = 80;
    PROP_STEP = 10;
    calcByProportion("_W_EQ_NL" + N_LOCI + "_GS" + GROUP_SIZE  + "_NA" + N_ALLELES);
  }

  public void testAllSingleFamily_forML(int nTrials) {
    setupAllSingleFamily_forML();
    R_MTRX_TYPE = Kingroup.PAIRWISE_R_WANG;
    N_TRIALS = nTrials;
    calcByProportion("_W_TR_NL" + N_LOCI + "_GS" + GROUP_SIZE  + "_NA" + N_ALLELES);
  }

  public void testSingleFamilyV3_bySize(int nTrials) {
    R_MTRX_TYPE = Kingroup.PAIRWISE_R_WANG;
    N_TRIALS = nTrials;  // 10,000 paper
    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.SINGLE_FULL_SIB_FAMILY_BY_SIZE);
    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_ONE_COMMON);
    N_ALLELES = 5;
    N_LOCI = 10;
    GROUP_SIZE = 10; // number of full-sibs!!!!
    MIN_PROP = 20;   // MIN of the total sample size
    MAX_PROP = 150;
    PROP_STEP = 10;
    calcByProportion("_W_EQ_NL" + N_LOCI + "_NFS" + GROUP_SIZE  + "_NA" + N_ALLELES);
  }
}

