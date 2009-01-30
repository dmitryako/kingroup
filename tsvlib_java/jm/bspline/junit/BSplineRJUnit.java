package jm.bspline.junit;
import jm.bspline.BSplineBasis;
import jm.bspline.BSplineR;
import jm.grid.WFQuadrR;
import junit.framework.Test;
import junit.framework.TestSuite;
import stlx.valarrayx.valarray;

import javax.vecmathx.function.FuncArr;
import javax.vecmathx.grid.StepGrid;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 18/04/2005, Time: 12:05:09
 */
public class BSplineRJUnit extends BSplineBasisJUnit {
  public static Test suite() {
    return new TestSuite(BSplineRJUnit.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testBSplineR() {
    double FIRST = 0;
    int NUM_STEPS = 101;
    double LAST = 20; //
    StepGrid x = new StepGrid(FIRST, LAST, NUM_STEPS);
    WFQuadrR w = new WFQuadrR(x);
    int k = 3;
    int N = 5;
    valarray t = BSplineBasis.makeBSplineKnotsFromGrid(N, w.x, k);
    FuncArr arr = new BSplineR(w, t, k);
    saveArrayK(x, arr, k, "orthog");
    double res = w.calcMaxOrthonErr(arr);
    assertEquals(0, res, NORM_ERROR);
  }
}
