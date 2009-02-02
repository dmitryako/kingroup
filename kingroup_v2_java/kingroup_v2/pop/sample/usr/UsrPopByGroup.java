package kingroup_v2.pop.sample.usr;
import kingroup.KinGroupError;
import kingroup_v2.pop.UserGenotype;
import kingroup_v2.pop.UserLocus;

import javax.utilx.arrays.IntVec;
import java.util.Arrays;
import java.util.Comparator;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 10/10/2005, Time: 10:15:50
 */
public class UsrPopByGroup extends UsrPopSLOW {
  private int[] byGroup;
  private final UsrPopSLOW ref;    // PROXY pattern

  public UsrPopByGroup(UsrPopSLOW pop) {
    shallowCopyFrom(pop);
    setHasGroupId(true);
    ref = pop;
    Object[] arr = IntVec.makeIdxList(pop.size()).toArray();

    Arrays.sort(arr, new Comparator() {
      public int compare(Object o1, Object o2) {
        int i = ((Integer)o1).intValue();
        int i2 = ((Integer)o2).intValue();
        return ref.getGroupNum(i) - ref.getGroupNum(i2);
      }
    });
    byGroup = IntVec.makeIdxArr(pop.size());
    for (int i = 0; i < arr.length; i++) {
      byGroup[i] = ((Integer)arr[i]).intValue();
    }
  }


  public void addGenotype(UserGenotype g) {
    throw new KinGroupError("no setters in sortedByGroup");
  }
  public String getPatId(int i) {
    return ref.getPatId(byGroup[i]);
  }
  public String getMatId(int i) {
    return ref.getMatId(byGroup[i]);
  }
  public String getId(int i) {
    return ref.getId(byGroup[i]);
  }
  public String getGroupId(int i) {
    return ref.getGroupId(byGroup[i]);
  }
  public UserLocus getLocus(int i, int L) {
    return ref.getLocus(byGroup[i], L);
  }

}
