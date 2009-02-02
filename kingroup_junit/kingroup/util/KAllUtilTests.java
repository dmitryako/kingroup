package kingroup.util;

import junit.framework.Test;
import junit.framework.TestSuite;
import kingroup.model.KAllModelTests;

/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: May 10, 2004, Time: 4:18:58 PM
 */
public class KAllUtilTests {
   public static void main(String[] args) {
       junit.textui.TestRunner.run(suite());
   }
   public static Test suite() {
       TestSuite suite = new TestSuite("All JUnit geometryx for kingroup.util.*");
       suite.addTest(KArraysTest.suite());
       return suite;
   }
}
