package papers.kingroup2008_apbc;
import junit.framework.TestCase;
import kingroup_v2.pop.allele.freq.KonHegFreqAlg;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.allele.freq.SysAlleleFreqFactory;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;
import tsvlib.project.ProjectLogger;

import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/07/2007, Time: 17:03:17
 */
public class APBC2008_Common extends TestCase
{
  protected final static ProjectLogger log = ProjectLogger.getAnonymousLogger();
  public String DIR = "papers" + File.separator + "2007b_APBC2008" + File.separator + "output";

  protected final double EPS = 1e-5;

  protected int GROUP_SIZE = 1;
  protected int N_GROUPS = 4;

  protected int N_TRIALS = 1000;
  protected int N_ALLELES = 20;
  protected int N_LOCI = 10;
  protected double[] MSE_EST;
  protected double[] MSE_BEST;
  protected double[] MSE_SAMPLE;

  protected PopBuilderModel POP_BUILDER_MODEL = new PopBuilderModel();

  public APBC2008_Common() {
    N_TRIALS = 10000;  // 10,000 paper
    N_LOCI = 4;

    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_TRIANGULAR);
//    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_RANDOM);
//    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
  }

  public void testAll() {
    new APBC2008_CichlidDataSet().testCichlid();
    new APBC2008_SingleFamilyByProportion().testSingleFamily();
    new APBC2008_UnrelatedBySize().testUnrelatedFamily();
  }


  protected SysPop makeSysPopFrom() {
    SysPop res = null;
    while (true) {
      res = SysPopFactory.makeSysPopFrom(POP_BUILDER_MODEL);
//    log.info("\nsysPop=\n" + res);
//    log.info("\npopFreq=\n" + res.getFreq());
      if (SysAlleleFreqFactory.hasZeroHeterLocus(res)) {
        log.info("\nsysPop=\n" + res);
        continue;
      }
      break;
    }
    return res;
  }


  protected void calcRMSE(int i) {
    SysPop sysPop = makeSysPopFrom();           log.debug("\nsysPop=\n", sysPop);
    SysAlleleFreq trueFreq = sysPop.getFreq();  log.debug("\ntrueFreq=\n", trueFreq);
    SysAlleleFreq sampleFreq = SysAlleleFreqFactory.makeFrom(sysPop, true);     log.debug("\nsampleFreq=\n", sampleFreq);

    SysPop bestPop = sysPop.get(0
      , POP_BUILDER_MODEL.getGroupSize() - POP_BUILDER_MODEL.getNumFullSibs()); log.debug("\nbestPop=\n", bestPop);
    SysAlleleFreq bestFreq = SysAlleleFreqFactory.makeFrom(bestPop, true);     log.debug("\nbestFreq=\n", bestFreq);
    MSE_BEST[i] = trueFreq.mse(bestFreq);
    MSE_SAMPLE[i] = trueFreq.mse(sampleFreq);

    KonHegFreqAlg alg = new KonHegFreqAlg();
    int nIter = 100 * sysPop.size();
    SysAlleleFreq estFreq = alg.calc(sysPop, nIter, null);   log.debug("\nestFreq=\n", estFreq);
    MSE_EST[i] = trueFreq.mse(estFreq);
  }
}
