package kingroup.genetics;
import kingroup.genotype.Allele;
import kingroup.genotype.Locus;
/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: May 24, 2004, Time: 1:45:44 PM
 */
public class LikeHaploDip extends LikeKinship {
  private double r;
  private double notR;
  public LikeHaploDip(double r) {
    this.r = r;
    notR = 1. - r;
  }
  // PRECOND: a != null  && b != null
  //    a.size() == 1  &  b.size() == 1
  final public double calculateHaploidLog(Allele a, Allele b) {
    return probToLog(calculateHaploid(a, b));
  }
  final public double calculateHaploid(Allele a, Allele b) {
    return calculateProb(r, notR, a, b);
  }
  // PRECOND: a != null  && b != null
  //    a.size() == 2  &  b.size() == 1
  final public double calculateHaploDipLog(Locus a, Allele b) {
    return probToLog(calculateHaploDip(a, b));
  }
  final public double calculateHaploDip(Locus a, Allele b) {
    double sum = 0;
    // UNROLL THE LOOP
    Allele x = a.get(0);
    Allele x2 = a.get(1);
    double d = calculateProb(r, notR, x, b);    // y is haploid
    d *= x2.getProb();
    sum += d;
    if (!x.equals(x2)) {// if x==x2, there is only one combination
      // exchange x and x2
      d = calculateProb(r, notR, x2, b);    // y is haploid
      d *= x.getProb();
      sum += d;
    }
    return sum;
  }
}
