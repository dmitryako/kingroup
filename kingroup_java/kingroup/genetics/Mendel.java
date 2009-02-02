package kingroup.genetics;
import kingroup.genotype.Genotype;
import kingroup.genotype.Locus;
/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: May 7, 2004, Time: 7:49:09 AM
 */
public class Mendel {
  public static boolean validParentOffspring(Genotype parent, Genotype geno) {
    for (int L = 0; L < parent.getNumLoci(); L++) {
      Locus locusP = parent.getLocus(L);
      Locus locus = geno.getLocus(L);
      if (!locus.findAtLeastOneAlleleFrom(locusP))
        return false;
    }
    return true;
  }
}
