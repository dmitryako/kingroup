package papers.kingroup2005c_ms2.haplodip;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PopToPartitionFactory;
import kingroup.population.OldPop;
import kingroup.population.OldPopToSysPopFactory;
import kingroup_v2.partition.distance.LitowDistance;
import kingroup_v2.partition.ms.MSAlgModel;
import kingroup_v2.partition.ms.MSAlgV2;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistLocusAvr;
import kingroup_v2.partition.simpson.haplodip.HaplodipSibship;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 16/08/2005, Time: 15:54:17
 */
public class MS2HaplodipMS extends MS2Haplodip {
  private final MSAlgModel ALG_MODEL;
  public int MS_WIND_SIZE = 2;
  public static void main(String[] args) {
    MS2HaplodipMS test = new MS2HaplodipMS();
    LOG.setTrace(true);
//      test.run_P3();
//      test.run_P50_skewed_freq();
//      test.run_P50_skewed();
//      test.run_P50();
//    test.runP50_SDR_test();
    test.run_P50_with_halfsibs();
    System.exit(0);
  }
  public MS2HaplodipMS() {
    ALG_MODEL = new MSAlgModel();
    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusAvr());
    ALG_MODEL.setSibshipAlg(new HaplodipSibship());
    ALG_MODEL.setWindSize(MS_WIND_SIZE);

    N_TRIALS = 100; // 100-paper
    FILE = "MS";
  }
  protected double calcOneDistance(OldPop popA) {
    PopToPartitionFactory factory = PopToPartitionFactory.makeInstance(popA); // must be called here, since we created a new population
    Partition partA = factory.makeFromPopulation(popA);
//    SysPop pop = POP_FACTORY.makeHaplodipPopFrom(popA);
    SysPop pop = OldPopToSysPopFactory.makePopFrom(popA);

    MSAlgV2 method = new MSAlgV2(pop, ALG_MODEL);
    Partition partB = method.partition();
    int dist = new LitowDistance().distance(partA, partB);
    if (dist != 0) {
//         LOG.trace(this, "A=", partA);
//         LOG.trace(this, "B=", partB);
//         LOG.trace(this, "D(A,B)=", Integer.toString(dist));
    }
    return dist;
  }
}