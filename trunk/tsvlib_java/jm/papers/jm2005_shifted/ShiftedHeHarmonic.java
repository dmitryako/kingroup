package jm.papers.jm2005_shifted;
import Jama.EigenvalueDecomposition;
import jm.angular.Spin;
import jm.atom.HMtrx;
import jm.atom.Helium;
import jm.atom.SlaterLcr;
import jm.atom.SysTwoE;
import jm.grid.TransLcrToR;
import jm.grid.WFQuadrLcr;
import jm.harmonic.HarmonicLogCR;
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
 * User: jc138691, Date: 12/04/2005, Time: 10:33:17
 */
public class ShiftedHeHarmonic extends LogCRTestCase {
  int NUM_TRIALS = 10;
  protected static String DIR = "jm" + File.separator + "energy";
  public static Test suite() {
    return new TestSuite(ShiftedHeHarmonic.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testPaperFigureHeA_Ns() {
    NUM_STEPS = 400;
    FIRST = -2;
    STEP = 1. / 100.;
    int[] N_BY_L = {20};
    double Z = 2;
    ShellLS LS = new ShellLS(0, Spin.SINGLET);
    calcAllEnergies(true, Z, LS, N_BY_L, 0.5, Helium.EXACT_Ns_1S
      , "He_ns_beta05", "HeA");
    calcAllEnergies(true, Z, LS, N_BY_L, 0.6, Helium.EXACT_Ns_1S
      , "He_ns_beta06", "HeB");
//      calcAllEnergies(true, Z, ShellLS, N_BY_L, ALPHA_L, 10, Helium.EXACT_Ns_1S
//              , "He_ns_lambda100", "HeC");
  }
  public void testPaperTableHeA_Ns_1_1S() {
    NUM_STEPS = 440;
    FIRST = -3;
    STEP = 1. / 64.;
    double step = 0.01;
    double minVal = 0.45;
    int[] N_BY_L = {10};
    double Z = 2;
    ShellLS LS = new ShellLS(0, Spin.SINGLET);
    double e[] = new double[NUM_TRIALS];
    for (int i = 0; i < NUM_TRIALS; i++) {
      double val = minVal + i * step;
      e[i] = calcAllEnergies(false, Z, LS, N_BY_L, val, Helium.EXACT_Ns_1S
        , "HeHarmonic", "HeHarmonic2");
      LOG.report(this, "beta=" + val + ", TE=" + (float) e[i]);
    }
    int[] sorted = Vec.sortIdx(e);
    int idx = sorted[0];
    double zeta = minVal + idx * step;
    LOG.report(this, "beta=" + zeta + ", TE=" + e[idx]);
  }
  public double calcAllEnergies(boolean calcAll
    , double Z
    , ShellLS LS
    , int[] maxNL
    , double beta
    , double[] exact
    , String fileName
    , String fileName2) {
//      StepGrid x = new StepGrid(FIRST - Math.log(Z), NUM_STEPS, STEP);
    StepGrid x = new StepGrid(FIRST, NUM_STEPS, STEP);
    WFQuadrLcr w = new WFQuadrLcr(x);
    TransLcrToR xToR = w.getLogCRToR();
    DoubleArrayList arrDiff = new DoubleArrayList();
    DoubleArrayList arrE = new DoubleArrayList();
    DoubleArrayList arrN = new DoubleArrayList();
    ConfigArr cumBasis = null; // cumulative basis
    double E = 0;
    double res = 0;
    int runningN = 0;
    boolean isLast = false;
    for (int L = 0; L < maxNL.length; L++) {
      ConfigArr currBasis = null; // current
      for (int N = 1; N <= maxNL[L]; N++) {
        if (N == maxNL[L] && L == maxNL.length - 1)
          isLast = true;
        FuncArr arr = new HarmonicLogCR(xToR, N, L, beta);
        res = w.calcMaxOrthonErr(arr);
        TestCase.assertEquals(0, res, NORM_ERROR);
        SlaterLcr slater = new SlaterLcr(w);
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
      }
      cumBasis = currBasis; // accumulate configurations
      runningN += maxNL[L];
    }
    LOG.saveToFile(arrN.toArray(), arrDiff.toArray(), arrE.toArray(), DIR
      , fileName + "_1_" + LS.toString() + ".csv");
    LOG.saveToFile(arrN.toArray(), arrDiff.toArray(), arrE.toArray(), DIR
      , fileName2 + "_1_" + LS.toString() + ".csv");
    return res;
  }
}