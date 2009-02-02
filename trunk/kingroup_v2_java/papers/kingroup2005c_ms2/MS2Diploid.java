package papers.kingroup2005c_ms2;
import kingroup.papers.butler2004.family.ButlerFamilyData;
import kingroup_v2.partition.PartitionAccuracyTester;

import java.io.File;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 16/08/2005, Time: 11:35:58
 */
public class MS2Diploid extends PartitionAccuracyTester {
  public MS2Diploid() {
    DIR = "papers" + File.separator + "kingroup2005c_ms2" + File.separator + "output";
    POP_SIZE = 50;
    POP_MODEL.setSize(POP_SIZE);
    WITH_PARENTS = false;
    N_ALLELES = 10;
    MIN_NUM_LOCI = 1; // 1-paper
    MAX_NUM_LOCI = 8;//12-paper
    N_TRIALS = 10; // 100-paper
  }
  public void run_P50_skewed_freq() {
    FILE += "_U" + POP_SIZE;
    N_GROUPS = 5;
    POP_MODEL.setAllelicFreqType(ButlerFamilyData.SKEWED_BY_1);
//      runDiploid(DIR, FILE+"_5x10S_NA"+N_ALLELES+"_FREQ1.csv"
//              , false, N_ALLELES, N_GROUPS);
//      POP_MODEL.setAllelicFreqType(ButlerFamilyData.SKEWED_BY_2);
//      runDiploid(DIR, FILE+"_5x10S_NA"+N_ALLELES+"_FREQ2.csv"
//              , false, N_ALLELES, N_GROUPS);
  }
  public void runP50_SDR_test() {
    MAX_NUM_LOCI = 12;//12-paper
    String name = FILE + "_S" + POP_SIZE;
    calcAccuracyByLoci(DIR, name + "_20_3x5_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_20_3x5_5x2_5x1, N_ALLELES);
  }
  public void run_P50_with_halfsibs() {
    FILE += "_U" + POP_SIZE;
    POP_MODEL.setIncParents(false);
    POP_MODEL.setNumFullSibGroups(N_GROUPS = 1);
    POP_MODEL.setNumHalfSibGroups(N_HALFSIBS = 4);
    POP_MODEL.setnHalfSibs2(N_HALFSIBS2 = 0);
    runDiploid(DIR, FILE + "_1x10FS_4x10HS_NA" + N_ALLELES + ".csv");
  }
  public void run_P50_skewed() {
    FILE += "_S" + POP_SIZE;
    N_GROUPS = 5;
    int SKEW = 1;
//      runSkewed(DIR, FILE+"_5x10S_NA"+N_ALLELES+"_SKEW"+SKEW+".csv"
//              , false, N_ALLELES, N_GROUPS, SKEW);
//      SKEW = 2;
//      runSkewed(DIR, FILE+"_5x10S_NA"+N_ALLELES+"_SKEW"+SKEW+".csv"
//              , false, N_ALLELES, N_GROUPS, SKEW);
//      SKEW = 3;
//      runSkewed(DIR, FILE+"_5x10S_NA"+N_ALLELES+"_SKEW"+SKEW+".csv"
//              , false, N_ALLELES, N_GROUPS, SKEW);
    SKEW = 4;
//    runSkewed(DIR, FILE+"_5x10S_NA"+N_ALLELES+"_SKEW"+SKEW+".csv"
//        , false, N_ALLELES, N_GROUPS, SKEW);
  }
  public void run_P50() {
    FILE += "_U" + POP_SIZE;
    calcAccuracyByLoci(DIR, FILE+"_50x1S_NA"+N_ALLELES +".csv"
      , ButlerFamilyData.BUTLER_50x1, N_ALLELES);
    calcAccuracyByLoci(DIR, FILE+"_25x2S_NA"+N_ALLELES +".csv"
      , ButlerFamilyData.BUTLER_25x2, N_ALLELES);
    calcAccuracyByLoci(DIR, FILE+"_10x5S_NA"+N_ALLELES +".csv"
      , ButlerFamilyData.BUTLER_10x5, N_ALLELES);
    calcAccuracyByLoci(DIR, FILE + "_5x10S_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_5x10, N_ALLELES);
//      calcAccuracyByLoci(DIR, FILE+"_2x25S_NA"+N_ALLELES +".csv"
//              , ButlerFamilyData.BUTLER_2x25, N_ALLELES);
//      calcAccuracyByLoci(DIR, FILE+"_1x50S_NA"+N_ALLELES +".csv"
//              , ButlerFamilyData.BUTLER_1x50, WITH_PARENTS, N_ALLELES);
  }
  public void runP50_MB_set() {
    String name = FILE + "_U" + POP_SIZE;
//      calcAccuracyByLoci(DIR, name+"_10S_NA"+N_ALLELES +".csv"
//              , ButlerFamilyData.BUTLER_5x10, N_ALLELES);
    calcAccuracyByLoci(DIR, name + "_40S_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_40_5_2_2_1, N_ALLELES);
    calcAccuracyByLoci(DIR, name + "_20S_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_20_10_10_5_5, N_ALLELES);
    calcAccuracyByLoci(DIR, name + "_30S_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_30_5_5_5_5, N_ALLELES);
  }
  public void run_P50_with_parents() {
    FILE += "_U" + POP_SIZE;
    WITH_PARENTS = true;
//      calcAccuracyByLoci(DIR, FILE+"_25x2S_NA"+N_ALLELES +".csv"
//              , ButlerFamilyData.BUTLER_25x2, WITH_PARENTS, N_ALLELES);
//      calcAccuracyByLoci(DIR, FILE+"_10x5S_NA"+N_ALLELES +".csv"
//              , ButlerFamilyData.BUTLER_10x5, WITH_PARENTS, N_ALLELES);
    calcAccuracyByLoci(DIR, FILE + "_5x10P_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_5x10, N_ALLELES);
//      calcAccuracyByLoci(DIR, FILE+"_2x25S_NA"+N_ALLELES +".csv"
//              , ButlerFamilyData.BUTLER_2x25, WITH_PARENTS, N_ALLELES);
//      calcAccuracyByLoci(DIR, FILE+"_1x50S_NA"+N_ALLELES +".csv"
//              , ButlerFamilyData.BUTLER_1x50, WITH_PARENTS, N_ALLELES);
  }
  public void run_P20() {
    FILE += "_U" + POP_SIZE;
    calcAccuracyByLoci(DIR, FILE + "_20x1S_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_20x1, N_ALLELES);
    calcAccuracyByLoci(DIR, FILE + "_10x2S_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_10x2, N_ALLELES);
    calcAccuracyByLoci(DIR, FILE + "_5x4S_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_5x4, N_ALLELES);
    calcAccuracyByLoci(DIR, FILE + "_4x5S_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_4x5, N_ALLELES);
    calcAccuracyByLoci(DIR, FILE + "_2x10S_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_2x10, N_ALLELES);
    calcAccuracyByLoci(DIR, FILE + "_1x20S_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_1x20, N_ALLELES);
  }
  public void run_P12() {
    FILE += "_U" + POP_SIZE;
//    calcAccuracyByLoci(DIR, FILE + "_12x1S_NA" + N_ALLELES + ".csv"
//      , ButlerFamilyData.BUTLER_12x1, N_ALLELES);
//    calcAccuracyByLoci(DIR, FILE + "_6x2S_NA" + N_ALLELES + ".csv"
//      , ButlerFamilyData.BUTLER_6x2, N_ALLELES);
//    calcAccuracyByLoci(DIR, FILE + "_4x3S_NA" + N_ALLELES + ".csv"
//      , ButlerFamilyData.BUTLER_4x3, N_ALLELES);
//    calcAccuracyByLoci(DIR, FILE + "_3x4S_NA" + N_ALLELES + ".csv"
//      , ButlerFamilyData.BUTLER_3x4, N_ALLELES);
    calcAccuracyByLoci(DIR, FILE + "_2x6S_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_2x6, N_ALLELES);
//      calcAccuracyByLoci(DIR, FILE+"_1x12S_NA"+N_ALLELES +".csv"
//              , ButlerFamilyData.BUTLER_1x12, N_ALLELES);
  }
  public void run_P12_with_parents() {
    FILE += "_U" + POP_SIZE;
    WITH_PARENTS = true;
//      calcAccuracyByLoci(DIR, FILE+"_12x1P_NA"+N_ALLELES +".csv"
//              , ButlerFamilyData.BUTLER_12x1, N_ALLELES);
//      calcAccuracyByLoci(DIR, FILE+"_6x2P_NA"+N_ALLELES +".csv"
//              , ButlerFamilyData.BUTLER_6x2, N_ALLELES);
    calcAccuracyByLoci(DIR, FILE + "_4x3P_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_4x3, N_ALLELES);
    calcAccuracyByLoci(DIR, FILE + "_3x4P_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_3x4, N_ALLELES);
    calcAccuracyByLoci(DIR, FILE + "_2x6P_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_2x6, N_ALLELES);
//      calcAccuracyByLoci(DIR, FILE+"_1x12P_NA"+N_ALLELES +".csv"
//              , ButlerFamilyData.BUTLER_1x12, N_ALLELES);
  }
  public void run_rxg() {
//    POP_SIZE = 30;
//    POP_MODEL.setSize(POP_SIZE);
//    N_GROUPS = 10;
//    String name30 = (FILE + "_U" + POP_SIZE);
//    calcAccuracyByLoci(DIR, name30+"_10x3S_NA"+N_ALLELES +".csv"
//      , ButlerFamilyData.BUTLER_10x3, N_ALLELES);
//    N_GROUPS = 6;
//    calcAccuracyByLoci(DIR, name30+"_6x5S_NA"+N_ALLELES +".csv"
//      , ButlerFamilyData.BUTLER_6x5, N_ALLELES);
//
//    POP_SIZE = 100;
//    POP_MODEL.setSize(POP_SIZE);
//    String name = (FILE + "_U" + POP_SIZE);
//    N_GROUPS = 10;
//    calcAccuracyByLoci(DIR, name+"_10x10S_NA"+N_ALLELES +".csv"
//      , ButlerFamilyData.BUTLER_10x10, N_ALLELES);
//    N_GROUPS = 20;
//    calcAccuracyByLoci(DIR, name+"_20x5S_NA"+N_ALLELES +".csv"
//      , ButlerFamilyData.BUTLER_20x5, N_ALLELES);
//
//    POP_SIZE = 200;
//    POP_MODEL.setSize(POP_SIZE);
//    String name2 = (FILE + "_U" + POP_SIZE);
//    N_GROUPS = 10;
//    calcAccuracyByLoci(DIR, name2+"_10x20S_NA"+N_ALLELES +".csv"
//      , ButlerFamilyData.BUTLER_10x20, N_ALLELES);
//    N_GROUPS = 40;
//    calcAccuracyByLoci(DIR, name2+"_40x5S_NA"+N_ALLELES +".csv"
//      , ButlerFamilyData.BUTLER_40x5, N_ALLELES);

    POP_SIZE = 500;
    POP_MODEL.setSize(POP_SIZE);
    String name3 = (FILE + "_U" + POP_SIZE);
    N_GROUPS = 10;
    calcAccuracyByLoci(DIR, name3+"_10x50S_NA"+N_ALLELES +".csv"
      , ButlerFamilyData.BUTLER_10x50, N_ALLELES);
    N_GROUPS = 100;
    calcAccuracyByLoci(DIR, name3+"_100x5S_NA"+N_ALLELES +".csv"
      , ButlerFamilyData.BUTLER_100x5, N_ALLELES);

//    POP_SIZE = 1000;
//    POP_MODEL.setSize(POP_SIZE);
//    String name4 = (FILE + "_U" + POP_SIZE);
//    N_GROUPS = 20;
//    calcAccuracyByLoci(DIR, name4 + "_20x50S_NA" + N_ALLELES + ".csv"
//      , ButlerFamilyData.BUTLER_20x50, N_ALLELES);
//    POP_SIZE = 5000;
//    POP_MODEL.setSize(POP_SIZE);
//    String name5 = (FILE + "_U" + POP_SIZE);
//    N_GROUPS = 100;
//    calcAccuracyByLoci(DIR, name5 + "_100x50S_NA" + N_ALLELES + ".csv"
//      , ButlerFamilyData.BUTLER_100x50, N_ALLELES);
  }
}