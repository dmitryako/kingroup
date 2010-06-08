package kingroup.algorithm.window;
import tsvlib.project.ProjectLogger;

import javax.utilx.bitset.CompBitSet;
import javax.utilx.pair.Int2;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 1/06/2005, Time: 09:15:42
 */
public class AlgAccessViaPairs extends AlgWinAccessOrder {
  private static ProjectLogger log = ProjectLogger.getLogger(AlgAccessViaPairs.class.getName());
//   int dbgCount = 0;
  public static final int NOT_SET = -1;
  private final PairArray pairs;
  private int currPairIdx = NOT_SET;
  public AlgAccessViaPairs(int size, PairArray pairs) {
    super(size);
    this.pairs = pairs;
    pool = new CompBitSet();
    pool.set(0, size, true);
  }
  public boolean hasNext() {
    return (pool.cardinality() > 0 || currPairIdx != NOT_SET);
  }
  public int nextIdx() {
    int res = currPairIdx;
    if (currPairIdx == NOT_SET) {
      Int2 pair = (Int2)pairs.removeFirstPair();
      while (pair != null && !pool.get(pair.a) && !pool.get(pair.b)) {
        pair = (Int2)pairs.removeFirstPair();
      }
      if (pair == null)
        return NOT_SET;
      res = pair.a;
      currPairIdx = pair.b;
//      log.info("currPairIdx == NOT_SET\n" + pair);
    } else {
      res = currPairIdx;
      currPairIdx = NOT_SET;
    }
    if (pool.get(res)) {
      pool.set(res, false);
//         dbgCount--;
//      log.info("\nres=" + res + ", currPairIdx=" + currPairIdx);
      return res;
    }
    if (res == NOT_SET && pool.cardinality() == 0)
      return NOT_SET;
    return nextIdx();
  }
}
