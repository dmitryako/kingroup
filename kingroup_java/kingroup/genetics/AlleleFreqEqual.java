package kingroup.genetics;
import kingroup.genotype.Locus;
import kingroup.population.PopBuilderModelOLD;
/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: Apr 23, 2004, Time: 5:03:22 PM
 */
public class AlleleFreqEqual extends OldAlleleFreq {
  public AlleleFreqEqual(PopBuilderModelOLD model) {
    if (model == null)
      return;
    generateLocusIds(model.getNumLoci());
    generateAlleles(model.getNumAlleles());
    loadEqualProbs(0, getNumLoci() - 1);
  }
  public void loadEqualProbs(int startLocus, int endLocus) {
    for (int i = startLocus; i <= endLocus; i++) {
      Locus locus = getLocus(i);
      if (locus.size() <= 0)
        continue;
      for (int a = 0; a < locus.size(); a++)
        locus.get(a).setProb(1.0f);
    }
  }
}
