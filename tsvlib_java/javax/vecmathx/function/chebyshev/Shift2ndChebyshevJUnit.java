package javax.vecmathx.function.chebyshev;
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
 * User: jc138691, Date: Dec 10, 2004, Time: 1:07:02 PM
 */
public class Shift2ndChebyshevJUnit extends TestCase {
  public static Test suite() {
    return new TestSuite(Shift2ndChebyshevJUnit.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testShift2ndChebyshev() {
    int GRID_SIZE = 201;
    StepGrid r = new StepGrid(0., 1., GRID_SIZE);
    BooleQuadr w = new BooleQuadr(r);
    int N = 3;
    FuncArr arr = new Shift2ndChebyshevOrthonorm(r, N);
    double res = FastLoop.dot(arr.get(0), arr.get(0), w);
    assertEquals(0, Math.abs(res - 1), 2e-4); // 2e-4 for SIZE=201
    res = FastLoop.dot(arr.get(1), arr.get(1), w);
    assertEquals(0, Math.abs(res - 1), 6e-4);
    res = FastLoop.dot(arr.get(2), arr.get(2), w);
    assertEquals(0, Math.abs(res - 1), 2e-3);
    res = FastLoop.dot(arr.get(0), arr.get(1), w);
    assertEquals(0, Math.abs(res), 3e-16);
    res = FastLoop.dot(arr.get(0), arr.get(2), w);
    assertEquals(0, Math.abs(res), 4e-4);
    res = FastLoop.dot(arr.get(1), arr.get(2), w);
    assertEquals(0, Math.abs(res), 3e-16);
  }
}
