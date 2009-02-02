package kingroup.genetics;
import kingroup.population.SmithPopBuilderModel;

/* Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 */
public class AlleleFreqSmithMixed extends AlleleFreqSmithNonequal {
  public AlleleFreqSmithMixed(SmithPopBuilderModel model) {
    super(model, 1);
    if (model == null)
      return;
    loadEqualProbs(0, getNumLoci() / 2 - 1);
  }
}
