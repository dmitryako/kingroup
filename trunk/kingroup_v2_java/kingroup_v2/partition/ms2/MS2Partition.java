package kingroup_v2.partition.ms2;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.partition.ms.MSAlgPartition;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 26/06/2005, Time: 12:15:04
 */
public class MS2Partition extends MSAlgPartition {
  public MS2Partition(int universeSize) {
    super(universeSize);
  }
  public MS2Partition(Partition from, boolean deepCopy) {
    super(from, deepCopy);
  }
}
