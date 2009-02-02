package papers.inpress.kingroup2006_apbc.v1.efficiency;
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
 * User: jc138691, Date: 27/09/2005, Time: 13:32:00
 */
public class APBC2006_EfficiencyMS extends APBC2006_Efficiency
{
  private final MSAlgModel ALG_MODEL;
  public int WIND_SIZE_LIMIT = 2;
  public static void main(String[] args) {
    APBC2006_EfficiencyMS test = new APBC2006_EfficiencyMS();
    LOG.setTrace(true);
    test.calcEfficiency();
    System.exit(0);
  }
  public APBC2006_EfficiencyMS() {
    N_TRIALS = 100; // 100-paper
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
    double dist = new LitowDistance().distance(partA, partB);
    return new Efficiency(dist, 0, d);
  }
}