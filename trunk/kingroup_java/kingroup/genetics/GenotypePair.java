package kingroup.genetics;
import kingroup.genotype.Genotype;
/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: Apr 27, 2004, Time: 8:23:12 AM
 */
final public class GenotypePair {
  private Genotype first_ = null;
  private Genotype second_ = null;
  public boolean equals(Object obj) {
    if (obj == this)
      return true;
    if (!(obj instanceof GenotypePair))
      return false;
    GenotypePair objPair = (GenotypePair) obj;
    if (first_.equals(objPair.first_)
      && second_.equals(objPair.second_))
      return true;
    if (second_.equals(objPair.first_)
      && first_.equals(objPair.second_))
      return true;
    return false;
  }
  public GenotypePair(Genotype _first, Genotype _second) {
    first_ = _first;
    second_ = _second;
  }
  final public Genotype first() {
    return first_;
  }
  final public Genotype second() {
    return second_;
  }
  final public Genotype pair(Object geno) {
    if (geno == first_)
      return second_;
    else
      return first_;
  }
}
