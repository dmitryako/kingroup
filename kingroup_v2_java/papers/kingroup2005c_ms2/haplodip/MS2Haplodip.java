package papers.kingroup2005c_ms2.haplodip;
import kingroup.papers.butler2004.family.ButlerFamilyData;
import kingroup_v2.partition.PartitionAccuracyTester;

import java.io.File;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 16/08/2005, Time: 14:53:30
 */
public class MS2Haplodip extends PartitionAccuracyTester {
  public MS2Haplodip() {
    DIR = "papers" + File.separator + "kingroup2005c_ms2" + File.separator + "output" + File.separator + "haplodip";
    POP_SIZE = 50;
    POP_MODEL.setSize(POP_SIZE);
    N_ALLELES = 10;
    MIN_NUM_LOCI = 1; // 1-paper
    MAX_NUM_LOCI = 10;//10-paper
    N_TRIALS = 10; // 100-paper
  }
  public void runP50_SDR_test() {
    MAX_NUM_LOCI = 12;//12-paper
    String name = FILE + "_U" + POP_SIZE;
    calcHaploDByLoci(DIR, name + "_20_3x5_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_20_3x5_5x2_5x1, N_ALLELES);
  }
  public void run_P50_skewed_freq() {
    FILE += "_U" + POP_SIZE;
    N_GROUPS = 5;
    POP_MODEL.setNumFullSibGroups(N_GROUPS);
    POP_MODEL.setIncParents(false);
    POP_MODEL.setAllelicFreqType(ButlerFamilyData.SKEWED_BY_1);
    runHaplod(DIR, FILE + "_5x10S_NA" + N_ALLELES + "_FREQ1.csv");
//      POP_MODEL.setAllelicFreqType(ButlerFamilyData.SKEWED_BY_2);
//      runHaplod(DIR, FILE+"_5x10S_NA"+N_ALLELES+"_FREQ2.csv"
//              , false, N_ALLELES, N_GROUPS);
  }
  public void run_P50_with_halfsibs() {
    FILE += "_U" + POP_SIZE;
    POP_MODEL.setIncParents(false);
    POP_MODEL.setNumFullSibGroups(N_GROUPS = 1);
    POP_MODEL.setNumHalfSibGroups(N_HALFSIBS = 4);
    POP_MODEL.setnHalfSibs2(N_HALFSIBS2 = 0);
    runHaplod(DIR, FILE + "_1x10FS_4x10HS_NA" + N_ALLELES + ".csv");
  }
  public void run_P50_skewed() {
    FILE += "_S" + POP_SIZE;
    N_GROUPS = 5;
    int SKEW = 1;
    POP_MODEL.setIncParents(false);
    POP_MODEL.setNumFullSibGroups(N_GROUPS);
    SKEW = 4;
    POP_MODEL.setSkew(SKEW);
    runHaplod(DIR, FILE + "_5x10S_NA" + N_ALLELES + "_SKEW" + SKEW + ".csv");
  }
  public void run_P50() {
    FILE += "_U" + POP_SIZE;
    POP_MODEL.setIncParents(false);
    POP_MODEL.setNumFullSibGroups(N_GROUPS = 50);
    runHaplod(DIR, FILE + "_50x1S_NA" + N_ALLELES + ".csv");
    POP_MODEL.setNumFullSibGroups(N_GROUPS = 25);
    runHaplod(DIR, FILE + "_25x2S_NA" + N_ALLELES + ".csv");
    POP_MODEL.setNumFullSibGroups(N_GROUPS = 10);
    runHaplod(DIR, FILE + "_10x5S_NA" + N_ALLELES + ".csv");
    POP_MODEL.setNumFullSibGroups(N_GROUPS = 5);
    runHaplod(DIR, FILE + "_5x10S_NA" + N_ALLELES + ".csv");
//    POP_MODEL.setNumFullSibGroups(N_GROUPS = 2);
//    runHaplod(DIR, FILE + "_2x25S_NA" + N_ALLELES + ".csv");
//    POP_MODEL.setNumFullSibGroups(N_GROUPS = 1);
//    runHaplod(DIR, FILE + "_1x50S_NA" + N_ALLELES + ".csv");
  }
  public void run_P3() {
    POP_SIZE = 4;
    POP_MODEL.setSize(POP_SIZE);
    N_ALLELES = 10;
    MIN_NUM_LOCI = 2; // 1-paper
    MAX_NUM_LOCI = 5;//12-paper
    N_TRIALS = 1; // 100-paper
    FILE += "_U" + POP_SIZE;
//    runHaplod(DIR, FILE+"_2x2S_NA"+N_ALLELES +".csv"
//        , false, N_ALLELES, 2);
  }
  public void run_P12() {
    String name = FILE + "_U" + POP_SIZE;
    calcHaploDByLoci(DIR, name + "_2x6S_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_2x6, N_ALLELES);
  }

  public void run_rxg() {
    POP_SIZE = 30;
    POP_MODEL.setSize(POP_SIZE);
    String name30 = (FILE + "_U" + POP_SIZE);
//    runHaplod(DIR, name30+"_10x3S_NA"+N_ALLELES +".csv", false, N_ALLELES, 10);
//    runHaplod(DIR, name30+"_6x5S_NA"+N_ALLELES +".csv", false, N_ALLELES, 6);
    POP_SIZE = 100;
    POP_MODEL.setSize(POP_SIZE);
    String name = (FILE + "_U" + POP_SIZE);
//    runHaplod(DIR, name+"_10x10S_NA"+N_ALLELES +".csv", false, N_ALLELES, 10);
//    runHaplod(DIR, name+"_20x5S_NA"+N_ALLELES +".csv", false, N_ALLELES, 20);
    POP_SIZE = 200;
    POP_MODEL.setSize(POP_SIZE);
    String name2 = (FILE + "_U" + POP_SIZE);
//    runHaplod(DIR, name2+"_10x20S_NA"+N_ALLELES +".csv", false, N_ALLELES, 10);
//    runHaplod(DIR, name2+"_40x5S_NA"+N_ALLELES +".csv", false, N_ALLELES, 40);
    POP_SIZE = 500;
    POP_MODEL.setSize(POP_SIZE);
    String name3 = (FILE + "_U" + POP_SIZE);
//    runHaplod(DIR, name3+"_10x50S_NA"+N_ALLELES +".csv", false, N_ALLELES, 10);
//    runHaplod(DIR, name3+"_100x5S_NA"+N_ALLELES +".csv", false, N_ALLELES, 100);
  }
}