package kingroup.genotype;
import kingroup.util.FastId;

/* Copyright (C) 2003-2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 */

// GenotypeGroup is an array of Genotype's
public class GenotypeGroup extends FastId {
  private Genotype[] v_ = new Genotype[0];
  public GenotypeGroup() {
    v_ = new Genotype[0];
  }
  public GenotypeGroup(int _size) {
    v_ = new Genotype[_size];
  }
  public GenotypeGroup(GenotypeGroup from) {
    this(from.size());
    copyFastIdFrom(from);
    Genotype.arraycopy(from.v_, v_, size());
  }
  // POST: genotypes are reused, not a deep copy
  final public GenotypeGroup duplicate() {
    return new GenotypeGroup(this);
  }
  // HIGHLY-USED
  final public Genotype getGenotype(int i) {
    return v_[i];
  }
  final public void addGenotype(Genotype a) {
    v_ = GenotypeArrays.add(a, v_);
  }
  final public int size() {
    return v_.length;
  }
  final public void set(int i, Genotype a) {
    v_[i] = a;
  }
  final public void removeGenotypes() {
    v_ = new Genotype[0];
  }
  final public void remove(int i) {
    v_ = GenotypeArrays.remove(i, v_);
  }
  final public void remove(Genotype g) {
    v_ = GenotypeArrays.remove(g, v_);
  }
  final public int find(Genotype geno) {
    return GenotypeArrays.find(geno, v_);
  }
  // toString
  public String toString() {
    StringBuffer res = new StringBuffer();
    res.append("GroupId=").append(getId());
    res.append("=\n");
    res.append(GenotypeArrays.toString(v_));
    return res.toString();
  }
  public int countUnique() {
    int res = 0;
    for (int i = 0; i < size(); i++) {
      Genotype geno = getGenotype(i);
      boolean unique = true;
      for (int k = i + 1; k < size(); k++) {
        Genotype geno2 = getGenotype(k);
        if (geno.equalsByAllelesTo(geno2)) {
          unique = false;
          break;
        }
      }
      if (unique)
        res++;
    }
    return res;
  }
}


