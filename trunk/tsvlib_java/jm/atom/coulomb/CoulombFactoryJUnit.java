package jm.atom.coulomb;
import jm.grid.TransLcrToR;
import jm.grid.TransLogRToR;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import stlx.FastLoop;
import stlx.valarrayx.valarray;

import javax.vecmathx.function.FuncVec;
import javax.vecmathx.grid.StepGrid;
import javax.vecmathx.integration.BooleQuadr;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 14/03/2005, Time: 16:35:26
 */
public class CoulombFactoryJUnit extends TestCase {
  public static Test suite() {
    return new TestSuite(CoulombFactoryJUnit.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testMakeP1s() {
    int NUM_STEPS = 220;
    double FIRST = -4;
    double STEP = 1. / 16.;
    StepGrid x = new StepGrid(FIRST, NUM_STEPS, STEP);
    TransLcrToR logCR = new TransLcrToR(x);
    TransLogRToR logR = new TransLogRToR(x);
    valarray r = logR;
    BooleQuadr w = new BooleQuadr(x);
    FuncVec f = CoulombWFFactory.makeP1s(r, 1.);  // by log(r)
    double res = FastLoop.dot(f, f, w, r);
    assertEquals(0, Math.abs(res - 1), 1e-5);
    f = CoulombWFFactory.makeP1s(logCR, 1.); // by log(c+r)
//      LOG.saveToFile(valarray.asArray(x), valarray.asArray(f), "wf", "coulP1s.csv");
//      LOG.saveToFile(valarray.asArray(x), valarray.asArray(logCR), "wf", "logCR.csv");
//      LOG.saveToFile(valarray.asArray(x), valarray.asArray(logCR.getCR()), "wf", "y.csv");
    res = FastLoop.dot(f, f, w, logCR.getCR());
    assertEquals(0, Math.abs(res - 1), 6e-13);
    f = CoulombWFFactory.makeP1s(logCR, 2.);
    res = FastLoop.dot(f, f, w, logCR.getCR());
    assertEquals(0, Math.abs(res - 1), 4e-12);
    f = CoulombWFFactory.makeP1s(logR, 2.);
    res = FastLoop.dot(f, f, w, r);
    assertEquals(0, Math.abs(res - 1), 1e-4);
  }
  public void testMakeP2sp() {
    int NUM_STEPS = 220;
    double FIRST = -4;
    double STEP = 1. / 16.;
    StepGrid x = new StepGrid(FIRST, NUM_STEPS, STEP);
    TransLcrToR logCR = new TransLcrToR(x);
    valarray r = logCR;
    BooleQuadr w = new BooleQuadr(x);
    FuncVec f = CoulombWFFactory.makeP2s(r, 1.); // by log(c+r)
    double res = FastLoop.dot(f, f, w, logCR.getCR());
    assertEquals(0, Math.abs(res - 1), 2e-13);
    FuncVec f2 = CoulombWFFactory.makeP1s(r, 1.);
    res = FastLoop.dot(f, f2, w, logCR.getCR());
    assertEquals(0, Math.abs(res), 2e-13);
    f = CoulombWFFactory.makeP2s(r, 2.);
    res = FastLoop.dot(f, f, w, logCR.getCR());
    assertEquals(0, Math.abs(res - 1), 5e-13);
    f2 = CoulombWFFactory.makeP1s(r, 2.);
    res = FastLoop.dot(f, f2, w, logCR.getCR());
    assertEquals(0, Math.abs(res), 2e-12);
    f = CoulombWFFactory.makeP2p(r, 1.);      // 2p
    res = FastLoop.dot(f, f, w, logCR.getCR());
    assertEquals(0, Math.abs(res - 1), 7e-14);
    f = CoulombWFFactory.makeP2p(r, 2.);    // 2p
    res = FastLoop.dot(f, f, w, logCR.getCR());
    assertEquals(0, Math.abs(res - 1), 3e-14);
  }
  public void testMakeP3spd() {
    int NUM_STEPS = 220;
    double FIRST = -4;
    double STEP = 1. / 16.;
    StepGrid x = new StepGrid(FIRST, NUM_STEPS, STEP);
    TransLcrToR logCR = new TransLcrToR(x);
    valarray r = logCR;
    BooleQuadr w = new BooleQuadr(x);
    FuncVec f = CoulombWFFactory.makeP3s(r, 1.); // by log(c+r)
    double res = FastLoop.dot(f, f, w, logCR.getCR());
    assertEquals(0, Math.abs(res - 1), 2e-11);
    FuncVec f2 = CoulombWFFactory.makeP1s(r, 1.);
    res = FastLoop.dot(f, f2, w, logCR.getCR());
    assertEquals(0, Math.abs(res), 1e-13);
    f2 = CoulombWFFactory.makeP2s(r, 1.);
    res = FastLoop.dot(f, f2, w, logCR.getCR());
    assertEquals(0, Math.abs(res), 2e-12);
    f = CoulombWFFactory.makeP3s(r, 2.);
    res = FastLoop.dot(f, f, w, logCR.getCR());
    assertEquals(0, Math.abs(res - 1), 6e-12);
    f = CoulombWFFactory.makeP3p(r, 2.);
    res = FastLoop.dot(f, f, w, logCR.getCR());
    assertEquals(0, Math.abs(res - 1), 2e-12);
    f2 = CoulombWFFactory.makeP2p(r, 2.);
    res = FastLoop.dot(f, f2, w, logCR.getCR());
    assertEquals(0, Math.abs(res), 3e-13);
    f = CoulombWFFactory.makeP3d(r, 2.);
    res = FastLoop.dot(f, f, w, logCR.getCR());
    assertEquals(0, Math.abs(res - 1), 1e-13);
  }
}
