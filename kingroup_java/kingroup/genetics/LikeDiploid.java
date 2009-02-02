package kingroup.genetics;
import kingroup.genotype.Allele;
import kingroup.genotype.Locus;
/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: May 24, 2004, Time: 1:27:34 PM
 */
public class LikeDiploid extends LikeKinship {
  private double rm;
  private double rp;
  private double notRm;
  private double notRp;
  public LikeDiploid(double rm, double rp) {
    this.rm = rm;
    this.rp = rp;
    notRm = 1. - rm;
    notRp = 1. - rp;
  }
  // PRECOND: a != null  && b != null
  //         && a.size() == Locus.DIPLOID
  //         && b.size() == Locus.DIPLOID
  final public double calcDiploidLog(Locus a, Locus b) {
    return probToLog(calcDiploid(a, b));
  }
  final public double calcDiploid(Locus a, Locus b) {
    return calcDiploidSum(a, b);  // this is how KINSHIP does it!!!!!
//      return calcDiploidAvr(a, b);
  }
  final public double calcDiploidAvr(Locus a, Locus b) {
    double sum = 0;
    int count = 0;
    for (int ix = 0; ix < Locus.DIPLOID; ix++) {
      Allele x = a.get(ix);
      Allele x2 = a.get(ix == 0 ? 1 : 0); // not; if 0, then 1; if 1, then 0

      // UNROLL THE LOOP
      Allele y = b.get(0);
      Allele y2 = b.get(1);
      double d = calculateProb(rm, notRm, x, y);
      double d2 = calculateProb(rp, notRp, x2, y2);
      d *= d2;
      sum += d;
      count++;
      if (!y.equals(y2)) {// if y==y2, there is only one combination that is DIFFERENT
        // exchange y and y2
        d = calculateProb(rm, notRm, x, y2);
        d2 = calculateProb(rp, notRp, x2, y);
        d *= d2;
        sum += d;
        count++;
      }
      if (x.equals(x2))
        break; // if x==x2, there is only one combination that is DIFFERENT
    }
    if (count != 0)
      return sum / count;
    return sum;
  }
  final public double calcDiploidSum(Locus a, Locus b) {
    double sum = 0;
    for (int ix = 0; ix < Locus.DIPLOID; ix++) {
      Allele x = a.get(ix);
      Allele x2 = a.get(ix == 0 ? 1 : 0); // not; if 0, then 1; if 1, then 0

      // UNROLL THE LOOP
      Allele y = b.get(0);
      Allele y2 = b.get(1);
      double d = calculateProb(rm, notRm, x, y);
      double d2 = calculateProb(rp, notRp, x2, y2);
      d *= d2;
      sum += d;
      if (!y.equals(y2)) {// if y==y2, there is only one combination that is DIFFERENT
        // exchange y and y2
        d = calculateProb(rm, notRm, x, y2);
        d2 = calculateProb(rp, notRp, x2, y);
        d *= d2;
        sum += d;
      }
      if (x.equals(x2))
        break; // if x==x2, there is only one combination that is DIFFERENT
    }
    return sum;
  }
}
