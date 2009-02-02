package kingroup.partition.bitsets;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;

import javax.iox.LOG;
import javax.utilx.RandomSeed;
import javax.utilx.bitset.CompBitSet;
import java.util.*;

/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Oct 15, 2004, Time: 10:11:22 AM
 */
public class PartitionFactory {
  private int size_;
  private CompBitSet[] workArr_;
  private TreeSet idxSet_ = new TreeSet();
  private RandomSeed rand_ = RandomSeed.getInstance();
//   {
//      LOG.error(this, "//private RandomSeed rand_ = RandomSeed.getInstance()", "");
//   }
//   private Random rand_ = new Random(0);
  public PartitionFactory(int size) {
    size_ = size;
    workArr_ = new CompBitSet[size];
    for (int i = 0; i < size_; i++) {
      idxSet_.add(new Integer(i));
    }
  }
  public int size() {
    return size_;
  }
  public Partition makeRandom() {
    return makeRandomGroupSize();
//      final int OPTIONS = 2;
//      switch(rand_.nextInt(OPTIONS)) {
//         case 1: return makeRandomGroupNum();
//         default: return makeRandomGroupSize();
//      }
  }
  final public Partition makeRandomGroupSize() {
    int usedBit = size_ - 1;
    int numGroups = 0;
    while (usedBit >= 0) {
      int groupSize = rand_.nextInt(size_) + 1; // between 1 and univSize
      //LOG.trace(this, "groupSize=", groupSize);
      CompBitSet group = new CompBitSet();
      workArr_[numGroups++] = group;
      for (int i = 0; i < groupSize && usedBit >= 0; i++) {
        group.set(usedBit--);
        //LOG.trace(this, "group=", group.toString());
      }
    }
    if (numGroups == 1)
      return loadFromWorkArr(numGroups);

    // randomize
    for (int i = 0; i < size_; i++) {
      int groupA = 0;
      int groupB = 1;
      if (numGroups != 2)
        groupB = rand_.nextInt(numGroups);
      while (groupA == groupB) {
        groupB = rand_.nextInt(numGroups);
      }
      workArr_[groupA].swapFirstDifferentSetBit(workArr_[groupB]);
    }
    return loadFromWorkArr(numGroups);
  }
  final public Partition makeRandomGroupNum() {
    int numGroups = rand_.nextInt(size_) + 1; // between 1 and univSize
    for (int i = 0; i < numGroups; i++) {
      CompBitSet set = new CompBitSet();
      workArr_[i] = set;
    }

    // addLine each item to a random group
    for (int i = 0; i < size_; i++) {
      int group = rand_.nextInt(numGroups);
      workArr_[group].set(i);
    }
    return loadFromWorkArr(numGroups);
  }
  private Partition loadFromWorkArr(int numGroups) {
    //LOG.trace(this, "loadFromWorkArr");
    Partition res = new Partition(size_);
    for (int i = 0; i < numGroups; i++)
      res.add(workArr_[i]);
    return res;
  }
  public Partition makeByGroupSize(int groupSize) {
    int usedBit = size_ - 1;
    int numGroups = 0;
    while (usedBit >= 0) {
      CompBitSet group = new CompBitSet();
      workArr_[numGroups++] = group;
      for (int i = 0; i < groupSize && usedBit >= 0; i++) {
        group.set(usedBit--);
      }
    }
    return loadFromWorkArr(numGroups);
  }
  public Partition makeRandomByIncrement() {
    TreeSet pool = (TreeSet) idxSet_.clone();
    int numGroups = 0;
    CompBitSet group = null;
    while (!pool.isEmpty()) {
      // randomly select a group OR create a new one
      int idxGroup = rand_.nextInt(numGroups + 1);
      if (idxGroup == numGroups) {
        group = new CompBitSet();
        workArr_[numGroups++] = group;
      } else
        group = workArr_[idxGroup];

      // randomly select from the pool of available individuals
      int idxFromPool = rand_.nextInt(pool.size());
      Object obj = pool.toArray()[idxFromPool];
      int idxUsed = ((Integer) obj).intValue();
      group.set(idxUsed);
      pool.remove(new Integer(idxUsed));
    }
    return loadFromWorkArr(numGroups);
  }
  public Partition makeByMoving(Partition from, int x) {
    Partition res = new Partition(from, true);
//      LOG.trace(this, "res=", res);
    List allIdx = new ArrayList();
    Map map = new TreeMap(); // idx to bitset
    Partition test = new Partition(size_);
    for (Iterator itA = res.iterator(); itA.hasNext();) {
      CompBitSet setA = (CompBitSet) itA.next();
      int idx = setA.nextSetBit(0);
      while (idx != -1) {
        Integer key = new Integer(idx);
        allIdx.add(key);
        map.put(key, setA);
        test.add(setA);
        idx = setA.nextSetBit(idx + 1);
      }
    }
    if (test.size() <= 1) { // nohing to move OR only one group
      return res;
    }
    ArrayList toMove = new ArrayList();
    for (int i = 0; i < x; i++) {     // generateReplicates the list of elements to move
      int idx = rand_.nextInt(allIdx.size());
      toMove.add(allIdx.remove(idx));
    }
    for (int i = 0; i < toMove.size(); i++) {
      Integer fromIdx = (Integer) toMove.get(i);
      CompBitSet fromSet = (CompBitSet) map.get(fromIdx);
      CompBitSet toSet;
      for (; ;) {
        Integer toIdx = (Integer) allIdx.get(rand_.nextInt(allIdx.size()));
        toSet = (CompBitSet) map.get(toIdx);
        if (toSet.equals(fromSet))
          continue;
        break;
      }
      int idx = fromIdx.intValue();
      res.remove(fromSet);  // NOTE!!! you can't change bitst once it is inside a TreeSet
      fromSet.set(idx, false); // take out
      res.add(fromSet);  //
      if (fromSet.length() == 0) {
        if (!res.remove(fromSet))
          LOG.throwError(this, "empty set was not removed", "");
      }
      res.remove(toSet);
      toSet.setLocked(false);
      toSet.set(idx); // put in
      toSet.setLocked(true);
      res.add(toSet);
    }
    return res;
  }

  public static Partition makePartitionFrom(SysPop pop)
  {
    Partition res = new Partition(pop.size());
    SysPop[] arr = SysPopFactory.makeGroupsFrom(pop);

    for (int groupIdx = 0; groupIdx < arr.length; groupIdx++) {
      SysPop group = arr[groupIdx];
      CompBitSet set = new CompBitSet();
      for (int i = 0; i < group.size(); i++) {
        set.set(group.getIdIdx(i));
      }
      res.add(set);
    }
    return res;
  }
}
