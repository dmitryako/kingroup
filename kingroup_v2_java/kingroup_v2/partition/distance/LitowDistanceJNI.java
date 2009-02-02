package kingroup_v2.partition.distance;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PartitionPair;
import ledax.LedaWeightAssignmentJNI;

import javax.iox.LOG;
import javax.vecmathx.matrix.SquareIntegerMatrix;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Nov 11, 2004, Time: 1:36:15 PM
 */
public class LitowDistanceJNI extends LitowDistance {
  public int distance(Partition A, Partition B) {
    LOG.trace(this, "distance");
    if (A.equals(B))
      return 0;
    PartitionPair w = A.removeAllCommon(B);
    SquareIntegerMatrix cost = calcCostMatrix(w.a, w.b);
//      LOG.trace(this, "cost =\n", cost.toString());
    return new LedaWeightAssignmentJNI(cost.getArray()).calcMinCost();
  }
}
