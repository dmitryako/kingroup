package papers.kingroup2005c_limit.figure_6;
import kingroup_v2.pop.sample.PopBuilderModel;
import papers.kingroup2005c_limit.LimitCommon;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 19/01/2006, Time: 14:05:58
 */
public class LimitFigure6 extends  LimitCommon
{
  public LimitFigure6()
  {
    GROUP_SIZE = 5;
    MIN_N_GROUPS = 5;
    MAX_N_GROUPS = 40;  // 40 for MS
    MAX_N_GROUPS = 30;  // 30 for MCS
    MAX_N_GROUPS = 30;  // 25 for SIMPS 
    N_GROUPS_STEP = 5;
    N_ALLELES = 10;
    N_LOCI = 5;

    N_TRIALS = 10; // 100-paper

    BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
    BUILDER_MODEL.setGroupsBuilderIdx(PopBuilderModel.EQUAL_GROUPS);
    BUILDER_MODEL.setNumAlleles(N_ALLELES);
  }
  public void calcEfficiency() {
    String name = (ALG_NAME + "_GS" + GROUP_SIZE + "_NL" + N_LOCI + "_NA" + N_ALLELES);
    calcAccuracyByNGroups(DIR, name + "_dist.csv", name + "_time.csv");
  }
}