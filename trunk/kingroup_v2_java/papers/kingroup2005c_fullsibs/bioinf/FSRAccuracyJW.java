package papers.kingroup2005c_fullsibs.bioinf;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PopToPartitionFactory;
import kingroup.population.OldPop;
import kingroup.population.OldPopToSysPopFactory;
import kingroup_v2.partition.distance.LitowDistance;
import kingroup_v2.partition.jw.JWAlg;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.iox.LOG;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 3/08/2005, Time: 15:11:57
 */
public class FSRAccuracyJW extends FSRAccuracy {

  public static void main(String[] args) {
    FSRAccuracyJW test = new FSRAccuracyJW();
    LOG.setTrace(true);
    test.setup_r2();
    test.run_n20_uniform();
//      test.run_r2_uniform();
//      test.run_r5_uniform();
//      test.run_r10_uniform();
    System.exit(0);
  }
  public void setup_r2() {
    POP_SIZE = -1;
    POP_MODEL.setSize(POP_SIZE);
    N_ALLELES = 10;
    MIN_NUM_LOCI = 1; // 1-paper
    MAX_NUM_LOCI = 12;//12-paper
    N_TRIALS = 10; // 100-paper
    FILE = "JW_U";
  }
  protected double calcOneDistance(OldPop popA) {
    PopToPartitionFactory factory = PopToPartitionFactory.makeInstance(popA); // must be called here, since we created a new population
    Partition partA = factory.makeFromPopulation(popA);
    SysPop pop = OldPopToSysPopFactory.makePopFrom(popA);
    JWAlg method = new JWAlg(pop, popA.getAlleleFreq());
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