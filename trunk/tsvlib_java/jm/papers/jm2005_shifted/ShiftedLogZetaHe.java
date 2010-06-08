package jm.papers.jm2005_shifted;
import jm.angular.Spin;
import jm.atom.SlaterLcr;
import jm.atom.SysTwoE;
import jm.atom.coulomb.CoulombWFFactory;
import jm.grid.TransLcrToR;
import jm.grid.WFQuadrLcr;
import jm.shell.Shell;
import jm.shell.ShellLS;
import jm.shell.TwoEShells;
import junit.framework.Test;
import junit.framework.TestSuite;
import stlx.FastLoop;
import stlx.valarrayx.valarray;

import javax.vecmathx.function.FuncVec;
import javax.vecmathx.grid.StepGrid;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 4/04/2005, Time: 11:38:02
 */
public class ShiftedLogZetaHe extends LogCRTestCase {
  public static Test suite() {
    return new TestSuite(ShiftedLogZetaHe.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void test_He_1S_LogCR() {
    double Z = 2;
    StepGrid x = new StepGrid(FIRST - Math.log(Z), NUM_STEPS, STEP);
    WFQuadrLcr w = new WFQuadrLcr(x);
    TransLcrToR xToR = w.getLogCRToR();
    valarray r = xToR;

    // WF
    double ZETA = 1.6875;// from p445 of Clementi Roetti, Atomic Data 14, 177 (1974)
    int L = 0;
    FuncVec f = CoulombWFFactory.makeP1s(r, ZETA);
    f.setX(xToR.x); // MUST change grid for derivatives
    f.mult(xToR.getDivSqrtCR());
    double res = FastLoop.dot(f, f, w.getWithCR2());
    assertAndView("He norm(ZETA, LogCR)=", res - 1, 3e-12);
    double test0 = -2.84765625;
    double testKin = -test0;
    double testPot = 2 * test0;
    ShellLS LS = new ShellLS(L, Spin.SINGLET);
    Shell sh = new Shell(1, f, 2, LS.L, LS);
    TwoEShells fc = new TwoEShells(sh);
    SlaterLcr slater = new SlaterLcr(w);
    SysTwoE sys = new SysTwoE(-Z, slater);
    double kin = sys.calcKin(fc, fc);
    assertAndView("Hy kin(LogCR)=", testKin - kin, 2e-10);
    double pot = sys.calcPot(fc, fc);
    assertAndView("Hy pot(LogCR)=", testPot - pot, 1e-10);
    assertAndView("Hy kin/pot(LogCR)=", -2. - pot / kin, 2e-10);
    res = sys.calcTot(fc, fc);
    assertAndView("Hy tot(LogCR)=", test0 - res, 2e-10);
  }
}