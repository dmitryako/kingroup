package kingroup.genetics;
import kingroup.genotype.Allele;
import kingroup.genotype.Locus;
import kingroup.population.SmithPopBuilderModel;

import javax.utilx.RandomSeed;

/* Copyright (C) 2004  Dr. Dmitry Konovalov.
* This code is licensed under the GPL license (see www.gnu.org) for academic,
* not-for-profit use or for use within other Open Source software (see www.opensource.org).
* See www.kingroup.org for more details.
*/
public class AlleleFreqRandom extends AlleleFreqEqual {
  public AlleleFreqRandom(SmithPopBuilderModel model, RandomSeed random) {
    super(model);
    if (model == null)
      return;
    loadRandomProbs(random, 0, getNumLoci() - 1);
  }
  public void loadRandomProbs(RandomSeed random, int startLocus, int endLocus) {
    for (int i = startLocus; i <= endLocus; i++) {
      Locus locus = getLocus(i);
      if (locus.size() <= 0)
        continue;
      for (int a = 0; a < locus.size(); a++) {
        Allele allele = locus.get(a);
        allele.setProb(random.nextDouble()); // [0, 1]
      }
    }
  }
}
