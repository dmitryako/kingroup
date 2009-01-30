package jm.bspline.junit;
import jm.bspline.BSplineBasis;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import stlx.valarrayx.valarray;

import javax.iox.LOG;
import javax.vecmathx.function.FuncArr;
import javax.vecmathx.grid.StepGrid;
import javax.vecmathx.integration.BooleQuadr;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 15/04/2005, Time: 14:40:23
 */
public class BSplineBasisJUnit extends TestCase {
  protected double NORM_ERROR = 1e-10;
  public static Test suite() {
    return new TestSuite(BSplineBasisJUnit.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testArrayK() {
    calcArrayK(3);
    calcArrayK(5);
  }
  public void calcArrayK(int k) {
    double FIRST = 0;
    double LAST = 5;
    int NUM_STEPS = 101;
    StepGrid x = new StepGrid(FIRST, LAST, NUM_STEPS);
    BooleQuadr w = new BooleQuadr(x);
    StepGrid knots = new StepGrid(FIRST, LAST, Math.round((float) LAST + 1));
    BSplineBasis arr = new BSplineBasis(x, knots, k);
    saveArrayK(x, arr, k, "basis");
    arr.makeOrthonorm(w);
    double normErr = w.calcMaxOrthonErr(arr);
    saveArrayK(x, arr, k, "orthog");
    assertEquals(0, normErr, NORM_ERROR);
  }
  public void saveArrayK(valarray x, FuncArr arr, int k, String name) {
    for (int i = 0; i < arr.size(); i++) {
      LOG.saveToFile(valarray.asArray(x), valarray.asArray(arr.get(i)), "wf"
        , "bspline_" + name + "_k" + k + "_i" + i + ".csv");
    }
  }
}