package papers.kingroup2005c_ms2;
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
 * User: jc138691, Date: 16/08/2005, Time: 11:36:18
 */
public class MS2DiploidMS2 extends MS2DiploidMS {
  public static void main(String[] args) {
    MS2DiploidMS2 test = new MS2DiploidMS2();
    LOG.setTrace(true);
//      test.run_rxg();
//      test.run_P50_skewed_freq();
//      test.run_P50_skewed();
    test.run_P50();
//      test.run_P50_with_parents();
//      test.run_P50_with_halfsibs();
//      test.runP50_SDR_test();
    System.exit(0);
  }
  public MS2DiploidMS2() {
//      MIN_NUM_LOCI = 8;
//      MAX_NUM_LOCI = 8;
    POP_SIZE = 50;
    POP_MODEL.setSize(POP_SIZE);
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
//         LOG.trace(this, "pop=", pop);
//      }
    return dist;
  }
}