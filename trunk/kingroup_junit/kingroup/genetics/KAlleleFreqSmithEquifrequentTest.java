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
 * Date: Apr 26, 2004, Time: 1:26:15 PM
 */
/**
 * Testing <code>kingroup.genetics.AlleleFreqSmithEqual</code>.
 */
public class KAlleleFreqSmithEquifrequentTest extends KAlleleFreqEqualTest {
   public static void main(String[] args) {
       junit.textui.TestRunner.run(suite());
   }
   public static Test suite() {
       return new TestSuite(KAlleleFreqSmithEquifrequentTest.class);
   }
   public void testKAlleleFreqSmithEquifrequent() {
      SmithPopBuilderModel model = new kingroup.population.SmithPopBuilderModel();
      nl_ = 4;
      na_ = 5;
      loadModelAttributes(model);
      AlleleFreqSmithEqual freq = new AlleleFreqSmithEqual(model);
      assertEqualFreqs(1.0, freq, 0, nl_ - 1);

      freq.normalize(1, false);
      double prob = 1.0 / na_;
      assertEqualFreqs(prob, freq, 0, nl_ - 1);
   }
}
