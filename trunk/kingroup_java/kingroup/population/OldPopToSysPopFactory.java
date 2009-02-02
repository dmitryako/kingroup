package kingroup.population;
import kingroup.KinGroupError;
import kingroup.genotype.Allele;
import kingroup.genotype.Genotype;
import kingroup.genotype.Locus;
import kingroup_v2.partition.simpson.SimpsPartition;
import kingroup_v2.partition.simpson.SimpsPartitionFactory;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.iox.LOG;
import javax.utilx.bitset.CompBitSet;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 29/11/2005, Time: 10:41:33
 */
public class OldPopToSysPopFactory
{
  public static CompBitSet makeOneGroup(OldPop pop) {
    SysPop sys = makePopFrom(pop);
    LOG.trace(null, "simps pop=\n", sys);
    SimpsPartitionFactory factory = new SimpsPartitionFactory(sys);
    SimpsPartition group = factory.makeOneGroup();
    return group.getGroupByIdIdx(0);
  }
  public static SysPop makePopFrom(OldPop pop) {
//    OldAlleleFreq freq = pop.getAlleleFreq();
//    SysAlleleFreq sysFreq = OldAlleleFreqFactory.makeFrom(freq);

    int popSize = pop.size();
    int numLoci = pop.getNumLoci();
    SysPop sysPop = new SysPop(popSize, numLoci);
//    sysPop.setFreq(sysFreq);

    for (int L = 0; L < numLoci; L++) {
      loadLocus(sysPop, L, pop);
    }
    loadGroups(sysPop, pop);
    sysPop.resetIdIdx();
    sysPop.setSize(pop.size());

    return sysPop;
  }

  private static void loadGroups(SysPop sysPop, OldPop pop)
  {
    HashMap<String, Integer> map = new HashMap<String, Integer>();
    int idx = 0;
    for (short i = 0; i < pop.size(); i++) {
      Genotype geno = pop.getGenotype(i);
      String groupId = geno.getGroupId();
      Integer val = map.get(groupId);
      if (val == null) {
        val = new Integer(idx++);
        map.put(groupId, val);
      }
      sysPop.setGroupId(i, val.intValue());
    }
  }

  public static void loadLocus(SysPop sysPop, int L, OldPop pop) {
    TreeMap mapAlleleIdToIdx = new TreeMap();
    for (short i = 0; i < pop.size(); i++) {
      Genotype geno = pop.getGenotype(i);
      Locus locus = geno.getLocus(L);
      if (locus.size() > Locus.DIPLOID)
        throw new KinGroupError("get.size() > Locus.DIPLOID");
      byte a = SysPop.NOT_SET;
      byte b = SysPop.NOT_SET;
      if (locus.size() == Locus.DIPLOID) {
        a = getIdx(locus.get(0), mapAlleleIdToIdx);
        b = getIdx(locus.get(1), mapAlleleIdToIdx);
      } else if (locus.size() == Locus.HAPLOID) {
        a = getIdx(locus.get(0), mapAlleleIdToIdx);
      }
      sysPop.setAllelePair(i, L, a, b);
    }
  }
  private static byte getIdx(Allele allele, TreeMap map) {
    Object obj = map.get(allele.getId());
    if (obj == null) {
      byte idx = (byte) map.size();
      map.put(allele.getId(), new Byte(idx));
      return idx;
    }
    return ((Byte) obj).byteValue();
  }
}
