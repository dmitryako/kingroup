package kingroup_v2.partition.simpson;
import javax.utilx.bitset.CompBitSet;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/05/2005, Time: 08:30:22
 */
public class MSAlgGroup extends CompBitSet {
  public MSAlgGroup() {
  }
  public MSAlgGroup(MSAlgGroup from) {
    this.or(from); // copy values
  }
}
