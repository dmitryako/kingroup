package stlx.valarrayx;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import numeric.functor.functor_rand;

import javax.iox.LOG;
import javax.langx.SystemX;
import javax.utilx.RandomSeed;
import javax.utilx.arrays.vec.Vec;
import javax.vecmathx.function.FuncVec;
import javax.vecmathx.grid.StepGrid;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 2, 2004, Time: 12:22:51 PM
 */
public class valarray_junit extends TestCase {
  private double EPS = 1e-16;
  public static Test suite() {
    return new TestSuite(valarray_junit.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testSum() {
    double[] v = {1, 1.5, -0.5};
    assertEquals(2, Vec.sum(v), EPS);
    double[] v2 = {1};
    assertEquals(1, Vec.sum(v2), EPS);
    double[] v3 = {};
    assertEquals(0, Vec.sum(v3), EPS);
  }
  public void testDot() {
    RandomSeed rand = RandomSeed.getInstance();
    int NUM_RUNS = 1000;
    int duff_better = 0;
    double duff_total = 0;
    double dot_total = 0;
    int MAX_ARR_SIZE = 100;
    long TIME_SCALE = 100;
    LOG.setTrace(true);
    for (int c = 0; c < NUM_RUNS; c++) {
      int arrSize = rand.nextInt(MAX_ARR_SIZE);
      StepGrid grid = new StepGrid(0., 1., arrSize);
      FuncVec randFunc = new FuncVec(grid, new functor_rand(RandomSeed.getInstance()));
      long time = SystemX.time();
      double duff_res = randFunc.duff_dot(grid);
      double duff_time = (double) (SystemX.time() - time) / TIME_SCALE;
      duff_total += duff_time;
      time = SystemX.time();
      double dot_res = randFunc.dot(grid);
      double dot_time = (double) (SystemX.time() - time) / TIME_SCALE;
      dot_total += dot_time;
      assertEquals(dot_res, duff_res, EPS);
      if (duff_time < dot_time) {
        duff_better++;
        LOG.trace(this, "arrSize = " + arrSize);
      }
    }
    LOG.trace(this, "DUFF LOOP is better = " + 100. * duff_better / NUM_RUNS, "%");
    LOG.trace(this, "GVector.dot/DUFF total = " + 100. * dot_total / duff_total, "%");
  }
}
