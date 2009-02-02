package papers.kingroup2005c_ms2.efficiency;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PopToPartitionFactory;
import kingroup.population.OldPop;
import kingroup.population.OldPopToSysPopFactory;
import kingroup_v2.partition.PartitionAccuracyTester;
import kingroup_v2.partition.distance.LitowDistance;
import kingroup_v2.partition.simpson.DiploidSibship;
import kingroup_v2.partition.simpson.SIMPS2Alg;
import kingroup_v2.partition.simpson.SIMPS2AlgModel;
import kingroup_v2.pop.sample.sys.SysPop;
import papers.kingroup2005b_simpson.v1.Efficiency;

import javax.iox.LOG;
import javax.langx.SystemX;
import javax.mathx.MathX;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 24/08/2005, Time: 12:08:42
 */
public class MS2EfficiencySIMPS extends MS2EfficiencyMS {
  private final SIMPS2AlgModel ALG_MODEL;
  private int SIMPS_ITERATIONS = 1000;
  private int SIMPS_POWER = 3;
  private int SIMPS_POWER_CONST = 5;
  public static void main(String[] args) {
    MS2EfficiencySIMPS test = new MS2EfficiencySIMPS();
    LOG.setTrace(true);
    test.calcEfficiency();
    System.exit(0);
  }
  public MS2EfficiencySIMPS() {
    SIMPS_POWER = 2;
    SIMPS_POWER_CONST = 4;
    FILE = "SIMPS_N" + SIMPS_POWER;
//      FILE = "SIMPS_N"+SIMPS_POWER+"_"+SIMPS_POWER_CONST;
    N_TRIALS = 10; // 100-paper

    ALG_MODEL = new SIMPS2AlgModel();
    ALG_MODEL.setSibshipAlg(new DiploidSibship());
    ALG_MODEL.setNumIters(SIMPS_ITERATIONS);
  }
  public void calcEfficiency() {
    setUpR5();
    super.calcEfficiency();
    setUpR10();
    super.calcEfficiency();
  }
  private void setUpR5() {
    GROUP_SIZE = 5;
    N_GROUPS_STEP = 5;
    MIN_N_GROUPS = 5;
    MAX_N_GROUPS = 40;
    N_GROUPS = 5;
    GROUP_SIZE_STEP = 5;
    MIN_GROUP_SIZE = 5;
    MAX_GROUP_SIZE = 40;
  }
  private void setUpR10() {
    GROUP_SIZE = 10;
    N_GROUPS_STEP = 2;
    MIN_N_GROUPS = 2;
    MAX_N_GROUPS = 20;
    N_GROUPS = 10;
    GROUP_SIZE_STEP = 2;
    MIN_GROUP_SIZE = 2;
    MAX_GROUP_SIZE = 20;
  }
  protected Efficiency calcEfficiency(OldPop popA) {
    PopToPartitionFactory factory = PopToPartitionFactory.makeInstance(popA); // must be called here, since we created a new population
    Partition partA = factory.makeFromPopulation(popA);
    SysPop pop = OldPopToSysPopFactory.makePopFrom(popA);
    SIMPS_ITERATIONS = ((int) MathX.pow(POP_MODEL.getSize(), SIMPS_POWER))
      * SIMPS_POWER_CONST;

    ALG_MODEL.setNumIters(SIMPS_ITERATIONS);
    SIMPS2Alg method = new SIMPS2Alg(pop, ALG_MODEL);
    long time = SystemX.time();
    Partition partB = method.partition();
    double d = (double) (SystemX.time() - time) / PartitionAccuracyTester.TIME_SCALE;
    int dist = new LitowDistance().distance(partA, partB);
    return new Efficiency(dist, 0, d);
  }
}