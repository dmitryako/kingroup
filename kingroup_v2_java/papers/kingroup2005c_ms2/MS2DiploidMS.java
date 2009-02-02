package papers.kingroup2005c_ms2;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PopToPartitionFactory;
import kingroup.population.OldPop;
import kingroup.population.OldPopToSysPopFactory;
import kingroup_v2.partition.distance.LitowDistance;
import kingroup_v2.partition.ms.MSAlgModel;
import kingroup_v2.partition.ms.MSAlgV2;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 16/08/2005, Time: 13:47:40
 */
public class MS2DiploidMS extends MS2Diploid {
  private final MSAlgModel ALG_MODEL;
  public int WIND_SIZE_LIMIT = 2;
  public static void main(String[] args) {
    MS2DiploidMS test = new MS2DiploidMS();
    LOG.setTrace(true);
//      test.run_rxg();
//      test.run_P50_skewed_freq();
//      test.run_P50_skewed();
//      test.run_P50();
//    test.runP50_SDR_test();
    test.run_P50_with_halfsibs();
    System.exit(0);
  }
  public MS2DiploidMS() {
    WIND_SIZE_LIMIT = 2;
    N_TRIALS = 100; // 100-paper
    FILE = "MS";

    ALG_MODEL = new MSAlgModel();
    ALG_MODEL.setWindSize(WIND_SIZE_LIMIT);
  }
  protected double calcOneDistance(OldPop popA) {
    PopToPartitionFactory factory = PopToPartitionFactory.makeInstance(popA); // must be called here, since we created a new population
    Partition partA = factory.makeFromPopulation(popA);
//      LOG.trace(this, "A=", partA);
    SysPop pop = OldPopToSysPopFactory.makePopFrom(popA);
    MSAlgV2 method = new MSAlgV2(pop, ALG_MODEL);
    Partition partB = method.partition();
//      LOG.trace(this, "B=", partB);
    int dist = new LitowDistance().distance(partA, partB);
//      LOG.trace(this, "D(A,B)=", Integer.toString(dist));
    return dist;
  }
  public void calcEfficiencyByGroupSize() {
    WIND_SIZE_LIMIT = 10;
    DIR = "simps_win";
    DIR += "_W" + WIND_SIZE_LIMIT;
    MIN_GROUP_SIZE = 2;
    MAX_GROUP_SIZE = 40;
    GROUP_SIZE_STEP = 2;
    N_ALLELES = 10;
    N_LOCI = 5;
    N_GROUPS = 5;
    N_TRIALS = 10;
    String marker = "NG" + N_GROUPS + "_NL" + N_LOCI + "_NA" + N_ALLELES;
    calcEfficiencyByGroupSize(DIR//, marker + "_efficiency.csv"
      , marker + "_dist.csv"
      , marker + "_time.csv");
  }
}