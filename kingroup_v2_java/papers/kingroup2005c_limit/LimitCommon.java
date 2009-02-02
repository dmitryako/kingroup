package papers.kingroup2005c_limit;
import kingroup_v2.partition.AlgAccuracyTester;
import kingroup_v2.pop.sample.PopBuilderModel;

import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/11/2005, Time: 07:40:06
 */
public class LimitCommon extends AlgAccuracyTester
{
  public LimitCommon() {
    DIR = "papers" + File.separator + "kingroup2005c_limit" + File.separator + "output";

    // LIMIT
    GROUP_SIZE = 1;
    MIN_N_GROUPS = 2;
    MAX_N_GROUPS = 20;
    N_GROUPS_STEP = 1;
    N_ALLELES = 10;
    N_LOCI = 1;
    N_TRIALS = 10;

    BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
    BUILDER_MODEL.setGroupsBuilderIdx(PopBuilderModel.EQUAL_GROUPS);
    BUILDER_MODEL.setNumAlleles(N_ALLELES);
  }
  public void runGroupSizes() {
    runGS5();
    runGS10();
    runGS20();
    runGS50();
  }
  public void runGS5() {
    String name;
    GROUP_SIZE = 5;
    name = (ALG_NAME + "_GS" + GROUP_SIZE + "_NL" + N_LOCI + "_NA" + N_ALLELES);
    calcAccuracyByNGroups(DIR, name + "_dist.csv", name + "_time.csv");
  }
  public void runGS10() {
    String name;
    GROUP_SIZE = 10;
    name = (ALG_NAME + "_GS" + GROUP_SIZE + "_NL" + N_LOCI + "_NA" + N_ALLELES);
    calcAccuracyByNGroups(DIR, name + "_dist.csv", name + "_time.csv");
  }
  public void runGS20() {
    String name;
    GROUP_SIZE = 20;
    name = (ALG_NAME + "_GS" + GROUP_SIZE + "_NL" + N_LOCI + "_NA" + N_ALLELES);
    calcAccuracyByNGroups(DIR, name + "_dist.csv", name + "_time.csv");
  }
  public void runGS50() {
    String name;
    GROUP_SIZE = 50;
    name = (ALG_NAME + "_GS" + GROUP_SIZE + "_NL" + N_LOCI + "_NA" + N_ALLELES);
    calcAccuracyByNGroups(DIR, name + "_dist.csv", name + "_time.csv");
  }

  public void run_P50() {
    BUILDER_MODEL.setGroupSize(GROUP_SIZE = 10);
    BUILDER_MODEL.setNumGroups(N_GROUPS = 5);
    calcAccuracyByLoci(DIR, ALG_NAME + "_5x10S_NA" + N_ALLELES + ".csv");
  }
}
