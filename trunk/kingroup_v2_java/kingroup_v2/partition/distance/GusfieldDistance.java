package kingroup_v2.partition.distance;
import kingroup.partition.bitsets.Partition;
import ledax.LedaWeightAssignment;

import javax.vecmathx.matrix.SquareIntegerMatrixByRows;
import java.util.BitSet;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Jan 12, 2005, Time: 12:57:21 PM
 */
public class GusfieldDistance extends LitowDistance {
  // REF: Dan Gusfield, Inform Process Lett 82 (2002) 159-164
  public int distance(Partition A, Partition B) {
    if (A.equals(B))
      return 0;
    SquareIntegerMatrixByRows cost = calcCostMatrix(A, B);
//      LOG.trace(this, "cost =\n", cost.toString());
    int maxCost = new LedaWeightAssignment(cost).calcMaxCost();
//      LOG.trace(this, "max cost =\n", maxCost);
//      LOG.trace(this, "N =\n", A.getUniverseSize());
    return A.getSampleSize() - maxCost;
  }
  protected int calcElement(BitSet a, BitSet b) {
    BitSet copyA = (BitSet) a.clone();
//      LOG.trace(this, "A =", a);
//      LOG.trace(this, "A =", copyA);
//      LOG.trace(this, "B =", b);
    copyA.and(b);
//      LOG.trace(this, "A and B =", copyA);
    return copyA.cardinality();
  }
  protected void adjustForSquare(SquareIntegerMatrixByRows res, Partition a, Partition b) {
    // no adjustement is needed
  }
}

