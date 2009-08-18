package papers.kingroup2007_relatedness.before_0709;

import kingroup_v2.cervus.AlleleAnalysisFactory;
import kingroup_v2.kinship.KingroupFreqFromR;
import kingroup_v2.kinship.KingroupRFromFreq;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.allele.freq.SysAlleleFreqFactory;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;

import javax.utilx.arrays.mtrx.Mtrx;
import javax.utilx.arrays.vec.Vec;

/**
 * Created by: jc1386591
 * Date: 9/07/2006. Time: 14:22:37
 */
public class RareFreqTestingFreq extends RareFreqKinship
{
  public void testByNA() {
    N_TRIALS = 2;  // 10,000 paper
    N_LOCI = 4;
    MIN_N_ALLELES = 10;
    MAX_N_ALLELES = 10;
    GROUP_SIZE = 9;
    N_GROUPS = 2; // 2
    MIN_N_GROUPS = 2;
    MAX_N_GROUPS = 2;


    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_TRIANGULAR);
//    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_RANDOM);
//    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
    POP_BUILDER_MODEL.setIncParents(true);
    //POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.FULL_SIB_BUILDER);
    //POP_BUILDER_MODEL.setIncMatPatIds(true);
    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.PEDIGREE_BUILDER);

    calcByNA("_TEST_TRI_NL" + N_LOCI + "_FGS" + GROUP_SIZE + "_NG" + N_GROUPS);
  }

  public void testByNG() {
    N_ALLELES = 10;
    N_LOCI = 10;
    calcByNG("_TEST_TRI_NL" + N_LOCI + "_FGS" + GROUP_SIZE + "_NA" + N_ALLELES);
  }

  protected void calcRMatrix(PopBuilderModel builderModel) {
    //runTest(builderModel);
    runTest2(builderModel);
  }
  protected void runTest2(PopBuilderModel builderModel) {
    SysPop sysPop = SysPopFactory.makeSysPopFrom(builderModel);
    int n = sysPop.size();
    log.info("\nsysPop=\n" + sysPop);
    SysAlleleFreq trueFreq = sysPop.getFreq();
    log.info("\ntrueFreq=\n" + trueFreq);

    double[] trueHet = AlleleAnalysisFactory.calcTrueHeteroz(sysPop.getFreq());
    log.info("\ntrueHet=" + Vec.toString(trueHet));
    log.info("\ntrueHet Avr=" + (float)Vec.mean(trueHet));

    double[] obsHet = AlleleAnalysisFactory.calcObservHeteroz(sysPop);
    log.info("\nobsHet=" + Vec.toString(obsHet));
    log.info("\nobsHet Avr=" + (float)Vec.mean(obsHet));
    log.info("\nobsHet Avr2=" + (float)AlleleAnalysisFactory.calcObservHeterozAvr(sysPop));

    double[] currW = Vec.makeArray(1./n, n);
    SysAlleleFreq tmpFreq = SysAlleleFreqFactory.makeFrom(currW, sysPop);
    sysPop.setFreq(tmpFreq);
    double[] tmpHet = AlleleAnalysisFactory.calcTrueHeteroz(sysPop.getFreq());
    log.info("\ntmpHet=" + Vec.toString(tmpHet));
    log.info("\ntmpHet Avr=" + (float) Vec.mean(tmpHet));

    int ITERS = 20;
    double[] steps = {0.80, 1.2, 0.9, 1.1, 0.95, 1.05};
    double currDist = Vec.distL2(obsHet, tmpHet);
    for (int m = 0; m < ITERS; m++) {
      for (int s = 0; s < steps.length; s++) {
        for (int i = 0; i < n; i++) {
          double[] newW = Vec.copy(currW);
          newW[i] *= steps[s];
          Vec.norm(newW, 1.);
          tmpFreq = SysAlleleFreqFactory.makeFrom(newW, sysPop);
          sysPop.setFreq(tmpFreq);
          tmpHet = AlleleAnalysisFactory.calcTrueHeteroz(sysPop.getFreq());
          double newDist = Vec.distL2(obsHet, tmpHet);
          if (newDist > currDist)
            continue; // ignore
          log.info("\ncurrDist =" + (float)currDist
            + "\nnewDist =" + (float)newDist
            //+ "\ncurrW =" + DoubleArr.toString(currW)
            + "\nnewW =" + Vec.toString(newW)
          );
          log.info("\ntmpFreq=\n" + tmpFreq);
          currW = newW;  // rember better weights
          currDist = newDist;
        }
      }
    }

    KingroupRFromFreq rFromP = new KingroupRFromFreq(sysPop);
    KingroupFreqFromR freqFromR = new KingroupFreqFromR();  // ESTIMATE estimFreq FROM r
    double[][] estimR = SysPopFactory.makePedigreeR(sysPop);

    SysAlleleFreq estimFreq = freqFromR.calc(estimR, sysPop);
    log.info("\ntmpFreq=\n" + tmpFreq);

    // STARTING POINT
    SysAlleleFreq observedFreq = SysAlleleFreqFactory.makeFrom(sysPop, true);
    log.info("observedFreq=\n" + observedFreq);
    sysPop.setFreq(observedFreq);

    ITERS = 10;
    rFromP.calc();
    for (int i = 0; i < ITERS; i++) {
      estimR = rFromP.toArray(0, -1);
      log.info("estimR=\n" + Mtrx.toString(estimR));
      rFromP.correctForBias();
    }

    ITERS = 20;
    for (int i = 0; i < ITERS; i++) {
      rFromP.calc();
      estimR = rFromP.toArray(0, -1);
      estimFreq = freqFromR.calc(estimR, sysPop);
      sysPop.setFreq(estimFreq);
    }
  }

  protected void runTest(PopBuilderModel builderModel) {
    SysPop sysPop = SysPopFactory.makeSysPopFrom(builderModel);

    log.info("\nsysPop=\n" + sysPop);
    double[][] r = SysPopFactory.makePedigreeR(sysPop);

    /*
    // TEST
    KingroupFreqFromR mtrxR = new KingroupFreqFromR(sysPop);
    mtrxR.setPedigreeR(r);
    mtrxR.calc();

    int ITERS = 5;
    PairwiseRMtrx kinR = new PairwiseRMtrx(sysPop);
    mtrxR = new KingroupFreqFromR(sysPop);  // ESTIMATE r-matrix
    for (int i = 0; i < ITERS; i++) {
      mtrxR.calc();
      sysPop.setFreq(mtrxR.getEstimFreq()); // START

      kinR.calc();  // freq -> r
      mtrxR.setPedigreeR(kinR.toArray());  //
    }
    loadKin(FS, 0.5, SysPopFactory.getFS(mtrxR, sysPop));
    loadKin(HS, 0.25, SysPopFactory.getHS(mtrxR, sysPop));
    loadKin(PO, 0.5, SysPopFactory.getPO(mtrxR, sysPop));
    loadKin(NR, 0.0, SysPopFactory.getNR(mtrxR, sysPop));
    */
  }
}
