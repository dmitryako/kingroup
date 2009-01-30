package javax.vecmathx.grid;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 1, 2004, Time: 8:30:46 AM
 */
public class StepGridJUnit extends TestCase {
  private double eps_ = 1e-16;
  public static Test suite() {
    return new TestSuite(StepGridJUnit.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testStepGrid() {
    int num_steps = 220;
    double step = 1. / 16.;
    double first = -4.;
    double last = first + step * num_steps;
    int size = num_steps + 1;
    StepGrid grid = new StepGrid(first, last, size);
    StepGrid grid2 = new StepGrid(first, num_steps, step);
    assertEquals(size, grid.size());
    assertEquals(size, grid2.size());
    int idx = 0;
    assertEquals(first, grid.getElement(idx), eps_);
    assertEquals(first, grid2.getElement(idx), eps_);
    idx = 1;
    assertEquals(first + step, grid.getElement(idx), eps_);
    assertEquals(first + step, grid2.getElement(idx), eps_);
  }
}
