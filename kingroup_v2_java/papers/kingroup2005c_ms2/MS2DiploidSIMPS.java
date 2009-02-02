package papers.kingroup2005c_ms2;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PopToPartitionFactory;
import kingroup.population.OldPop;
import kingroup.population.OldPopToSysPopFactory;
import kingroup_v2.partition.distance.LitowDistance;
import kingroup_v2.partition.simpson.SIMPS2Alg;
import kingroup_v2.partition.simpson.SIMPS2AlgModel;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.iox.LOG;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 16/08/2005, Time: 12:38:44
 */
public class MS2DiploidSIMPS extends MS2Diploid {
  private final SIMPS2AlgModel ALG_MODEL;
  protected int SIMPS_ITERATIONS = 1000;
  
  public static void main(String[] args) {
    MS2DiploidSIMPS test = new MS2DiploidSIMPS();
    LOG.setTrace(true);
//      test.run_rxg();
//      test.run_P50_skewed_freq();
//      test.run_P50_skewed();
//      test.run_P50();
//      test.run_P50_with_parents();
//      test.runP50_SDR_test();
    test.run_P50_with_halfsibs();
//      System.exit(0);
  }
  public MS2DiploidSIMPS() {
    SIMPS_ITERATIONS = 100000; // 100,000-paper
    N_TRIALS = 100; // 100-paper
    FILE = "SIMPS";

    ALG_MODEL = new SIMPS2AlgModel();
    ALG_MODEL.setNumIters(SIMPS_ITERATIONS);
  }
  protected double calcOneDistance(OldPop popA) {
    PopToPartitionFactory factory = PopToPartitionFactory.makeInstance(popA); // must be called here, since we created a new population
    Partition partA = factory.makeFromPopulation(popA);
    SysPop pop = OldPopToSysPopFactory.makePopFrom(popA);
    SIMPS2Alg method = new SIMPS2Alg(pop, ALG_MODEL);
    Partition partB = method.partition();
    int dist = new LitowDistance().distance(partA, partB);
//      if (dist != 0) {
//         LOG.trace(this, "A=", partA);
//         LOG.trace(this, "B=", partB);
//         LOG.trace(this, "D(A,B)=", Integer.toString(dist));
//      }
    return dist;
  }
}