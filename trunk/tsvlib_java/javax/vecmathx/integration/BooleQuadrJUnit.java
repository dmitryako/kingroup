package javax.vecmathx.integration;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import numeric.functor.FuncConst;
import numeric.functor.FuncExp;
import numeric.functor.FuncPolynom;
import numeric.functor.FuncSin;

import javax.vecmathx.function.FuncVec;
import javax.vecmathx.grid.StepGrid;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 1, 2004, Time: 12:44:49 PM
 */
public class BooleQuadrJUnit extends TestCase {
  private double eps_ = 1e-16;
  public static Test suite() {
    return new TestSuite(BooleQuadrJUnit.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testBodeWeights() {
    StepGrid grid = new StepGrid(0., 1., 5);
    BooleQuadr w = new BooleQuadr(grid);
    assertEquals(0.5 * 7.0 / 45, w.get(0), eps_);
    assertEquals(0.5 * 32.0 / 45, w.get(1), eps_);
    assertEquals(0.5 * 12.0 / 45, w.get(2), eps_);
    assertEquals(0.5 * 32.0 / 45, w.get(3), eps_);
    assertEquals(0.5 * 7.0 / 45, w.get(4), eps_);
    FuncVec func = new FuncVec(grid, new FuncConst(1.0));
    assertEquals(1.0, w.dot(func), eps_);
    double[] c = {1, -1, 1, -1, 1};
    func = new FuncVec(grid, new FuncPolynom(c));
    assertEquals(1. - 1. / 2 + 1. / 3 - 1. / 4 + 1. / 5, w.dot(func), eps_);
    double[] c2 = {0, 1};
    func = new FuncVec(grid, new FuncPolynom(c2));
    assertEquals(0.5, w.dot(func), eps_);
    double[] c3 = {0, 0, 1};
    func = new FuncVec(grid, new FuncPolynom(c3));
    assertEquals(1. / 3, w.dot(func), eps_);
  }
  public void testBodeWeights2() {
    StepGrid grid = new StepGrid(0., Math.PI, 5);
    BooleQuadr w = new BooleQuadr(grid);
    FuncVec func = new FuncVec(grid, new FuncSin());
    assertEquals(2., w.dot(func), 2e-3);
    grid = new StepGrid(0., Math.PI, 9);
    w = new BooleQuadr(grid);
    func = new FuncVec(grid, new FuncSin());
    assertEquals(2., w.dot(func), 2e-5);
    grid = new StepGrid(0., 1., 9);
    w = new BooleQuadr(grid);
    func = new FuncVec(grid, new FuncExp(1));
    assertEquals(Math.exp(1.) - 1., w.dot(func), 1e-7);
    grid = new StepGrid(-1., 1., 9);
    w = new BooleQuadr(grid);
    func = new FuncVec(grid, new FuncExp(-1.));
    assertEquals(Math.exp(1.) - Math.exp(-1.), w.dot(func), 2e-6);
    grid = new StepGrid(-1., 1., 17);
    w = new BooleQuadr(grid);
    func = new FuncVec(grid, new FuncExp(-1.));
    assertEquals(Math.exp(1.) - Math.exp(-1.), w.dot(func), 1e-7);
    grid = new StepGrid(-1., 1., 13);
    w = new BooleQuadr(grid);
    func = new FuncVec(grid, new FuncExp(-1.));
    assertEquals(0., Math.abs(Math.exp(1.) - Math.exp(-1.) - w.dot(func)), 2e-7);
    grid = new StepGrid(-1., 1., 5);
    w = new BooleQuadr(grid);
    func = new FuncVec(grid, new FuncExp(-1.));
    assertEquals(0., Math.abs(Math.exp(1.) - Math.exp(-1.) - w.dot(func)), 7e-5);
  }
}
