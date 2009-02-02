package kingroup_v2.pop.sample.usr;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PartitionGroupFactory;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 8/10/2005, Time: 16:53:33
 */
public class UsrPopPart extends UsrPopSLOW {
  private int[] groups;

  public UsrPopPart(Partition part, UsrPopSLOW pop) {
    shallowCopyFrom(pop);
    groups = PartitionGroupFactory.getGroups(part);
  }
  public boolean getHasGroupId() {
    return true;
  }
  public String getGroupId(int i) {
    return Integer.toString(groups[i]);
  }
  public int getGroupNum(int i) {
    return groups[i];
  }
}
