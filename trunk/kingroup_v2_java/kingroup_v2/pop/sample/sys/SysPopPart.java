package kingroup_v2.pop.sample.sys;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PartitionGroupFactory;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 7/10/2005, Time: 15:49:28
 */
public class SysPopPart extends SysPop {
  private int[] groups;

  public SysPopPart(Partition part, SysPop pop) {
    shallowCopyFrom(pop);    
    groups = PartitionGroupFactory.getGroups(part);
  }

  public int getGroupId(int i) {
    return groups[i];
  }
}
