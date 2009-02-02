package papers.kingroup2005b_simpson.v2.figure_2;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistLocusAvr;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/01/2006, Time: 11:52:03
 */
public class MSAccuracyMS_avr  extends MSAccuracyMS
{
  public static void main(String[] args) {
    MSAccuracyMS_avr test = new MSAccuracyMS_avr();
    LOG.setTrace(true);
    test.runFigure2();
    System.exit(0);
  }
  public MSAccuracyMS_avr() {
    ALG_NAME = "MS_avr";
    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusAvr());

    N_TRIALS = 100; // 100-paper
    WIND_SIZE_LIMIT = 10; // 10
    ALG_MODEL.setWindSize(WIND_SIZE_LIMIT);
  }
}
