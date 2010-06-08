package jm.papers.jm2005_shifted;
import Jama.EigenvalueDecomposition;
import jm.angular.Spin;
import jm.atom.Energy;
import jm.atom.HMtrx;
import jm.atom.SlaterLcr;
import jm.atom.SysTwoE;
import jm.atom.coulomb.CoulombWFFactory;
import jm.grid.TransLcrToR;
import jm.grid.WFQuadrLcr;
import jm.laguerre.JmLagrrOrthLcr;
import jm.shell.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import stlx.FastLoop;
import stlx.valarrayx.valarray;

import javax.iox.LOG;
import javax.utilx.arrays.vec.Vec;
import javax.vecmathx.function.FuncVec;
import javax.vecmathx.function.FuncArr;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 11/04/2005, Time: 12:51:56
 */
public class ShiftedLogDensity extends LogCRTestCase {
  public static Test suite() {
    return new TestSuite(ShiftedLogDensity.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testHeZeta() {
    LOG.setTrace(true);
    double Z = 2;
//      StepGrid x = new StepGrid(FIRST - Math.log(Z), NUM_STEPS, STEP);
    WFQuadrLcr w = new WFQuadrLcr(x);
    TransLcrToR xToR = w.getLogCRToR();
    valarray r = xToR;

    // from p445 of Clementi Roetti, Atomic Data 14, 177 (1974)
    double Zeff = 1.6875;// from p445 of Clementi Roetti, Atomic Data 14, 177 (1974)
    FuncVec f = CoulombWFFactory.makeP1s(r, Zeff);
    f.setX(xToR.x); // MUST change grid for derivatives
    f.mult(xToR.getDivSqrtCR());
    double res = FastLoop.dot(f, f, w.getWithCR2());
//      LOG.saveToFile(valarray.asArray(x), valarray.asArray(f), "wf", "He_zeta.csv");
    assertEquals(0, Math.abs(res - 1), 3e-14);
    FuncVec zeta = new FuncVec(f);
    zeta.mult(f);
    zeta.mult(xToR.getCR2());
    zeta.scale(2);
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(zeta), "wf", "He_zeta.csv");
    ShellLS LS = new ShellLS(0, Spin.SINGLET);
    Shell sh = new Shell(1, f, 2, LS.L, LS);
    TwoEShells fc = new TwoEShells(sh);
    SlaterLcr slater = new SlaterLcr(w);
    SysTwoE sys = new SysTwoE(-Z, slater);
    res = sys.calcTot(fc, fc);
    assertEquals(0, Math.abs(-2.84765625 - res), 4e-12);
    FuncVec conf = sys.calcConfigDensity(fc, fc);
    res = FastLoop.dot(conf, w);
    assertEquals(2, res, 3e-14);
//      conf.scale(xToR.getDivR());
//      conf.scale(xToR.getDivR());
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(conf), "wf", "He_zeta_density.csv");
  }
  public void testEnergy() {
    double Z = 2;
    WFQuadrLcr w = new WFQuadrLcr(x);
    TransLcrToR xToR = w.getLogCRToR();
    int sN = 10;
    int sAlpha = 2;
//      double sLambda = 2 * 1.6875;
    double sLambda = 4;
    int L = 0;
    ShellLS LS = new ShellLS(0, Spin.SINGLET);
    FuncArr arr = new JmLagrrOrthLcr(xToR, sN, sAlpha, sLambda);
    double res = w.calcMaxOrthonErr(arr);
    TestCase.assertEquals(0, res, NORM_ERROR);
    SlaterLcr slater = new SlaterLcr(w);
    SysTwoE sys = new SysTwoE(-Z, slater);
    ConfigArr currBasis = ConfigArrFactory.makeTwoElecFrom(null, LS, sN, L, arr);
    int totN = currBasis.size();
    LOG.report(this, "totN=" + totN);
    HMtrx H = new HMtrx(currBasis, sys);
    EigenvalueDecomposition eig = H.eig();
    LOG.report(this, "H=" + Vec.toString(eig.getRealEigenvalues()));
    double E = eig.getRealEigenvalues()[0];
    LOG.report(this, "L=" + L + ", N=" + sN + ", E[0]=" + E);
    Energy configE = H.calcEnergy(eig, 0);
    LOG.report(this, "config E=" + (configE.kin + configE.pot));
    TestCase.assertEquals(E, configE.kin + configE.pot, 1e-10);
    FuncVec conf = H.calcDensity(eig, 0);
    res = FastLoop.dot(conf, w);
//      conf.scale(xToR.getDivR());
//      conf.scale(xToR.getDivR());
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(conf), "wf"
      , "He_ground_density_" + sN + ".csv");
    assertEquals(2, res, 1e-10);
  }
}