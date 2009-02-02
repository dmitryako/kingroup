package kingroup_v2.pop.allele.freq;
import javax.langx.SysProp;
import javax.utilx.arrays.StrVec;
import java.util.ArrayList;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 16/09/2005, Time: 08:44:34
 */
public class UsrAlleleFreq {
  private final ArrayList<Locus> loci;
  private final String[] locusIds;
  private int maxNumAlleles;
  public UsrAlleleFreq(String[] locusIds) {
    this.locusIds = locusIds;
    loci = new ArrayList<Locus>();
    maxNumAlleles = 0;
    for (int i = 0; i < locusIds.length; i++) {
      loci.add(new Locus());
    }
  }
  public void add(int locusIdx, AlleleFreqPair pair) {
    Locus locus = loci.get(locusIdx);
    locus.add(pair);
    if (maxNumAlleles < locus.size()) {
      maxNumAlleles = locus.size();
    }
  }
  public AlleleFreqPair get(int locusIdx, int alleleIdx) {
    Locus locus = loci.get(locusIdx);
    if (alleleIdx >= locus.size()) {
      return null;
    }
    return locus.get(alleleIdx);
  }
  public String[] getLocusIds() {
    return locusIds;
  }
  public int getNumLoci() {
    return Math.min(locusIds.length, loci.size());
  }
  public int getMaxNumAlleles() {
    return maxNumAlleles;
  }
  public String getLocusId(int i) {
    return locusIds[i];
  }
  public int getLocusSize(int locusIdx) {
    Locus locus = loci.get(locusIdx);
    return locus.size();
  }

  public String toString()
  {
    StringBuffer buff = new StringBuffer();
    int nLoci = getNumLoci();
    buff.append(StrVec.toCSV(this.locusIds)).append(SysProp.EOL);
    for (int L = 0; L < nLoci; L++) {
      buff.append("L="+L+": ");
      int locusSize = getLocusSize(L);
      for (int i = 0; i < locusSize; i++) {
        AlleleFreqPair allele = get(L, i);
        buff.append(allele.toString("=")).append(", ");
      }
      buff.append(SysProp.EOL);
    }
    return buff.toString();
  }

  private class Locus extends ArrayList<AlleleFreqPair> {
  }
}
