package javax.vecmathx.function.legendra;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import stlx.FastLoop;

import javax.vecmathx.function.FuncArr;
import javax.vecmathx.grid.StepGrid;
import javax.vecmathx.integration.BooleQuadr;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 10, 2004, Time: 10:53:34 AM
 */
public class ShiftedLegendraJUnit extends TestCase {
  public static Test suite() {
    return new TestSuite(ShiftedLegendraJUnit.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testShiftedArray() {
    int GRID_SIZE = 201;
    StepGrid r = new StepGrid(0., 1., GRID_SIZE);
    BooleQuadr w = new BooleQuadr(r);
    int N = 3;
    FuncArr arr = new ShiftedLegendraOrthonorm(r, N);
    double res = FastLoop.dot(arr.get(0), arr.get(0), w);
    assertEquals(0, Math.abs(res - 1), 2e-15);
    res = FastLoop.dot(arr.get(1), arr.get(1), w);
    assertEquals(0, Math.abs(res - 1), 2e-15);
    res = FastLoop.dot(arr.get(2), arr.get(2), w);
    assertEquals(0, Math.abs(res - 1), 4e-15);
    res = FastLoop.dot(arr.get(0), arr.get(1), w);
    assertEquals(0, Math.abs(res), 2e-15);
    res = FastLoop.dot(arr.get(0), arr.get(2), w);
    assertEquals(0, Math.abs(res), 3e-15);
    res = FastLoop.dot(arr.get(1), arr.get(2), w);
    assertEquals(0, Math.abs(res), 3e-15);
  }
}
