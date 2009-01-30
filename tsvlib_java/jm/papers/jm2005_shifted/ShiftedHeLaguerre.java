package jm.papers.jm2005_shifted;
import Jama.EigenvalueDecomposition;
import jm.angular.Spin;
import jm.atom.*;
import jm.grid.TransLogCRToR;
import jm.grid.WFQuadrLogCR;
import jm.laguerre.LagrrLogCR;
import jm.shell.ConfigArr;
import jm.shell.ConfigArrFactory;
import jm.shell.ShellLS;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.iox.LOG;
import javax.utilx.arrays.DoubleArrayList;
import javax.utilx.arrays.vec.Vec;
import javax.vecmathx.function.FuncArr;
import javax.vecmathx.grid.StepGrid;
import java.io.File;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 5/04/2005, Time: 14:22:51
 */
public class ShiftedHeLaguerre extends LogCRTestCase {
  int NUM_TRIALS = 1;
  protected static String DIR = "jm" + File.separator + "energy";
  public static Test suite() {
    return new TestSuite(ShiftedHeLaguerre.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testPaperFigureHeA_Ns() {
    NUM_STEPS = 440;
    FIRST = -4;
    STEP = 1. / 50.;
    int[] N_BY_L = {20};
    int[] ALPHA_L = {2};
    int L = 0;
    double Z = 2;
    ShellLS LS = new ShellLS(L, Spin.SINGLET);
    calcAllEnergies(true, Z, LS, N_BY_L, ALPHA_L, 4, Helium.EXACT_Ns_1S
      , "He_ns_lambda40", "HeA");
    calcAllEnergies(true, Z, LS, N_BY_L, ALPHA_L, 7, Helium.EXACT_Ns_1S
      , "He_ns_lambda70", "HeB");
//      calcAllEnergies(true, Z, ShellLS, N_BY_L, ALPHA_L, 10, Helium.EXACT_Ns_1S
//              , "He_ns_lambda100", "HeC");
  }
  public void testPaperTableE() {
    double thisEs = -2.8790285063210046;
    double[] E = {0, 0};
//      E[0] = Helium.EXACT_Nsp_1S[0] - Helium.EXACT_Ns_1S[0] + thisEs;
    E[0] = Helium.EXACT_Nsp_1S[0] - Helium.EXACT_Ns_1S[0];
    LOG.report(this, "Ep=" + E[0]);
    E[0] = -2.879028506 - -2.900515460;
    LOG.report(this, "paper Ep=" + E[0]);
    E[0] = -2.879028627 - -2.900515774;
    LOG.report(this, "STO Ep=" + E[0]);
    E[0] = Helium.EXACT_Nspd_1S[0] - Helium.EXACT_Nsp_1S[0];
    LOG.report(this, "Ed=" + E[0]);
    E[0] = Helium.EXACT_Nspdf_1S[0] - Helium.EXACT_Nspd_1S[0];
    LOG.report(this, "Ef=" + E[0]);
  }
  public void testPaperFigureHeB_Np() {
    NUM_STEPS = 440;
    FIRST = -4;
    STEP = 1. / 50.;
    int[] N_BY_L = {20, 19};
    int[] ALPHA_L = {2, 4};
    double[] lambda = {9.5, 0};
    double thisEs = -2.8790285063210046;
    double[] Ep = {0, 0};
    Ep[0] = Helium.EXACT_Nsp_1S[0] - Helium.EXACT_Ns_1S[0] + thisEs;
    int L = 0;
    double Z = 2;
    ShellLS LS = new ShellLS(L, Spin.SINGLET);
    lambda[1] = 4;
    calcEnergyLastL(Z, LS, N_BY_L, ALPHA_L, lambda, Ep, "He_nsp_lambda40", "HeA");
    lambda[1] = 7;
    calcEnergyLastL(Z, LS, N_BY_L, ALPHA_L, lambda, Ep, "He_nsp_lambda70", "HeB");
    lambda[1] = 9.5;
    calcEnergyLastL(Z, LS, N_BY_L, ALPHA_L, lambda, Ep, "He_nsp_lambda95", "HeC");
  }
  public void testPaperFigureHeB_Nd() {
    NUM_STEPS = 440;
    FIRST = -4;
    STEP = 1. / 50.;
    int[] N_BY_L = {20, 19, 18};
    int[] ALPHA_L = {2, 4, 6};
    double[] lambda = {9.5, 9.5, 0};
    double thisEsp = -2.900515460445031;
    double[] Ep = {0, 0};
    Ep[0] = Helium.EXACT_Nspd_1S[0] - Helium.EXACT_Nsp_1S[0] + thisEsp;
    int L = 0;
    double Z = 2;
    ShellLS LS = new ShellLS(L, Spin.SINGLET);
    lambda[2] = 4;
    calcEnergyLastL(Z, LS, N_BY_L, ALPHA_L, lambda, Ep, "He_nspd_lambda40", "HeA");
    lambda[2] = 7;
    calcEnergyLastL(Z, LS, N_BY_L, ALPHA_L, lambda, Ep, "He_nspd_lambda70", "HeB");
    lambda[2] = 9.5;
    calcEnergyLastL(Z, LS, N_BY_L, ALPHA_L, lambda, Ep, "He_nspd_lambda95", "HeC");
  }
  public void testPaperTableHeA_Ns_1_1S() {
    NUM_STEPS = 440;
    FIRST = -4;
    STEP = 1. / 50.;
    double step = 0.1;
    double minVal = 9.5;
    int[] N_BY_L = {20};
    int[] ALPHA_L = {2};
    int L = 0;
    double Z = 2;
    ShellLS LS = new ShellLS(L, Spin.SINGLET);
    double e[] = new double[NUM_TRIALS];
    for (int i = 0; i < NUM_TRIALS; i++) {
      double val = minVal + i * step;
      e[i] = calcAllEnergies(false, Z, LS, N_BY_L, ALPHA_L, val, Helium.EXACT_Ns_1S
        , "HeSearch", "HeSearch2");
      LOG.report(this, "lambda=" + val + ", TE=" + (float) e[i]);
    }
    int[] sorted = Vec.sortIdx(e);
    int idx = sorted[0];
    double zeta = minVal + idx * step;
    LOG.report(this, "lambda=" + zeta + ", TE=" + e[idx]);
  }
  public void testPaperTableHeA_NsNp() {
    double step = 0.5;
    double minVal = 9.5;
    double[] lambda = {9.5, 0};
    int[] N_BY_L = {20, 19};
    int[] ALPHA_L = {2, 4};
    int currL = 1;
    int L = 0;
    double Z = 2;
    ShellLS LS = new ShellLS(L, Spin.SINGLET);
    double e[] = new double[NUM_TRIALS];
    for (int i = 0; i < NUM_TRIALS; i++) {
      double val = minVal + i * step;
      lambda[currL] = val;
//         e[i] = calcAllEnergies(false, Z, ShellLS, N_BY_L, ALPHA_L, lambda, Helium.EXACT_Nsp_1S
      e[i] = calcAllEnergies(false, Z, LS, N_BY_L, ALPHA_L, val, Helium.EXACT_Nsp_1S
//         e[i] = calcAllEnergies(false, Z, ShellLS, N_BY_L, ALPHA_L, val, Helium.EXACT_Nspd_1S
        , "HeSearch", "HeSearch2");
      LOG.report(this, "lambda=" + val + ", TE=" + (float) e[i]);
    }
    int[] sorted = Vec.sortIdx(e);
    int idx = sorted[0];
    double zeta = minVal + idx * step;
    LOG.report(this, "lambda=" + zeta + ", TE=" + e[idx]);
  }
  public void testAllHe_1S() {
    int[] N_BY_L = {20, 19, 18, 17, 16};
    int[] ALPHA_L = {2, 4, 6, 8, 10};
    int L = 0;
    double Z = 2;
    ShellLS LS = new ShellLS(L, Spin.SINGLET);
    calcAllEnergies(true, Z, LS, N_BY_L, ALPHA_L, 4, Helium.EXACT_1S, "HeA", "He_L4_lambda40");
    calcAllEnergies(true, Z, LS, N_BY_L, ALPHA_L, 7, Helium.EXACT_1S, "HeB", "He_L4_lambda70");
    calcAllEnergies(true, Z, LS, N_BY_L, ALPHA_L, 9.5, Helium.EXACT_1S, "HeC", "He_L4_lambda95");
  }
  public void testBestHe_1S() {
    int L = 0;
    double Z = 2;
    ShellLS LS = new ShellLS(L, Spin.SINGLET);
    int[] N_BY_L = {25, 24, 24, 24, 24};
    int[] ALPHA_L = {2, 4, 4, 4, 4};       //9035031375715032

//      int[] N_BY_L = {24, 23, 22, 21, 20};
//      int[] ALPHA_L = {2, 4, 6, 8, 10};      //903490 2670689475
//      int[] ALPHA_L = {2, 4, 6, 6, 6};       //903497 3821001095
//      int[] ALPHA_L = {2, 4, 4, 4, 4};       //903498 735562124
    calcAllEnergies(false, Z, LS, N_BY_L, ALPHA_L, 4, Helium.EXACT_1S, "HeA", "");
//      calcAllEnergies(true, Z, ShellLS, N_BY_L, ALPHA_L, 2, exact, "HeB");
//      calcAllEnergies(true, Z, ShellLS, N_BY_L, ALPHA_L, 1, exact, "HeC");
  }
  public void testHy_1S() {
    int[] N_BY_L = {25, 24, 24, 24, 24};
    int[] ALPHA_L = {2, 4, 4, 4, 4};       //9035031375715032
    int L = 0;
    double Z = 1;
    ShellLS LS = new ShellLS(L, Spin.SINGLET);
    calcAllEnergies(true, Z, LS, N_BY_L, ALPHA_L, 2, Hydrogen.EXACT_1S, "HyA", "");
    calcAllEnergies(true, Z, LS, N_BY_L, ALPHA_L, 1, Hydrogen.EXACT_1S, "HyB", "");
    calcAllEnergies(true, Z, LS, N_BY_L, ALPHA_L, 0.5, Hydrogen.EXACT_1S, "HyC", "");
  }
//   public void testHe_3S() {
//      int L = 0;
//      double Z = 2;
//      ShellLS ShellLS = new ShellLS(L, Spin.TRIPLET);
//      double[] exact = {-2.1752294 // Acad et al (1971) PhysRev A4, p516
//                        , -2.1752294};
//      calcAllEnergies(true, Z, ShellLS, N_BY_L, ALPHA_L, 4, exact, "HeA");
//      calcAllEnergies(true, Z, ShellLS, N_BY_L, ALPHA_L, 2, exact, "HeB");
//      calcAllEnergies(true, Z, ShellLS, N_BY_L, ALPHA_L, 1, exact, "HeC");
//   }
//   public void testHy_3S() {
//      int L = 0;
//      double Z = 1;
//      ShellLS ShellLS = new ShellLS(L, Spin.TRIPLET);
//      double[] exact = {-0.4993 // Pekeris 1962 PhysRev 126, p1470
//                        , -0.4993};
//      calcAllEnergies(true, Z, ShellLS, N_BY_L, ALPHA_L, 2, exact, "HyA");
//      calcAllEnergies(true, Z, ShellLS, N_BY_L, ALPHA_L, 1, exact, "HyB");
//      calcAllEnergies(true, Z, ShellLS, N_BY_L, ALPHA_L, 0.5, exact, "HyC");
//   }
//   public void testHe_1P() {
//      int L = 1;
//      double Z = 2;
//      ShellLS ShellLS = new ShellLS(L, Spin.SINGLET);
//      double[] exact = {-2.1238431 // Acad et al (1971) PhysRev A4, p516
//                        , -2.1238431};
//      calcAllEnergies(true, Z, ShellLS, N_BY_L, ALPHA_L, 4, exact, "HeA");
//      calcAllEnergies(true, Z, ShellLS, N_BY_L, ALPHA_L, 2, exact, "HeB");
//   }
//   public void testHe_3P() {
//      int L = 1;
//      double Z = 2;
//      ShellLS ShellLS = new ShellLS(L, Spin.TRIPLET);
//      double[] exact = {-2.1752294 // Acad et al (1971) PhysRev A4, p516
//                        , -2.1752294};
//      calcAllEnergies(true, Z, ShellLS, N_BY_L, ALPHA_L, 4, exact, "HeA");
//      calcAllEnergies(true, Z, ShellLS, N_BY_L, ALPHA_L, 2, exact, "HeB");
//   }
  public double calcAllEnergies(boolean calcAll
    , double Z
    , ShellLS LS
    , int[] maxNL
    , int[] alphaL
    , double lambda
    , double[] exact
    , String fileName
    , String fileName2) {
    double[] lambdaL = Vec.makeArray(lambda, alphaL.length);
    return calcAllEnergies(calcAll, Z, LS, maxNL
      , alphaL, lambdaL, exact, fileName, fileName2);
  }
  public double calcAllEnergies(boolean calcAll
    , double Z
    , ShellLS LS
    , int[] maxNL
    , int[] alphaL
    , double[] lambda
    , double[] exact
    , String fileName
    , String fileName2) {
    StepGrid x = new StepGrid(FIRST - Math.log(Z), NUM_STEPS, STEP);
    WFQuadrLogCR w = new WFQuadrLogCR(x);
    TransLogCRToR xToR = w.getLogCRToR();
    DoubleArrayList arrDiff = new DoubleArrayList();
    DoubleArrayList arrDiff2 = new DoubleArrayList();
    DoubleArrayList arrE = new DoubleArrayList();
    DoubleArrayList arrE2 = new DoubleArrayList();
    DoubleArrayList arrN = new DoubleArrayList();
    DoubleArrayList arrN2 = new DoubleArrayList();
    ConfigArr cumBasis = null; // cumulative basis
    double E = 0;
    double res = 0;
    int runningN = 0;
    boolean isLast = false;
    for (int L = 0; L < alphaL.length; L++) {
      ConfigArr currBasis = null; // current
      for (int N = 1; N <= maxNL[L]; N++) {
        if (N == maxNL[L] && L == alphaL.length - 1)
          isLast = true;
        FuncArr arr = new LagrrLogCR(xToR, N, alphaL[L], lambda[L]);
        res = w.calcMaxOrthonErr(arr);
        TestCase.assertEquals(0, res, NORM_ERROR);
        SlaterLCR slater = new SlaterLCR(w);
        SysTwoE sys = new SysTwoE(-Z, slater);
        currBasis = ConfigArrFactory.makeTwoElecFrom(cumBasis, LS, N, L, arr);
        int totN = currBasis.size();
        LOG.report(this, "totN=" + totN);
        if (totN < 1)
          continue;
        EigenvalueDecomposition eig = null;
        E = 1;
        if (calcAll || isLast) {
          HMtrx H = new HMtrx(currBasis, sys);
          eig = H.eig();
//            LOG.report(this, "H=" + DoubleArr.toString(eig.getRealEigenvalues()));
          E = eig.getRealEigenvalues()[0];
        }
        LOG.report(this, "L=" + L + ", N=" + N + ", E[0]=" + E);
        TestCase.assertEquals(true, exact[0] < E);
        arrE.add(E);
        res = E;
        E = Math.log(E - exact[0]);
        arrDiff.add(E);
        arrN.add(runningN + N);
        if (totN > 1) {
          double E2 = 1;
          if (calcAll || isLast) {
            E2 = eig.getRealEigenvalues()[1];
            LOG.report(this, "  " + L + ",   " + N + ", E[1]=" + E2);
          }
          arrE2.add(E2);
          E2 = Math.log(E2 - exact[1]);
          arrDiff2.add(E2);
          arrN2.add(runningN + N);
        }
      }
      cumBasis = currBasis; // accumulate configurations
      runningN += maxNL[L];
    }
    LOG.saveToFile(arrN.toArray(), arrDiff.toArray(), arrE.toArray(), DIR
      , fileName + "_1_" + LS.toString() + ".csv");
    LOG.saveToFile(arrN.toArray(), arrDiff.toArray(), arrE.toArray(), DIR
      , fileName2 + "_1_" + LS.toString() + ".csv");
    LOG.saveToFile(arrN2.toArray(), arrDiff2.toArray(), arrE2.toArray()
      , DIR, fileName + "_2_" + LS.toString() + ".csv");
    LOG.saveToFile(arrN2.toArray(), arrDiff2.toArray(), arrE2.toArray()
      , DIR, fileName2 + "_2_" + LS.toString() + ".csv");
    return res;
  }
  public double calcEnergyLastL(double Z
    , ShellLS LS
    , int[] maxNL
    , int[] alphaL
    , double[] lambda
    , double[] exact
    , String fileName
    , String fileName2) {
    StepGrid x = new StepGrid(FIRST - Math.log(Z), NUM_STEPS, STEP);
    WFQuadrLogCR w = new WFQuadrLogCR(x);
    TransLogCRToR xToR = w.getLogCRToR();
    DoubleArrayList arrDiff = new DoubleArrayList();
    DoubleArrayList arrE = new DoubleArrayList();
    DoubleArrayList arrN = new DoubleArrayList();
    ConfigArr cumBasis = null; // cumulative basis
    double E = 0;
    double res = 0;
    boolean isLast = false;
    for (int L = 0; L < alphaL.length; L++) {
      if (L == alphaL.length - 1)
        isLast = true;
      ConfigArr currBasis = null; // current
      for (int N = 1; N <= maxNL[L]; N++) {
        FuncArr arr = new LagrrLogCR(xToR, N, alphaL[L], lambda[L]);
        res = w.calcMaxOrthonErr(arr);
        TestCase.assertEquals(0, res, NORM_ERROR);
        SlaterLCR slater = new SlaterLCR(w);
        SysTwoE sys = new SysTwoE(-Z, slater);
        currBasis = ConfigArrFactory.makeTwoElecFrom(cumBasis, LS, N, L, arr);
        int totN = currBasis.size();
        LOG.report(this, "totN=" + totN);
        if (totN < 1 || !isLast)
          continue;
        EigenvalueDecomposition eig = null;
        E = 1;
        HMtrx H = new HMtrx(currBasis, sys);
        eig = H.eig();
        E = eig.getRealEigenvalues()[0];
        LOG.report(this, "L=" + L + ", N=" + N + ", E[0]=" + E);
        TestCase.assertEquals(true, exact[0] < E);
        arrE.add(E);
        res = E;
        E = Math.log(E - exact[0]);
        arrDiff.add(E);
        arrN.add(N);
      }
      cumBasis = currBasis; // accumulate configurations
    }
    LOG.saveToFile(arrN.toArray(), arrDiff.toArray(), arrE.toArray(), DIR
      , fileName + "_1_" + LS.toString() + ".csv");
    LOG.saveToFile(arrN.toArray(), arrDiff.toArray(), arrE.toArray(), DIR
      , fileName2 + "_1_" + LS.toString() + ".csv");
    return res;
  }
}
