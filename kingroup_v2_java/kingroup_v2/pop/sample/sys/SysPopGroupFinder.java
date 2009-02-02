package kingroup_v2.pop.sample.sys;
import tsvlib.project.ProjectLogger;

import javax.utilx.MapIntToIdx;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/09/2005, Time: 11:00:34
 */
public class SysPopGroupFinder {
  private static ProjectLogger log = ProjectLogger.getLogger(SysPopGroupFinder.class.getName());
  private final MapIntToIdx mapToIdx;
  public SysPopGroupFinder(SysPop pop) {
    mapToIdx = new MapIntToIdx();
    for (int i = 0; i < pop.size(); i++) {
      int key = pop.getGroupId(i);
      int count = mapToIdx.getIdx(key);
      if (count == -1)  // not there
        mapToIdx.put(key, 1); // init to 1
      else
        mapToIdx.put(key, ++count);
    }
  }
  public int getGroupSize(int id) {
    return mapToIdx.getIdx(id);
  }
  public int size() {
    return mapToIdx.size();
  }
  public String toString() {
    return mapToIdx.toString();
  }
}
