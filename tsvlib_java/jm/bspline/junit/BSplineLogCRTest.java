package jm.bspline.junit;
import jm.bspline.BSplineFactory;
import jm.grid.TransLcrToR;
import jm.grid.WFQuadrLcr;
import junit.framework.Test;
import junit.framework.TestSuite;

import javax.vecmathx.function.FuncArr;
import javax.vecmathx.grid.StepGrid;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 18/04/2005, Time: 11:16:23
 */
public class BSplineLogCRTest extends BSplineBasisJUnit {
  public static Test suite() {
    return new TestSuite(BSplineLogCRTest.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testBSplineLogCR() {
    double FIRST = -2;
    int NUM_STEPS = 101;
    double LAST = 2; //
    StepGrid x = new StepGrid(FIRST, LAST, NUM_STEPS);
    WFQuadrLcr w = new WFQuadrLcr(x);
    TransLcrToR xToR = w.getLogCRToR();
    int k = 5;
    int N = 6;
    FuncArr arr = BSplineFactory.makeBSplineLogCR(w, N, k);
    saveArrayK(x, arr, k, "orthog");
    double res = w.calcMaxOrthonErr(arr);
    assertEquals(0, res, NORM_ERROR);
  }
}
