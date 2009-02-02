package papers.inpress.kingroup2006_apbc.v1.efficiency;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PopToPartitionFactory;
import kingroup.population.OldPop;
import kingroup.population.OldPopToSysPopFactory;
import kingroup_v2.partition.PartitionAccuracyTester;
import kingroup_v2.partition.distance.LitowDistance;
import kingroup_v2.partition.ms2.MS2AlgV2;
import kingroup_v2.pop.sample.sys.SysPop;
import papers.kingroup2005b_simpson.v1.Efficiency;

import javax.iox.LOG;
import javax.langx.SystemX;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/09/2005, Time: 13:41:09
 */
public class APBC2006_EfficiencyMS2 extends APBC2006_EfficiencyMS {
  public static void main(String[] args) {
    APBC2006_EfficiencyMS2 test = new APBC2006_EfficiencyMS2();
    LOG.setTrace(true);
    test.calcEfficiency();
    System.exit(0);
  }
  public APBC2006_EfficiencyMS2() {

    // LIMIT
//    GROUP_SIZE = 10;
//    GROUP_SIZE = 20;
//    GROUP_SIZE = 30;
    GROUP_SIZE = 50;
    MIN_N_GROUPS = 2;
    MAX_N_GROUPS = 10;
    N_GROUPS_STEP = 1;
    N_ALLELES = 10;
    N_LOCI = 1;

    N_TRIALS = 100; // 100-paper
    FILE = "MS2";
  }
  protected Efficiency calcEfficiency(OldPop popA) {
    PopToPartitionFactory factory = PopToPartitionFactory.makeInstance(popA); // must be called here, since we created a new population
    Partition partA = factory.makeFromPopulation(popA);
//      LOG.trace(this, "A=", partA);
    SysPop pop = OldPopToSysPopFactory.makePopFrom(popA);
    MS2AlgV2 method = new MS2AlgV2(pop);
    long time = SystemX.time();
//    LOG.report(this, "time=" + time);
    Partition partB = method.partition();
    time = SystemX.time() - time;
//    LOG.report(this, "time=" + time);
    double d = (double) time;
//    LOG.report(this, "(double)time=" + d);
    d = d / PartitionAccuracyTester.TIME_SCALE;
//    LOG.report(this, "(sec)d=" + d);
    double dist = new LitowDistance().distance(partA, partB);
    return new Efficiency(dist, 0, d);
  }
}