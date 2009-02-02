package kingroup_v2.partition.distance;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PartitionPair;
import ledax.LedaWeightAssignment;

import javax.vecmathx.matrix.SquareIntegerMatrixByRows;
import java.util.BitSet;
import java.util.Iterator;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Oct 20, 2004, Time: 2:17:49 PM
 */
public class LitowDistance extends DistanceAlgorithm {
  public int distance(Partition A, Partition B) {
//      LOG.trace(this, "distance");
    if (A.equals(B))
      return 0;
    PartitionPair w = A.removeAllCommon(B);
//    LOG.trace(this, "w.a=", w.a);
//    LOG.trace(this, "w.b=", w.b);
    if (w.a.size() == 0 || w.b.size() == 0)
      return 0;
    SquareIntegerMatrixByRows cost = calcCostMatrix(w.a, w.b);
//    LOG.trace(this, "cost =\n", cost.toString());
    return new LedaWeightAssignment(cost).calcMinCost();
    //return new HungarianAlgorithm(cost).calcMinCost();
  }
  protected int calcElement(BitSet a, BitSet b) {
    BitSet copyA = (BitSet) a.clone();
    copyA.andNot(b);
    //LOG.trace(this, "A xor B =", copyA);
    return copyA.cardinality();
  }
  // makes cost matrix
  // A(i) * not(B(j))
  protected SquareIntegerMatrixByRows calcCostMatrix(Partition A, Partition B) {
    int n = Math.max(A.size(), B.size());
    SquareIntegerMatrixByRows res = new SquareIntegerMatrixByRows(n, true);
    int row = 0;    // as rows
    for (Iterator itA = A.iterator(); itA.hasNext(); row++) {
      BitSet setA = (BitSet) itA.next();
      //LOG.trace(this, "row set A =", setA);
      int col = 0;
      for (Iterator itB = B.iterator(); itB.hasNext(); col++) {
        BitSet setB = (BitSet) itB.next();
        //LOG.trace(this, "col set B =", setB);
        int v = calcElement(setA, setB);
//            LOG.trace(this, "M[row=" + row + ", col=" + col + "] = ", v);
        res.setElement(row, col, v);
      }
    }
    adjustForSquare(res, A, B);
    return res;
  }
  protected void adjustForSquare(SquareIntegerMatrixByRows res, Partition a, Partition b) {
    // when there are more Bs than As, nothing to do. All values are zero already
    // When more As than Bs:
    for (int col = b.size(); col < res.size(); col++) {
      Iterator itA = a.iterator();
      for (int row = 0; row < res.size(); row++) {
        BitSet setA = (BitSet) itA.next();
        res.setElement(row, col, setA.cardinality());
      }
    }
  }
}
