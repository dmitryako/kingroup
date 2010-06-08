package kingroup.partition;
import kingroup.genetics.KinshipRatioMtrxV1;

import javax.utilx.arrays.DoubleArrayList;
import javax.utilx.arrays.IntArrayList;
import javax.utilx.pair.Int2;
import java.util.Iterator;
import java.util.LinkedList;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 4/03/2005, Time: 15:17:04
 */
public class PartitionV2DescRatioPairs extends LinkedList {
  public Int2 removeFirstPair() {
    return (Int2) removeFirst();
  }
  public PartitionV2DescRatioPairs(KinshipRatioMtrxV1 pr) {
    PartitionV2Pool pool = new PartitionV2Pool(pr.getGenotypeData().size());
    while (pool.size() > 1) {
      Int2 pair = buildDescRatioList(pool, pr);
      add(pair);
      pool.removeInt(pair.a);
      pool.removeInt(pair.b);
    }
//      LOG.trace(this, "All pairs=", this);
  }
  protected Int2 buildDescRatioList(PartitionV2Pool pool, KinshipRatioMtrxV1 pr) {
    if (pool.size() == 2)
      return new Int2(pool.firstInt(), pool.lastInt());
    int n = pool.size();
    int arrSize = n * (n - 1) / 2;
    DoubleArrayList arr = new DoubleArrayList(arrSize);
    IntArrayList C = new IntArrayList(arrSize);
    IntArrayList R = new IntArrayList(arrSize);
    for (Iterator it = pool.iterator(); it.hasNext();) {
      int r = ((Integer) it.next()).intValue();
      for (Iterator it2 = pool.iterator(); it2.hasNext();) {
        int c = ((Integer) it2.next()).intValue();
        if (c >= r)
          break;
        arr.add(pr.getLog(c, r));
        C.add(c);
        R.add(r);
      }
    }
    int idx = arr.maxIdx();
    Int2 res = new Int2(C.get(idx), R.get(idx));
//      LOG.trace(this, "arr=", arr);
//      LOG.trace(this, "max=arr[" + idx + "]=", arr.get(idx));
//      LOG.trace(this, "pair=", res);
    return res;
  }
  public String toString() {
    return super.toString();
  }
}
