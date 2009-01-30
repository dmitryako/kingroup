package javax.utilx.arrays;
import javax.utilx.arrays.vec.Vec;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 4/03/2005, Time: 15:32:38
 */
public class DoubleArrayList {
  private double[] v;
  private int size_ = 0;
  private int capacity = 10;
  public DoubleArrayList() {
    v = new double[capacity];
  }
  public DoubleArrayList(int initialCapacity) {
    capacity = initialCapacity;
    v = new double[capacity];
  }
  public DoubleArrayList(DoubleArrayList from) {
    this(from.size_);
    System.arraycopy(v, 0, from.v, 0, from.size_);
  }
  final public int size() {
    return size_;
  }
  final public double last() {
    return v[size_ - 1];
  }
  final public double first() {
    return v[0];
  }
  final public double get(int idx) {
    return v[idx];
  }
  public boolean add(double val) {
    if (capacity == size_) {
      capacity += Math.max(2, capacity / 2);
      double[] newV = new double[capacity];
      System.arraycopy(v, 0, newV, 0, v.length);
      v = newV;
    }
    v[size_++] = val;
    return true;
  }
  final public int maxIdx() {
    return Vec.maxIdx(v, size_);
  }
  public String toString() {
    return Vec.toString(v, size_);
  }
  public double[] toArray() {
    double[] res = new double[size_];
    System.arraycopy(v, 0, res, 0, size_);
    return res;
  }
}
