package jm;
import jm.angular.all_angular_junit;
import jm.atom.all_atom_junit;
import jm.bspline.all_bspline_junit;
import jm.laguerre.all_laguerre_junit;
import jm.shell.all_shell_junit;
import junit.framework.Test;
import junit.framework.TestSuite;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 18/03/2005, Time: 09:37:06
 */
public class all_jm_junit {
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public static Test suite() {
    TestSuite suite = new TestSuite("All JUnit for jm.*");
    suite.addTest(all_bspline_junit.suite());
    suite.addTest(all_laguerre_junit.suite());
    suite.addTest(all_angular_junit.suite());
    suite.addTest(all_shell_junit.suite());
    suite.addTest(all_atom_junit.suite());
    return suite;
  }
}