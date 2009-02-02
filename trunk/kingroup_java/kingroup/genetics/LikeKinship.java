package kingroup.genetics;
import kingroup.genotype.Allele;
/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: May 24, 2004, Time: 12:42:47 PM
 */
public class LikeKinship extends Like {
  // trying to inline
  // precond: notR = 1 - r
  public static double calculateProb(double r, double notR, Allele a, Allele b) {
    if (a.equals(b))
      return a.getProb() * (r + notR * a.getProb());
    else
      return a.getProb() * notR * b.getProb();
  }
}
