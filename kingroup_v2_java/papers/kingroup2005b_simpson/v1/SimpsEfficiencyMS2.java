package papers.kingroup2005b_simpson.v1;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PopToPartitionFactory;
import kingroup.population.OldPop;
import kingroup.population.OldPopToSysPopFactory;
import kingroup_v2.partition.distance.LitowDistance;
import kingroup_v2.partition.ms2.MS2AlgV2;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.iox.LOG;
import javax.langx.SystemX;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/06/2005, Time: 13:04:29
 */
public class SimpsEfficiencyMS2 extends SimpsEfficiencyMS {
  public static void main(String[] args) {
    SimpsEfficiencyMS2 test = new SimpsEfficiencyMS2();
    LOG.setTrace(true);
    test.calcEfficiencyByNumGroups();
    System.exit(0);
  }
  public void calcEfficiencyByNumGroups() {
    DIR = "simps_win_v2";
    GROUP_SIZE = 5;
    MIN_N_GROUPS = 2;
    MAX_N_GROUPS = 100;
    N_GROUPS_STEP = 8;
    N_ALLELES = 10;
    N_LOCI = 5;
    N_TRIALS = 100;
    String marker = "GS" + GROUP_SIZE + "_NL" + N_LOCI + "_NA" + N_ALLELES;
    calcEfficiencyByNGroups(DIR//, marker + "_efficiency.csv"
      , marker + "_dist.csv"
      , marker + "_time.csv");
  }
  protected Efficiency calcEfficiency(OldPop popA) {
    PopToPartitionFactory factory = PopToPartitionFactory.makeInstance(popA); // must be called here, since we created a new population
    Partition partA = factory.makeFromPopulation(popA);
//      LOG.trace(this, "A=", partA);
    SysPop pop = OldPopToSysPopFactory.makePopFrom(popA);
//      MSAlgV2 method = new MSAlgV2(pop, WIND_SIZE_LIMIT);
    MS2AlgV2 method = new MS2AlgV2(pop);
    long time = SystemX.time();
    Partition partB = method.partition();
    double d = (double) (SystemX.time() - time) / TIME_SCALE;
    int dist = new LitowDistance().distance(partA, partB);
    return new Efficiency(dist, 0, d);
  }
}
