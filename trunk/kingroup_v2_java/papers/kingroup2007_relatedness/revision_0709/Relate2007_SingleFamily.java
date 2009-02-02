package papers.kingroup2007_relatedness.revision_0709;
import kingroup_v2.Kingroup;
import kingroup_v2.kinship.KinshipAlleleFreqOpt;
import kingroup_v2.pop.sample.PopBuilderModel;
import papers.kingroup2007_relatedness.Relate2007_Common;
import tsvlib.project.ProjectLogger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 6/09/2007, Time: 10:17:10
 */
public class Relate2007_SingleFamily extends Relate2007_Common
{
  protected final static ProjectLogger log = ProjectLogger.getAnonymousLogger();

  protected final double EPS = 1e-5;
  protected int CLASS_I_ERROR = 5;

  public Relate2007_SingleFamily() {
    N_TRIALS = 10000;  // 10,000 paper
    N_LOCI = 4;
    MIN_N_ALLELES = 4;
    MAX_N_ALLELES = 20;
    GROUP_SIZE = 9;
    N_GROUPS = 2; // 2
    MIN_N_GROUPS = 2;
    MAX_N_GROUPS = 10;

    MIN_PROP = 10;
    MAX_PROP = 50;
    PROP_STEP = 5;

    R_MTRX_TYPE = Kingroup.PAIRWISE_R_QG;
    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_DIERKES_2005);
//    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_TRIANGULAR);
//    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_RANDOM);
//    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);

    POP_BUILDER_MODEL.setNumGroups(N_GROUPS);
    POP_BUILDER_MODEL.setGroupSize(GROUP_SIZE);
    POP_BUILDER_MODEL.setNumLoci(N_LOCI);
    POP_BUILDER_MODEL.setIncParents(true); // not really used anyway

//    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.PEDIGREE_BUILDER);

    KinshipAlleleFreqOpt opt = KINSHIP.getAlleleFreqOpt();
    opt.setAllowZeroFreq(true);
  }

  public void testAllSingleFamily() {
    N_TRIALS = 400;  // 10,000 for the paper
//    testSingleFamily(N_TRIALS, Kingroup.PAIRWISE_R_KH_OUTBRED, "_KH_OUTBRED");
//    testSingleFamily(N_TRIALS, Kingroup.PAIRWISE_R_INBRED_KH, "_KH_INBRED"); //TODO
//    testSingleFamily(N_TRIALS, Kingroup.PAIRWISE_R_LR, "_LR");
    testSingleFamily(N_TRIALS, Kingroup.PAIRWISE_R_LL, "_LL");
//    testSingleFamily(N_TRIALS, Kingroup.PAIRWISE_R_WANG, "_W");
//    testSingleFamily(N_TRIALS, Kingroup.PAIRWISE_R_QG, "_QG");
//    new Relate2007_TrueFreq().testSingleFamily(N_TRIALS, Kingroup.PAIRWISE_R_QG, "_QG_TRUE");

//    new Relate2007_OuterFreq().testSingleFamily(N_TRIALS, Kingroup.PAIRWISE_R_KINSHIP, "_QG_OUTER");

//    new RareFreqKinshipTrueFreq().testSingleFamilyV3(N_TRIALS);
//    new RareFreqKinshipOuterPairs().testSingleFamilyV3(N_TRIALS);
//    new RareFreqHegKon().testSingleFamilyV3(N_TRIALS);
  }

  public void testSingleFamily(int nTrials, int type, String tag) {
    R_MTRX_TYPE = type;
    N_TRIALS = nTrials;  // 10,000 paper
    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.SINGLE_FULL_SIB_FAMILY);

    N_LOCI = 5;
    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_DIERKES_2005);
//    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
//    N_ALLELES = 10;

    GROUP_SIZE = 100;
    MIN_PROP = 10;
    MAX_PROP = 50;
    PROP_STEP = 5;
    calcByProportion(tag + "_DIERKES_GS" + GROUP_SIZE);
  }

}

