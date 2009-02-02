package papers.kingroup2005b_simpson.v2;
import kingroup_v2.partition.AlgAccuracyTester;
import kingroup_v2.pop.sample.PopBuilderModel;

import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/01/2006, Time: 11:46:02
 */
public class MSCommon extends AlgAccuracyTester
{
  public MSCommon() {
    DIR = "papers" + File.separator + "kingroup2005b_simpson" + File.separator + "output";
    N_TRIALS = 10; // 100-paper

    BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
    BUILDER_MODEL.setGroupsBuilderIdx(PopBuilderModel.EQUAL_GROUPS);
    BUILDER_MODEL.setNumAlleles(N_ALLELES);
  }
}
