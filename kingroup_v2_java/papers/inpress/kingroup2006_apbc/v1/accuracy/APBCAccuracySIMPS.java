package papers.inpress.kingroup2006_apbc.v1.accuracy;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PopToPartitionFactory;
import kingroup.population.OldPop;
import kingroup.population.OldPopToSysPopFactory;
import kingroup_v2.partition.distance.LitowDistance;
import kingroup_v2.partition.simpson.DiploidSibship;
import kingroup_v2.partition.simpson.SIMPS2Alg;
import kingroup_v2.partition.simpson.SIMPS2AlgModel;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 29/06/2005, Time: 17:46:20
 */
public class APBCAccuracySIMPS extends APBCAccuracy {
  private final SIMPS2AlgModel ALG_MODEL;
  private int SIMPS_ITERATIONS = 1000;
  public static void main(String[] args) {
    APBCAccuracySIMPS test = new APBCAccuracySIMPS();
    LOG.setTrace(true);
    test.run_P50();
    System.exit(0);
  }
  public APBCAccuracySIMPS() {
    POP_SIZE = 50;
    POP_MODEL.setSize(POP_SIZE);
    N_ALLELES = 10;
    MIN_NUM_LOCI = 1; // 1-paper
    MAX_NUM_LOCI = 15;//15-paper
    N_TRIALS = 100; // 100-paper
    SIMPS_ITERATIONS = 100000;
    FILE = "SIMPS";

    ALG_MODEL = new SIMPS2AlgModel();
    ALG_MODEL.setSibshipAlg(new DiploidSibship());
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
