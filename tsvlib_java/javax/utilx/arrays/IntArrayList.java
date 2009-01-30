package javax.utilx.arrays;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 4/03/2005, Time: 11:10:23
 *
 * @see java.util.ArrayList
 */
public class IntArrayList {
  private int[] v;
  private int size_ = 0;
  private int capacity = 10;
  public IntArrayList() {
    v = new int[capacity];
  }
  public IntArrayList(int initialCapacity) {
    capacity = initialCapacity;
    v = new int[capacity];
  }
  public IntArrayList(IntArrayList from) {
    this(from.size_);
    System.arraycopy(from.v, 0, v, 0, from.size_);
    size_ = from.size_;
  }
  public boolean hasInt(int val) {
    return IntVec.has(v, size_, val);
  }
  final public int size() {
    return size_;
  }
  final public int get(int idx) {
    return v[idx];
  }
  public boolean add(int val) {
    if (capacity == size_) {
      capacity += Math.max(2, capacity / 2);
      int[] newV = new int[capacity];
      System.arraycopy(v, 0, newV, 0, v.length);
      v = newV;
    }
    v[size_++] = val;
    return true;
  }
  public String toString() {
    return IntVec.toString(v, size_);
  }
  final public int maxIdx() {
    return IntVec.maxIdx(v, size_);
  }
  final public int minIdx() {
    return IntVec.minIdx(v, size_);
  }
  public int[] toArray() {
    int[] res = new int[size_];
    System.arraycopy(v, 0, res, 0, size_);
    return res;
  }
}
