package kingroup.partition.bitsets;
import javax.utilx.bitset.CompBitSet;
import javax.utilx.pair.BitSetPair;
import java.util.*;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Nov 5, 2004, Time: 2:40:04 PM
 */
public class Partition  {
  private int sampleSize = 0;
  private TreeSet<CompBitSet> tree;

  public Partition() {
    init();
  }
  public Partition(int universeSize) {
    this.sampleSize = universeSize;
    init();
  }
  private void init(){
    tree = new TreeSet<CompBitSet>();
  }
  public Object[] sortBySize() {
    Object[] res = tree.toArray();
    Arrays.sort(res, new Comparator() {
      public int compare(Object o1, Object o2) {
        BitSet g1 = (BitSet) o1;
        BitSet g2 = (BitSet) o2;
        return g2.cardinality() - g1.cardinality();
      }
    });
    return res;
  }
  public Partition(Partition from, boolean deepCopy) {
    super();
    sampleSize = from.getSampleSize();
    if (deepCopy)
      deepCopyFrom(from);
    else
      shallowCopyFrom(from);
  }
  public int getSampleSize() {
    return sampleSize;
  }
  private void deepCopyFrom(Partition from) {
    for (Iterator itA = from.iterator(); itA.hasNext();) {
      CompBitSet set = (CompBitSet) itA.next();
      add(new CompBitSet(set));
    }
  }
  private void shallowCopyFrom(Partition from) {
    for (Iterator itA = from.iterator(); itA.hasNext();) {
      CompBitSet set = (CompBitSet) itA.next();
      add(set);
    }
  }
//  public boolean add(Object o) {
//    LOG.throwError(this, "use addLine(ComparableBitSet) instead", "");
//    return false;
//  }
  public boolean add(CompBitSet set) {
    set.setLocked(true);
    return tree.add(set);
  }
  public boolean remove(CompBitSet set) {
    set.setLocked(false);
    return tree.remove(set);
  }
  // collection of BitSets
  public PartitionPair removeAllCommon(Partition part) {
    Partition a = new Partition(this, false);
    Partition b = new Partition(part, false);
    a.removeAll(part);
    b.removeAll(this);
    return new PartitionPair(a, b);
  }

  private void removeAll(Partition part)
  {
    tree.removeAll(part.tree);
  }

  public BitSetPair findAllDifferences(Partition part) {
    Iterator itA = tree.iterator();
    if (!itA.hasNext())
      return null;
    BitSet setA = (BitSet) itA.next();
    Iterator it = part.iterator();
    while (it.hasNext()) {
      BitSet setB = (BitSet) it.next();
      if (!setA.intersects(setB)) // must have at least something in common
        continue;
      BitSet andSet = (BitSet) setA.clone();
      andSet.and(setB); // this is to findFirstIdxSLOW "j"
      BitSet xorSet = (BitSet) setA.clone();
      xorSet.xor(setB); // this is to findFirstIdxSLOW H_delta_H
      return new BitSetPair(andSet, xorSet);
    }
    return null;
  }

  public Iterator iterator()
  {
    return tree.iterator();
  }

  public Partition subtract(BitSet set) {
    CompBitSet compl = (CompBitSet) set.clone();
    assert(sampleSize > 0);
    if (sampleSize == 0) {
      throw new RuntimeException("subtract() can only used with a valid sampleSize ");
    }
    compl.flip(0, sampleSize); // complement
    Partition res = new Partition(sampleSize);
    for (Iterator it = tree.iterator(); it.hasNext();) {
      CompBitSet setA = (CompBitSet) it.next();
      CompBitSet newSet = (CompBitSet) setA.clone();
      newSet.and(compl);
      if (!newSet.isEmpty())
        res.add(newSet);
    }
    return res;
  }
  public int calcSimpsonIndex() {
    int sum = 0;
    for (Iterator it = tree.iterator(); it.hasNext();) {
      BitSet set = (BitSet) it.next();
      int nj = set.cardinality();
      sum += nj * (nj - 1);
    }
    return sum;
  }
  public int cardinality() {
    int sum = 0;
    for (Iterator it = tree.iterator(); it.hasNext();) {
      BitSet set = (BitSet) it.next();
      int nj = set.cardinality();
      sum += nj;
    }
    return sum;
  }
  public BitSet union() {
    BitSet res = new BitSet();
    for (Iterator it = tree.iterator(); it.hasNext();) {
      BitSet set = (BitSet) it.next();
      res.or(set);
    }
    return res;
  }

  public void setSampleSize(int sampleSize) {
    this.sampleSize = sampleSize;
  }

  final public int size()
  {
    return tree.size();
  }

  public boolean contains(CompBitSet set)
  {
    return tree.contains(set);
  }
}
