package javax.mathx;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 15/05/2005, Time: 13:19:36
 */
public class BinomialCoeffJUnit extends TestCase {
  private static final double EPS = 1e-13;
  public static Test suite() {
    return new TestSuite(BinomialCoeffJUnit.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testCalc() {
    int P = 10;
    BinomialCoeff bc = new BinomialCoeff(P);
    assertEquals(1., bc.nCk(P, P), EPS);
    assertEquals(P, bc.nCk(P, 1), EPS);
    assertEquals(6, bc.nCk(4, 2), EPS);
  }
}