package papers.kingroup2008_apbc;
import kingroup_v2.pop.sample.PopBuilderModel;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;
import javax.stats.Stats;
import javax.stats.StatsRes;
import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/07/2007, Time: 17:04:50
 */
public class APBC2008_SingleFamilyByProportion extends APBC2008_Common
{
  protected final static ProjectLogger log = ProjectLogger.getAnonymousLogger();

  protected int PROP = 1;     // proportion of full sibs in the sample
  protected int MIN_PROP = 0;
  protected int MAX_PROP = 50;
  protected int PROP_STEP = 5;

  public APBC2008_SingleFamilyByProportion() {
    N_TRIALS = 10000;  // 10,000 paper
    N_LOCI = 4;
    GROUP_SIZE = 9;
    N_GROUPS = 1; // 2

    MIN_PROP = 10;
    MAX_PROP = 50;
    PROP_STEP = 5;

    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_TRIANGULAR);
//    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_RANDOM);
//    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
    POP_BUILDER_MODEL.setNumGroups(N_GROUPS);
    POP_BUILDER_MODEL.setGroupSize(GROUP_SIZE);
    POP_BUILDER_MODEL.setNumLoci(N_LOCI);
    POP_BUILDER_MODEL.setIncParents(true); // not really used anyway

    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.PEDIGREE_BUILDER);
  }

  public void testSingleFamily() {
    log.start(APBC2008_SingleFamilyByProportion.class.getName());
    log.setThresholdInfo();

//    log.setAll();
//    log.setThresholdAll();

    N_TRIALS = 100;  // 10,000 for the paper
    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.SINGLE_FULL_SIB_FAMILY);
    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_TRIANGULAR);
    N_ALLELES = 10;
    N_LOCI = 10;
    GROUP_SIZE = 50;
    MIN_PROP = 10;
    MAX_PROP = 40;
    PROP_STEP = 5;
    calcSingleFamilyByNumFullSibs("_NL" + N_LOCI + "_GS" + GROUP_SIZE  + "_NA" + N_ALLELES);
  }

  public void calcSingleFamilyByNumFullSibs(String tag) {
    MSE_EST = new double[N_TRIALS];
    MSE_BEST = new double[N_TRIALS];
    MSE_SAMPLE = new double[N_TRIALS];
    int ARR_SIZE = (MAX_PROP - MIN_PROP) / PROP_STEP + 1;
    double[] X = new double[ARR_SIZE];
    double[] meanEst = new double[ARR_SIZE];
    double[] meanBest = new double[ARR_SIZE];
    double[] meanSample = new double[ARR_SIZE];

    POP_BUILDER_MODEL.setNumAlleles(N_ALLELES);
    POP_BUILDER_MODEL.setNumLoci(N_LOCI);
    POP_BUILDER_MODEL.setGroupSize(GROUP_SIZE);
    for (PROP = MIN_PROP; PROP <= MAX_PROP; PROP += PROP_STEP) {
      for (int i = 0; i < N_TRIALS; i++) {
        POP_BUILDER_MODEL.setNumFullSibs(PROP);
        calcRMSE(i);
      }
      int idx = (PROP - MIN_PROP) / PROP_STEP;
      X[idx] = PROP;
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
