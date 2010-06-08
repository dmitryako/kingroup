package jm.papers.jm2005_shifted;
import jm.JMatrix;
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

import javax.iox.LOG;
import javax.utilx.arrays.vec.Vec;
import javax.vecmathx.function.FuncVec;
import javax.vecmathx.grid.StepGrid;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 4/04/2005, Time: 12:24:05
 */
public class ShiftedLogZetaTwoElec extends LogCRTestCase {
  final private int NUM_TRIALS = 10;
  public static Test suite() {
    return new TestSuite(ShiftedLogZetaHe.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testFindBestZeta() {
    calcTotEnergyLogCR(1, 0.687495, 1e-6);
    calcTotEnergyLogCR(2, 1.687495, 1e-6);
    calcTotEnergyLogCR(3, 2.687495, 1e-6);
    calcTotEnergyLogCR(4, 3.687495, 1e-6);
  }
  public void testCalcEnergies() {
    calcEnergiesLogCR(1, 0.6875, -0.47265625);
    calcEnergiesLogCR(2, 1.6875, -2.84765625);
    calcEnergiesLogCR(3, 2.6875, -7.22265625);
    calcEnergiesLogCR(4, 3.6875, -13.59765625);
  }
  public void calcTotEnergyLogCR(double Z, double minZeta, double stepZeta) {
    double e[] = new double[NUM_TRIALS];
    for (int i = 0; i < NUM_TRIALS; i++) {
      double zeta = minZeta + i * stepZeta;
      e[i] = calcTotEnergyLogCR(Z, zeta);
      LOG.report(this, "Z=" + Z + ", zeta=" + zeta + ", TE=" + e[i]);
    }
    int[] sorted = Vec.sortIdx(e);
    int idx = sorted[0];
    double zeta = minZeta + idx * stepZeta;
    LOG.report(this, "Z=" + Z + ", zeta=" + zeta + ", TE=" + e[idx]);
  }
  public double calcTotEnergyLogCR(double Z, double zeta) {
    StepGrid x = new StepGrid(FIRST - Math.log(Z), NUM_STEPS, STEP);
    WFQuadrLcr w = new WFQuadrLcr(x);
    TransLcrToR xToR = w.getLogCRToR();
    valarray r = xToR;

    // WF
    int L = 0;
    FuncVec f = CoulombWFFactory.makeP1s(r, zeta);
    f.setX(xToR.x); // MUST change grid for derivatives
    f.mult(xToR.getDivSqrtCR());
    JMatrix.trimTailSLOW(f);
    double res = FastLoop.dot(f, f, w.getWithCR2());
    assertAndView("He norm(ZETA, LogCR)=", res - 1, 3e-12);
    ShellLS LS = new ShellLS(L, Spin.SINGLET);
    Shell sh = new Shell(1, f, 2, LS.L, LS);
    TwoEShells fc = new TwoEShells(sh);
    SlaterLcr slater = new SlaterLcr(w);
    SysTwoE sys = new SysTwoE(-Z, slater);
    return sys.calcTot(fc, fc);
  }
  public void calcEnergiesLogCR(double Z, double zeta, double testTot) {
    double testPot = 2 * testTot;
    double testKin = -testTot;
    StepGrid x = new StepGrid(FIRST - Math.log(Z), NUM_STEPS, STEP);
    WFQuadrLcr w = new WFQuadrLcr(x);
    TransLcrToR xToR = w.getLogCRToR();
    valarray r = xToR;

    // WF
    int L = 0;
    FuncVec f = CoulombWFFactory.makeP1s(r, zeta);
    f.setX(xToR.x); // MUST change grid for derivatives
    f.mult(xToR.getDivSqrtCR());
    double norm = FastLoop.dot(f, f, w.getWithCR2());
    ShellLS LS = new ShellLS(L, Spin.SINGLET);
    Shell sh = new Shell(1, f, 2, LS.L, LS);
    TwoEShells fc = new TwoEShells(sh);
    SlaterLcr slater = new SlaterLcr(w);
    SysTwoE sys = new SysTwoE(-Z, slater);
    double tot = sys.calcTot(fc, fc);
    double kin = sys.calcKin(fc, fc);
    double pot = sys.calcPot(fc, fc);
    LOG.report(this, "Z=" + Z + ", zeta=" + zeta);
    LOG.report(this, "exact TE=" + testTot);
    LOG.report(this, "exact PE=" + testPot);
    LOG.report(this, "exact KE=" + testKin);
    LOG.report(this, "error tot=" + Math.abs(testTot - tot));
    LOG.report(this, "error pot=" + Math.abs(testPot - pot));
    LOG.report(this, "error kin=" + Math.abs(testKin - kin));
    LOG.report(this, "error pot/kin=" + Math.abs(-2 - pot / kin));
    LOG.report(this, "error norm=" + Math.abs(1 - norm));
  }
}
