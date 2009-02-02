package papers.kingroup2005b_simpson.v1;
import junit.framework.TestCase;
import kingroup.genetics.OldAlleleFreq;
import kingroup.genetics.OldAlleleFreqFactory;
import kingroup.genotype.GenotypeFactory;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PopToPartitionFactory;
import kingroup.population.OldPop;
import kingroup.population.OldPopToSysPopFactory;
import kingroup.population.PopBuilderSibGroups;
import kingroup.project.KinGroupProjectV1;
import kingroup_v2.partition.distance.LitowDistance;
import kingroup_v2.partition.ms.MSAlgModel;
import kingroup_v2.partition.ms.MSAlgV2;
import kingroup_v2.partition.simpson.DiploidSibship;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistLocusAvr;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.iox.LOG;
import javax.langx.SystemX;
import javax.utilx.DoubleArrayByInt;
import javax.utilx.arrays.IntVec;
import javax.utilx.pair.SumByInt;
import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 23/05/2005, Time: 10:17:02
 */
public class SimpsEfficiencyMS extends SimpsAccuracyMS {
  private final MSAlgModel ALG_MODEL;
  public static void main(String[] args)
  {
    SimpsEfficiencyMS test = new SimpsEfficiencyMS();
    LOG.setTrace(true);
//      test.calcUsedP25();
//      test.calcPartitionSize();// for counting the partition space size//
    test.calcEfficiencyByGroupSize();
    test.calcEfficiencyByNumGroups();
    System.exit(0);
  }
  public SimpsEfficiencyMS() {
    KinGroupProjectV1.makeInstance("SimpsEfficiencyMS", "1");
    N_TRIALS = 100;
    WIND_SIZE_LIMIT = 1000000;// for counting the partition space size
//      WIND_SIZE_LIMIT = 10;
    DIR = "simps_win";
    DIR += "_W" + WIND_SIZE_LIMIT;

    ALG_MODEL = new MSAlgModel();
    ALG_MODEL.setSibshipAlg(new DiploidSibship());
    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusAvr());
    ALG_MODEL.setWindSize(WIND_SIZE_LIMIT);
  }
//   public void calcUsedP25() {
//      calcUsedPartitions(DIR, "used_partitions_25x1_NA"+N_ALLELES +".csv"
//              , ButlerFamilyData.BUTLER_25x1, false, N_ALLELES);
//      calcUsedPartitions(DIR, "used_partitions_5x5_NA"+N_ALLELES +".csv"
//              , ButlerFamilyData.BUTLER_5x5, false, N_ALLELES);
//      calcUsedPartitions(DIR, "used_partitions_12_NA"+N_ALLELES +".csv"
//              , ButlerFamilyData.BUTLER_12_5_5_2_1, false, N_ALLELES);
//      calcUsedPartitions(DIR, "used_partitions_21_NA"+N_ALLELES +".csv"
//              , ButlerFamilyData.BUTLER_21_1_1_1_1, false, N_ALLELES);
//   }
  public void calcEfficiencyByGroupSize() {
    WIND_SIZE_LIMIT = 10;
    DIR = "simps_win";
    DIR += "_W" + WIND_SIZE_LIMIT;
    MIN_GROUP_SIZE = 2;
    MAX_GROUP_SIZE = 40;
    N_ALLELES = 10;
    N_LOCI = 5;
    N_GROUPS = 5;
    N_TRIALS = 10;
    String marker = "NG" + N_GROUPS + "_NL" + N_LOCI + "_NA" + N_ALLELES;
    calcEfficiencyByGroupSize(DIR, marker + "_efficiency.csv"
      , marker + "_accuracy.csv"
      , marker + "_time.csv", 2);
  }
  public void calcEfficiencyByNumGroups() {
    WIND_SIZE_LIMIT = 2;
    DIR = "simps_win";
    DIR += "_W" + WIND_SIZE_LIMIT;
    GROUP_SIZE = 5;
    MIN_N_GROUPS = 10;
    MAX_N_GROUPS = 100;
    N_GROUPS_STEP = 10;
    N_ALLELES = 10;
    N_LOCI = 5;
    N_TRIALS = 100;
    String marker = "GS" + GROUP_SIZE + "_NL" + N_LOCI + "_NA" + N_ALLELES;
    calcEfficiencyByNGroups(DIR//, marker + "_efficiency.csv"
      , marker + "_dist.csv"
      , marker + "_time.csv");
  }
  public void calcPartitionSize() {
    WIND_SIZE_LIMIT = 1000000;// for counting the partition space size
    DIR = "simps_win";
    DIR += "_W" + WIND_SIZE_LIMIT;
    N_ALLELES = 10;
    N_LOCI = 5;
    GROUP_SIZE = 1;
    N_GROUPS_STEP = 1;
    MAX_N_GROUPS = 11;
    String marker = "PartitionSize_Popx5_NL" + N_LOCI + "_NA" + N_ALLELES;
//      String marker = "newSib_POPx5_NL"+N_LOCI+"_NA"+N_ALLELES;
    calcEfficiencyByNGroups(DIR//, marker + "_efficiency.csv"
      , marker + "_dist.csv"
      , marker + "_time.csv");
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
    double d = (double) (SystemX.time() - time) / TIME_SCALE;
    int dist = new LitowDistance().distance(partA, partB);
    return new Efficiency(dist, 0, d);
  }
  public void calcEfficiencyByGroupSize(String dirName, String efficFileName
    , String distFileName
    , String timeFileName
    , int groupSizeStep) {
    POP_MODEL.setIncParents(false);
    allTimes_ = new DoubleArrayByInt();
    allNumGroups_ = new DoubleArrayByInt();
    allDist_ = new DoubleArrayByInt();
    count_ = new SumByInt();
    for (int groupSize = MIN_GROUP_SIZE; groupSize <= MAX_GROUP_SIZE; groupSize += groupSizeStep) {
      int[] groups = IntVec.makeArray(N_GROUPS, groupSize);
      POP_MODEL.setSize(N_GROUPS * groupSize);
      POP_MODEL.setNumLoci(N_LOCI);
      POP_MODEL.setNumAlleles(N_ALLELES);
      OldAlleleFreq freq = OldAlleleFreqFactory.makeAlleleFreq(POP_MODEL);
      freq.normalize(1.0f, false);
      LOG.report(this, "popSize=" + POP_MODEL.getSize());
      for (int i = 0; i < N_TRIALS; i++) {
        GenotypeFactory.init();
        OldPop popA = new PopBuilderSibGroups(freq).buildSibGroups(groups);
        TestCase.assertEquals(POP_MODEL.getSize(), popA.size());
        Efficiency res = calcEfficiency(popA);
        allTimes_.add(POP_MODEL.getSize(), res.time);
        allDist_.add(POP_MODEL.getSize(), 100. * res.distance / POP_MODEL.getSize());
        allNumGroups_.add(POP_MODEL.getSize(), res.numGroups);
        count_.sum(POP_MODEL.getSize(), 1);
      }
    }
    LOG.saveToFile(allTimes_, dirName, timeFileName);
    LOG.saveToFile(allDist_, dirName, distFileName);
    LOG.saveToFile(allNumGroups_, dirName, efficFileName);
    LOG.saveToFile(allTimes_, dirName + File.separator + "P" + POP_SIZE + "_" + N_TRIALS, timeFileName);
    LOG.saveToFile(allDist_, dirName + File.separator + "P" + POP_SIZE + "_" + N_TRIALS, distFileName);
    LOG.saveToFile(allNumGroups_, dirName + File.separator + "P" + POP_SIZE + "_" + N_TRIALS, efficFileName);
  }
}
