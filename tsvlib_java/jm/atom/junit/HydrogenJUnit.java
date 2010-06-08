package jm.atom.junit;
import Jama.EigenvalueDecomposition;
import jm.angular.Spin;
import jm.atom.HMtrx;
import jm.atom.HMtrxFactory;
import jm.atom.SlaterLcr;
import jm.atom.SysOneE;
import jm.atom.coulomb.CoulombWFFactory;
import jm.grid.TransLcrToR;
import jm.grid.TransLogRToR;
import jm.grid.WFQuadrLcr;
import jm.shell.Shell;
import jm.shell.ShellConfig;
import jm.shell.ShellLS;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import stlx.FastLoop;
import stlx.valarrayx.valarray;

import javax.iox.LOG;
import javax.utilx.arrays.vec.Vec;
import javax.vecmathx.function.FuncVec;
import javax.vecmathx.grid.StepGrid;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 3, 2004, Time: 4:53:59 PM
 */
public class HydrogenJUnit extends TestCase {
  public static Test suite() {
    return new TestSuite(HydrogenJUnit.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void test_2s() {
    int L = 0;
    int N = 3;
    double Zeff = 0.5;
    int alpha = 2 * L + 2;
    double lambda = 2. * Zeff / (L + 1);
    double test1 = -0.5 / 4.;
    int SIZE = 261; //261            // x = log(r)
    double FIRST = -3;
    double LAST = 5; // exp(4.605) =100
    HMtrx H
      = HMtrxFactory.makeHyFromLagrrLcr(FIRST, LAST, SIZE, N, L, alpha, lambda);
    EigenvalueDecomposition eig = H.eig();
    LOG.report(this, H.toString(eig, 0));
    LOG.report(this, H.toString(eig, 1));
    LOG.report(this, H.toString(eig, 2));
    LOG.report(this, H.toStringSorted(eig, 1));
    double e1 = eig.getRealEigenvalues()[1];
    LOG.report(this, "eigX=" + Vec.toString(eig.getRealEigenvalues()));
    assertEquals(0, Math.abs(test1 - e1), 4e-13);
  }
  public void testL() {
    LOG.setTrace(true);
    double FIRST = -5;
    int NUM_STEPS = 261;
    double LAST = 7; // exp(7) = 1096
    StepGrid x = new StepGrid(FIRST, LAST, NUM_STEPS);
//      double FIRST = -4;
//      int NUM_STEPS = 220;
//      double STEP = 1./16.;
//      StepGrid x = new StepGrid(FIRST, NUM_STEPS, STEP);
    WFQuadrLcr wCR = new WFQuadrLcr(x);
    TransLcrToR xToR = wCR.getLogCRToR();
    valarray r = xToR;
    double Zeff = 1;
    FuncVec f = CoulombWFFactory.makeP1s(r, Zeff);
    f.setX(xToR.x); // MUST change grid for derivatives
    f.mult(xToR.getDivSqrtCR());
    double res = FastLoop.dot(f, f, wCR.getWithCR2());
    assertEquals(0, Math.abs(res - 1), 6e-15);
    f = CoulombWFFactory.makeP2p(r, Zeff);
    f.setX(xToR.x); // MUST change grid for derivatives
    f.mult(xToR.getDivSqrtCR());
    res = FastLoop.dot(f, f, wCR.getWithCR2());
    assertEquals(0, Math.abs(res - 1), 4e-16);
    int L = 1;
    double test0 = -0.125;
    double testKin = -test0;
    double testPot = 2 * test0;
    ShellLS LS = new ShellLS(L, Spin.ELECTRON);
    Shell sh = new Shell(1, f, 1, LS.L, LS);
    ShellConfig fc = new ShellConfig(sh);
    SlaterLcr slater = new SlaterLcr(wCR);
    SysOneE sys = new SysOneE(-1., slater);
    double kin = sys.calcKin(fc, fc);
    assertEquals(0, Math.abs(testKin - kin), 4e-12);
    double pot = sys.calcPot(fc, fc);
    assertEquals(0, Math.abs(testPot - pot), 2e-16);
    assertEquals(0, Math.abs(-2. - pot / kin), 6e-11);
    res = sys.calcTot(fc, fc);
    assertEquals(0, Math.abs(test0 - res), 4e-12);
  }
  public void testSplitIntegrationR() {
    LOG.setTrace(true);
    int L = 0;
    int N = 3;
    double Zeff = 1;
    int alpha = 2 * L + 2;
    double lambda = 2. * Zeff;
    double FIRST = 0;
    int LAST = 10;
    double F_HALF = 0.5; // first half
    int F_SIZE = 13;
    int S_SIZE = 401;
    int R_SIZE = F_SIZE + S_SIZE - 1;
    HMtrx Ham2nd
      = HMtrxFactory.makeFromLgrrR(F_HALF, LAST, S_SIZE, N, L, alpha, lambda);
    LOG.trace(this, "Ham2nd=\n", Ham2nd);
//      HMtrx Ham2ndLogR
//         = HMtrxFactory.makeFromLaguerreLogR(F_HALF, LAST, S_SIZE, N, L, alpha, lambda);
    HMtrx Ham
      = HMtrxFactory.makeFromLgrrR(FIRST, LAST, R_SIZE, N, L, alpha, lambda);
    HMtrx HamF
      = HMtrxFactory.makeFromLgrrR(FIRST, F_HALF, F_SIZE, N, L, alpha, lambda);
//      HMtrx HamFLogR
//         = HMtrxFactory.makeFromLaguerreLogR(FIRST, F_HALF, F_SIZE, N, L, alpha, lambda);
    EigenvalueDecomposition eig = HamF.eig();
    LOG.report(this, "FIRST(via R)=" + Vec.toString(eig.getRealEigenvalues()));

//      EigenvalueDecomposition eigLogR = HamFLogR.eig();
//      LOG.report(this, "FIRST (LogR)=" + DoubleArr.toString(eigLogR.getRealEigenvalues()));
    eig = Ham2nd.eig();
    LOG.report(this, "2nd part=" + Vec.toString(eig.getRealEigenvalues()));

//      eigLogR = Ham2ndLogR.eig();
//      LOG.report(this, "2nd LogR=" + DoubleArr.toString(eigLogR.getRealEigenvalues()));
    HamF.add(Ham2nd);
    eig = HamF.eig();
    double eFull = eig.getRealEigenvalues()[0];
    LOG.report(this, "via two part=" + Vec.toString(eig.getRealEigenvalues()));
    eig = Ham.eig();
    LOG.report(this, "eigR=" + Vec.toString(eig.getRealEigenvalues()));
    double eR = eig.getRealEigenvalues()[0];
    assertEquals(0, Math.abs(-0.5 - eR), 3e-7);
    assertEquals(0, Math.abs(-0.5 - eFull), 3e-7);
  }
  public void testSplitIntegrationLogR() {
    int L = 0;
    int N = 4;
    double Zeff = 1;
    int alpha = 2 * L + 2;
    double lambda = 2. * Zeff;
    double FIRST = -5;
    int LAST = 5;
    double F_HALF = 0; // first half
    int F_SIZE = 101;
    int S_SIZE = 101;
    int R_SIZE = F_SIZE + S_SIZE - 1;
    HMtrx Ham
      = HMtrxFactory.makeFromLaguerreLogR(FIRST, LAST, R_SIZE, N, L, alpha, lambda);
    HMtrx HamF
      = HMtrxFactory.makeFromLaguerreLogR(FIRST, F_HALF, F_SIZE, N, L, alpha, lambda);
    HMtrx Ham2nd
      = HMtrxFactory.makeFromLaguerreLogR(F_HALF, LAST, S_SIZE, N, L, alpha, lambda);
    EigenvalueDecomposition eig = HamF.eig();
    LOG.report(this, "FIRST part=" + Vec.toString(eig.getRealEigenvalues()));
    eig = Ham2nd.eig();
    LOG.report(this, "2nd part=" + Vec.toString(eig.getRealEigenvalues()));
    HamF.add(Ham2nd);
    eig = HamF.eig();
    LOG.report(this, "via two part=" + Vec.toString(eig.getRealEigenvalues()));
    eig = Ham.eig();
    LOG.report(this, "as one=" + Vec.toString(eig.getRealEigenvalues()));
    double e0 = eig.getRealEigenvalues()[0];
    assertEquals(0, Math.abs(-0.5 - e0), 5e-8);
  }
  public void testEnergyLogCR() {
    int L = 0;
    int N = 4;
    double Zeff = 1;
    int alpha = 2 * L + 2;
    double lambda = 2. * Zeff;
    double FIRST = -5;
    int LAST = 5;
    double F_HALF = -1; // first half
    int F_SIZE = 101;
    int S_SIZE = 101;
    int R_SIZE = F_SIZE + S_SIZE - 1;
    HMtrx Ham
      = HMtrxFactory.makeHyFromLagrrLcr(FIRST, LAST, R_SIZE, N, L, alpha, lambda);
    HMtrx Ham2
      = HMtrxFactory.makeHyFromLagrrLcr(F_HALF, LAST, S_SIZE, N, L, alpha, lambda);
    EigenvalueDecomposition eig = Ham2.eig();
    LOG.report(this, "x=[0,5]=" + Vec.toString(eig.getRealEigenvalues()));
    double e0 = eig.getRealEigenvalues()[0];
    assertEquals(0, Math.abs(-0.5 - e0), 2e-9);
    eig = Ham.eig();
    LOG.report(this, "x=[-5,5]=" + Vec.toString(eig.getRealEigenvalues()));
    e0 = eig.getRealEigenvalues()[0];
    assertEquals(0, Math.abs(-0.5 - e0), 6e-12);
  }
  public void testEnergy() {
    int L = 0;
    int N = 1;
    double Zeff = 1;
    int alpha = 2 * L + 2;
    double lambda = 2. * Zeff;
    int SIZE_X = 261; //261            // x = log(r)
    double FIRST_X = -5; //-5
    double LAST_X = 5; // exp(4.605) =100
    HMtrx HamX
      = HMtrxFactory.makeFromLaguerreLogR(FIRST_X, LAST_X, SIZE_X, N, L, alpha, lambda);
    double FIRST_R = 0;
    double LAST_SMALL_R = Math.exp(FIRST_X); // e.g. FIRST = -5;
    int SMALL_SIZE = 21;
    HMtrx HamS // S-for small
      = HMtrxFactory.makeFromLgrrR(FIRST_R, LAST_SMALL_R, SMALL_SIZE, N, L, alpha, lambda);
    double LAST_R = Math.exp(LAST_X);
    int R_SIZE = 5001;
    HMtrx HamR
      = HMtrxFactory.makeFromLgrrR(FIRST_R, LAST_R, R_SIZE, N, L, alpha, lambda);
    EigenvalueDecomposition eig = HamX.eig();
    double eX = eig.getRealEigenvalues()[0];
    LOG.report(this, "eigX=" + Vec.toString(eig.getRealEigenvalues()));
    HamX.add(HamS);
    eig = HamX.eig();
    double eXR = eig.getRealEigenvalues()[0];
    LOG.report(this, "eigX+SmallR=" + Vec.toString(eig.getRealEigenvalues()));
    eig = HamR.eig();
    double eR = eig.getRealEigenvalues()[0];
    LOG.report(this, "eigR=" + Vec.toString(eig.getRealEigenvalues()));
    assertEquals(0, Math.abs(-0.5 - eX), 3e-7);
    assertEquals(0, Math.abs(-0.5 - eXR), 8e-13);
    assertEquals(0, Math.abs(-0.5 - eR), 2e-9);
  }
  public void testEnergies_N_10() {
    int L = 0;
    int N = 10;
    double Zeff = 1;
    int alpha = 2 * L + 2;
    double lambda = 2. * Zeff;
    double exact_0 = -0.5;
    int SIZE_X = 261; //261            // x = log(r)
    double FIRST_X = -5; //-5
    double LAST_X = 5; // exp(4.605) =100
    StepGrid x = new StepGrid(FIRST_X, LAST_X, SIZE_X);
    FuncVec xToR = new TransLogRToR(x);
    HMtrx HamX
      = HMtrxFactory.makeFromLaguerreLogR(FIRST_X, LAST_X, SIZE_X, N, L, alpha, lambda);
    double FIRST_R = 0;
    int SMALL_SIZE = 9;
    HMtrx HamS // S-for small
      = HMtrxFactory.makeFromLgrrR(FIRST_R, xToR.getFirst(), SMALL_SIZE, N, L, alpha, lambda);
    int R_SIZE = 5001;
    HMtrx HamR
      = HMtrxFactory.makeFromLgrrR(FIRST_R, xToR.getLast(), R_SIZE, N, L, alpha, lambda);
    EigenvalueDecomposition eig = HamX.eig();
    double eX = eig.getRealEigenvalues()[0];
    LOG.report(this, "eigX=" + Vec.toString(eig.getRealEigenvalues()));
    HamX.add(HamS);
    eig = HamX.eig();
    double eXR = eig.getRealEigenvalues()[0];
    LOG.report(this, "eigX+SmallR=" + Vec.toString(eig.getRealEigenvalues()));
    eig = HamR.eig();
    double eR = eig.getRealEigenvalues()[0];
    LOG.report(this, "eigR=" + Vec.toString(eig.getRealEigenvalues()));
    assertEquals(0, -0.5 - eX, 3e-6);   // 261 X-points
    assertEquals(0, -0.5 - eXR, 8e-13); // + 9 R-points
    assertEquals(0, -0.5 - eR, 2e-9); // with 5,001 points
  }
  public void testEnergies_L() {
    int L = 1;
    int N = 1;
    double Zeff = 1;
    int alpha = 2 * L + 2;
    double lambda = 2. * Zeff / (L + 1);
    double test0 = -0.125;
    double test1 = test0 * 4 / 9;
    int SIZE = 261; //261            // x = log(r)
    double FIRST = -3;
    double LAST = 5; // exp(4.605) =100
    HMtrx H
      = HMtrxFactory.makeHyFromLagrrLcr(FIRST, LAST, SIZE, N, L, alpha, lambda);
    EigenvalueDecomposition eig = H.eig();
    double e0 = eig.getRealEigenvalues()[0];
    LOG.report(this, "eigX=" + Vec.toString(eig.getRealEigenvalues()));
//      assertEquals(test0, e0, 3e-16);
    assertEquals(0, Math.abs(test0 - e0), 2e-13);
    N = 10;
    H = HMtrxFactory.makeHyFromLagrrLcr(FIRST, LAST, SIZE, N, L, alpha, lambda);
    eig = H.eig();
    e0 = eig.getRealEigenvalues()[0];
    double e1 = eig.getRealEigenvalues()[1];
    LOG.report(this, "eigX=" + Vec.toString(eig.getRealEigenvalues()));
    assertEquals(0, Math.abs(test0 - e0), 2e-13);
    assertEquals(0, Math.abs(test1 - e1), 5e-10);

//      H.calcMaxNormErrorByColumn(eig);
//      H.calcMaxNormErrorByRow(eig);
    LOG.report(this, H.toString(eig, 0));
    LOG.report(this, H.toStringSorted(eig, 0));
  }
}