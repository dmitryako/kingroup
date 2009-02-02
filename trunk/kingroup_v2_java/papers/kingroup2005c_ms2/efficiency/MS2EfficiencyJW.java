package papers.kingroup2005c_ms2.efficiency;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PopToPartitionFactory;
import kingroup.population.OldPop;
import kingroup.population.OldPopToSysPopFactory;
import kingroup_v2.partition.PartitionAccuracyTester;
import kingroup_v2.partition.distance.LitowDistance;
import kingroup_v2.partition.jw.JWAlg_v2;
import kingroup_v2.pop.sample.sys.SysPop;
import papers.kingroup2005b_simpson.v1.Efficiency;

import javax.iox.LOG;
import javax.langx.SystemX;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 8/09/2005, Time: 15:07:15
 */
public class MS2EfficiencyJW extends MS2EfficiencyMS {
  public static void main(String[] args) {
    MS2EfficiencyJW test = new MS2EfficiencyJW();
    LOG.setTrace(true);
    test.calcEfficiency();
    System.exit(0);
  }
  public MS2EfficiencyJW() {
    N_TRIALS = 100; // 100-paper
    FILE = "JW";
  }
  public void calcEfficiency() {
    setUpR5();
    super.calcEfficiency();
    setUpR10();
    super.calcEfficiency();
  }
  private void setUpR5() {
    GROUP_SIZE = 5; // 5 - paper
    N_GROUPS_STEP = 2;
    MIN_N_GROUPS = 2;
    MAX_N_GROUPS = 10;
    N_GROUPS = 5; // 5 - paper
    GROUP_SIZE_STEP = 2;
    MIN_GROUP_SIZE = 4;
    MAX_GROUP_SIZE = 10;
  }
  private void setUpR10() {
    GROUP_SIZE = 10; // 10 - paper
    N_GROUPS_STEP = 1;
    MIN_N_GROUPS = 3;
    MAX_N_GROUPS = 5;
    N_GROUPS = 10; // 10 - paper
    GROUP_SIZE_STEP = 1;
    MIN_GROUP_SIZE = 3;
    MAX_GROUP_SIZE = 5;
  }
  protected Efficiency calcEfficiency(OldPop popA) {
    PopToPartitionFactory factory = PopToPartitionFactory.makeInstance(popA); // must be called here, since we created a new population
    Partition partA = factory.makeFromPopulation(popA);
//      LOG.trace(this, "A=", partA);
    SysPop pop = OldPopToSysPopFactory.makePopFrom(popA);
    JWAlg_v2 method = new JWAlg_v2(pop, popA.getAlleleFreq());
    long time = SystemX.time();
    LOG.report(this, "time=" + time);
    Partition partB = method.partition();
    time = SystemX.time() - time;
    LOG.report(this, "time=" + time);
    double d = (double) time;
    LOG.report(this, "(double)time=" + d);
    d = d / PartitionAccuracyTester.TIME_SCALE;
    LOG.report(this, "(sec)d=" + d);
    int dist = new LitowDistance().distance(partA, partB);
    return new Efficiency(dist, -1, d);
  }
}