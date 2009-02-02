package kingroup_v2.partition.simpson.exact;

import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;

import javax.langx.SysProp;
import javax.iox.LOG;
import javax.utilx.bitset.CompBitSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 1/07/2005, Time: 16:11:54
 */
public class SimpsPhenotypeSet {
  private final SimpsPhenoToGenoMap[] phenos;  // phenotypes is a set of genotypes
  public SimpsPhenotypeSet(SysPop from) {
    SysPop pop = new SysPop(from.size(), from.getNumLoci());
    ArrayList tmp = new ArrayList();
    pop.setSize((short) 0);
    for (int i = 0; i < from.size(); i++) {
//      LOG.report(this, from.toString(i));
      int idx = SysPopFactory.findFirstIdxSLOW(pop, from, i);
      SimpsPhenoToGenoMap g;
      if (idx == -1) {
        pop.addGenotype(i, from);
        g = new SimpsPhenoToGenoMap();
        tmp.add(g);
//            phenos[pop.size()-1] = g;
      } else {
        g = (SimpsPhenoToGenoMap) tmp.get(idx);
//            g = phenos[idx];
      }
//         g.setGroupSize(g.getGroupSize()+1);
      g.set(i);
      LOG.report(this, g.toString());
    }
    phenos = new SimpsPhenoToGenoMap[tmp.size()];
    for (int i = 0; i < phenos.length; i++) {
      phenos[i] = (SimpsPhenoToGenoMap) tmp.get(i);
    }
    sortByCardinality(phenos);
    LOG.report(this, "from=\n" + from.toString());
//      LOG.report(this, "pop=\n" + pop.toString());
  }
  public String toString() {
    StringBuffer res = new StringBuffer();
    for (short i = 0; i < phenos.length; i++) {
      res.append("[" + i + "] ");
      res.append(phenos[i]);
      res.append(SysProp.EOL);
    }
    return res.toString();
  }
  public void sortByCardinality(SimpsPhenoToGenoMap[] arr) {
    Arrays.sort(arr, new Comparator() { // SORT by descending group sizes
      public int compare(Object obj, Object obj2) {
        SimpsPhenoToGenoMap g = (SimpsPhenoToGenoMap) obj;
        SimpsPhenoToGenoMap g2 = (SimpsPhenoToGenoMap) obj2;
        return g2.cardinality() - g.cardinality();
      }
    });
  }
  public int getPhenotypeSize(int i) {
    assert(i < phenos.length);
    if (i >= phenos.length) {
      throw new RuntimeException("i >= size()");
    }
    return phenos[i].cardinality();
  }
  public CompBitSet toGenotypes(SimpsPhenoGroup g) {
    CompBitSet res = new CompBitSet();
    for (int setIdx = -1; ;) {
      setIdx = g.nextSetBit(setIdx + 1);
      if (setIdx == -1)
        break;
      res.or(phenos[setIdx]);
    }
    return res;
  }
  public int size() {
    return phenos.length;
  }
  public SimpsPhenoGroup mergeTwoLargestPhenotypes(BitSet usedPhenos) {
    LOG.report(this, "usedPhenos=" + usedPhenos);
    SimpsPhenoGroup res = new SimpsPhenoGroup();
    int idx = usedPhenos.nextClearBit(0);
    if (idx >= size())
      return null; // not even one
    res.set(idx);
    idx = usedPhenos.nextClearBit(idx + 1);
    if (idx < size())
      res.set(idx);
    LOG.report(this, "res=" + res);
    return res;
  }

//   public SimpsPhenoToGenoMap getPhenotype(short idx) {
//      return phenos[idx];
//   }
}
