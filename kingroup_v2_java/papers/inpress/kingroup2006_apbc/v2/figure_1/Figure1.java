package papers.inpress.kingroup2006_apbc.v2.figure_1;
import kingroup_v2.pop.sample.PopBuilderModel;
import papers.inpress.kingroup2006_apbc.v2.APBC2006_Common;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 11/01/2006, Time: 11:17:21
 */
public class Figure1 extends  APBC2006_Common
{
  public Figure1()
  {
    GROUP_SIZE = 5;
    MIN_N_GROUPS = 10;
    MAX_N_GROUPS = 50;  // 100
    N_GROUPS_STEP = 10;
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