package papers.inpress.kingroup2006_apbc.v1.efficiency;
import papers.kingroup2005c_ms2.MS2Diploid;

import java.io.File;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/09/2005, Time: 13:31:07
 */
public class APBC2006_Efficiency extends MS2Diploid {
  public APBC2006_Efficiency() {
    DIR = "papers" + File.separator + "kingroup2006_apbc" + File.separator + "output";
    GROUP_SIZE = 5;
    MIN_N_GROUPS = 10;
    MAX_N_GROUPS = 100;
    N_GROUPS_STEP = 10;
    N_ALLELES = 10;

    N_LOCI = 5;

    N_TRIALS = 10; // 100-paper
  }
  public void calcEfficiency() {
    String name = (FILE + "_GS" + GROUP_SIZE + "_NL" + N_LOCI + "_NA" + N_ALLELES);
    calcEfficiencyByNGroups(DIR, name + "_dist.csv", name + "_time.csv");
  }
}
