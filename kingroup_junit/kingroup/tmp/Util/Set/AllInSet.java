package kingroup.tmp.Util.Set;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Copyright (C) 2003-2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 */
public class AllInSet {
   public static Test suite() {
     TestSuite suite = new TestSuite("All JUnit geometryx for kingroup.Util.Set.*");

     suite.addTest(SetFactoryTest.suite());
     suite.addTest(SetImmutableTest.suite());

     return suite;
   }
}
