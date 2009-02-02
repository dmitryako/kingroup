package papers.kingroup2005c_fullsibs.bioinf;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PopToPartitionFactory;
import kingroup.population.OldPop;
import kingroup.population.OldPopToSysPopFactory;
import kingroup_v2.partition.distance.LitowDistance;
import kingroup_v2.partition.ms2.MS2AlgV2;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.iox.LOG;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/07/2005, Time: 10:12:36
 */
public class FSRAccuracyMS2 extends FSRAccuracy {
  
  public static void main(String[] args) {
    FSRAccuracyMS2 test = new FSRAccuracyMS2();
    LOG.setTrace(true);
    test.setup_r2();
    test.run_random();
//      test.run_n20_uniform();
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
    N_TRIALS = 100; // 100-paper
    FILE = "MS2";
  }
  protected double calcOneDistance(OldPop popA) {
    PopToPartitionFactory factory = PopToPartitionFactory.makeInstance(popA); // must be called here, since we created a new population
    Partition partA = factory.makeFromPopulation(popA);
    SysPop pop = OldPopToSysPopFactory.makePopFrom(popA);
    MS2AlgV2 method = new MS2AlgV2(pop);
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