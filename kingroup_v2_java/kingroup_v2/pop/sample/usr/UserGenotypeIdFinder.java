package kingroup_v2.pop.sample.usr;
import javax.utilx.HashMapToIdx;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 22/09/2005, Time: 17:34:44
 */
public class UserGenotypeIdFinder {
  private final HashMapToIdx mapToIdx;
  public UserGenotypeIdFinder(UsrPopSLOW pop) {
    mapToIdx = new HashMapToIdx();
    int count = 0;
    for (int i = 0; i < pop.size(); i++) {
      String name = pop.getId(i);
      if (findIdIdx(name) != -1)
        continue;
      mapToIdx.put(name, count++);
    }
  }
  public int findIdIdx(String name) {
    return mapToIdx.getIdx(name);
  }
}

