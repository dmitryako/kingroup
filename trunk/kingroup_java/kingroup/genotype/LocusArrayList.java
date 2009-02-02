package kingroup.genotype;
import kingroup.KinGroupError;

import javax.iox.LOG;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 7/03/2005, Time: 17:47:54
 */
public class LocusArrayList {
  private Locus[] v;
  private int size_ = 0;
  private int capacity = 8;
  public LocusArrayList() {
    v = new Locus[capacity];
  }
  final public int size() {
    return size_;
  }
  final public Locus get(int idx) {
    return v[idx];
  }
  public boolean add(Locus val) {
    if (capacity == size_) {
      capacity += Math.max(2, capacity / 2);
      Locus[] newV = new Locus[capacity];
      System.arraycopy(v, 0, newV, 0, v.length);
      v = newV;
    }
    v[size_++] = val;
    return true;
  }
  public Locus set(int idx, Locus newVal) {
    if (idx >= size_) {
      String mssg = "idx >= size_;  " + idx + " >= " + size_;
      LOG.error(this, mssg, "");
      throw new KinGroupError(mssg);
    }
    Locus oldVal = v[idx];
    v[idx] = newVal;
    return oldVal;
  }
  public void limitSize(int maxSize) {
    if (size_ > maxSize)
      size_ = maxSize;
  }
}
