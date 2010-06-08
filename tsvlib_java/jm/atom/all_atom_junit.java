package jm.atom;
import jm.atom.coulomb.all_coulomb_junit;
import jm.atom.junit.HydrogenJUnit;
import jm.atom.junit.RkLcrTest;
import jm.atom.junit.YkLcrTest;
import junit.framework.Test;
import junit.framework.TestSuite;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 15/03/2005, Time: 08:43:59
 */
public class all_atom_junit {
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public static Test suite() {
    TestSuite suite = new TestSuite("All JUnit for jm.atom.*");
    suite.addTest(all_coulomb_junit.suite());
    suite.addTest(HydrogenJUnit.suite());
    suite.addTest(YkLcrTest.suite());
    suite.addTest(RkLcrTest.suite());
    return suite;
  }
}