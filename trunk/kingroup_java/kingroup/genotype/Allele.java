package kingroup.genotype;
/* Copyright (C) 2003-2004  Dr. Dmitry Konovalov.
* This code is licensed under the GPL license (see www.gnu.org) for academic,
* not-for-profit use or for use within other Open Source software (see www.opensource.org).
* See www.kingroup.org for more details.
*/
import kingroup.util.FastId;
/**
 * al·lele [as in eel] (plural al·leles) noun - form of gene:
 * one of two or more alternative forms of a gene, occupying the same position (get)
 * on paired chromosomes
 * and controlling the same inherited characteristic. Also called allelomorph
 */
final public class Allele extends FastId {
  private double prob_; // probability of occuring
  public Allele(String name, double prob) {
    super(name);
    prob_ = prob;
  }
  public Allele() {
  }
  // [common 040426] JUnit tested in kingroup.genetics.AlleleJUnit.testGetSet
  final public double getProb() {
    return prob_;
  }
  final public void setProb(double d) {
    prob_ = d;
  }
  // [common 040426] JUnit tested in kingroup.genetics.AlleleJUnit.testSubtract
  final public void subtractProb(double val) {
    prob_ -= val;
  }
  // [common 040426] JUnit tested in kingroup.genetics.AlleleJUnit.testAddMultiply
  final public void addProb(double val) {
    prob_ += val;
  }
  final public void multiplyProb(double val) {
    prob_ *= val;
  }
  // [common 040426] JUnit tested in kingroup.genetics.AlleleJUnit.testCopyAlleleFrom
  final public void copyAlleleFrom(Allele from) {
    copyFastIdFrom(from);
    prob_ = from.prob_;
  }
  // [common 040426] JUnit tested in kingroup.genetics.AlleleJUnit.testDuplicate
  final public Allele duplicate() {
    Allele res = new Allele();
    res.copyAlleleFrom(this);
    return res;
  }
  public String toString() {
    StringBuffer res = new StringBuffer();
    res.append(getId());
//    res.append("(").append((float) getProb()).append(")");
    return res.toString();
  }
  public String toStringBrief() {
    StringBuffer res = new StringBuffer();
    res.append(getId());
    res.append("[").append((float) getProb()).append("]");
    return res.toString();
  }
}

