package kingroup.partition;
import javax.utilx.IntTreeSet;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 4/03/2005, Time: 13:32:28
 */
public class PartitionV2Pool extends IntTreeSet {
  public PartitionV2Pool(int size) {
    for (int i = 0; i < size; i++) {
      addInt(i);
    }
  }
}
