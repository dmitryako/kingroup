package papers.kingroup2005c_ms2.efficiency;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PopToPartitionFactory;
import kingroup.population.OldPop;
import kingroup.population.OldPopToSysPopFactory;
import kingroup_v2.partition.PartitionAccuracyTester;
import kingroup_v2.partition.distance.LitowDistance;
import kingroup_v2.partition.ms.MSAlgModel;
import kingroup_v2.partition.ms.MSAlgV2;
import kingroup_v2.partition.simpson.DiploidSibship;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistLocusAvr;
import kingroup_v2.pop.sample.sys.SysPop;
import papers.kingroup2005b_simpson.v1.Efficiency;

import javax.iox.LOG;
import javax.langx.SystemX;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 24/08/2005, Time: 10:06:44
 */
public class MS2EfficiencyMS extends MS2Efficiency {

  private final MSAlgModel ALG_MODEL;
  public int WIND_SIZE_LIMIT = 2;
  public static void main(String[] args) {
    MS2EfficiencyMS test = new MS2EfficiencyMS();
    LOG.setTrace(true);
    test.calcEfficiency();
    System.exit(0);
  }
  public MS2EfficiencyMS() {
    N_TRIALS = 10; // 100-paper
    FILE = "MS";

    ALG_MODEL = new MSAlgModel();
    ALG_MODEL.setSibshipAlg(new DiploidSibship());
    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusAvr());
    ALG_MODEL.setWindSize(WIND_SIZE_LIMIT);
  }
  protected Efficiency calcEfficiency(OldPop popA) {
    PopToPartitionFactory factory = PopToPartitionFactory.makeInstance(popA); // must be called here, since we created a new population
    Partition partA = factory.makeFromPopulation(popA);
//      LOG.trace(this, "A=", partA);
    SysPop pop = OldPopToSysPopFactory.makePopFrom(popA);
    MSAlgV2 method = new MSAlgV2(pop, ALG_MODEL);
//      MS2AlgV2 method = new MS2AlgV2(pop);
    long time = SystemX.time();
    Partition partB = method.partition();
    double d = (double) (SystemX.time() - time) / PartitionAccuracyTester.TIME_SCALE;
    int dist = new LitowDistance().distance(partA, partB);
    return new Efficiency(dist, 0, d);
  }
}
