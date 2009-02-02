package papers.kingroup2005c_fullsibs.bioinf;
import kingroup.papers.butler2004.family.ButlerFamilyData;
import kingroup_v2.partition.PartitionAccuracyTester;

import java.io.File;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 29/07/2005, Time: 14:48:53
 */
public class FSRAccuracy extends PartitionAccuracyTester {
//   public int N_GROUPS = 4;
  public FSRAccuracy() {
    DIR = "papers" + File.separator + "kingroup2005c_fullsibs" + File.separator + "output";
  }
  public void run_random() {
    POP_SIZE = 20;
    POP_MODEL.setSize(POP_SIZE);
    FILE += ("_R" + POP_SIZE);
    N_GROUPS = 4;
    applyToRandomFamilies(DIR, FILE + "_NG" + N_GROUPS + "_NA" + N_ALLELES + ".csv"
      , WITH_PARENTS, N_ALLELES, N_GROUPS);
  }
  public void run_n20_uniform() {
    POP_SIZE = 20;
    POP_MODEL.setSize(POP_SIZE);
    FILE += ("_U" + POP_SIZE);
    calcAccuracyByLoci(DIR, FILE + "_20x1_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_20x1, N_ALLELES);
    calcAccuracyByLoci(DIR, FILE + "_10x2_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_10x2, N_ALLELES);
    calcAccuracyByLoci(DIR, FILE + "_5x4_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_5x4, N_ALLELES);
    calcAccuracyByLoci(DIR, FILE + "_4x5_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_4x5, N_ALLELES);
    calcAccuracyByLoci(DIR, FILE + "_2x10_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_2x10, N_ALLELES);
    calcAccuracyByLoci(DIR, FILE + "_1x20_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_1x20, N_ALLELES);
  }
  public void run_r2_uniform() {
    POP_SIZE = 10;
    POP_MODEL.setSize(POP_SIZE);
    FILE += ("_U" + POP_SIZE);
    calcAccuracyByLoci(DIR, FILE + "_2x5_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_2x5, N_ALLELES);
    POP_SIZE = 20;
    POP_MODEL.setSize(POP_SIZE);
    calcAccuracyByLoci(DIR, FILE + "_2x10_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_2x10, N_ALLELES);
    POP_SIZE = 50;
    POP_MODEL.setSize(POP_SIZE);
    calcAccuracyByLoci(DIR, FILE + "_2x25_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_2x25, N_ALLELES);
    POP_SIZE = 100;
    POP_MODEL.setSize(POP_SIZE);
    calcAccuracyByLoci(DIR, FILE + "_2x50_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_2x50, N_ALLELES);
    POP_SIZE = 200;
    POP_MODEL.setSize(POP_SIZE);
    calcAccuracyByLoci(DIR, FILE + "_2x100_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_2x100, N_ALLELES);
  }
  public void run_r5_uniform() {
    POP_SIZE = 5;
    POP_MODEL.setSize(POP_SIZE);
    FILE += ("_U" + POP_SIZE);
    calcAccuracyByLoci(DIR, FILE + "_5x1_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_5x1, N_ALLELES);
    POP_SIZE = 10;
    POP_MODEL.setSize(POP_SIZE);
    calcAccuracyByLoci(DIR, FILE + "_5x2_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_5x2, N_ALLELES);
    POP_SIZE = 20;
    POP_MODEL.setSize(POP_SIZE);
    calcAccuracyByLoci(DIR, FILE + "_5x4_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_5x4, N_ALLELES);
    POP_SIZE = 50;
    POP_MODEL.setSize(POP_SIZE);
    calcAccuracyByLoci(DIR, FILE + "_5x10_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_5x10, N_ALLELES);
    POP_SIZE = 100;
    POP_MODEL.setSize(POP_SIZE);
    calcAccuracyByLoci(DIR, FILE + "_5x20_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_5x20, N_ALLELES);
    POP_SIZE = 200;
    POP_MODEL.setSize(POP_SIZE);
    calcAccuracyByLoci(DIR, FILE + "_5x40_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_5x40, N_ALLELES);
  }
  public void run_r10_uniform() {
    POP_SIZE = 10;
    POP_MODEL.setSize(POP_SIZE);
    FILE += ("_U" + POP_SIZE);
    calcAccuracyByLoci(DIR, FILE + "_10x1_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_10x1, N_ALLELES);
    POP_SIZE = 20;
    POP_MODEL.setSize(POP_SIZE);
    calcAccuracyByLoci(DIR, FILE + "_10x2_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_10x2, N_ALLELES);
    POP_SIZE = 50;
    POP_MODEL.setSize(POP_SIZE);
    calcAccuracyByLoci(DIR, FILE + "_10x5_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_10x5, N_ALLELES);
    POP_SIZE = 100;
    POP_MODEL.setSize(POP_SIZE);
    calcAccuracyByLoci(DIR, FILE + "_10x10_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_10x10, N_ALLELES);
    POP_SIZE = 200;
    POP_MODEL.setSize(POP_SIZE);
    calcAccuracyByLoci(DIR, FILE + "_10x20_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_10x20, N_ALLELES);
  }
  public void run_r2_skewed() {
    FILE = "DR_S";
    POP_SIZE = 4;
    FILE += ("_U" + POP_SIZE);
    POP_MODEL.setSize(POP_SIZE);
    calcAccuracyByLoci(DIR, FILE + "_1_2_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_2x2, N_ALLELES);
    POP_SIZE = 6;
    POP_MODEL.setSize(POP_SIZE);
    calcAccuracyByLoci(DIR, FILE + "_1_3_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_2x3, N_ALLELES);
    POP_SIZE = 8;
    POP_MODEL.setSize(POP_SIZE);
    calcAccuracyByLoci(DIR, FILE + "_1_4_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_2x4, N_ALLELES);
    POP_SIZE = 10;
    POP_MODEL.setSize(POP_SIZE);
    calcAccuracyByLoci(DIR, FILE + "_1_5_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_2x5, N_ALLELES);
    POP_SIZE = 20;
    POP_MODEL.setSize(POP_SIZE);
    calcAccuracyByLoci(DIR, FILE + "_1_10_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_2x10, N_ALLELES);
  }
}
