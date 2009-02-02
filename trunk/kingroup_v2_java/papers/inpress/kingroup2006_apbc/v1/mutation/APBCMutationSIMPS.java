package papers.inpress.kingroup2006_apbc.v1.mutation;
import kingroup.population.OldPop;
import kingroup.population.PopErrorFactory;
import papers.inpress.kingroup2006_apbc.v1.accuracy.APBCAccuracySIMPS;

import javax.iox.LOG;
import java.io.File;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 29/06/2005, Time: 17:46:52
 */
public class APBCMutationSIMPS extends APBCAccuracySIMPS {
  public static final int ERROR_PERCENTAGE = 2;
  public static void main(String[] args) {
    APBCMutationSIMPS test = new APBCMutationSIMPS();
    LOG.setTrace(true);
    test.run_P50_skewed();
//    test.runP50_SDR_test();
//    test.run_P50();
    System.exit(0);
  }
  public APBCMutationSIMPS() {
    DIR += (File.separator + "mutation" + ERROR_PERCENTAGE);
    N_TRIALS = 100; // 100-paper
  }
  protected double calcOneDistance(OldPop popA) {
    OldPop pop = PopErrorFactory.makeLociMutationFrom(popA, ERROR_PERCENTAGE);
    return super.calcOneDistance(pop);
  }
}