package kingroup.genotype;
/* Copyright (C) 2003-2004  Dr. Dmitry Konovalov.
* This code is licensed under the GPL license (see www.gnu.org) for academic,
* not-for-profit use or for use within other Open Source software (see www.opensource.org).
* See www.kingroup.org for more details.
*/
import kingroup.util.FastId;

/* lo·cus [lokus] (plural lo·ci [losie]) noun
1. place: a place where something happens
2. mathematics: set of points: a set of points, the positions of which satisfy a set of algebraic conditions
3. genetics: gene position: the position of a gene in a chromosome

[Early 18th century. From Latin, place (source of English local and locate), of unknown origin.]
*/
final public class Locus extends FastId {
  public static final int HAPLOID = 1;
  public static final int DIPLOID = 2;
  public static final int MATERNAL_IDX = 0;
  public static final int PATERNAL_IDX = 1;
  private AlleleArrayList aa = null;
  static public void arraycopy(Locus[] src, Locus[] dest, int len) {
    int size = Math.min(Math.min(dest.length, src.length), len);
    for (int i = 0; i < size; i++)
      dest[i] = src[i];
  }
  public Locus() {
    aa = new AlleleArrayList();
  }
//   public Locus(int _size)   {aa = new AlleleArrayList(_size);    }
  public Locus(String[] ids) {
    if (ids == null || ids.length == 0) {
      aa = new AlleleArrayList();
      return;
    }
    aa = new AlleleArrayList();
    for (int i = 0; i < ids.length; i++) {
      Allele allele = new Allele();
      allele.setId(ids[i]);
      aa.add(allele);
    }
  }

  // DO NOT USE LocusLocusfrom)
  //    very easy to make a mistake when trying to create an empty Locus
  //    but get a copied Locus.
  //public LocusLocusfrom) {
  //    this(from.size());
  //    super.copyFrom(from);
  //    ArraysX.arraycopy(from.aa, aa, size());
  //}
  // HIGHLY-USED
  final public Allele get(int i) {
    return aa.get(i);
  }// throws ArrayIndexOutOfBoundsException
  final public int size() {
    return aa.size();
  }
  // ADD Allele is a special case!!!
  // It does not make sense to have different Alleles with the same ids
  final public Locus add(Allele newAllele) {
    Allele oldAllele = find(newAllele);
    if (oldAllele != null)
      aa.add(oldAllele);
    else
      aa.add(newAllele);
    return this;
  }
  public Locus duplicate() {
    Locus res = new Locus();
    res.copyFastIdFrom(this);
    for (int i = 0; i < aa.size(); i++)
      res.add(aa.get(i).duplicate());
    return res;
  }
  public void initProb() {
    for (int i = 0; i < aa.size(); i++)
      aa.get(i).setProb(0.0f);
  }
  final public void subtractProbs(Locus val) {
    for (int i = 0; i < aa.size(); i++)
      aa.get(i).subtractProb(val.aa.get(i).getProb());
  }
  final public void multiplyProb(double val) {
    for (int i = 0; i < aa.size(); i++)
      aa.get(i).multiplyProb(val);
  }
  public Allele put(String alleleId) {
    Allele allele = find(alleleId);
    if (allele != null)
      return allele;
    allele = new Allele();
    allele.setId(alleleId);
    add(allele);
    return allele;
  }
  public String toString() {
    StringBuffer res = new StringBuffer();
//    res.append(getId()).append("={");
//    res.append(getId());
    if (size() < 1)
      return res.append("empty}").toString();
    for (int i = 0; i < aa.size(); i++) {
      res.append(get(i).toString());
      if (i != aa.size() - 1) // all except last
        res.append("/");
//      res.append(", ");
    }
//    return res.append("}").toString();
    return res.toString();
  }
  public void replaceSharedAllele(Allele sharedAllele) {
    boolean done = false;
    // NOTE!!! There are may be multiple instances of the same getAllele
    for (int i = 0; i < aa.size(); i++) {
      Allele allele = aa.get(i);
      if (allele.equals(sharedAllele)) {
        aa.set(i, sharedAllele);
        done = true;
      }
    }
    if (!done)
      add(sharedAllele);
  }
  final public boolean findAtLeastOneAlleleFrom(Locus locus) {
    for (int A = 0; A < locus.size(); A++) {
      Allele allele = locus.get(A);
      if (find(allele) != null)
        return true;
    }
    return false;
  }
  public Allele find(FastId findId) {
    for (int i = 0; i < aa.size(); i++) {
      Allele allele = aa.get(i);
      if (allele.equals(findId))
        return allele;
    }
    return null;
  }
  public int findIdx(FastId findId) {
    for (int i = 0; i < aa.size(); i++) {
      Allele allele = aa.get(i);
      if (allele.equals(findId))
        return i;
    }
    return -1;
  }
  public Allele find(String alleleId) {
    Allele findAllele = new Allele();
    findAllele.setId(alleleId);
    return find(findAllele);
  }
  public boolean isEqualLocus(Locus test) {
    if (test.size() != size())
      return false;
    for (int i = 0; i < size(); i++) {
      if (!test.get(i).equals(get(i)))
        return false;
    }
    return true;
  }
  public String toStringBrief(String alleleDelim) {
    if (size() < 1)
      return " ";  // NOTE! If "" is returned, it becomes hard to read ;;;;;-like input
    StringBuffer res = new StringBuffer();
    for (int i = 0; i < aa.size(); i++) {
      if (i == size() - 1)
        res.append(get(i).getId());
      else
        res.append(get(i).getId()).append(alleleDelim);
    }
    return res.toString();
  }
  public void shareAllelesWith(Locus toLocus) {
    for (int i = 0; i < size(); i++) {
      toLocus.add(get(i));
    }
  }
  public void removeAll() {
    aa = new AlleleArrayList();
  }
  public Allele replace(int idx, Allele newVal) {
    return aa.set(idx, newVal);
  }
  public boolean equalsByAllelesTo(Locus locus2) {
    if (!foundAllAllelesIn(locus2))
      return false;
    return locus2.foundAllAllelesIn(this);
  }
  private boolean foundAllAllelesIn(Locus locus2) {
    if (size() != locus2.size())
      return false;
    for (int i = 0; i < size(); i++) {
      Allele a = get(i);
      boolean found = false;
      for (int k = 0; k < locus2.size(); k++) { // alleles could be in any order
        Allele a2 = locus2.get(k);
        if (a.equals(a2)) {
          found = true;
        }
      }
      if (!found)
        return false;
    }
    return true;
  }
}
