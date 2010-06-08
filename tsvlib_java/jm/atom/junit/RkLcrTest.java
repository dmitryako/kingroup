package jm.atom.junit;
import jm.atom.RkLcr;
import jm.atom.YkLcr;
import jm.atom.coulomb.CoulombWFFactory;
import jm.grid.TransLcrToR;
import jm.grid.WFQuadrLcr;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import stlx.FastLoop;
import stlx.valarrayx.valarray;

import javax.vecmathx.function.FuncVec;
import javax.vecmathx.grid.StepGrid;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 17/03/2005, Time: 17:33:27
 */
public class RkLcrTest extends TestCase {
  public static Test suite() {
    return new TestSuite(RkLcrTest.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testCalcRkLogCR() {
    double FIRST = -4;
    int NUM_STEPS = 220;
    double STEP = 1. / 16.;
    StepGrid x = new StepGrid(FIRST, NUM_STEPS, STEP);
    TransLcrToR xToR = new TransLcrToR(x);
    valarray r = xToR;
//      BooleQuadr w = new BooleQuadr(x);
    WFQuadrLcr wCR = new WFQuadrLcr(x);
    FuncVec f = CoulombWFFactory.makeP1s(r, 1.);
    f.mult(xToR.getDivSqrtCR());
    double res = FastLoop.dot(f, f, wCR.getWithCR2());
    assertEquals(0, Math.abs(res - 1), 6e-13);
    FuncVec T = CoulombWFFactory.makeY_0_1s(r); // valid
    FuncVec Y = new YkLcr(wCR.getLogCRToR(), f, f, 0).calcYk();
    assertEquals(0, Math.abs(T.distSLOW(Y)), 2e-9);
    res = RkLcr.calc(wCR, f, f, f, f, 0);
    // from p236 of C.F.Fischer
    //CRkH  F_1s1s(new CRk(w, r, f_1s, f_1s, f_1s, f_1s, CL(0)));
    //job.addLine( F_1s1s,     5./8,     "F_1s1s",     3e-9); // Fischer's 0.5??
    assertEquals(0, Math.abs(res - 5. / 8), 5e-11);
    FuncVec f2s = CoulombWFFactory.makeP2s(r, 1.);
    f2s.mult(xToR.getDivSqrtCR());
    res = FastLoop.dot(f2s, f2s, wCR.getWithCR2());
    assertEquals(0, Math.abs(res - 1), 2e-13);
    res = RkLcr.calc(wCR, f, f2s, f, f2s, 0);
    //CRkH  F_2s1s(new CRk(w, r, f_1s, f_2s, f_1s, f_2s, CL(0)));
    //job.addLine( F_2s1s,   17./81,     "F_1s2s",   0.6e-9); // 0.7
    assertEquals(0, Math.abs(res - 17. / 81), 4e-11);
    res = RkLcr.calc(wCR, f2s, f, f2s, f, 0);
    //CRkH F_2s1s_(new CRk(w, r, f_2s, f_1s, f_2s, f_1s, CL(0)));
    //job.addLine(F_2s1s_,   17./81,    "F_1s2s_",   0.3e-9); // 0.7
    assertEquals(0, Math.abs(res - 17. / 81), 4e-11);
    res = RkLcr.calc(wCR, f, f2s, f2s, f, 0);
    //CRkH  G_2s1s(new CRk(w, r, f_1s, f_2s, f_2s, f_1s, CL(0)));
    //job.addLine( G_2s1s,  16./729,     "G_1s2s",   0.4e-9); // 0.136
    assertEquals(0, Math.abs(res - 16. / 729), 5e-11);
    res = RkLcr.calc(wCR, f2s, f, f, f2s, 0);
    //CRkH G_2s1s_(new CRk(w, r, f_2s, f_1s, f_1s, f_2s, CL(0)));
    //job.addLine(G_2s1s_,  16./729,    "G_1s2s_",   0.4e-9); // 0.136
    assertEquals(0, Math.abs(res - 16. / 729), 5e-11);
    res = RkLcr.calc(wCR, f2s, f2s, f2s, f2s, 0);
    //CRkH  F_2s2s(new CRk(w, r, f_2s, f_2s, f_2s, f_2s, CL(0)));
    //job.addLine( F_2s2s,  77./512,     "F_2s2s",   0.4e-9); // 1.1
    assertEquals(0, Math.abs(res - 77. / 512), 4e-10);
    FuncVec f2p = CoulombWFFactory.makeP2p(r, 1.);
    f2p.mult(xToR.getDivSqrtCR());
    res = FastLoop.dot(f2p, f2p, wCR.getWithCR2());
    assertEquals(0, Math.abs(res - 1), 7e-14);
    res = RkLcr.calc(wCR, f2p, f, f2p, f, 0);
    //CRkH  F_2p1s(new CRk(w, r, f_2p, f_1s, f_2p, f_1s, CL(0)));
    //job.addLine( F_2p1s,   59./243,     "F_2p1s",    0.03e-9); // 0.277
    assertEquals(0, Math.abs(res - 59. / 243), 3e-11);
    res = RkLcr.calc(wCR, f, f2p, f, f2p, 0);
    //CRkH F_2p1s_(new CRk(w, r, f_1s, f_2p, f_1s, f_2p, CL(0)));
    //job.addLine(F_2p1s_,   59./243,    "F_2p1s_",    0.6e-9); // 0.277
    assertEquals(0, Math.abs(res - 59. / 243), 3e-11);
    res = RkLcr.calc(wCR, f2p, f, f, f2p, 1);
    //CRkH  G_2p1s(new CRk(w, r, f_2p, f_1s, f_1s, f_2p, CL(1))); // NOTE: CL(1)
    //job.addLine( G_2p1s, 112./2187,     "G_2p1s",    0.2e-9); // 0.28
    assertEquals(0, Math.abs(res - 112. / 2187), 2e-10);
    res = RkLcr.calc(wCR, f2s, f2p, f2s, f2p, 0);
    //CRkH  F_2p2s(new CRk(w, r, f_2s, f_2p, f_2s, f_2p, CL(0)));
    //job.addLine( F_2p2s,  83./512,     "F_2p2s",    0.2e-9); // 1.1
    assertEquals(0, Math.abs(res - 83. / 512), 2e-10);
    res = RkLcr.calc(wCR, f2p, f2s, f2p, f2s, 0);
    //CRkH F_2p2s_(new CRk(w, r, f_2p, f_2s, f_2p, f_2s, CL(0)));
    //job.addLine(F_2p2s_,  83./512,    "F_2p2s_",    0.2e-9); // 1.1
    assertEquals(0, Math.abs(res - 83. / 512), 2e-10);
    res = RkLcr.calc(wCR, f2s, f2p, f2p, f2s, 1);
    //CRkH  G_2p2s(new CRk(w, r, f_2s, f_2p, f_2p, f_2s, CL(1))); // NOTE: CL(1)
    //job.addLine( G_2p2s,  45./512,     "G_2p2s",    0.8e-9); // 0.17
    assertEquals(0, Math.abs(res - 45. / 512), 9e-10);
    res = RkLcr.calc(wCR, f2p, f2p, f2p, f2p, 0);
    //CRkH  F_2p2p(new CRk(w, r, f_2p, f_2p, f_2p, f_2p, CL(0)));
    //job.addLine( F_2p2p,  93./512,     "F_2p2p",   0.1e-9); // 0.65
    assertEquals(0, Math.abs(res - 93. / 512), 1e-10);
    res = RkLcr.calc(wCR, f2p, f2p, f2p, f2p, 1);
    assertEquals(0, Math.abs(res - 0.12044270784428178), 2e-20); // at x=-4, 1/16, 220
    res = RkLcr.calc(wCR, f2p, f2p, f2p, f2p, 2);
    assertEquals(0, Math.abs(res - 0.08789062488574255), 2e-20); // at x=-4, 1/16, 220
  }
}