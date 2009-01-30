package javax.utilx.bitset;
import tsvlib.project.ProjectLogger;

import javax.utilx.RandomSeed;
import javax.utilx.arrays.IntVec;
import java.util.BitSet;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 6/06/2007, 16:25:53
 */
public class BitSetFactory
{
  private static ProjectLogger log = ProjectLogger.getLogger(BitSetFactory.class);
  public static BitSet makeRandom(int k, int n)
  {
    return makeRandom(0, k, n);
  }

  public static BitSet makeRandom(int startIdx, int k, int n)
  {
    log.debug("makeRandom(startIdx=", startIdx); log.debug("k=", k);  log.debug("n=", n);
    if (startIdx > 0)
      n = n - startIdx;
    BitSet res = new BitSet(n);
    int[] arr = IntVec.makeRandIdxArr(n);
    for (int i = 0; i < k; i++) {
      res.set(startIdx + arr[i]);
    }
    log.debug("res=", res);
    return res;
  }

  public static BitSet swapRandom(BitSet from, int n)
  {
    return swapRandom(0, from, n);
  }

  public static BitSet swapRandom(int startIdx, BitSet from, int n)
  {
    log.debug("swapRandom(from=", from);  log.debug("startIdx=", startIdx);  log.debug("n=", n);
    BitSet res = new BitSet();
    res.or(from); // copy
    RandomSeed rand = RandomSeed.getInstance();
    int idxDel = getRandomSetIdx(startIdx, res);    log.debug("idxDel=", idxDel);
    while (res.equals(from)) {
      int newIdx = rand.nextInt(n);                 log.debug("newIdx=", newIdx);
      if (newIdx < startIdx
//        ||  newIdx == idxDel   //  res.get(newIdx) covers this case
        || res.get(newIdx))
        continue;
      res.set(idxDel, false); // delete
      res.set(newIdx);
    }
    log.debug("res=", res);
    return res;
  }

  public static BitSet addRandom(int startIdx, BitSet from, int n)
  {
    log.debug("addRandom(from=", from);  log.debug("startIdx=", startIdx);  log.debug("n=", n);
    BitSet res = new BitSet();
    res.or(from); // copy
    RandomSeed rand = RandomSeed.getInstance();
    while (res.equals(from)) {
      int newIdx = rand.nextInt(n);                 log.debug("newIdx=", newIdx);
      if (newIdx < startIdx
        || res.get(newIdx))
        continue;
      res.set(newIdx);
    }
    log.debug("res=", res);
    return res;
  }

  public static BitSet delRandom(int startIdx, BitSet from)
  {
    log.debug("delRandom(from=", from);  log.debug("startIdx=", startIdx);
    BitSet res = new BitSet();
    res.or(from); // copy
    int idxDel = getRandomSetIdx(startIdx, res);    log.debug("idxDel=", idxDel);
    res.set(idxDel, false);                         log.debug("res=", res);
    return res;
  }

  public static int getRandomSetIdx(BitSet set) {
    return getRandomSetIdx(0, set);
  }
  public static BitSet getRandomSet(int k, BitSet set) {
    BitSet res = new BitSet();
    res.or(set);
    if (res.cardinality() <= k)
      return res;

    while (res.cardinality() > k) {
      res = delRandom(0, res);
    }
    return res;
  }
  public static int getRandomSetIdx(int startIdx, BitSet set) {
    log.debug("getRandomSetIdx(from=", set);
    int c = set.cardinality();     log.debug("|set|=", c);
    if (startIdx > 0) {
      BitSet fixed = set.get(0, startIdx); log.debug("fixed=", fixed);
      c = c - fixed.cardinality();         log.debug("c-|fixed|=", c);
    }
    int randCount = RandomSeed.getInstance().nextInt(c);  log.debug("randCount=", randCount);
    int res = 0;
    for (int count = 0; count < c; count++, res++)
    {
      res = set.nextSetBit(res);
      if (res < startIdx)
        count--;     // ignore bits before startIdx
      if (res == -1) {
        RuntimeException e = new RuntimeException("error");
        log.error("Bug in BitSetFactory.getRandom", e);
        throw e;
      }
      if (count == randCount)
        return res;
    }
    return -1;
  }
}
