package jm.harmonic;
import Jama.EigenvalueDecomposition;
import jm.angular.Spin;
import jm.atom.HMtrx;
import jm.atom.SlaterLCR;
import jm.atom.SysTwoE;
import jm.grid.TransLogCRToR;
import jm.grid.WFQuadrLogCR;
import jm.shell.ConfigArr;
import jm.shell.ConfigArrFactory;
import jm.shell.ShellLS;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.iox.LOG;
import javax.utilx.arrays.vec.Vec;
import javax.vecmathx.function.FuncArr;
import javax.vecmathx.grid.StepGrid;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/04/2005, Time: 10:39:43
 */
public class HarmonicHeJUnit extends TestCase {
  public static Test suite() {
    return new TestSuite(HarmonicHeJUnit.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testLimitNS() {
    LOG.setTrace(true);
    double FIRST = -2;
    int NUM_STEPS = 400;
    double STEP = 1. / 100;
    StepGrid x = new StepGrid(FIRST, NUM_STEPS, STEP);
    WFQuadrLogCR w = new WFQuadrLogCR(x);
    TransLogCRToR xToR = w.getLogCRToR();
    int L = 0;
    double B = 0.5;
    int N = 20;
    FuncArr arr = new HarmonicLogCR(xToR, N, L, B);
    double res = w.calcMaxOrthonErr(arr);
    assertEquals(0, res, 1e-10);
    SlaterLCR slater = new SlaterLCR(w);
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
  }
}
