package jm.laguerre;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.vecmathx.grid.StepGrid;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 2, 2004, Time: 3:01:36 PM
 */
public class LagrrArrTest extends TestCase {
  private double EPS = 1e-16;
  public static Test suite() {
    return new TestSuite(LagrrArrTest.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testLaguerreArray() {
    StepGrid grid = new StepGrid(0., 1., 5);
    LagrrArr a12 = new LagrrArr(grid, 13, 0, 1);
    LagrrArr arr = new LagrrArr(grid, 1, 0, 1);
    assertEquals(1., arr.get(0).get(0), EPS);
    assertEquals(1., a12.get(0).get(0), EPS);
    assertEquals(1., arr.get(0).get(4), EPS);
    assertEquals(1., a12.get(0).get(4), EPS);
    arr = new LagrrArr(grid, 2, 0, 1);
    assertEquals(0.5, arr.get(1).get(2), EPS);
    assertEquals(0.5, a12.get(1).get(2), EPS);
    assertEquals(0., arr.get(1).get(4), EPS);
    assertEquals(0., a12.get(1).get(4), EPS);
    arr = new LagrrArr(grid, 3, 0, 1);
    assertEquals(-0.5, arr.get(2).get(4), EPS);
    assertEquals(-0.5, a12.get(2).get(4), EPS);
    arr = new LagrrArr(grid, 4, 0, 1);
    assertEquals(-2.0 / 3, arr.get(3).get(4), EPS);
    assertEquals(-2.0 / 3, a12.get(3).get(4), EPS);
    assertEquals(0., Math.abs(0.4962122235 - a12.get(12).get(4)), 1e-11);
    grid = new StepGrid(0., 5., 5);
    a12 = new LagrrArr(grid, 13, 0, 1);
    assertEquals(0., Math.abs(-1.4486042948 - a12.get(12).get(4)), 5e-11);
  }
}
