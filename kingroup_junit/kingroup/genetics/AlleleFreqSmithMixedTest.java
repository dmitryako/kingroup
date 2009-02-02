package kingroup.genetics;

import junit.framework.Test;
import junit.framework.TestSuite;
import kingroup.population.SmithPopBuilderModel;

/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: Apr 26, 2004, Time: 11:58:58 AM
 */

/**
 * Testing <code>kingroup.genetics.AlleleFreqSmithMixed</code>.
 */
public class AlleleFreqSmithMixedTest extends KAlleleFreqSmithNonequifrequentTest {
   public static void main(String[] args) {
       junit.textui.TestRunner.run(suite());
   }
   public static Test suite() {
       return new TestSuite(AlleleFreqSmithMixedTest.class);
   }
   public void testKAlleleFreqSmithMixed() {
      SmithPopBuilderModel model = new kingroup.population.SmithPopBuilderModel();
      nl_ = 2;
      int endOfFirstHalf = 0;
      na_ = 3;
      loadModelAttributes(model);
      AlleleFreqSmithMixed freq = new AlleleFreqSmithMixed(model);
      assertEqualFreqs(1.0, freq, 0, endOfFirstHalf);
      assertNonEqualFreqs(1.0, freq, endOfFirstHalf + 1, nl_ - 1);

      nl_ = 3;
      endOfFirstHalf = 0;
      na_ = 3;
      loadModelAttributes(model);
      freq = new AlleleFreqSmithMixed(model);
      assertEqualFreqs(1.0, freq, 0, endOfFirstHalf);
      assertNonEqualFreqs(1.0, freq, endOfFirstHalf + 1, nl_ - 1);

      nl_ = 1;
      endOfFirstHalf = -1;
      na_ = 4;
      loadModelAttributes(model);
      freq = new AlleleFreqSmithMixed(model);
      assertEqualFreqs(1.0, freq, 0, endOfFirstHalf);
      assertNonEqualFreqs(1.0, freq, endOfFirstHalf + 1, nl_ - 1);

      nl_ = 4;
      endOfFirstHalf = 1;
      na_ = 4;
      loadModelAttributes(model);
      freq = new AlleleFreqSmithMixed(model);
      assertEqualFreqs(1.0, freq, 0, endOfFirstHalf);
      assertNonEqualFreqs(1.0, freq, endOfFirstHalf + 1, nl_ - 1);
   }
}
