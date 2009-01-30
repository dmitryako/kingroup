package jm.atom.coulomb;
import junit.framework.Test;
import junit.framework.TestSuite;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 15/03/2005, Time: 08:49:27
 */
public class all_coulomb_junit {
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public static Test suite() {
    TestSuite suite = new TestSuite("All JUnit for jm.atom.coulomb.*");
    suite.addTest(CoulombFactoryJUnit.suite());
    return suite;
  }
}