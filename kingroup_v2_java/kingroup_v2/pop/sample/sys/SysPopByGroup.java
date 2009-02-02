package kingroup_v2.pop.sample.sys;
import kingroup.KinGroupError;

import javax.utilx.arrays.IntVec;
import java.util.Arrays;
import java.util.Comparator;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 8/10/2005, Time: 18:22:46
 */
public class SysPopByGroup  extends SysPop {
  private int[] byGroup;
  private final SysPop ref;    // PROXY pattern

  public SysPopByGroup(SysPop pop) {
    shallowCopyFrom(pop);
    ref = pop;
    Object[] arr = IntVec.makeIdxList(pop.size()).toArray();

    Arrays.sort(arr, new Comparator() {
      public int compare(Object o1, Object o2) {
        int i = ((Integer)o1).intValue();
        int i2 = ((Integer)o2).intValue();
        return ref.getGroupId(i) - ref.getGroupId(i2);
      }
    });
    byGroup = IntVec.makeIdxArr(pop.size());
    for (int i = 0; i < arr.length; i++) {
      byGroup[i] = ((Integer)arr[i]).intValue();
    }
  }

  public int getAllele(int idIdx, int locusIdx, int slotIdx) {
    return ref.getAllele(byGroup[idIdx], locusIdx, slotIdx);
  }
  public int getMatAllele(int idIdx, int locusIdx, int slotIdx) {
    return ref.getMatAllele(byGroup[idIdx], locusIdx, slotIdx);
  }
  public int getPatAllele(int idIdx, int locusIdx, int slotIdx) {
    return ref.getPatAllele(byGroup[idIdx], locusIdx, slotIdx);
  }
  public String toString(int i) {    return ref.toString(byGroup[i]);  }
  public int getMatId(int idx) {    return ref.getMatId(byGroup[idx]);  }
  public int getPatId(int idx) {    return ref.getPatId(byGroup[idx]);  }
  public int getGroupId(int idx) {    return ref.getGroupId(byGroup[idx]);  }
  public int getIdIdx(int idx) {    return ref.getIdIdx(byGroup[idx]);  }


  public void setAllele(int idIdx, int locusIdx, int slotId, int a) {
    throw new KinGroupError("no setters in sortedByGroup");
  }
  public void setTwoAlleles(int idIdx, int locusIdx, int a, int a2) {
    throw new KinGroupError("no setters in sortedByGroup");
  }
  public void setMatId(int idx, int mid) {
    throw new KinGroupError("no setters in sortedByGroup");
  }
  public void setPatId(int idx, int pid) {
    throw new KinGroupError("no setters in sortedByGroup");
  }
  public void setGroupId(int idx, int groupid) {
    throw new KinGroupError("no setters in sortedByGroup");
  }
  public void addGenotype(int fromIdx, SysPop from) {
    throw new KinGroupError("no setters in sortedByGroup");
  }
  public void setId(int idx, int id) {
    throw new KinGroupError("no setters in sortedByGroup");
  }
}
