package kingroup.genetics;
import kingroup.genotype.Allele;
import kingroup.genotype.Locus;
import kingroup.population.PopBuilderModelOLD;

/* Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 */
public class AlleleFreqSmithNonequal extends AlleleFreqSmithEqual {
  public AlleleFreqSmithNonequal(PopBuilderModelOLD model, int skew) {
    super(model);
    if (model == null)
      return;
    loadNonequalProbs(0, getNumLoci() - 1, skew);
  }
  public void loadNonequalProbs(int startLocus, int endLocus, int skew) {
    for (int i = startLocus; i <= endLocus; i++) {
      Locus locus = getLocus(i);
      if (locus.size() <= 0)
        continue;
      int weight = 1;
      for (int a = 0; a < locus.size(); a++) {
        Allele allele = locus.get(a);
        allele.setProb(weight);
        weight += skew;
      }
    }
  }
}
