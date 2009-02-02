package papers.kingroup2005c_fullsibs.bioinf;
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
 * User: jc138691, Date: 3/08/2005, Time: 16:00:29
 */
public class FSRAccuracySIMPS extends FSRAccuracyMS2 {
  private final SIMPS2AlgModel ALG_MODEL;
  private int SIMPS_ITERATIONS = 1000;
  public static void main(String[] args) {
    FSRAccuracySIMPS test = new FSRAccuracySIMPS();
    LOG.setTrace(true);
    test.setup_r2();
    test.run_n20_uniform();
//      test.run_r2_uniform();
//      test.run_r5_uniform();
//      test.run_r10_uniform();
    System.exit(0);
  }
  public FSRAccuracySIMPS()
  {
    ALG_MODEL = new SIMPS2AlgModel();
    ALG_MODEL.setNumIters(SIMPS_ITERATIONS);
  }

  public void setup_r2() {
    SIMPS_ITERATIONS = 10000;
    POP_SIZE = -1;
    POP_MODEL.setSize(POP_SIZE);
    N_ALLELES = 10;
    MIN_NUM_LOCI = 1; // 1-paper
    MAX_NUM_LOCI = 12;//12-paper
    N_TRIALS = 100; // 100-paper
    FILE = "SIMPS_U";

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
