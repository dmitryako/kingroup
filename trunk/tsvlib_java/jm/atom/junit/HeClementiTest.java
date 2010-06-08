package jm.atom.junit;
import Jama.EigenvalueDecomposition;
import jm.JMatrix;
import jm.angular.Spin;
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
import javax.vecmathx.grid.StepGrid;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 29/03/2005, Time: 17:15:59
 */
public class HeClementiTest extends TestCase {
  public static Test suite() {
    return new TestSuite(HeClementiTest.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testClementiSingleZeta() {
    LOG.setTrace(true);
    double FIRST = -5;
    int NUM_STEPS = 261;
    double LAST = 7; // exp(7) = 1096
    StepGrid x = new StepGrid(FIRST, LAST, NUM_STEPS);
    WFQuadrLcr wCR = new WFQuadrLcr(x);
    TransLcrToR xToR = wCR.getLogCRToR();
    valarray r = xToR;

    // from p445 of Clementi Roetti, Atomic Data 14, 177 (1974)
    double Zeff = 1.6875;// from p445 of Clementi Roetti, Atomic Data 14, 177 (1974)
    FuncVec f = CoulombWFFactory.makeP1s(r, Zeff);
    f.setX(xToR.x); // MUST change grid for derivatives
    f.mult(xToR.getDivSqrtCR());
    double res = FastLoop.dot(f, f, wCR.getWithCR2());
    assertEquals(0, Math.abs(res - 1), 3e-14);
    ShellLS LS = new ShellLS(0, Spin.SINGLET);
    Shell sh = new Shell(1, f, 2, LS.L, LS);
    TwoEShells fc = new TwoEShells(sh);
    SlaterLcr slater = new SlaterLcr(wCR);
    SysTwoE sys = new SysTwoE(-2., slater);
    double kin = sys.calcKin(fc, fc);
//      job.addLine(CTestableDH(new CTestableD(He.calc_kin(c_1s2_He, c_1s2_He)))
//         , 2.8476562, "Kin.E. He_1s2", 8e-6);
    assertEquals(0, Math.abs(2.84765625 - kin), 2e-11);

//      job.addLine(CTestableDH(new CTestableD(He.calc_pot(c_1s2_He, c_1s2_He)))
//         , -5.6953125, "Pot.E. He_1s2", 7e-11);
    double pot = sys.calcPot(fc, fc);
    assertEquals(0, Math.abs(-5.6953125 - pot), 2e-11);
    assertEquals(0, Math.abs(-2. - pot / kin), 8e-12);

//      job.addLine(CTestableDH(new CTestableD(He.calc(c_1s2_He, c_1s2_He)))
//         , -2.8476562, "He_1s2", 8e-6);
    res = sys.calcTot(fc, fc);
    assertEquals(0, Math.abs(-2.84765625 - res), 4e-12);
  }
  public void testClementiLimitOneConfig() {
    LOG.setTrace(true);
    double FIRST = -5;
    int NUM_STEPS = 381;
    double LAST = 7; // exp(7) = 1096
    StepGrid x = new StepGrid(FIRST, LAST, NUM_STEPS);
    WFQuadrLcr w = new WFQuadrLcr(x);
    TransLcrToR xToR = w.getLogCRToR();
    int L = 0;
    double Zeff = 1.6875;// from p445 of Clementi Roetti, Atomic Data 14, 177 (1974)
    int alpha = 2 * L + 2;
    double lambda = 2. * Zeff;
    int N = 12;
    FuncArr arr = new JmLagrrOrthLcr(xToR, N, alpha, lambda);
    JMatrix.trimTailSLOW(arr);
    double res = w.calcMaxOrthonErr(arr);
    assertEquals(0, res, 2e-11);
    SlaterLcr slater = new SlaterLcr(w);
    ShellLS LS = new ShellLS(0, Spin.SINGLET);
    SysTwoE sys = new SysTwoE(-2., slater);

    // One config hartree-fock limit
    double kin = 2.8617128;// from Clementi, p185
    double pot = -5.7233927;// from Clementi, p185
    double tot = -2.8616799;
    assertEquals(kin + pot, tot, 6e-22);
    ConfigArr basis = ConfigArrFactory.makeTwoElecSameN(LS, N, arr);
    HMtrx H = new HMtrx(basis, sys);
    EigenvalueDecomposition eig = H.eig();
    LOG.report(this, "H=" + Vec.toString(eig.getRealEigenvalues()));
    double e0 = eig.getRealEigenvalues()[0];
    LOG.report(this, "\nkin+pot="
      + (kin + pot) + "\n   e[0]=" + e0);
    assertEquals(0, Math.abs(-2.8615628084911 - e0), 1e-13); //
    assertEquals(0, e0 - tot, 2e-4);
    FuncVec conf = H.calcDensity(eig, 0);
//      LOG.saveToFile(valarray.asArray(x), valarray.asArray(conf), "wf", "He_ground_density.csv");
    res = FastLoop.dot(conf, w);
    assertEquals(2, res, 3e-15);
  }
}
