package kingroup.genotype;
/* Copyright (C) 2003-2004  Dr. Dmitry Konovalov.
* This code is licensed under the GPL license (see www.gnu.org) for academic,
* not-for-profit use or for use within other Open Source software (see www.opensource.org).
* See www.kingroup.org for more details.
*/
import kingroup.util.FastId;
import kingroup.util.FastIdArray;
/**
 * loci - Plural of get
 * lo·cus [lokus] (plural lo·ci [losie]) noun
 * 1. place: a place where something happens
 * 2. mathematics: set of points: a set of points, the positions of which satisfy a set of algebraic conditions
 * 3. genetics: gene position: the position of a gene in a chromosome
 * <p/>
 * [Early 18th century. From Latin, place (source of English local and locate),
 * of unknown origin.]
 */
public class Loci extends FastId {
  private LocusArrayList v_ = null; // array of loci
  public Loci() {
    v_ = new LocusArrayList();
  }
  public void limitSize(int maxSize) {
    v_.limitSize(maxSize);
  }
  final public Locus getLocus(int i) {
    return v_.get(i);
  }// throws ArrayIndexOutOfBoundsException
  final public Loci add(Locus a) {
    v_.add(a);
    return this;
  }
  final public int getNumLoci() {
    return v_.size();
  }
  final public Locus replace(int i, Locus a) {
    return v_.set(i, a);
  }
  public int getMaxAlleleCount() {
    int maxCount = 0;
    for (int i = 0; i < v_.size(); i++) {
      int count = v_.get(i).size();
      if (maxCount < count)
        maxCount = count;
    }
    return maxCount;
  }
  public void initProb() {
    for (int i = 0; i < v_.size(); i++)
      v_.get(i).initProb();
  }
  final public void subtractProbs(Loci val) {
    for (int i = 0; i < v_.size(); i++)
      v_.get(i).subtractProbs(val.getLocus(i));
  }
  final public void multiplyProb(double val) {
    for (int i = 0; i < v_.size(); i++)
      v_.get(i).multiplyProb(val);
  }
  public void replaceSharedAllele(Locus forLocus, Allele sharedAllele) {
    Locus locus = find(forLocus);
    if (locus == null) {
      locus = new Locus();
      locus.setId(forLocus);
      add(locus);
    }
    locus.replaceSharedAllele(sharedAllele);
  }
  public Allele find(Locus findLocus, Allele findAllele) {
    Locus locus = find(findLocus);
    if (locus == null)
      return null;
    return locus.find(findAllele);
  }
  public Locus find(FastId findId) {
    for (int i = 0; i < v_.size(); i++) {
      Locus locus = v_.get(i);
      if (locus.equals(findId))
        return locus;
    }
    return null;
  }
  public Locus find(String locusId) {
    Locus findLocus = new Locus();
    findLocus.setId(locusId);
    return find(findLocus);
  }
  public Locus put(String locusId) {
    Locus locus = find(locusId);
    if (locus != null)
      return locus;
    locus = new Locus();
    locus.setId(locusId);
    add(locus);
    return locus;
  }
  public void put(String[] ids) {
    if (ids == null || ids.length == 0)
      return;
    for (int i = 0; i < ids.length; i++)
      put(ids[i]);
  }
  final public FastIdArray getLocusIds() {
    FastIdArray res = new FastIdArray(getNumLoci());
    for (int L = 0; L < getNumLoci(); L++)
      res.set(L, getLocus(L));
    return res;
  }
  public String toString() {
    StringBuffer res = new StringBuffer();
    res.append(getId()).append("={");
    if (getNumLoci() < 1)
      return res.append(" is empty}").toString();
    for (int i = 0; i < v_.size(); i++) {
      res.append(getLocus(i).toString());
      if (i != v_.size() - 1) // all except last
        res.append(", ");
    }
    return res.append("}").toString();
  }
  public void shareAllelesWith(Loci to) {
//      if (to.size() != size()) {
//         LOG.throwError(this, "shareAllelesWith assumes to.size()==this.size()", "");
//      }
    for (int L = 0; L < getNumLoci(); L++) {
      Locus fromLocus = getLocus(L);
      Locus toLocus = new Locus();
      toLocus.setId(fromLocus);
      to.add(toLocus);
      fromLocus.shareAllelesWith(toLocus);
    }
  }
  public boolean equalsByAllelesTo(Loci loci2) {
    if (getNumLoci() != loci2.getNumLoci())
      return false;
    for (int L = 0; L < getNumLoci(); L++) {
      Locus locus = getLocus(L);
      Locus locus2 = loci2.getLocus(L);
      if (!locus.equalsByAllelesTo(locus2))
        return false;
    }
    return true;
  }
}
