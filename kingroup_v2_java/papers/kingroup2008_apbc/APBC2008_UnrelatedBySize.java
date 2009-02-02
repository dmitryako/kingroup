package papers.kingroup2008_apbc;
import kingroup_v2.pop.sample.PopBuilderModel;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;
import javax.stats.Stats;
import javax.stats.StatsRes;
import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 20/09/2007, Time: 10:57:03
 */
public class APBC2008_UnrelatedBySize extends APBC2008_Common
{
  protected final static ProjectLogger log = ProjectLogger.getAnonymousLogger();

  private int SIZE;
  private int MIN_SIZE;
  private int MAX_SIZE;
  private int SIZE_STEP;

  public APBC2008_UnrelatedBySize() {
    N_TRIALS = 10000;  // 10,000 paper
    N_LOCI = 4;
    GROUP_SIZE = 9;
    N_GROUPS = 1; // 2

    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_TRIANGULAR);
//    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_RANDOM);
//    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);

    POP_BUILDER_MODEL.setNumGroups(N_GROUPS);
    POP_BUILDER_MODEL.setGroupSize(GROUP_SIZE);
    POP_BUILDER_MODEL.setNumLoci(N_LOCI);
    POP_BUILDER_MODEL.setIncParents(true); // not really used anyway

    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.PEDIGREE_BUILDER);
  }

  public void testUnrelatedFamily() {
    log.start(APBC2008_UnrelatedBySize.class.getName());
    log.setThresholdInfo();

//    log.setAll();
//    log.setThresholdAll();

    N_TRIALS = 100;  // 10,000 for the paper
    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.SINGLE_FULL_SIB_FAMILY);
    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_TRIANGULAR);
    N_ALLELES = 20;
    N_LOCI = 5;
    GROUP_SIZE = 50;
    MIN_SIZE = 10;
    MAX_SIZE = 40;
    SIZE_STEP = 5;
    calcUnrelatedBySize("_NL" + N_LOCI + "_NA" + N_ALLELES);
  }

  public void calcUnrelatedBySize(String tag) {
    MSE_EST = new double[N_TRIALS];
    MSE_BEST = new double[N_TRIALS];
    MSE_SAMPLE = new double[N_TRIALS];
    int ARR_SIZE = (MAX_SIZE - MIN_SIZE) / SIZE_STEP + 1;
    double[] X = new double[ARR_SIZE];
    double[] meanEst = new double[ARR_SIZE];
    double[] meanBest = new double[ARR_SIZE];
    double[] meanSample = new double[ARR_SIZE];

    POP_BUILDER_MODEL.setNumAlleles(N_ALLELES);
    POP_BUILDER_MODEL.setNumLoci(N_LOCI);
    POP_BUILDER_MODEL.setNumFullSibs(0);
    for (SIZE = MIN_SIZE; SIZE <= MAX_SIZE; SIZE += SIZE_STEP) {
      for (int i = 0; i < N_TRIALS; i++) {
        POP_BUILDER_MODEL.setGroupSize(SIZE);
        calcRMSE(i);
      }
      int idx = (SIZE - MIN_SIZE) / SIZE_STEP;
      X[idx] = SIZE;
      log.info("\nidx = " + idx);
      StatsRes stats = new Stats(MSE_EST);
      meanEst[idx] = stats.getMean();

      stats = new Stats(MSE_BEST);
      meanBest[idx] = stats.getMean();

      stats = new Stats(MSE_SAMPLE);
      meanSample[idx] = stats.getMean();
    }
    LOG.saveToFile(X, meanEst, DIR, "meanEst"+tag+".csv");
    LOG.saveToFile(X, meanBest, DIR, "meanBest"+tag+".csv");
    LOG.saveToFile(X, meanSample, DIR, "meanSample"+tag+".csv");
    LOG.saveToFile(X, meanEst, DIR + File.separator + N_TRIALS, "meanEst"+tag+".csv");
    LOG.saveToFile(X, meanBest, DIR + File.separator + N_TRIALS, "meanBest"+tag+".csv");
    LOG.saveToFile(X, meanSample, DIR + File.separator + N_TRIALS, "meanSample"+tag+".csv");
  }

}


