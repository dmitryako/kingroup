package kingroup_v2.partition;
import junit.framework.TestCase;
import kingroup.genetics.OldAlleleFreq;
import kingroup.genetics.OldAlleleFreqFactory;
import kingroup.genotype.GenotypeFactory;
import kingroup.papers.butler2004.ButlerPopBuilder;
import kingroup.papers.butler2004.ButlerPopBuilderModel;
import kingroup.papers.butler2004.family.ButlerFamily;
import kingroup.papers.butler2004.family.ButlerFamilyData;
import kingroup.partition.PartitionModel;
import kingroup.population.MendelPopBuilder;
import kingroup.population.OldPop;
import kingroup.population.PopBuilderSibGroups;
import kingroup.population.haplodip.HaplodipPopBuilder;
import kingroup.project.KinGroupProjectV1;
import papers.kingroup2005b_simpson.v1.Efficiency;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;
import javax.utilx.DoubleArrayByInt;
import javax.utilx.arrays.IntVec;
import javax.utilx.pair.SumByInt;
import java.io.File;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 14, 2004, Time: 8:23:58 AM
 */
public class PartitionAccuracyTester //extends PerformanceChart
{
  private static ProjectLogger log = ProjectLogger.getLogger(PartitionAccuracyTester.class.getName());
  public static final long TIME_SCALE = 1000000000;
  public boolean WITH_PARENTS = false;
  public int N_HALFSIBS = 1;
  public int N_HALFSIBS2 = 1;
  protected DoubleArrayByInt allDist_ = new DoubleArrayByInt();
  protected SumByInt count_ = new SumByInt();
  protected DoubleArrayByInt allTimes_ = new DoubleArrayByInt();
  protected ButlerPopBuilderModel POP_MODEL = new ButlerPopBuilderModel();
  protected String DIR = "fullsibs";
  protected String FILE = "fullsibs";
  public int N_ALLELES = 10;
  protected int MIN_NUM_LOCI = 4;  //4-paper
  protected int MAX_NUM_LOCI = 8; // 24-paper
  public int MIN_GROUP_SIZE = 2; // 2-paper
  public int MAX_GROUP_SIZE = 20;
  public int N_GROUPS = 5;
  public int N_LOCI = 5;
  public int GROUP_SIZE = 1;// for counting the partition space size//
  public int GROUP_SIZE_STEP = 5;
  public int MIN_N_GROUPS = 2;
  public int MAX_N_GROUPS = 30;
  public int N_GROUPS_STEP = 10;
  protected int N_TRIALS = 10; // 1000-paper, 2,500
  protected int POP_SIZE = 50;      // 50,200-paper
  public static void main(String[] args) {
    PartitionAccuracyTester test = new PartitionAccuracyTester();
    LOG.setTrace(true);
    test.setupP50();
//      test.runP50_SetA();
//      test.runP50_SetB();
    test.runP50_SetA_parents();

//      test.runP50_parents();
//      test.runP200();
    System.exit(0);
  }
  public PartitionAccuracyTester() {
//    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    KinGroupProjectV1.makeInstance("PartitionAccuracyTester", "v1");
    DIR = "d_ratio";
    POP_MODEL.loadDefaults();
    POP_MODEL.setIncParents(false);
    POP_MODEL.setAllelicFreqType(ButlerFamilyData.EQUAL_FREQ);
    POP_MODEL.setSize(POP_SIZE);
  }
  public void setupP50() {
    POP_SIZE = 50;
    POP_MODEL.setSize(POP_SIZE);
    N_ALLELES = 8;
    MIN_NUM_LOCI = 1; // 1-paper
    MAX_NUM_LOCI = 16;//15-paper
    N_TRIALS = 100; // 100-paper
    FILE = "d_ratio_P" + POP_SIZE;
  }
  public void runP50_SetA() {
    calcAccuracyByLoci(DIR, FILE + "_10S_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_5x10, N_ALLELES);
    calcAccuracyByLoci(DIR, FILE + "_20S_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_20_10_10_5_5, N_ALLELES);
    calcAccuracyByLoci(DIR, FILE + "_30S_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_30_5_5_5_5, N_ALLELES);
    calcAccuracyByLoci(DIR, FILE + "_40S_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_40_5_2_2_1, N_ALLELES);
  }
  public void runP50_SetB() {
    calcAccuracyByLoci(DIR, FILE + "_1S_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_50x1, N_ALLELES);
//      calcAccuracyByLoci(DIR, FILE+"_10S_NA"+N_ALLELES+".csv"
//              , ButlerFamilyData.BUTLER_5x10, false, N_ALLELES);
    calcAccuracyByLoci(DIR, FILE + "_25S_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_25_10_10_4_1, N_ALLELES);
    calcAccuracyByLoci(DIR, FILE + "_46S_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_46_1_1_1_1, N_ALLELES);
  }
  public void runP50_SetA_parents() {
    WITH_PARENTS = true;
    calcAccuracyByLoci(DIR, FILE + "_8P_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_5x8, N_ALLELES);
  }
  public void runP50_parents() {
    WITH_PARENTS = true;
    N_ALLELES = 8;
    MIN_NUM_LOCI = 1; // 1-paper
    MAX_NUM_LOCI = 16;//15-paper
    N_TRIALS = 10; // 100-paper
    DIR = "d_ratio";
    POP_SIZE = 51;
    POP_MODEL.setSize(POP_SIZE);
    calcAccuracyByLoci(DIR, "d_ratio_17x1s2p_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_17_OF_1, N_ALLELES);
    POP_SIZE = 50;
    POP_MODEL.setSize(POP_SIZE);
//      calcAccuracyByLoci(DIR, "d_ratio_5x10P_NA"+N_ALLELES+".csv"
//              , ButlerFamilyData.BUTLER_5x10, true, N_ALLELES);
//      calcAccuracyByLoci(DIR, "d_ratio_25P_NA"+N_ALLELES+".csv"
//              , ButlerFamilyData.BUTLER_25_10_10_4_1, true, N_ALLELES);
//      calcAccuracyByLoci(DIR, "d_ratio_46P_NA"+N_ALLELES+".csv"
//              , ButlerFamilyData.BUTLER_46_1_1_1_1, true, N_ALLELES);
    System.exit(0);
  }
  public String calcOneDistance(OldPop popA, PartitionModel partitionModel)
  {
    return "DEPRICATED!!!";
  }
  public void calcEfficiencyByGroupSize(String dirName, String distFileName, String timeFileName) {
    POP_MODEL.setIncParents(false);
    allTimes_ = new DoubleArrayByInt();
    allDist_ = new DoubleArrayByInt();
    count_ = new SumByInt();
    for (int groupSize = MIN_GROUP_SIZE; groupSize <= MAX_GROUP_SIZE
      ; groupSize += GROUP_SIZE_STEP) {
      int[] groups = IntVec.makeArray(N_GROUPS, groupSize);
      POP_MODEL.setSize(N_GROUPS * groupSize);
      POP_MODEL.setNumLoci(N_LOCI);
      POP_MODEL.setNumAlleles(N_ALLELES);
      OldAlleleFreq freq = OldAlleleFreqFactory.makeAlleleFreq(POP_MODEL);
      freq.normalize(1.0f, false);

      // log.info("popSize=" + POP_MODEL.getSize());
      for (int i = 0; i < N_TRIALS; i++) {
        GenotypeFactory.init();
        OldPop popA = new PopBuilderSibGroups(freq).buildSibGroups(groups);
        TestCase.assertEquals(POP_MODEL.getSize(), popA.size());
        Efficiency res = calcEfficiency(popA);
        allTimes_.add(POP_MODEL.getSize(), res.time);
        allDist_.add(POP_MODEL.getSize(), 100. * res.distance / POP_MODEL.getSize());
        count_.sum(POP_MODEL.getSize(), 1);
      }
    }
    LOG.saveToFile(allTimes_, dirName, timeFileName);
    LOG.saveToFile(allDist_, dirName, distFileName);
    LOG.saveToFile(allTimes_, dirName + File.separator + "P" + POP_SIZE + "_NT" + N_TRIALS, timeFileName);
    LOG.saveToFile(allDist_, dirName + File.separator + "P" + POP_SIZE + "_NT" + N_TRIALS, distFileName);
  }
  public void calcEfficiencyByNGroups(String dirName, String distFileName, String timeFileName) {
    POP_MODEL.setIncParents(false);
    allTimes_ = new DoubleArrayByInt();
    allDist_ = new DoubleArrayByInt();
    count_ = new SumByInt();
    for (N_GROUPS = MIN_N_GROUPS; N_GROUPS <= MAX_N_GROUPS; N_GROUPS += N_GROUPS_STEP) {
      int[] groups = IntVec.makeArray(N_GROUPS, GROUP_SIZE);
      POP_MODEL.setSize(N_GROUPS * GROUP_SIZE);
      POP_MODEL.setNumLoci(N_LOCI);
      POP_MODEL.setNumAlleles(N_ALLELES);
      OldAlleleFreq freq = OldAlleleFreqFactory.makeAlleleFreq(POP_MODEL);
      freq.normalize(1.0f, false);

      log.info("popSize=" + POP_MODEL.getSize());
      for (int i = 0; i < N_TRIALS; i++) {
        GenotypeFactory.init();
        OldPop popA = new PopBuilderSibGroups(freq).buildSibGroups(groups);
        TestCase.assertEquals(POP_MODEL.getSize(), popA.size());
        Efficiency res = calcEfficiency(popA);
        allTimes_.add(POP_MODEL.getSize(), res.time);
        allDist_.add(POP_MODEL.getSize(), 100. * res.distance / POP_MODEL.getSize());
        count_.sum(POP_MODEL.getSize(), 1);
      }
    }
    LOG.saveToFile(allTimes_, dirName, timeFileName);
    LOG.saveToFile(allDist_, dirName, distFileName);
    LOG.saveToFile(allTimes_, dirName + File.separator + "P" + POP_SIZE + "_NT" + N_TRIALS, timeFileName);
    LOG.saveToFile(allDist_, dirName + File.separator + "P" + POP_SIZE + "_NT" + N_TRIALS, distFileName);
  }
  protected Efficiency calcEfficiency(OldPop popA) {
    return null;
  }
  public void calcAccuracyByLoci(String dirName, String fileName, int familyType
    , int numAlleles) {
    POP_MODEL.setFamilyType(familyType);
    POP_MODEL.setIncParents(WITH_PARENTS);
    allDist_ = new DoubleArrayByInt();
    count_ = new SumByInt();
    for (N_LOCI = MIN_NUM_LOCI; N_LOCI <= MAX_NUM_LOCI; N_LOCI++) {
      POP_MODEL.setNumLoci(N_LOCI);
      if (numAlleles == -1)
        POP_MODEL.setNumAlleles(N_LOCI);
      else
        POP_MODEL.setNumAlleles(numAlleles);
      OldAlleleFreq freq = OldAlleleFreqFactory.makeAlleleFreq(POP_MODEL);
      freq.normalize(1.0f, false);

      log.info("N_LOCI = " + N_LOCI);
      for (int i = 0; i < N_TRIALS; i++) {
        GenotypeFactory.init();
        OldPop popA = new ButlerPopBuilder(freq).buildButler(POP_MODEL);
//        log.info("popA =\n" + popA.toString());

        TestCase.assertEquals(POP_MODEL.getSize(), popA.size());
        double dist = calcOneDistance(popA);
        allDist_.add(N_LOCI, 100. * dist / POP_MODEL.getSize());
        count_.sum(N_LOCI, 1);
      }
    }
    LOG.saveToFile(allDist_, dirName, fileName);
    LOG.saveToFile(allDist_, dirName + File.separator + "P" + POP_SIZE + "_NT" + N_TRIALS, fileName);
    LOG.saveBoxplot(allDist_, dirName + File.separator + "boxplot", fileName);
    LOG.saveBoxplot(allDist_, dirName + File.separator + "boxplot" + File.separator + "P" + POP_SIZE + "_NT" + N_TRIALS, fileName);
  }
  public void calcHaploDByLoci(String dirName, String fileName, int familyType
    , int numAlleles) {
    POP_MODEL.setFamilyType(familyType);
    POP_MODEL.setIncParents(WITH_PARENTS);
    ButlerFamilyData data = ButlerFamilyData.getInstance();
    ButlerFamily family = data.getFamily(familyType);
    allDist_ = new DoubleArrayByInt();
    count_ = new SumByInt();
    for (int numLoci = MIN_NUM_LOCI; numLoci <= MAX_NUM_LOCI; numLoci++) {
      POP_MODEL.setNumLoci(numLoci);
      if (numAlleles == -1)
        POP_MODEL.setNumAlleles(numLoci);
      else
        POP_MODEL.setNumAlleles(numAlleles);
      OldAlleleFreq freq = OldAlleleFreqFactory.makeAlleleFreq(POP_MODEL);
      freq.normalize(1.0f, false);

      // log.info("numLoci=" + numLoci);
      for (int i = 0; i < N_TRIALS; i++) {
        GenotypeFactory.init();
        OldPop popA = new HaplodipPopBuilder(freq).build(WITH_PARENTS, family.toIntArray());
        TestCase.assertEquals(POP_MODEL.getSize(), popA.size());
        double dist = calcOneDistance(popA);
        allDist_.add(numLoci, 100. * dist / POP_MODEL.getSize());
        count_.sum(numLoci, 1);
      }
    }
    LOG.saveToFile(allDist_, dirName, fileName);
    LOG.saveToFile(allDist_, dirName + File.separator + "P" + POP_SIZE + "_NT" + N_TRIALS, fileName);
    LOG.saveBoxplot(allDist_, dirName + File.separator + "boxplot", fileName);
    LOG.saveBoxplot(allDist_, dirName + File.separator + "boxplot" + File.separator + "P" + POP_SIZE + "_NT" + N_TRIALS, fileName);
  }
  public void applyToRandomFamilies(String dirName, String fileName
    , boolean withParents, int numAlleles, int nGroups) {
    allDist_ = new DoubleArrayByInt();
    count_ = new SumByInt();
    for (int numLoci = MIN_NUM_LOCI; numLoci <= MAX_NUM_LOCI; numLoci++) {
      POP_MODEL.setNumLoci(numLoci);
      if (numAlleles == -1)
        POP_MODEL.setNumAlleles(numLoci);
      else
        POP_MODEL.setNumAlleles(numAlleles);
      OldAlleleFreq freq = OldAlleleFreqFactory.makeAlleleFreq(POP_MODEL);
      freq.normalize(1.0f, false);

      // log.info("numLoci=" + numLoci);
      for (int i = 0; i < N_TRIALS; i++) {
        GenotypeFactory.init();
        OldPop popA = new MendelPopBuilder(freq).buildRandom(withParents, POP_MODEL.getSize(), nGroups);
        TestCase.assertEquals(POP_MODEL.getSize(), popA.size());
        double dist = calcOneDistance(popA);
        if (i == N_TRIALS - 1) {
          int dbg = 1;
        }
        allDist_.add(numLoci, 100. * dist / POP_MODEL.getSize());
        count_.sum(numLoci, 1);
      }
    }
    LOG.saveToFile(allDist_, dirName, fileName);
    LOG.saveToFile(allDist_, dirName + File.separator + "P" + POP_SIZE + "_NT" + N_TRIALS, fileName);
    LOG.saveBoxplot(allDist_, dirName + File.separator + "boxplot", fileName);
    LOG.saveBoxplot(allDist_, dirName + File.separator + "boxplot" + File.separator + "P" + POP_SIZE + "_NT" + N_TRIALS, fileName);
  }
  public void runHaplod(String dirName, String fileName) {
    allDist_ = new DoubleArrayByInt();
    count_ = new SumByInt();
    for (int numLoci = MIN_NUM_LOCI; numLoci <= MAX_NUM_LOCI; numLoci++) {
      POP_MODEL.setNumLoci(numLoci);
      if (N_ALLELES == -1)
        POP_MODEL.setNumAlleles(numLoci);
      else
        POP_MODEL.setNumAlleles(N_ALLELES);
      OldAlleleFreq freq = OldAlleleFreqFactory.makeAlleleFreq(POP_MODEL);
      freq.normalize(1.0f, false);

      // log.info("numLoci=" + numLoci);
      for (int i = 0; i < N_TRIALS; i++) {
        GenotypeFactory.init();
        OldPop popA = new HaplodipPopBuilder(freq).buildSkewed(POP_MODEL);
        TestCase.assertEquals(POP_MODEL.getSize(), popA.size());
        double dist = calcOneDistance(popA);
        allDist_.add(numLoci, 100. * dist / POP_MODEL.getSize());
        count_.sum(numLoci, 1);
      }
    }
    LOG.saveToFile(allDist_, dirName, fileName);
    LOG.saveToFile(allDist_, dirName + File.separator + "P" + POP_SIZE + "_NT" + N_TRIALS, fileName);
    LOG.saveBoxplot(allDist_, dirName + File.separator + "boxplot", fileName);
    LOG.saveBoxplot(allDist_, dirName + File.separator + "boxplot" + File.separator + "P" + POP_SIZE + "_NT" + N_TRIALS, fileName);
  }
  public void runDiploid(String dirName, String fileName) {
    allDist_ = new DoubleArrayByInt();
    count_ = new SumByInt();
    for (int numLoci = MIN_NUM_LOCI; numLoci <= MAX_NUM_LOCI; numLoci++) {
      POP_MODEL.setNumLoci(numLoci);
      if (N_ALLELES == -1)
        POP_MODEL.setNumAlleles(numLoci);
      else
        POP_MODEL.setNumAlleles(N_ALLELES);
      OldAlleleFreq freq = OldAlleleFreqFactory.makeAlleleFreq(POP_MODEL);
      freq.normalize(1.0f, false);

      // log.info("numLoci=" + numLoci);
      for (int i = 0; i < N_TRIALS; i++) {
        GenotypeFactory.init();
        OldPop popA = new MendelPopBuilder(freq).buildSkewed(POP_MODEL);
        TestCase.assertEquals(POP_MODEL.getSize(), popA.size());
        double dist = calcOneDistance(popA);
        allDist_.add(numLoci, 100. * dist / POP_MODEL.getSize());
        count_.sum(numLoci, 1);
      }
    }
    LOG.saveToFile(allDist_, dirName, fileName);
    LOG.saveToFile(allDist_, dirName + File.separator + "P" + POP_SIZE + "_NT" + N_TRIALS, fileName);
    LOG.saveBoxplot(allDist_, dirName + File.separator + "boxplot", fileName);
    LOG.saveBoxplot(allDist_, dirName + File.separator + "boxplot" + File.separator + "P" + POP_SIZE + "_NT" + N_TRIALS, fileName);
  }
  protected double calcOneDistance(OldPop popA) {
    throw new RuntimeException("must overwrite calcOneDistance(OldPop popA)");
  }
}