package jm.bspline.junit;
import jm.bspline.BSplineArr;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import stlx.valarrayx.valarray;

import javax.iox.LOG;
import javax.vecmathx.grid.StepGrid;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 14/04/2005, Time: 08:29:29
 */
public class BSplineArrJUnit extends TestCase {
  private double eps_ = 1e-16;
  public static Test suite() {
    return new TestSuite(BSplineArrJUnit.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testMakeBSplineKnots() {
    int k = 3;
    double LAST = 5;
    StepGrid knots = new StepGrid(0., LAST, 6);
    valarray t = BSplineArr.makeTrueKnots(knots, k);
    LOG.report(this, "knots=" + knots.toString());
    LOG.report(this, "t=" + t.toString());
    int i = 0;
    assertEquals(0, t.get(i++), eps_);
    assertEquals(0, t.get(i++), eps_);
    assertEquals(0, t.get(i++), eps_);
    assertEquals(1, t.get(i++), eps_);
    assertEquals(2, t.get(i++), eps_);
    assertEquals(3, t.get(i++), eps_);
    assertEquals(4, t.get(i++), eps_);
    assertEquals(5, t.get(i++), eps_);
    assertEquals(5, t.get(i++), eps_);
    assertEquals(5, t.get(i++), eps_);
  }
  public void testArray() {
    double LAST = 4;
    int k = 3;
    StepGrid x = new StepGrid(0., LAST, 21);
    StepGrid knots = new StepGrid(0., LAST, 5);
    BSplineArr arr = new BSplineArr(x, knots, k);
    for (int kj = 0; kj < k; kj++) {
      for (int i = 0; i < arr.size(); i++) {
        LOG.saveToFile(valarray.asArray(x), valarray.asArray(arr.get(i)), "wf"
          , "bspline_array_k" + kj + "_i" + i + ".csv");
      }
      if (kj < k - 1)
        arr.loadNextK();
    }
  }
}