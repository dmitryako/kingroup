package papers.kingroup2008_apbc;
import kingroup_v2.pop.allele.freq.KonHegFreqAlg;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.allele.freq.SysAlleleFreqFactory;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;
import javax.stats.Stats;
import javax.stats.StatsRes;
import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 21/09/2007, Time: 10:14:24
 */
public class APBC2008_CichlidDataSet  extends APBC2008_Common
{
  protected final static ProjectLogger log = ProjectLogger.getAnonymousLogger();

  protected int N_UNRELATED = 10;
  protected int MIN_N_GROUPS = 3;
  protected int MAX_N_GROUPS = 7;
  protected int N_GROUPS = 4;

  public APBC2008_CichlidDataSet() {
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

  public void testCichlid() {
    log.start(APBC2008_CichlidDataSet.class.getName());
    log.setThresholdInfo();

//    log.setAll();
//    log.setThresholdAll();

    N_TRIALS = 100;  // 10,000 for the paper

    N_LOCI = 5;
    N_ALLELES = 20;
    GROUP_SIZE = 5;
    N_GROUPS = 2; // 2
    MIN_N_GROUPS = 2;
    MAX_N_GROUPS = 5;
    POP_BUILDER_MODEL.setNumLoci(N_LOCI);
    N_UNRELATED = 10;
    POP_BUILDER_MODEL.setNumUnrelated(N_UNRELATED);
    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_DIERKES_2005);
//    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.PEDIGREE_BUILDER);
    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.PEDIGREE_TRIANG_BUILDER);
    calcCichlidByNG("_CICHLID_NL" + N_LOCI + "_FGS" + GROUP_SIZE);
  }

  public void calcCichlidByNG(String tag) {
    MSE_EST = new double[N_TRIALS];
    MSE_BEST = new double[N_TRIALS];
    MSE_SAMPLE = new double[N_TRIALS];
    int ARR_SIZE = MAX_N_GROUPS - MIN_N_GROUPS + 1;
    double[] X = new double[ARR_SIZE];
    double[] meanEst = new double[ARR_SIZE];
    double[] meanBest = new double[ARR_SIZE];
    double[] meanSample = new double[ARR_SIZE];

    POP_BUILDER_MODEL.setNumAlleles(N_ALLELES);
    POP_BUILDER_MODEL.setNumLoci(N_LOCI);
    POP_BUILDER_MODEL.setGroupSize(GROUP_SIZE);

    for (N_GROUPS = MIN_N_GROUPS; N_GROUPS <= MAX_N_GROUPS; N_GROUPS++) {
      for (int i = 0; i < N_TRIALS; i++) {
        POP_BUILDER_MODEL.setNumGroups(N_GROUPS);
        calcRMSE(i); // from first (POP_BUILDER_MODEL.getNumGroups() + 1)
      }
      int idx = N_GROUPS - MIN_N_GROUPS;
      X[idx] = N_GROUPS;
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

  protected void calcRMSE(int i) {
    SysPop sysPop = makeSysPopFrom();           log.debug("\nsysPop=\n", sysPop);
    SysAlleleFreq trueFreq = sysPop.getFreq();  log.debug("\ntrueFreq=\n", trueFreq);
    SysAlleleFreq sampleFreq = SysAlleleFreqFactory.makeFrom(sysPop, true);     log.debug("\nsampleFreq=\n", sampleFreq);

    SysPop bestPop = sysPop.get(0
      , POP_BUILDER_MODEL.getNumGroups() + POP_BUILDER_MODEL.getNumUnrelated() + 1); log.debug("\nbestPop=\n", bestPop);
    SysAlleleFreq bestFreq = SysAlleleFreqFactory.makeFrom(bestPop, true);     log.debug("\nbestFreq=\n", bestFreq);
    MSE_BEST[i] = trueFreq.mse(bestFreq);
    MSE_SAMPLE[i] = trueFreq.mse(sampleFreq);

    KonHegFreqAlg alg = new KonHegFreqAlg();
    int nIter = 100 * sysPop.size();
    SysAlleleFreq estFreq = alg.calc(sysPop, nIter, null);   log.debug("\nestFreq=\n", estFreq);
    MSE_EST[i] = trueFreq.mse(estFreq);
  }
}

