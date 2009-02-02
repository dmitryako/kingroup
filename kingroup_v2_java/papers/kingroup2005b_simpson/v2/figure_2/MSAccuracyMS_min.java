package papers.kingroup2005b_simpson.v2.figure_2;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistLocusAvr;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/01/2006, Time: 11:51:48
 */
public class MSAccuracyMS_min  extends MSAccuracyMS
{
  public static void main(String[] args) {
    MSAccuracyMS_min test = new MSAccuracyMS_min();
    LOG.setTrace(true);
    test.runFigure2();
    System.exit(0);
  }
  public MSAccuracyMS_min() {
    ALG_NAME = "MS_min_no_shuffle";
    ALG_NAME = "MS_min";
    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusAvr());

    N_TRIALS = 100; // 100-paper
    WIND_SIZE_LIMIT = 10; // 10
    ALG_MODEL.setWindSize(WIND_SIZE_LIMIT);
  }
}