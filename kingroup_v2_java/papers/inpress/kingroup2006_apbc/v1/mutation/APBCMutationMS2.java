package papers.inpress.kingroup2006_apbc.v1.mutation;
import kingroup.population.OldPop;
import kingroup.population.PopErrorFactory;
import papers.inpress.kingroup2006_apbc.v1.accuracy.APBCAccuracyMS2;

import javax.iox.LOG;
import java.io.File;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 28/06/2005, Time: 17:42:21
 */
public class APBCMutationMS2 extends APBCAccuracyMS2 {
  public static final int ERROR_PERCENTAGE = 2;
  public static void main(String[] args) {
    APBCMutationMS2 test = new APBCMutationMS2();
    LOG.setTrace(true);
    test.run_P50_skewed();
//    test.runP50_SDR_test();
//    test.run_P50();
    System.exit(0);
  }
  public APBCMutationMS2() {
    DIR += (File.separator + "mutation" + ERROR_PERCENTAGE);
    N_TRIALS = 100; // 100-paper
    FILE = "MS2";
  }
  protected double calcOneDistance(OldPop popA) {
    OldPop pop = PopErrorFactory.makeLociMutationFrom(popA, ERROR_PERCENTAGE);
    return super.calcOneDistance(pop);
  }
}