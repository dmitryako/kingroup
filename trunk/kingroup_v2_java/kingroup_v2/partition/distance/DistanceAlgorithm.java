package kingroup_v2.partition.distance;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PartitionPair;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Nov 9, 2004, Time: 3:40:14 PM
 */
abstract public class DistanceAlgorithm {
  abstract public int distance(Partition A, Partition B);
  public int calcEffectiveComlpexitySize(Partition A, Partition B) {
    if (A.equals(B))
      return 0;
    PartitionPair w = A.removeAllCommon(B);
    return Math.max(w.a.size(), w.b.size());
  }
}
