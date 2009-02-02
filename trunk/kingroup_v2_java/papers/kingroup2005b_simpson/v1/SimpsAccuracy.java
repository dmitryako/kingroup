package papers.kingroup2005b_simpson.v1;
import kingroup.papers.butler2004.family.ButlerFamilyData;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PopToPartitionFactory;
import kingroup.population.OldPop;
import kingroup.population.OldPopToSysPopFactory;
import kingroup_v2.partition.distance.LitowDistance;
import kingroup_v2.partition.simpson.DiploidSibship;
import kingroup_v2.partition.simpson.SIMPS2Alg;
import kingroup_v2.partition.simpson.SIMPS2AlgModel;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.iox.LOG;
import javax.langx.SystemX;
import javax.utilx.DoubleArrayByInt;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 10/05/2005, Time: 08:02:25
 */
public class SimpsAccuracy extends OutputDir {

  private final SIMPS2AlgModel ALG_MODEL;
  protected DoubleArrayByInt allNumGroups_ = new DoubleArrayByInt();
  protected int SIMPS_ITERATIONS = 100000;  // 100,000 paper
//   private static final int SIMPS_ITERATIONS_MAX = 100000;
  public SimpsAccuracy() {
    ALG_MODEL = new SIMPS2AlgModel();
    ALG_MODEL.setSibshipAlg(new DiploidSibship());
  }
  public static void main(String[] args) {
    SimpsAccuracy test = new SimpsAccuracy();
    LOG.setTrace(true);
    test.calcEfficiencyByNGroups();
//      test.calcEfficiencyByGroupSize();

    //
//      test.setupP50();
//      test.runP50_SetA();
//      test.runP50_SetB();
//      test.runP50_SetA_parents();

//      test.setupP25();
//      test.calcDistP50_parents();
//      test.runP25();
//      test.calcUsedP25();
//      test.runP6();
    System.exit(0);
  }
  public void calcEfficiencyByNGroups() {
    N_ALLELES = 10;
    N_LOCI = 5;
    GROUP_SIZE = 5;
    MIN_N_GROUPS = 2;
    MAX_N_GROUPS = 30;
    N_GROUPS_STEP = 2;
    SIMPS_ITERATIONS = 100000;
    N_TRIALS = 10; // 100-paper
    DIR = "simpson";
    DIR += SIMPS_ITERATIONS;
    String marker = "GS" + GROUP_SIZE + "_NL" + N_LOCI + "_NA" + N_ALLELES;
    calcEfficiencyByNGroups(DIR//, marker + "_efficiency.csv"
      , marker + "_dist.csv"
      , marker + "_time.csv");
  }
  public void calcSimpsByNumGroupsN3() {
    N_ALLELES = 10;
    N_LOCI = 5;
    GROUP_SIZE = 5;
    MIN_N_GROUPS = 2;
    MAX_N_GROUPS = 40;
    N_GROUPS_STEP = 2;
    N_TRIALS = 100; // 100-paper
    DIR = "simpsonN3_5";   //SIMPS_ITERATIONS=n^3/5
//      DIR = "simpsonN3_4";   //SIMPS_ITERATIONS=n^3/4
//      DIR = "simpsonN3_3";   //SIMPS_ITERATIONS=n^3/3
//      DIR = "simpsonN3";   //SIMPS_ITERATIONS=n^3
    String marker = "GS" + GROUP_SIZE + "_NL" + N_LOCI + "_NA" + N_ALLELES;
    calcEfficiencyByNGroups(DIR//, marker + "_efficiency.csv"
      , marker + "_accuracy.csv"
      , marker + "_time.csv");
  }
  public void runP25() {
    calcAccuracyByLoci(DIR, FILE + "_25x1_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_25x1, N_ALLELES);
    calcAccuracyByLoci(DIR, FILE + "_5x5_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_5x5, N_ALLELES);
    calcAccuracyByLoci(DIR, FILE + "_12_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_12_5_5_2_1, N_ALLELES);
    calcAccuracyByLoci(DIR, FILE + "_21_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_21_1_1_1_1, N_ALLELES);
  }
  public void setupP50() {
    POP_SIZE = 50;
    POP_MODEL.setSize(POP_SIZE);
    SIMPS_ITERATIONS = 100000; // 100,000 -paper
    N_ALLELES = 8;
    MIN_NUM_LOCI = 1; // 1-paper
    MAX_NUM_LOCI = 16;//15-paper
    N_TRIALS = 100; // 100-paper
    DIR = "simpson" + SIMPS_ITERATIONS;
    FILE = "simpson_P" + POP_SIZE;
  }
  public void setupP25() {
    POP_SIZE = 25;
    POP_MODEL.setSize(POP_SIZE);
    SIMPS_ITERATIONS = 10000; // 100,000 -paper
    N_ALLELES = 10;
    MIN_NUM_LOCI = 1; // 1-paper
    MAX_NUM_LOCI = 10;//15-paper
    N_TRIALS = 100; // 100-paper
    DIR = "simpson" + SIMPS_ITERATIONS;
    FILE = "simpson_P" + POP_SIZE;
  }
  public void calcDistP50_parents() {
    SIMPS_ITERATIONS = 100000; // 100,000 -paper
    N_ALLELES = 8;
    MIN_NUM_LOCI = 1; // 1-paper
    MAX_NUM_LOCI = 16;//15-paper
    N_TRIALS = 10; // 100-paper
    DIR = "simpson";
    POP_SIZE = 51;
    POP_MODEL.setSize(POP_SIZE);
    WITH_PARENTS = true;
    calcAccuracyByLoci(DIR, "simpson" + SIMPS_ITERATIONS + "_17x1s2p_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_17_OF_1, N_ALLELES);
    POP_SIZE = 50;
    POP_MODEL.setSize(POP_SIZE);
//      calcAccuracyByLoci(DIR, "simpson"+SIMPS_ITERATIONS+"_5x10S_NA"+N_ALLELES+".csv"
//              , ButlerFamilyData.BUTLER_5x10, false, N_ALLELES);
//      calcAccuracyByLoci(DIR, "simpson"+SIMPS_ITERATIONS+"_25S_NA"+N_ALLELES+".csv"
//              , ButlerFamilyData.BUTLER_25_10_10_4_1, false, N_ALLELES);
//      calcAccuracyByLoci(DIR, "simpson"+SIMPS_ITERATIONS+"_46S_NA"+N_ALLELES+".csv"
//              , ButlerFamilyData.BUTLER_46_1_1_1_1, false, N_ALLELES);
  }
//   public void calcUsedP25() {
//      calcUsedPartitions(DIR, "used_partitions"+SIMPS_ITERATIONS+"_25x1_NA"+N_ALLELES +".csv"
//              , ButlerFamilyData.BUTLER_25x1, false, N_ALLELES);
//      calcUsedPartitions(DIR, "used_partitions"+SIMPS_ITERATIONS+"_5x5_NA"+N_ALLELES +".csv"
//              , ButlerFamilyData.BUTLER_5x5, false, N_ALLELES);
//      calcUsedPartitions(DIR, "used_partitions"+SIMPS_ITERATIONS+"_12_NA"+N_ALLELES +".csv"
//              , ButlerFamilyData.BUTLER_12_5_5_2_1, false, N_ALLELES);
//      calcUsedPartitions(DIR, "used_partitions"+SIMPS_ITERATIONS+"_21_NA"+N_ALLELES +".csv"
//              , ButlerFamilyData.BUTLER_21_1_1_1_1, false, N_ALLELES);
//   }
  public void runP6() {
    calcAccuracyByLoci(DIR, "simpson" + SIMPS_ITERATIONS + "_P6_3_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_3_2_1, N_ALLELES);
  }
  protected double calcOneDistance(OldPop popA) {
    PopToPartitionFactory factory = PopToPartitionFactory.makeInstance(popA); // must be called here, since we created a new population
    Partition partA = factory.makeFromPopulation(popA);
//      LOG.trace(this, "A=", partA);
//    SysPop pop = OldPopToSysPopFactory.makePopFrom(popA);
    SysPop pop = OldPopToSysPopFactory.makePopFrom(popA);
    ALG_MODEL.setNumIters(SIMPS_ITERATIONS);
    SIMPS2Alg method = new SIMPS2Alg(pop, ALG_MODEL);
    Partition partB = method.partition();
//      LOG.trace(this, "B=", partB);
    int dist = new LitowDistance().distance(partA, partB);
//      LOG.trace(this, "D(A,B)=", Integer.toString(dist));
    return dist;
  }
  protected Efficiency calcEfficiency(OldPop popA) {
    PopToPartitionFactory factory = PopToPartitionFactory.makeInstance(popA); // must be called here, since we created a new population
    Partition partA = factory.makeFromPopulation(popA);
//      LOG.trace(this, "A=", partA);
//    SysPop pop = OldPopToSysPopFactory.makePopFrom(popA);
    SysPop pop = OldPopToSysPopFactory.makePopFrom(popA);

//      SIMPS_ITERATIONS = ((int)MathX.pow(POP_MODEL.size(), 3))/5;
//         SIMPS_ITERATIONS = Math.min(((int)MathX.pow(POP_MODEL.size(), 3))/5, SIMPS_ITERATIONS_MAX);
//         SIMPS_ITERATIONS = Math.min(((int)MathX.pow(POP_MODEL.getGroupSize(), 3)), SIMPS_ITERATIONS_MAX);
    ALG_MODEL.setNumIters(SIMPS_ITERATIONS);
    SIMPS2Alg method = new SIMPS2Alg(pop, ALG_MODEL);
    long time = SystemX.time();
    Partition partB = method.partition();
    double d = (double) (SystemX.time() - time) / TIME_SCALE;
    int dist = new LitowDistance().distance(partA, partB);
    return new Efficiency(dist, 0, d);
  }
}
