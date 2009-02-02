package kingroup.genetics;

import javax.utilx.RandomSeed;
import junit.framework.Test;
import junit.framework.TestSuite;
import kingroup.genotype.Locus;
import kingroup.population.SmithPopBuilderModel;

import java.util.Random;

/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: Apr 26, 2004, Time: 12:23:35 PM
 */
/**
 * Testing <code>kingroup.genetics.AlleleFreqRandom</code>.
 */
public class KAlleleFreqRandomTest extends KAlleleFreqEqualTest {
   public static void main(String[] args) {
       junit.textui.TestRunner.run(suite());
   }
   public static Test suite() {
       return new TestSuite(KAlleleFreqRandomTest.class);
   }
   public void testKAlleleFreqRandom() {
      SmithPopBuilderModel model = new kingroup.population.SmithPopBuilderModel();
      nl_ = 2;
      na_ = 3;
      long seed = 9;
      loadModelAttributes(model);
      AlleleFreqRandom freq = new AlleleFreqRandom(model, RandomSeed.getInstance());
      assertRandomFreqs(RandomSeed.getInstance(), freq, 0, nl_ - 1);
   }

   public void testLoadRandomProbs() {
      kingroup.population.SmithPopBuilderModel model = new kingroup.population.SmithPopBuilderModel();
      nl_ = 4;
      na_ = 3;
      long seed = 0;
      loadModelAttributes(model);
      AlleleFreqRandom freq = new AlleleFreqRandom(model, RandomSeed.getInstance());
      freq.initProb();

      int start = nl_ - 2;
      int end = nl_ - 1;
      seed++;
      freq.loadRandomProbs(RandomSeed.getInstance(), start, end);
      assertRandomFreqs(new Random(seed), freq, start, end);
      assertEqualFreqs(0.0, freq, 0, start - 1);
   }

   protected void assertRandomFreqs(Random random, AlleleFreqEqual freq
                                 , int idxFirstLocus, int idxLastLocus) {
      for (int L = idxFirstLocus; L <= idxLastLocus; L++)  {
         Locus locus = freq.getLocus(L);
         for (int allele = 0; allele < locus.size(); allele++)
            assertEquals(random.nextDouble(), locus.get(allele).getProb(), delta_);
      }
   }
}
