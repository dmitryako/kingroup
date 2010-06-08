package jm.harmonic;
import jm.grid.TransLcrToR;
import jm.grid.WFQuadrLcr;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.vecmathx.function.FuncArr;
import javax.vecmathx.grid.StepGrid;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/04/2005, Time: 08:07:30
 */
public class HarmonicLogCRJUnit extends TestCase {
  private double NORM_ERROR = 1e-10;
  public static Test suite() {
    return new TestSuite(HarmonicLogCRJUnit.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testL() {
    double FIRST = -1;
    int NUM_STEPS = 221;
    double LAST = 2.5;
    StepGrid x = new StepGrid(FIRST, LAST, NUM_STEPS);
    WFQuadrLcr w = new WFQuadrLcr(x);
    TransLcrToR xToR = w.getLogCRToR();
    double B = 1;
    int N = 10;
    int L = 0;
    FuncArr arr = new HarmonicLogCR(xToR, N, L, B);
    double res = w.calcMaxOrthonErr(arr);
    assertEquals(0, res, NORM_ERROR);
    L = 1;
    arr = new HarmonicLogCR(xToR, N, L, B);
    res = w.calcMaxOrthonErr(arr);
    assertEquals(0, res, NORM_ERROR);
    B = 0.5;
    arr = new HarmonicLogCR(xToR, N, L, B);
    res = w.calcMaxOrthonErr(arr);
    assertEquals(0, res, NORM_ERROR);
  }
}
