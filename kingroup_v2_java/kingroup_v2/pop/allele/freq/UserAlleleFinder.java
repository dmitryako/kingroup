package kingroup_v2.pop.allele.freq;
import javax.utilx.HashMapToIdx;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 20/09/2005, Time: 10:59:24
 */
public class UserAlleleFinder {
//  private final UsrAlleleFreq freq;
  private final HashMapToIdx[] mapToIdx;
  public UserAlleleFinder(UsrAlleleFreq freq) {
//    this.freq = freq;
    int nLoci = freq.getNumLoci();
    mapToIdx = new HashMapToIdx[nLoci];
    for (int L = 0; L < nLoci; L++) {
      mapToIdx[L] = new HashMapToIdx();
      int locusSize = freq.getLocusSize(L);
      for (int a = 0; a < locusSize; a++) {
        String name = freq.get(L, a).getName();
        if (mapToIdx[L].getIdx(name) == -1)
          mapToIdx[L].put(name, a);
      }
    }
  }
  public int findAlleleIdx(int locusIdx, String name) {
    return mapToIdx[locusIdx].getIdx(name);
  }
}
