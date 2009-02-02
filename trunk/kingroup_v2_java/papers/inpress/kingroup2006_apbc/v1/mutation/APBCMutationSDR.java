package papers.inpress.kingroup2006_apbc.v1.mutation;
import kingroup.papers.butler2004.family.ButlerFamilyData;
import kingroup.population.OldPop;
import kingroup.population.PopErrorFactory;
import papers.inpress.kingroup2006_apbc.v1.accuracy.APBCAccuracySDR;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;
import java.io.File;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 29/06/2005, Time: 09:00:51
 */
public class APBCMutationSDR extends APBCAccuracySDR {
  private static ProjectLogger log = ProjectLogger.getLogger(APBCMutationSDR.class.getName());
  public static final int ERROR_PERCENTAGE = 2;
//   public static final int ERROR_PERCENTAGE = 10;
  public static void main(String[] args) {
    APBCMutationSDR test = new APBCMutationSDR();
    LOG.setTrace(true);
    test.run_P50_skewed();
//    test.runP50_SDR_test();
//    test.setup_P50();
//    test.run_P50();
    System.exit(0);
  }
  public APBCMutationSDR() {
    DIR += (File.separator + "mutation" + ERROR_PERCENTAGE);
    N_TRIALS = 100; // 100-paper
// TEST
//    POP_SIZE = 10;
//    POP_MODEL.setSize(POP_SIZE);
//    MIN_NUM_LOCI = 15; // 15-test
  }
  protected double calcOneDistance(OldPop popA) {
//    // log.info("popA"+popA);
    OldPop pop = PopErrorFactory.makeLociMutationFrom(popA, ERROR_PERCENTAGE);
//    // log.info("pop"+pop);
    return super.calcOneDistance(pop);
  }
  public void runTest() {
//      calcAccuracyByLoci(DIR, FILE+"_2x5S_NA"+N_ALLELES+".csv"
//              , ButlerFamilyData.BUTLER_2x5, false, N_ALLELES);
    calcAccuracyByLoci(DIR, FILE + "_1x10S_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_1x10, N_ALLELES);
  }
}