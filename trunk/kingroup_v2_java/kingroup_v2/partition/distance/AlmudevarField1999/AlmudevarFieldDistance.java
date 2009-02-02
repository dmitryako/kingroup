package kingroup_v2.partition.distance.AlmudevarField1999;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PartitionPair;
import kingroup_v2.partition.distance.DistanceAlgorithm;

import javax.iox.LOG;
import javax.utilx.pair.BitSetPair;
import java.util.BitSet;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Oct 14, 2004, Time: 12:16:11 PM
 */
public class AlmudevarFieldDistance extends DistanceAlgorithm {
  private double depth_ = 0;
  static private final double MAX_DEPTH = Math.pow(2, 10);
  // A.Almudevar and C.Field
  // "Estimation of single generation sibling relationships based on DNA markers",
  // J.Agric.,Biol., Env.Stat, 4 (1999), 136-165
  //p164
//   final public int distance(PartitionPair pair) {
//      return distance(pair.getA(), pair.getB());
//   }
  final public int distance(Partition A, Partition B) {
    depth_ = 0;
    return privateDistance(A, B);
  }
  final private int privateDistance(Partition A, Partition B) {
    depth_++;
    //LOG.trace(this, "distance(depth=", Math.log(depth_));
    if (depth_ > MAX_DEPTH) {
      LOG.error(this, "exceeded max depth=", "" + MAX_DEPTH);
      depth_--;
      return -1;
    }
    if (A.equals(B)) {
      depth_--;
      return 0;
    }
    PartitionPair w = A.removeAllCommon(B);
    BitSetPair c = w.a.findAllDifferences(w.b);
    if (c == null)
      return 0;
    int distJ = calcDistance(c.a, A, B);
    int distC = calcDistance(c.b, A, B);
    depth_--;
    return Math.min(distC, distJ);
  }
  private int calcDistance(BitSet set
    , Partition a
    , Partition b) {
    Partition A = a.subtract(set);
    Partition B = b.subtract(set);
    int dist = privateDistance(A, B);
    if (dist == -1)
      return dist;
    dist += set.cardinality();
    return dist;
  }
}
