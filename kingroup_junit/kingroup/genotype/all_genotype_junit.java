package kingroup.genotype;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 8/03/2005, Time: 08:55:02
 */
public class all_genotype_junit  {
   public static void main(String[] args) {
      junit.textui.TestRunner.run(suite());
   }
   public static Test suite() {
      TestSuite suite = new TestSuite("All JUnit for kingroup.getGenotype.*");
      suite.addTest(AlleleJUnit.suite());
      suite.addTest(GenotypeFactoryJUnit.suite());
      suite.addTest(LocusJUnit.suite());
      suite.addTest(GenotypeGroupJUnit.suite());
      return suite;
   }
}