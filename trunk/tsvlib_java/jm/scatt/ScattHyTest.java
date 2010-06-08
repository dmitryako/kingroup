package jm.scatt;
import Jama.EigenvalueDecomposition;
import jm.angular.Spin;
import jm.atom.HMtrx;
import jm.atom.SlaterLcr;
import jm.atom.SysTwoE;
import jm.bspline.BSplineFactory;
import jm.bspline.BSplineHeJUnit;
import jm.bspline.junit.BSplineLogCRTest;
import jm.grid.WFQuadrLcr;
import jm.shell.ConfigArr;
import jm.shell.ConfigArrFactory;
import jm.shell.ShellLS;
import junit.framework.Test;
import junit.framework.TestSuite;

import javax.iox.LOG;
import javax.utilx.arrays.vec.Vec;
import javax.vecmathx.function.FuncArr;
import javax.vecmathx.grid.StepGrid;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 19/04/2005, Time: 14:43:57
 */
public class ScattHyTest extends BSplineLogCRTest {
  public static Test suite() {
    return new TestSuite(BSplineHeJUnit.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testLimitNS() {
    LOG.setTrace(true);
    double FIRST = -2;
    int NUM_STEPS = 441;
    double LAST = 2; //
    StepGrid x = new StepGrid(FIRST, LAST, NUM_STEPS);
    WFQuadrLcr w = new WFQuadrLcr(x);
    int k = 5;
    int N = 20;
    FuncArr arr = BSplineFactory.makeBSplineLogCR(w, N, k);
    double res = w.calcMaxOrthonErr(arr);
    assertEquals(0, res, NORM_ERROR);
    SlaterLcr slater = new SlaterLcr(w);
    ShellLS S1 = new ShellLS(0, Spin.SINGLET);
    SysTwoE sys = new SysTwoE(-2., slater);

    // Multi-Config Hartree-Fock with 1s2+...+4s2
    double tot = -2.878990; // from p.164 of Froese-Fischer
    int L = 0;
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
