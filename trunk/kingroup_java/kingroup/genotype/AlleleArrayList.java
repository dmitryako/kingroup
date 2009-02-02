package kingroup.genotype;
import kingroup.KinGroupError;

import javax.iox.LOG;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 7/03/2005, Time: 16:33:14
 */
public class AlleleArrayList {
  private Allele[] v;
  private int size_ = 0;
  private int capacity = 2;
  public AlleleArrayList() {
    v = new Allele[capacity];
  }
//   public AlleleArrayList(int initialCapacity) {
//      capacity = initialCapacity;
//      v = new Allele[capacity];
//   }
//   public AlleleArrayList(AlleleArrayList from) {
//      this(from.size_);
//      System.arraycopy(from.v, 0, v, 0, from.size_);
//      size_ = from.size_;
//   }
  final public int size() {
    return size_;
  }
  final public Allele get(int idx) {
    return v[idx];
  }
  public boolean add(Allele val) {
    if (capacity == size_) {
      capacity += Math.max(2, capacity / 2);
      Allele[] newV = new Allele[capacity];
      System.arraycopy(v, 0, newV, 0, v.length);
      v = newV;
    }
    v[size_++] = val;
    return true;
  }
//   public String toString() {
//      return IntVec.toString(v, size_);
//   }
  public Allele set(int idx, Allele newVal) {
    if (idx >= size_) {
      String mssg = "idx >= size_;  " + idx + " >= " + size_;
      LOG.error(this, mssg, "");
      throw new KinGroupError(mssg);
    }
    Allele oldVal = v[idx];
    v[idx] = newVal;
    return oldVal;
  }
}
