package kingroup_v2.pop.sample.usr;
import javax.utilx.HashMapToIdx;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/09/2005, Time: 09:33:55
 */
public class UserPopGroupFinder {
  private final HashMapToIdx mapToIdx;
  public UserPopGroupFinder(UsrPopSLOW pop) {
    mapToIdx = new HashMapToIdx();
    int count = 0;
    for (int i = 0; i < pop.size(); i++) {
      String name = pop.getGroupId(i);
      if (getGroupIdx(name) != -1)
        continue;
      mapToIdx.put(name, count++);
    }
  }
  public int getGroupIdx(String name) {
    return mapToIdx.getIdx(name);
  }
  public int size() {
    return mapToIdx.size();
  }
}
