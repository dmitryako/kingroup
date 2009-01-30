package jm.papers.jm2005_shifted;
import junit.framework.TestCase;

import javax.iox.LOG;
import javax.vecmathx.grid.StepGrid;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 4/04/2005, Time: 11:38:42
 */
public class LogCRTestCase extends TestCase {
  protected double NORM_ERROR = 1e-10;
//   int NUM_STEPS = 880;
//   double FIRST = -4;
//   double STEP = 1./64.;
  int NUM_STEPS = 440;
  double FIRST = -4;
  double LAST = 4;
  double STEP = 1. / 50.;

//   int NUM_STEPS = 440;
//   double FIRST = -4;
//   double STEP = 1./32.;

//   int NUM_STEPS = 220;
//   double FIRST = -4;
//   double STEP = 1./16.;
  protected StepGrid x = new StepGrid(FIRST, NUM_STEPS, STEP);
//   public static Test suite() { return new TestSuite(LogCRZetaHy.class); }
//   public static void main(String[] args) {  junit.textui.TestRunner.run(suite()); }
  public void assertAndView(String text, double val, double error) {
    float f = (float) Math.abs(val);
    LOG.report(this, text + f);
    assertEquals(0, f, error);
  }
}