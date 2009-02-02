package papers.kingroup2005c_ms2.haplodip;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PopToPartitionFactory;
import kingroup.population.OldPop;
import kingroup.population.OldPopToSysPopFactory;
import kingroup_v2.partition.distance.LitowDistance;
import kingroup_v2.partition.ms2.MS2AlgModel;
import kingroup_v2.partition.ms2.MS2AlgV2;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistLocusAvr;
import kingroup_v2.partition.simpson.haplodip.HaplodipSibship;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 16/08/2005, Time: 15:53:53
 */
public class MS2HaplodipMS2 extends MS2HaplodipMS {
  private final MS2AlgModel ALG_MODEL;
  public static void main(String[] args)
  {
    MS2HaplodipMS2 test = new MS2HaplodipMS2();
    LOG.setTrace(true);
//      test.run_rxg();
//      test.run_P50_skewed_freq();
//      test.run_P50_skewed();
//      test.run_P50();
//    test.runP50_SDR_test();
    test.run_P50_with_halfsibs();
    System.exit(0);
  }
  public MS2HaplodipMS2() {
    N_TRIALS = 100; // 100-paper
    FILE = "MS2";

    ALG_MODEL = new MS2AlgModel();
    ALG_MODEL.setSibshipAlg(new HaplodipSibship());
    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusAvr());
  }
  protected double calcOneDistance(OldPop popA) {
    PopToPartitionFactory factory = PopToPartitionFactory.makeInstance(popA); // must be called here, since we created a new population
    Partition partA = factory.makeFromPopulation(popA);
    SysPop pop = OldPopToSysPopFactory.makePopFrom(popA);
//    SysPop pop = POP_FACTORY.makeHaplodipPopFrom(popA);
    MS2AlgV2 method = new MS2AlgV2(pop, ALG_MODEL);
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