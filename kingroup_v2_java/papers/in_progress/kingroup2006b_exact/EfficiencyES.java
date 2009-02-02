package papers.in_progress.kingroup2006b_exact;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PopToPartitionFactory;
import kingroup.population.OldPop;
import kingroup.population.OldPopToSysPopFactory;
import kingroup_v2.partition.distance.LitowDistance;
import kingroup_v2.partition.simpson.DiploidSibship;
import kingroup_v2.partition.simpson.exact.SimpsExactAlg;
import kingroup_v2.partition.simpson.exact.SimpsExactAlgModel;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistLocusAvr;
import kingroup_v2.pop.sample.sys.SysPop;
import papers.kingroup2005b_simpson.v1.Efficiency;
import papers.kingroup2005b_simpson.v1.SimpsAccuracy;

import javax.iox.LOG;
import javax.langx.SystemX;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 1/07/2005, Time: 16:52:36
 */
public class EfficiencyES extends SimpsAccuracy {
  private SimpsExactAlgModel ALG_MODEL = new SimpsExactAlgModel();

  public static void main(String[] args) {
    EfficiencyES test = new EfficiencyES();
    LOG.setTrace(true);
    test.calcEfficiencyByNumGroups();
    System.exit(0);
  }
  public EfficiencyES()
  {
    ALG_MODEL = new SimpsExactAlgModel();
    ALG_MODEL.setSibshipAlg(new DiploidSibship());
    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusAvr());
  }
  public void calcEfficiencyByNumGroups() {
    N_ALLELES = 10;
    N_LOCI = 5;
    GROUP_SIZE = 5;
    MIN_N_GROUPS = 2;
    MAX_N_GROUPS = 20;
    N_GROUPS_STEP = 2;
//    POP_FACTORY = new SimpsPopLocusMinFactory();
    N_TRIALS = 10; // 100-paper
    DIR = "exact_simps";
    String marker = "ES_GS" + GROUP_SIZE + "_NL" + N_LOCI + "_NA" + N_ALLELES;
    calcEfficiencyByNGroups(DIR//, marker + "_efficiency.csv"
      , marker + "_dist.csv"
      , marker + "_time.csv");
  }
  protected Efficiency calcEfficiency(OldPop popA) {
    PopToPartitionFactory factory = PopToPartitionFactory.makeInstance(popA); // must be called here, since we created a new population
    Partition partA = factory.makeFromPopulation(popA);
//      LOG.trace(this, "A=", partA);
    SysPop pop = OldPopToSysPopFactory.makePopFrom(popA);
    SimpsExactAlg method = new SimpsExactAlg(pop, ALG_MODEL); // NOTE!! SimpsExactAlg
    long time = SystemX.time();
    Partition partB = method.partition();
    double d = (double) (SystemX.time() - time) / TIME_SCALE;
    int dist = new LitowDistance().distance(partA, partB);
    return new Efficiency(dist, 0, d);
  }
}
