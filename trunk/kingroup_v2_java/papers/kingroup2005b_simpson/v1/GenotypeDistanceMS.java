package papers.kingroup2005b_simpson.v1;
import kingroup.papers.butler2004.family.ButlerFamilyData;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistLocusAvr;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistLocusLog;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistLocusMax_WRONG;

import javax.iox.LOG;
import java.io.File;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 19/05/2005, Time: 13:07:19
 */
public class GenotypeDistanceMS extends SimpsAccuracyMS {
  public static void main(String[] args) {
    GenotypeDistanceMS test = new GenotypeDistanceMS();
    LOG.setTrace(true);
    test.runDistanceVersions();
    System.exit(0);
  }
  public GenotypeDistanceMS()
  {
  }
  public void runDistanceVersions() {
    POP_SIZE = 25;
    POP_MODEL.setSize(POP_SIZE);
    N_ALLELES = 10;  //10-paper
    MIN_NUM_LOCI = 1;  //1-paper
    MAX_NUM_LOCI = 10; // 15-paper
    N_TRIALS = 200; // 200-paper

    DIR += File.separator + "simps_win";
    WIND_SIZE_LIMIT = 20;
    DIR += "_W" + WIND_SIZE_LIMIT;

    ALG_MODEL.setWindSize(WIND_SIZE_LIMIT);
    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusAvr());
    calcAccuracyByLoci(DIR, "locus_avr_5x5_NA"+N_ALLELES+".csv"
      , ButlerFamilyData.BUTLER_5x5, N_ALLELES);

    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusAvr());
    calcAccuracyByLoci(DIR, "locus_min_5x5_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_5x5, N_ALLELES);

    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusMax_WRONG());
    calcAccuracyByLoci(DIR, "locus_max_5x5_NA"+N_ALLELES+".csv"
      , ButlerFamilyData.BUTLER_5x5, N_ALLELES);

    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusLog());
    calcAccuracyByLoci(DIR, "locus_log_5x5_NA"+N_ALLELES+".csv"
      , ButlerFamilyData.BUTLER_5x5, N_ALLELES);
  }
}
