package papers.inpress.kingroup2006_apbc.v1.accuracy;
import kingroup.papers.butler2004.family.ButlerFamilyData;
import kingroup_v2.partition.PartitionAccuracyTester;

import java.io.File;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 28/06/2005, Time: 16:51:10
 */
public class APBCAccuracy extends PartitionAccuracyTester {
  public APBCAccuracy() {
    DIR = "papers" + File.separator + "kingroup2006_apbc" + File.separator + "output";
    POP_SIZE = 50;
    POP_MODEL.setSize(POP_SIZE);
    N_ALLELES = 10;
    MIN_NUM_LOCI = 1; // 1-paper
    MAX_NUM_LOCI = 15;//15-paper
    N_TRIALS = 100; // 100-paper
  }
  public void run_P50_skewed() {
    FILE += "_S" + POP_SIZE;
    N_GROUPS = 5;
    POP_MODEL.setNumFullSibGroups(N_GROUPS);
    int SKEW = 1;
    POP_MODEL.setSkew(SKEW);
//    runDiploid(DIR, FILE+"_5x10S_NA"+N_ALLELES+"_SKEW"+SKEW+".csv");
    SKEW = 2;
    POP_MODEL.setSkew(SKEW);
//    runDiploid(DIR, FILE+"_5x10S_NA"+N_ALLELES+"_SKEW"+SKEW+".csv");
//    SKEW = 3;
//    POP_MODEL.setSkew(SKEW);
//    runDiploid(DIR, FILE+"_5x10S_NA"+N_ALLELES+"_SKEW"+SKEW+".csv");
    SKEW = 4;
    POP_MODEL.setSkew(SKEW);
    runDiploid(DIR, FILE + "_5x10S_NA" + N_ALLELES + "_SKEW" + SKEW + ".csv");
  }
  public void runP50_SDR_test() {
    String name = FILE + "_S" + POP_SIZE;
    calcAccuracyByLoci(DIR, name + "_20_3x5_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_20_3x5_5x2_5x1, N_ALLELES);
  }
  public void run_P50() {
    String name = FILE + "_U" + POP_SIZE;
    calcAccuracyByLoci(DIR, name + "_50x1S_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_50x1, N_ALLELES);
    calcAccuracyByLoci(DIR, name + "_25x2S_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_25x2, N_ALLELES);
    calcAccuracyByLoci(DIR, name + "_10x5S_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_10x5, N_ALLELES);
    calcAccuracyByLoci(DIR, name + "_5x10S_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_5x10, N_ALLELES);
    calcAccuracyByLoci(DIR, name + "_2x25S_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_2x25, N_ALLELES);
    calcAccuracyByLoci(DIR, name + "_1x50S_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_1x50, N_ALLELES);
  }
}
