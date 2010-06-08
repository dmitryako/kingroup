package jm.atom.junit;
import jm.atom.YkLcr;
import jm.atom.coulomb.CoulombWFFactory;
import jm.grid.TransLcrToR;
import jm.grid.WFQuadrLcr;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import stlx.FastLoop;
import stlx.valarrayx.valarray;

import javax.iox.LOG;
import javax.vecmathx.function.FuncVec;
import javax.vecmathx.grid.StepGrid;
import javax.vecmathx.integration.BooleQuadr;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 14/03/2005, Time: 14:31:52
 */
public class YkLcrTest extends TestCase {
  public static Test suite() {
    return new TestSuite(YkLcrTest.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testZ_1() {
    double FIRST = -4;
    int NUM_STEPS = 220;
    double STEP = 1. / 16.;
    StepGrid x = new StepGrid(FIRST, NUM_STEPS, STEP);
    TransLcrToR xToR = new TransLcrToR(x);
    valarray r = xToR;
    BooleQuadr w = new BooleQuadr(x);
    WFQuadrLcr wCR = new WFQuadrLcr(x);
    FuncVec f = CoulombWFFactory.makeP1s(r, 1.);
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(f), "wf", "P1s_test.csv");
    double res = FastLoop.dot(f, f, w, xToR.getCR());
    assertEquals(0, Math.abs(res - 1), 6e-13);
    f.mult(xToR.getDivSqrtCR());
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(f), "wf", "P1s_sqrtCR.csv");
    res = FastLoop.dot(f, f, w, xToR.getCR2());
    assertEquals(0, Math.abs(res - 1), 5.5e-13);
    res = FastLoop.dot(f, f, wCR.getWithCR2());
    assertEquals(0, Math.abs(res - 1), 5.5e-13);
    FuncVec T = CoulombWFFactory.makeZ_1_1s(r); // valid
    FuncVec Z = new YkLcr(xToR, f, f, 1).calcZk();
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(T), "wf", "Z_1_1s_test.csv");
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(Z), "wf", "Z_1_1s.csv");
    assertEquals(0, Math.abs(T.distSLOW(Z)), 6e-9);
  }
  public void testZkLogCR() {
    double FIRST = -4;
//      int NUM_STEPS = 440;
//      double STEP = 1./32.;
    int NUM_STEPS = 220;
    double STEP = 1. / 16.;
    StepGrid x = new StepGrid(FIRST, NUM_STEPS, STEP);
    TransLcrToR xToR = new TransLcrToR(x);
    valarray r = xToR;
    BooleQuadr w = new BooleQuadr(x);
    FuncVec f = CoulombWFFactory.makeP1s(r, 1.);
    double res = FastLoop.dot(f, f, w, xToR.getCR());
    assertEquals(0, Math.abs(res - 1), 6e-13);
    f.mult(xToR.getDivSqrtCR());
    FuncVec T = CoulombWFFactory.makeZ_0_1s(r); // valid
    FuncVec Z = new YkLcr(xToR, f, f, 0).calcZk();
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(r), "wf", "logCR.csv");
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(T), "wf", "Z_0_1s_test.csv");
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(Z), "wf", "Z_0_1s.csv");
    assertEquals(0, Math.abs(T.distSLOW(Z)), 6e-9);
  }
  public void testYkLogCR() {
    double FIRST = -4;
    int NUM_STEPS = 220;
    double STEP = 1. / 16.;
    StepGrid x = new StepGrid(FIRST, NUM_STEPS, STEP);
    TransLcrToR xToR = new TransLcrToR(x);
    valarray r = xToR;
    BooleQuadr w = new BooleQuadr(x);

    // 1s
    FuncVec f = CoulombWFFactory.makeP1s(r, 1.);
    f.mult(xToR.getDivSqrtCR());
    double res = FastLoop.dot(f, f, w, xToR.getCR2());
    assertEquals(0, Math.abs(res - 1), 2e-10);
    FuncVec T = CoulombWFFactory.makeY_0_1s(r); // valid
    FuncVec Y = new YkLcr(xToR, f, f, 0).calcYk();
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(r), "wf", "logCR.csv");
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(T), "wf", "Y_0_1s_test.csv");
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(Y), "wf", "Y_0_1s.csv");
    assertEquals(0, Math.abs(T.distSLOW(Y)), 2e-9);

    // 1s-2s
    FuncVec f2 = CoulombWFFactory.makeP2s(r, 1.);
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(f2), "wf", "P2s_test.csv");
    f2.mult(xToR.getDivSqrtCR());
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(f2), "wf", "P2s_sqrtCR.csv");
    res = FastLoop.dot(f, f2, w, xToR.getCR2());
    assertEquals(0, Math.abs(res), 2e-13);
//      T = CoulombWFFactory.makeY_0_2s(r); // valid
    Y = new YkLcr(xToR, f, f2, 0).calcYk();
//      LOG.saveToFile(valarray.asArray(x), valarray.asArray(T), "wf", "Y_0_2s_test.csv");
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(Y), "wf", "Y_0_1s2s.csv");
//      assertEquals(0, Math.abs(T.distSLOW(Y)), 2e-8);

    // 2s
    f = CoulombWFFactory.makeP2s(r, 1.);
    res = FastLoop.dot(f, f, w, xToR.getCR());
    assertEquals(0, Math.abs(res - 1), 2e-13);
    f.mult(xToR.getDivSqrtCR());
    T = CoulombWFFactory.makeY_0_2s(r); // valid
    Y = new YkLcr(xToR, f, f, 0).calcYk();
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(T), "wf", "Y_0_2s_test.csv");
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(Y), "wf", "Y_0_2s.csv");
    assertEquals(0, Math.abs(T.distSLOW(Y)), 2e-8);

    // 2p
    f = CoulombWFFactory.makeP2p(r, 1.);
    res = FastLoop.dot(f, f, w, xToR.getCR());
    assertEquals(0, Math.abs(res - 1), 7e-14);
    f.mult(xToR.getDivSqrtCR());
    T = CoulombWFFactory.makeY_0_2p(r); // valid
    Y = new YkLcr(xToR, f, f, 0).calcYk();
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(T), "wf", "Y_0_2p_test.csv");
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(Y), "wf", "Y_0_2p.csv");
    assertEquals(0, Math.abs(T.distSLOW(Y)), 6e-9);
  }
  public void testY_2() {
    double FIRST = -4;
    int NUM_STEPS = 220;
    double STEP = 1. / 16.;
    StepGrid x = new StepGrid(FIRST, NUM_STEPS, STEP);
    TransLcrToR xToR = new TransLcrToR(x);
    valarray r = xToR;
    BooleQuadr w = new BooleQuadr(x);

    // 2p
    FuncVec f = CoulombWFFactory.makeP2p(r, 1.);
    double res = FastLoop.dot(f, f, w, xToR.getCR());
    assertEquals(0, Math.abs(res - 1), 7e-14);
    f.mult(xToR.getDivSqrtCR());
    FuncVec T = CoulombWFFactory.makeY_2_2p(r); // valid
    FuncVec Y = new YkLcr(xToR, f, f, 2).calcYk();
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(T), "wf", "Y_2_2p_test.csv");
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(Y), "wf", "Y_2_2p.csv");
    assertEquals(0, Math.abs(T.distSLOW(Y)), 5e-8);
  }
}