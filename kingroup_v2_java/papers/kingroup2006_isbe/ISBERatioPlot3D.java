package papers.kingroup2006_isbe;
import kingroup.genetics.Like;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.KinshipIBD;
import kingroup_v2.kinship.KinshipIBDFactory;
import kingroup_v2.kinship.KinshipSysPopFactory;
import kingroup_v2.kinship.like.KinshipLikeCalculator;
import kingroup_v2.kinship.like.KinshipLikeMtrx;
import kingroup_v2.kinship.like.KinshipRMaxLikeEstimator;
import kingroup_v2.kinship.like.KinshipRMaxLikeEstimatorDEBUG;
import kingroup_v2.like.thompson.ThompsonIBD;
import kingroup_v2.like.thompson.ThompsonLikeCalculator;
import kingroup_v2.like.thompson.ThompsonLikeMtrx;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.allele.freq.SysAlleleFreqFactory;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;
import javax.utilx.arrays.vec.Vec;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/03/2006, Time: 14:40:49
 */
public class ISBERatioPlot3D extends ISBECommon
{
  private final static ProjectLogger log = ProjectLogger.getLogger(ISBERatioPlot3D.class.getName());

  final double EPS = 1e-5;
//  final double FIRST = -0.5;
  double FIRST = -1;
  double LAST = 1;
//  final int GRID_SIZE = 21;  // 16 for paper
  int GRID_SIZE = 16;  // 16 for paper

//  final double FIRST = -0.1;
//  final double LAST = 1;
//  final int GRID_SIZE = 12;

  int N_LOCI = 5;
  int N_ALLELES = 5;

  public void testKinship() {
    FIRST = -0.5;
    LAST = 1;
    GRID_SIZE = 16;  // 16 for paper
    N_LOCI = 5;
    N_ALLELES = 5;
    saveKinship(KinshipIBDFactory.makeUnrelated(), "un");
//    saveKinship(KinshipIBDFactory.makeFullSib(), "fs");
//    saveKinship(KinshipIBDFactory.makeParentOffspring(), "po");
  }
  public void testKinshipBEDUG() {
    N_LOCI = 5;
    N_ALLELES = 5;
    FIRST = -1;
    LAST = 1;
    GRID_SIZE = 51;  // 16 for paper
//    SysPop pop = KinshipIBDFactory.makeUnrelated();
    saveKinship(KinshipIBDFactory.makeUnrelated(), "debug");
//    saveKinship(KinshipIBDFactory.makeFullSib(), "debug");
//    saveKinship(KinshipIBDFactory.makeParentOffspring(), "debug");
  }
  public void testThompson() {
    saveThompson(KinshipIBDFactory.makeUnrelated(), "un");
    saveThompson(KinshipIBDFactory.makeFullSib(), "fs");
    saveThompson(KinshipIBDFactory.makeParentOffspring(), "po");
  }

  public void saveKinship(KinshipIBD pairIBD, String tag) {
    Kinship kinship = new Kinship();
    kinship.setDisplayLogs(false);
    SysAlleleFreq freq = SysAlleleFreqFactory.makeTriangular(N_LOCI, N_ALLELES);
//    SysAlleleFreq freq = SysAlleleFreqFactory.makeEquifrequent(N_LOCI, N_ALLELES);
//    SysAlleleFreq freq = SysAlleleFreqFactory.makeRandom(N_LOCI, N_ALLELES);
    log.info("\nfreq=\n" + freq);
    SysPop pair = KinshipSysPopFactory.makePair(pairIBD, freq);
    log.info("\npair=\n" + pair);

//    loadDebugABCD(pair);
//    loadDebugABC(pair);
//    loadDebugAABC(pair);
//    loadDebugL5(pair);

    double[] rm = Vec.makeBySize(FIRST, LAST, GRID_SIZE);
    double[] rp = Vec.makeBySize(FIRST, LAST, GRID_SIZE);
    double[][] X = new double[GRID_SIZE][GRID_SIZE];
    double[][] Y = new double[GRID_SIZE][GRID_SIZE];
    double[][] Z = new double[GRID_SIZE][GRID_SIZE];
    double lastValid = 0;
    for (int m = 0; m < GRID_SIZE; m++) {
      for (int p = 0; p < GRID_SIZE; p++) {
//        log.info("\npair=\n" + pair);
        KinshipIBD ibd = new KinshipIBD();
        ibd.setRm((float)rm[m]);
        ibd.setRp((float)rp[p]);
        KinshipLikeMtrx mtrx = new KinshipLikeMtrx(pair);
        mtrx.calcLogs(new KinshipLikeCalculator(pair, ibd, kinship));
        X[m][p] = rm[m];
        Y[m][p] = rp[p];
//        kinship.toString()
        double v = mtrx.getLog(0, 1);
        v = kinship.logToView(v);
        if (v == Like.MIN_LOG)
          Z[m][p] = lastValid;
        else                 {
          Z[m][p] = v;
          lastValid = v;
        }
      }
    }
    LOG.saveToFile(X, DIR, "rm.csv");
    LOG.saveToFile(Y, DIR, "rp.csv");
    LOG.saveToFile(Z, DIR, "like_kinship_"+tag+".csv");
  }

  private void loadDebugABCD(SysPop pair)
  {
    int LOC_IDX = 0;
    byte a = 0;
    byte b = 1;
    byte c = 2;
    byte d = 3;
    int M = 0;
    int P = 1;
    int x = 0;
    int y = 1;
    SysAlleleFreq freq = pair.getFreq();
//    freq.setFreq(LOC_IDX, a, 0.1);
//    freq.setFreq(LOC_IDX, b, 0.10014665);
//    freq.setFreq(LOC_IDX, c, 0.080322586);
    pair.setAllele(x, LOC_IDX, M, a);
    pair.setAllele(x, LOC_IDX, P, b);
    pair.setAllele(y, LOC_IDX, M, c);
    pair.setAllele(y, LOC_IDX, P, d);

//    KinshipRMaxLikeEstimator est = new KinshipRMaxLikeEstimatorDEBUG(pair);
//    double res = est.calc(x, y);
//    log.info("res=" + res);
  }

  private void loadDebugABC(SysPop pair)
  {
    int LOC_IDX = 0;
    byte a = 0;
    byte b = 1;
    byte c = 2;
    byte d = 3;
    int M = 0;
    int P = 1;
    int x = 0;
    int y = 1;
    SysAlleleFreq freq = pair.getFreq();
//    freq.setFreq(LOC_IDX, a, 0.1);
//    freq.setFreq(LOC_IDX, b, 0.10014665);
//    freq.setFreq(LOC_IDX, c, 0.080322586);
    pair.setAllele(x, LOC_IDX, M, a);
    pair.setAllele(x, LOC_IDX, P, b);
    pair.setAllele(y, LOC_IDX, M, a);
    pair.setAllele(y, LOC_IDX, P, c);

    KinshipRMaxLikeEstimator est = new KinshipRMaxLikeEstimatorDEBUG(pair);
//    KinshipRMaxLikeEstimator est = new KinshipRMaxLikeEstimator(pair);
    double res = est.calc(x, y);
    log.info("res=" + res);
  }

  private void loadDebugAABC(SysPop pair)
  {
    int LOC_IDX = 0;
    byte a = 0;
    byte b = 1;
    byte c = 2;
    byte d = 3;
    int M = 0;
    int P = 1;
    int x = 0;
    int y = 1;
    SysAlleleFreq freq = pair.getFreq();
//    freq.setFreq(LOC_IDX, a, 0.1);
//    freq.setFreq(LOC_IDX, b, 0.10014665);
//    freq.setFreq(LOC_IDX, c, 0.080322586);
    pair.setAllele(x, LOC_IDX, M, a);
    pair.setAllele(x, LOC_IDX, P, a);
    pair.setAllele(y, LOC_IDX, M, b);
    pair.setAllele(y, LOC_IDX, P, c);

    KinshipRMaxLikeEstimator est = new KinshipRMaxLikeEstimatorDEBUG(pair);
    double res = est.calc(x, y);
    log.info("res=" + res);
  }

  private void loadDebugL5(SysPop pair)
  {
    int id = 0;
    int L = 0;
    pair.setAllelePair(id, L++, (byte)3, (byte)0);
    pair.setAllelePair(id, L++, (byte)3, (byte)0);
    pair.setAllelePair(id, L++, (byte)2, (byte)1);
    pair.setAllelePair(id, L++, (byte)0, (byte)2);
    pair.setAllelePair(id, L++, (byte)0, (byte)2);

    id++;
    L = 0;
    pair.setAllelePair(id, L++, (byte)0, (byte)3);
    pair.setAllelePair(id, L++, (byte)3, (byte)2);
    pair.setAllelePair(id, L++, (byte)4, (byte)2);
    pair.setAllelePair(id, L++, (byte)1, (byte)1);
    pair.setAllelePair(id, L++, (byte)4, (byte)3);

    KinshipRMaxLikeEstimator est = new KinshipRMaxLikeEstimatorDEBUG(pair);
    double res = est.calc(0, 1);
    log.info("res=" + res);
  }

  public void saveThompson(KinshipIBD pairIBD, String tag) {
    SysAlleleFreq freq = SysAlleleFreqFactory.makeEquifrequent(N_LOCI, N_ALLELES);
//    SysAlleleFreq freq = SysAlleleFreqFactory.makeRandom(N_LOCI, N_ALLELES);
//    log.info("\nfreq=\n" + freq);
    SysPop pair = KinshipSysPopFactory.makePair(pairIBD, freq);

    double[] k0 = Vec.makeBySize(FIRST, LAST, GRID_SIZE);
    double[] k1 = Vec.makeBySize(FIRST, LAST, GRID_SIZE);
    double[][] X = new double[GRID_SIZE][GRID_SIZE];
    double[][] Y = new double[GRID_SIZE][GRID_SIZE];
    double[][] Z = new double[GRID_SIZE][GRID_SIZE];
    double minVal = 0;
    for (int i1 = 0; i1 < GRID_SIZE; i1++) {
      for (int i2 = 0; i2 < GRID_SIZE; i2++) {
        X[i1][i2] = k0[i1];
        Y[i1][i2] = k1[i2];
//        ThompsonIBD ibd = ThompsonIBDFactory.makeValid(r[ir], k2[p]);
        double k = 1 - k0[i1] - k1[i2];
        if (k < EPS  ||  k0[i1] < EPS  ||   k1[i2] < EPS) {
          Z[i1][i2] = minVal;
          continue;
        }
        ThompsonIBD ibd = new ThompsonIBD();
        ibd.set(ibd.K_2, k);
        ibd.set(ibd.K_0, k0[i1]);
        ibd.set(ibd.K_1, k1[i2]);

        ThompsonLikeMtrx mtrx = new ThompsonLikeMtrx(pair);
        mtrx.calcLogs(new ThompsonLikeCalculator(pair, ibd));
        double v = mtrx.getLog(0, 1);
        if (v == Like.MIN_LOG)
          Z[i1][i2] = minVal;
        else                 {
          Z[i1][i2] = v;
          if (minVal > v)
            minVal = v;
        }
      }
    }

    for (int i1 = 0; i1 < GRID_SIZE; i1++) {
      for (int i2 = 0; i2 < GRID_SIZE; i2++) {
        double k = 1 - k0[i1] - k1[i2];
        if (k < EPS  ||  k0[i1] < EPS  ||   k1[i2] < EPS) {
          Z[i1][i2] = minVal;
        }
      }
    }
    LOG.saveToFile(X, DIR, "k1.csv");
    LOG.saveToFile(Y, DIR, "k2.csv");
    LOG.saveToFile(Z, DIR, "like_thompson_"+tag+".csv");
  }
}
