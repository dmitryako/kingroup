package jm.atom.junit;
import Jama.EigenvalueDecomposition;
import jm.angular.Spin;
import jm.atom.HMtrx;
import jm.atom.SlaterLCR;
import jm.atom.SysTwoE;
import jm.grid.TransLogCRToR;
import jm.grid.WFQuadrLogCR;
import jm.laguerre.LagrrLogCR;
import jm.shell.ConfigArr;
import jm.shell.ConfigArrFactory;
import jm.shell.ShellLS;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import stlx.FastLoop;
import stlx.valarrayx.valarray;

import javax.iox.LOG;
import javax.utilx.arrays.vec.Vec;
import javax.vecmathx.function.FuncVec;
import javax.vecmathx.function.FuncArr;
import javax.vecmathx.grid.StepGrid;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 18/03/2005, Time: 15:10:06
 */
public class HeliumJUnit extends TestCase {
  public static Test suite() {
    return new TestSuite(HeliumJUnit.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }

//   bool CTestHelium::test() const {
//      if (!test_two_configs()) //DONE testClementiLimitOneConfig,
//         return false;
//      if (!test_ns_configs()) // DONE, testClementiLimitNS
//         return false;
//      if (!test_eigen_vec()) // ?
//         return false;
//      if (!test_bunge_1S()) // DONE    testClementiLimitNS
//         return false;
//      if (!test_complete_laguarre_1S())  // todo
//         return false;
//      if (!test_best_1S()) // todo
//         return false;
//      if (!test_one_config())   // DONE, testClementiSingleZeta,
//         return false;
//      if (!test_2p2_1S()) // todo
//         return false;
//      return true;
//   }
  public void testLimitNS() {
    LOG.setTrace(true);
    double FIRST = -5;
    int NUM_STEPS = 381;
    double LAST = 7; // exp(7) = 1096
    StepGrid x = new StepGrid(FIRST, LAST, NUM_STEPS);
    WFQuadrLogCR wCR = new WFQuadrLogCR(x);
    TransLogCRToR xToR = wCR.getLogCRToR();
    int L = 0;
    double Zeff = 2;
    int alpha = 2 * L + 2;
    double lambda = 2. * Zeff;
    int N = 4;
    FuncArr arr = new LagrrLogCR(xToR, N, alpha, lambda);
    double res = wCR.calcMaxOrthonErr(arr);
    assertEquals(0, res, 7e-13);
    SlaterLCR slater = new SlaterLCR(wCR);
    ShellLS S1 = new ShellLS(0, Spin.SINGLET);
    SysTwoE sys = new SysTwoE(-2., slater);

    // Multi-Config Hartree-Fock with 1s2+...+4s2
    double tot = -2.878990; // from p.164 of Froese-Fischer
    ConfigArr basis = ConfigArrFactory.makeTwoElec(S1, N, L, arr);
    HMtrx H = new HMtrx(basis, sys);
    EigenvalueDecomposition eig = H.eig();
    LOG.report(this, "H=" + Vec.toString(eig.getRealEigenvalues()));
    double e0 = eig.getRealEigenvalues()[0];
    LOG.report(this, "Multi-Config Hartree-Fock with 1s2+...+4s2"
      + "\ntot =" + tot
      + "\ne[0]=" + e0);
    assertEquals(0, Math.abs(e0 - tot), 6e-4);

    // 1s2s limit
    tot = -2.877997; // 1s-2s from p.2617 of Bunge, PhysRevA 56 (1997) p2614
    N = 3;
    arr = new LagrrLogCR(xToR, N, alpha, lambda);
    res = wCR.calcMaxOrthonErr(arr);
    assertEquals(0, res, 1e-13);
    basis = ConfigArrFactory.makeTwoElec(S1, N, L, arr);
    eig = new HMtrx(basis, sys).eig();
    LOG.report(this, "H=" + Vec.toString(eig.getRealEigenvalues()));
    e0 = eig.getRealEigenvalues()[0];
    LOG.report(this, "\n1s2s limit=" + tot
      + "\n     e[0] =" + e0);
    assertEquals(0, Math.abs(e0 + 2.8781158112769547), 5e-10);
    assertEquals(0, Math.abs(e0 - tot), 2e-3);

    // BUNGE
//      double exact_1S  = -2.903724; // from p.164 of Froese-Fischer
//      double exact_1S  = -2.903724375; // from Pekeris PhysRev 115, p1216-1221
    tot = -2.879028732; // exact_s_limit_1S from p.2617 of Bunge, PhysRevA 56 (1997) p2614
    double mchf_1s2s = -2.144188; // from p.170
    N = 13;
    arr = new LagrrLogCR(xToR, N, alpha, lambda);
    res = wCR.calcMaxOrthonErr(arr);
    assertEquals(0, res, 1e-10);
    basis = ConfigArrFactory.makeTwoElec(S1, N, L, arr);
    eig = new HMtrx(basis, sys).eig();
    LOG.report(this, "H=" + Vec.toString(eig.getRealEigenvalues()));
    e0 = eig.getRealEigenvalues()[0];
    double e1 = eig.getRealEigenvalues()[1];
    LOG.report(this, "\nexact_s_limit_1S=" + tot
      + "\n           e[0] =" + e0);
    LOG.report(this, "\nmchf_1s2s_1S=" + mchf_1s2s
      + "\n       e[1] =" + e1);
    assertEquals(0, Math.abs(e0 - tot), 2e-3);
    assertEquals(0, Math.abs(e1 - mchf_1s2s), 6e-4);

    // TRIPLET
    ShellLS S3 = new ShellLS(0, Spin.TRIPLET);
    N = 10;
    basis = ConfigArrFactory.makeTwoElec(S3, N, L, arr);
    eig = new HMtrx(basis, sys).eig();
    LOG.report(this, "H=" + Vec.toString(eig.getRealEigenvalues()));
    e0 = eig.getRealEigenvalues()[0];
    double testS3 = -2.17522937824; // from Pekeris PhysRev 115, p1216-1221
    LOG.report(this, "\ntest S3=" + testS3
      + "\n  e[0] =" + e0);
    assertEquals(0, Math.abs(e0 - testS3), 3e-3);
  }
  public void debugGroundWithL() {
    LOG.setTrace(true);
    int NUM_STEPS = 440;
    double FIRST = -4;
    double STEP = 1. / 50.;
    StepGrid x = new StepGrid(FIRST, NUM_STEPS, STEP);
//      double FIRST = -5;
//      int NUM_STEPS = 381;
//      double LAST = 5; // exp(7) = 1096
//      StepGrid x = new StepGrid(FIRST, LAST, NUM_STEPS);
    WFQuadrLogCR w = new WFQuadrLogCR(x);
    TransLogCRToR xToR = w.getLogCRToR();
    int L = 0;
    double Zeff = 2;
    int alpha = 2 * L + 2;
    double lambda = 2. * Zeff;
    int N = 10;
    FuncArr arr = new LagrrLogCR(xToR, N, alpha, lambda);
    double res = w.calcMaxOrthonErr(arr);
    assertEquals(0, res, 7e-13);
    SlaterLCR slater = new SlaterLCR(w);
    ShellLS LS = new ShellLS(0, Spin.SINGLET);
    SysTwoE sys = new SysTwoE(-2., slater);

//      double exact_1S  = -2.903724; // from p.164 of Froese-Fischer
    double tot = -2.903724375; // from Pekeris PhysRev 115, p1216-1221
    ConfigArr basis = ConfigArrFactory.makeTwoElec(LS, N, L, arr);
    L = 1;
    N = 4;
    alpha = 2 * L + 2;
    arr = new LagrrLogCR(xToR, N, alpha, lambda);
    res = w.calcMaxOrthonErr(arr);
    assertEquals(0, res, 7e-13);
    basis = ConfigArrFactory.makeTwoElecFrom(basis, LS, N, L, arr);
    L = 2;
    basis = ConfigArrFactory.makeTwoElecFrom(basis, LS, N, L, arr);
//      L = 3;
//      basis = ConfigArrFactory.makeTwoElecFrom(basis, ShellLS, N, L, arr);
    HMtrx H = new HMtrx(basis, sys);
    EigenvalueDecomposition eig = H.eig();
    LOG.report(this, "H=" + Vec.toString(eig.getRealEigenvalues()));
    double e0 = eig.getRealEigenvalues()[0];
    LOG.report(this, "\ntot ="
      + tot + "\ne[0]=" + e0);
//      H.calcMaxNormErrorByColumn(eig);
//      H.calcMaxNormErrorByRow(eig);
//      LOG.report(this, H.toString(eig, 0));
    LOG.report(this, H.toStringSorted(eig, 0));
//      assertEquals(0, Math.abs(e0 - tot), 6e-11);
    FuncVec conf = H.calcDensity(eig, 0);
    res = FastLoop.dot(conf, w);
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(conf), "wf"
      , "He_ground_density_L" + L + ".csv");
    assertEquals(2, res, 1e-10);
  }
}