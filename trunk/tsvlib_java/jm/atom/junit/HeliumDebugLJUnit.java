package jm.atom.junit;
import Jama.EigenvalueDecomposition;
import jm.angular.Spin;
import jm.atom.HMtrx;
import jm.atom.SlaterLcr;
import jm.atom.SysTwoE;
import jm.grid.TransLcrToR;
import jm.grid.WFQuadrLcr;
import jm.laguerre.JmLagrrOrthLcr;
import jm.shell.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.iox.LOG;
import javax.utilx.arrays.vec.Vec;
import javax.vecmathx.function.FuncArr;
import javax.vecmathx.grid.StepGrid;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 29/03/2005, Time: 17:38:17
 */
public class HeliumDebugLJUnit extends TestCase {
  private static final double ENERGY_ERROR = 1e-5;
  private static final double NORM_ERROR = 1e-10;
  public static Test suite() {
    return new TestSuite(HeliumDebugLJUnit.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testGround_1s2_2p2_1S() {
    LOG.setTrace(true);
// from c++ v5
//      Configs hPEX (1s)^2:1S (2p)^2:1S
//      Configs hPE (1s)^2:1S (2p)^2:1S
    HMtrx H = testGround_Ns_Np_1S(1, 1);
    EigenvalueDecomposition eig = H.eig();
    LOG.report(this, "H=" + Vec.toString(eig.getRealEigenvalues()));
    double e0 = eig.getRealEigenvalues()[0];
    double e1 = eig.getRealEigenvalues()[1];
    assertEquals(-2.75, H.get(0, 0), ENERGY_ERROR);
    assertEquals(-0.252591, H.get(1, 0), ENERGY_ERROR);
    assertEquals(0.867187, H.get(1, 1), ENERGY_ERROR);
    assertEquals(-2.76755, e0, ENERGY_ERROR);
    assertEquals(0.884741, e1, ENERGY_ERROR);

// from c++ v5
// Configs hPEX (1s)^2:1S (2p)^2:1S 2p3p (3p)^2:1S
// Configs hPE (1s)^2:1S (2p)^2:1S 2p3p (3p)^2:1S
// CHamiltHeliumPEX h_pex(hPEX)
// -2.77334 -0.178905 2.32763 5.06148
//
// -2.75  -0.252591       -0.239629       -0.16238
//-0.252591       0.867187        1.53914 0.159375
//-0.239629       1.53914 2.46719 1.48874
//-0.16238        0.159375        1.48874 3.8525
    H = testGround_Ns_Np_1S(1, 2);
    eig = H.eig();
    LOG.report(this, "H=\n" + H);
    LOG.report(this, "H=" + Vec.toString(eig.getRealEigenvalues()));
    e0 = eig.getRealEigenvalues()[0];
    e1 = eig.getRealEigenvalues()[1];
    assertEquals(-2.75, H.get(0, 0), ENERGY_ERROR);
    assertEquals(-0.252591, H.get(1, 0), ENERGY_ERROR);
    assertEquals(-0.239629, H.get(2, 0), ENERGY_ERROR);
    assertEquals(0.867187, H.get(1, 1), ENERGY_ERROR);
    assertEquals(1.53914, H.get(2, 1), ENERGY_ERROR); // <(0p)^2 1S | H | 0p1p 1S>
    assertEquals(2.46719, H.get(2, 2), ENERGY_ERROR);
    assertEquals(-2.77334, e0, ENERGY_ERROR);
    assertEquals(-0.178905, e1, ENERGY_ERROR);
  }
  public HMtrx testGround_Ns_Np_1S(int NS, int NP) {
    ShellLS LS = new ShellLS(0, Spin.SINGLET);
    double FIRST = -5;
    int NUM_STEPS = 261;
    double LAST = 7; // exp(7) = 1096
    StepGrid x = new StepGrid(FIRST, LAST, NUM_STEPS);
    WFQuadrLcr wCR = new WFQuadrLcr(x);
    TransLcrToR xToR = wCR.getLogCRToR();
    int L = 0;
    double Zeff = 2;
    int alpha = 2 * L + 2;
    double lambda = 2. * Zeff;
    FuncArr arr = new JmLagrrOrthLcr(xToR, NS, alpha, lambda);
    ConfigArr basis = ConfigArrFactory.makeTwoElec(LS, NS, L, arr);
    double res = wCR.calcMaxOrthonErr(arr);
    assertEquals(0, res, NORM_ERROR);
    SlaterLcr slater = new SlaterLcr(wCR);
    SysTwoE sys = new SysTwoE(-2., slater);
    L = 1;
    alpha = 2 * L + 2;
    arr = new JmLagrrOrthLcr(xToR, NP, alpha, lambda);
    basis = ConfigArrFactory.makeTwoElecFrom(basis, LS, NP, L, arr);
    res = wCR.calcMaxOrthonErr(arr);
    assertEquals(0, res, 7e-13);
    return new HMtrx(basis, sys);
  }
  public void test_2p3p_1S() {
    ShellLS LS = new ShellLS(0, Spin.SINGLET);
    double FIRST = -5;
    int NUM_STEPS = 261;
    double LAST = 7; // exp(7) = 1096
    StepGrid x = new StepGrid(FIRST, LAST, NUM_STEPS);
    WFQuadrLcr wCR = new WFQuadrLcr(x);
    TransLcrToR xToR = wCR.getLogCRToR();
    int L = 1;
    int NP = 3;
    double Zeff = 2;
    int alpha = 2 * L + 2;
    double lambda = 2. * Zeff;
    FuncArr arr = new JmLagrrOrthLcr(xToR, NP, alpha, lambda);
    double res = wCR.calcMaxOrthonErr(arr);
    assertEquals(0, res, NORM_ERROR);
    Shell sh = new Shell(0, arr.get(0), 2, L, LS);
    ShellConfig fc_2p2 = new TwoEShells(sh);
    sh = new Shell(0, arr.get(0), L);
    ShellConfig fc_2p3p = new TwoEShells(sh);
    sh = new Shell(1, arr.get(1), L);
    fc_2p3p.addShell(sh, LS);
    SlaterLcr slater = new SlaterLcr(wCR);
    SysTwoE sys = new SysTwoE(-2., slater);
    res = sys.calcTot(fc_2p2, fc_2p3p);
    assertEquals(1.53914, res, ENERGY_ERROR);
  }
}
