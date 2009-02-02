package papers.kingroup2005c_ms2.haplodip;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PopToPartitionFactory;
import kingroup.population.OldPop;
import kingroup.population.OldPopToSysPopFactory;
import kingroup_v2.partition.distance.LitowDistance;
import kingroup_v2.partition.jw.JWAlg_v2;
import kingroup_v2.partition.jw.JWAlg_v2_HapDip;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 7/09/2005, Time: 10:31:48
 */
public class MS2HaplodipJW extends MS2Haplodip
{
  private static ProjectLogger log = ProjectLogger.getLogger(MS2HaplodipJW.class.getName());

  
  public static void main(String[] args) {
    MS2HaplodipJW test = new MS2HaplodipJW();
    LOG.setTrace(true);
//      test.run_rxg();
//      test.run_P50_skewed_freq();
//      test.run_P50_skewed();
//      test.run_P20();
//      test.run_P12();
//    test.runP50_SDR_test();
//    test.run_P50_with_halfsibs();
    test.run_P50();
    System.exit(0);
  }
  public MS2HaplodipJW() {
//    POP_SIZE = 12;
//    MIN_NUM_LOCI = 5;
//    MAX_NUM_LOCI = 5;
//    N_TRIALS = 1; // 100-paper

    POP_SIZE = 50;
    N_TRIALS = 100; // 100-paper

    POP_MODEL.setSize(POP_SIZE);
    FILE = "JW";
  }
  protected double calcOneDistance(OldPop popA)
  {
//    log.info("popA=" + popA);

    PopToPartitionFactory factory = PopToPartitionFactory.makeInstance(popA); // must be called here, since we created a new population
    Partition partA = factory.makeFromPopulation(popA);
    log.info("partA=" + partA);

    SysPop pop = OldPopToSysPopFactory.makePopFrom(popA);
//    log.info("pop=\n" + pop);

    JWAlg_v2 method = new JWAlg_v2_HapDip(pop, popA.getAlleleFreq());
    method.setDebug(false);
    Partition partB = method.partition();
    log.info("partB=" + partB);

    int dist = new LitowDistance().distance(partA, partB);
    log.info("D(A,B)=" + dist);
    return dist;
  }
}