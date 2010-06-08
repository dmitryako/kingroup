package jm.papers.jm2005_shifted;
import jm.bspline.BSplineFactory;
import jm.grid.WFQuadrLcr;
import junit.framework.Test;
import junit.framework.TestSuite;
import stlx.valarrayx.valarray;

import javax.iox.LOG;
import javax.vecmathx.function.FuncArr;
import javax.vecmathx.grid.StepGrid;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 18/04/2005, Time: 14:39:10
 */
public class ShiftedLogBSpline extends LogCRTestCase {
  public static Test suite() {
    return new TestSuite(ShiftedLogLaguerre.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testLaguerreLogCR() {
    double FIRST = -2;
    int NUM_STEPS = 221;
    double LAST = 2; //
    StepGrid x = new StepGrid(FIRST, LAST, NUM_STEPS);
    WFQuadrLcr w = new WFQuadrLcr(x);
    int k = 5;
    int N = 13;
    FuncArr arr = BSplineFactory.makeBSplineLogCR(w, N, k);
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(arr.get(0)), "wf", "BSplineLogCR_n0.csv");
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(arr.get(0).getDeriv()), "wf"
      , "BSplineLogCR_n0_deriv.csv");
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(arr.get(N - 1)), "wf"
      , "BSplineLogCR_n" + (N - 1) + ".csv");
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(arr.get(N - 1).getDeriv()), "wf"
      , "BSplineLogCR_n" + (N - 1) + "_deriv.csv");
    double res = w.calcMaxOrthonErr(arr);
    assertEquals(0, res, NORM_ERROR);
  }
}