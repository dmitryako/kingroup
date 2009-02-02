package kingroup.population;
import kingroup.genetics.OldAlleleFreq;
/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: Jul 12, 2004, Time: 12:06:24 PM
 */
public class PopHalfSib extends MendelPopBuilder {
  public PopHalfSib(OldAlleleFreq freq) {
    super(freq);
  }
  public OldPop buildSmith(SmithPopBuilderModel model) {
    OldPop pop = new OldPop(getAlleleFreq());
    return buildFullAndHalfMix(pop, model);
  }
}
