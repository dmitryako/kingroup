package papers.kingroup2005c_ms2.efficiency;
import papers.kingroup2005c_ms2.MS2Diploid;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 24/08/2005, Time: 10:52:06
 */
public class MS2Efficiency extends MS2Diploid {
  public MS2Efficiency() {
    GROUP_SIZE = 10; //
    MIN_N_GROUPS = 10;
    MAX_N_GROUPS = 100;
    N_GROUPS_STEP = 10;
    N_GROUPS = 10;
    MIN_GROUP_SIZE = 10;
    MAX_GROUP_SIZE = 100;
    GROUP_SIZE_STEP = 10;
    N_ALLELES = 10;
    N_LOCI = 5;
    N_TRIALS = 10; // 100-paper
  }
  public void calcEfficiency() {
    String name = (FILE + "_GS" + GROUP_SIZE + "_NL" + N_LOCI + "_NA" + N_ALLELES);
    calcEfficiencyByNGroups(DIR, name + "_dist.csv", name + "_time.csv");
    name = (FILE + "_NG" + N_GROUPS + "_NL" + N_LOCI + "_NA" + N_ALLELES);
    calcEfficiencyByGroupSize(DIR, name + "_dist.csv", name + "_time.csv");
  }
}