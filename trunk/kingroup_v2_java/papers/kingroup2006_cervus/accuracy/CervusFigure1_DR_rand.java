package papers.kingroup2006_cervus.accuracy;
import kingroup_v2.pop.sample.PopBuilderModel;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;
import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 23/01/2006, Time: 16:37:07
 */
public class CervusFigure1_DR_rand    extends CervusFigure1_DR
{
  private static ProjectLogger log = ProjectLogger.getLogger(CervusFigure1_DR_rand.class.getName());
  public static void main(String[] args) {
    CervusFigure1_DR_rand test = new CervusFigure1_DR_rand();
    LOG.setTrace(true);
    test.runFigure1();
    System.exit(0);
  }
  public CervusFigure1_DR_rand()
  {
    DIR += File.separator + "random_alleles";
    BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_RANDOM);
    N_TRIALS = 100; // 100-paper
  }
}
