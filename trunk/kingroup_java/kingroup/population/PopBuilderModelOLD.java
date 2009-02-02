package kingroup.population;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 28, 2004, Time: 3:55:32 PM
 */
public class PopBuilderModelOLD {
  private int numLoci;  // number of loci
  private int numAlleles; // number of alleles for each get
  public void copyTo(PopBuilderModelOLD bean) {
    bean.setNumLoci(getNumLoci());
    bean.setNumAlleles(getNumAlleles());
  }
  public void loadTestData() {
    setNumLoci(2);
    setNumAlleles(4);
  }
  final public void setNumLoci(int v) {
    numLoci = v;
  }
  final public int getNumLoci() {
    return numLoci;
  }
  final public void setNumAlleles(int v) {
    numAlleles = v;
  }
  final public int getNumAlleles() {
    return numAlleles;
  }
}
