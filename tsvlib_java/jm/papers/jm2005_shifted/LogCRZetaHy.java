package jm.papers.jm2005_shifted;
import jm.angular.Spin;
import jm.atom.SlaterLCR;
import jm.atom.SlaterLR;
import jm.atom.SlaterR;
import jm.atom.SysOneE;
import jm.atom.coulomb.CoulombWFFactory;
import jm.grid.*;
import jm.shell.Shell;
import jm.shell.ShellConfig;
import jm.shell.ShellLS;
import junit.framework.Test;
import junit.framework.TestSuite;
import stlx.FastLoop;
import stlx.valarrayx.valarray;

import javax.vecmathx.function.FuncVec;
import javax.vecmathx.grid.StepGrid;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 3/04/2005, Time: 11:21:38
 */
public class LogCRZetaHy extends LogCRTestCase {
  public static Test suite() {
    return new TestSuite(LogCRZetaHy.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void test_Hy_1S_LogCR() {
    WFQuadrLogCR w = new WFQuadrLogCR(x);
    TransLogCRToR xToR = w.getLogCRToR();
    valarray r = xToR;

    // WF
    double Zeff = 1;
    int L = 0;
    FuncVec f = CoulombWFFactory.makeP1s(r, Zeff);
    f.setX(xToR.x); // MUST change grid for derivatives
    f.mult(xToR.getDivSqrtCR());
    double res = FastLoop.dot(f, f, w.getWithCR2());
    assertAndView("Hy norm(LogCR)=", res - 1, 6e-13);
    double test0 = -0.5;
    double testKin = -test0;
    double testPot = 2 * test0;
    ShellLS LS = new ShellLS(L, Spin.ELECTRON);
    Shell sh = new Shell(1, f, 1, LS.L, LS);
    ShellConfig fc = new ShellConfig(sh);
    SlaterLCR slater = new SlaterLCR(w);
    SysOneE sys = new SysOneE(-1., slater);
    double kin = sys.calcKin(fc, fc);
    assertAndView("Hy kin(LogCR)=", testKin - kin, 4e-11);
    double pot = sys.calcPot(fc, fc);
    assertAndView("Hy pot(LogCR)=", testPot - pot, 5e-12);
    assertAndView("Hy kin/pot(LogCR)=", -2. - pot / kin, 2e-10);
    res = sys.calcTot(fc, fc);
    assertAndView("Hy tot(LogCR)=", test0 - res, 3e-11);
  }
  public void test_Hy_1S_LogR() {
    WFQuadrLogR w = new WFQuadrLogR(x);
    TransLogRToR xToR = w.getLogRToR();
    valarray r = xToR;

    // WF
    double Z = 1;
    int L = 0;
    FuncVec f = CoulombWFFactory.makeP1s(r, Z);
    f.setX(xToR.x); // MUST change grid for derivatives
    f.mult(xToR.getDivSqrtR());
    double res = FastLoop.dot(f, f, w.getWithR2());
    assertAndView("Hy norm(LogR)=", res - 1, 8e-6);
    double test0 = -0.5;
    double testKin = -test0;
    double testPot = 2 * test0;
    ShellLS LS = new ShellLS(L, Spin.ELECTRON);
    Shell sh = new Shell(1, f, 1, LS.L, LS);
    ShellConfig fc = new ShellConfig(sh);
    SlaterLR slater = new SlaterLR(w);
    SysOneE sys = new SysOneE(-Z, slater);
    double kin = sys.calcKin(fc, fc);
    assertAndView("Hy kin(LogR)=", testKin - kin, 7e-4);
    double pot = sys.calcPot(fc, fc);
    assertAndView("Hy pot(LogR)=", testPot - pot, 7e-4);
    assertAndView("Hy pot/kin(LogR)=", -2. - pot / kin, 2e-3);
    res = sys.calcTot(fc, fc);
    assertAndView("Hy tot(LogR)=", test0 - res, 4e-6);

    // With small R correction
    StepGrid smallR = new StepGrid(0, r.get(0), 9);
    f = CoulombWFFactory.makeP1s(smallR, Z);
    sh = new Shell(1, f, 1, LS.L, LS);
    fc = new ShellConfig(sh);
    WFQuadrR wR = new WFQuadrR(smallR);
    SlaterR slaterR = new SlaterR(wR);
    sys = new SysOneE(-Z, slaterR);
    kin += sys.calcKin(fc, fc);
    assertAndView("Hy kin(LogR+smallR)=", testKin - kin, 4e-11);
    pot += sys.calcPot(fc, fc);
    assertAndView("Hy pot(LogR+smallR)=", testPot - pot, 5e-12);
    assertAndView("Hy pot/kin(LogR+smallR)=", -2. - pot / kin, 2e-10);
    res += sys.calcTot(fc, fc);
    assertAndView("Hy tot(LogR+smallR)=", test0 - res, 4e-11);
  }
}
