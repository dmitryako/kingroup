package papers.kingroup2006_cervus;
import kingroup_v2.partition.AlgAccuracyTester;
import kingroup_v2.pop.sample.PopBuilderModel;

import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 23/01/2006, Time: 14:22:18
 */
public class CervusCommon extends AlgAccuracyTester
{
  public CervusCommon() {
    DIR = "papers" + File.separator + "kingroup2006_cervus" + File.separator + "output";
    GROUP_SIZE = 5;
    MIN_N_GROUPS = 10;
    MAX_N_GROUPS = 100;
    N_GROUPS_STEP = 10;
    N_ALLELES = 10;
    N_LOCI = 5;

    N_TRIALS = 10; // 100-paper

    BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
    BUILDER_MODEL.setGroupsBuilderIdx(PopBuilderModel.EQUAL_GROUPS);
    BUILDER_MODEL.setNumAlleles(N_ALLELES);
  }
}