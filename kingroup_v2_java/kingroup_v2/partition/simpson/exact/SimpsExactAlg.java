package kingroup_v2.partition.simpson.exact;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.partition.PartitionAlg;
import kingroup_v2.partition.ms2.MS2AlgV2;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.iox.LOG;
import javax.utilx.bitset.CompBitSet;
import java.util.*;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 1/07/2005, Time: 16:00:53
 */
public class SimpsExactAlg extends PartitionAlg {
  protected final SysPop pop;
  private final SimpsExactAlgModel model;
  public SimpsExactAlg(SysPop pop, SimpsExactAlgModel model) {
    this.pop = pop;
    this.model = model;
    assert(pop.size() > 0);
    assert(pop.getNumLoci() > 0);
  }
  public SimpsPhenoGroup[] buildGroupSet(SimpsPhenotypeSet phenoSet) {
    ArrayList sibGroups = new ArrayList();
    final int START = 2;
    final int START2 = 1;
    for (int i = START; i < phenoSet.size(); i++) {
      for (int i2 = START2; i2 < i; i2++) {
        for (int i3 = 0; i3 < i2; i3++) { // ALL groups with 3 genotypes
          LOG.report(this, phenoSet);
          SimpsPhenoGroup phenoG3 = new SimpsPhenoGroup();
          phenoG3.set(i);
          phenoG3.set(i2);
          phenoG3.set(i3);
          phenoG3.setGroupSize(phenoSet.getPhenotypeSize(i) + phenoSet.getPhenotypeSize(i2) + phenoSet.getPhenotypeSize(i3));
          LOG.report(this, "phenoG3=" + phenoG3);
          CompBitSet geno = phenoSet.toGenotypes(phenoG3);
          LOG.report(this, "geno=" + geno);
          if (!model.getSibshipAlg().isSibGroupSLOW(pop, geno))
            continue;
          sibGroups.add(phenoG3);
          for (int i4 = 0; i4 < i3; i4++) { // ALL groups with 4 genotypes
            SimpsPhenoGroup phenoG4 = new SimpsPhenoGroup(phenoG3);
            phenoG4.set(i4);
            geno = phenoSet.toGenotypes(phenoG4);
            if (!model.getSibshipAlg().isSibGroupSLOW(pop, geno))
              continue;
            phenoG4.setGroupSize(phenoG3.getGroupSize() + phenoSet.getPhenotypeSize(i4));
            LOG.report(this, "phenoG4=" + phenoG4);
            sibGroups.add(phenoG4);
          }
        }
      }
    }
    SimpsPhenoGroup[] arr = new SimpsPhenoGroup[sibGroups.size()];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = (SimpsPhenoGroup) sibGroups.get(i);
    }
    sortByGroupSize(arr);
    return arr;
  }
  private void sortByGroupSize(SimpsPhenoGroup[] arr) {
    Arrays.sort(arr, new Comparator() { // SORT by descending group sizes
      public int compare(Object obj, Object obj2) {
        SimpsPhenoGroup g = (SimpsPhenoGroup) obj;
        SimpsPhenoGroup g2 = (SimpsPhenoGroup) obj2;
        return g2.getGroupSize() - g.getGroupSize();
      }
    });
  }
  public Partition partition() {
    SimpsPhenotypeSet phenos = new SimpsPhenotypeSet(pop);
    Object[] groups = buildGroupSet(phenos);
//      return greedyFind(groups, phenos);
    return uniqueFind(groups, phenos);
  }
  private Partition greedyFind(Object[] arr, SimpsPhenotypeSet phenos) {
    Partition res = new Partition();
    BitSet done = new BitSet();
    for (int i = 0; i < arr.length; i++) { // build
//         LOG.report(this, "done=" + done);
//         LOG.report(this, "res=" + res);
      SimpsPhenoGroup PHENOTYPE = (SimpsPhenoGroup) arr[i]; // NOTE!!! PHENOTYPE is a set, while genotypes could be the same
//         LOG.report(this, "arr["+i+"]=" + PHENOTYPE);
      CompBitSet geno = phenos.toGenotypes(PHENOTYPE);
//         LOG.report(this, "geno=" + geno);
      if (i == 0) {
        res.add(geno);
        done.or(geno); // remember geno in this group
        continue;
      }
      CompBitSet tmp = new CompBitSet(geno);
      tmp.and(done);
      if (tmp.cardinality() > 0)
        continue; // ignore, since at least one getGenotype has been already assigned
//         LOG.report(this, "geno=" + geno);
      res.add(geno);
      done.or(geno); // remember assigned
    }
//      LOG.report(this, "done=" + done);
//      LOG.report(this, "res=" + res);
    if (done.cardinality() == pop.size())
      return res;
    MS2AlgV2 ms2 = new MS2AlgV2(pop, model);
    ms2.setAssignedPool(done);
    Partition resMS2 = ms2.partition();
    for (Iterator it = resMS2.iterator(); it.hasNext();) {
      CompBitSet g = (CompBitSet) it.next();
      res.add(g);
    }
    return res;
  }
  private Partition uniqueFind(Object[] groups, SimpsPhenotypeSet phenos) {
    ArrayList parts = new ArrayList();
    Partition overlaps = new Partition();
    for (int firstIdx = 0; firstIdx < groups.length; firstIdx++) {
      Partition res = new Partition();
      BitSet done = new BitSet();
      for (int i = 0; i < groups.length; i++) { // build
//         LOG.report(this, "done=" + done);
//         LOG.report(this, "res=" + res);
        SimpsPhenoGroup PHENOTYPE = (SimpsPhenoGroup) groups[i]; // NOTE!!! PHENOTYPE is a set, while genotypes could be the same
//         LOG.report(this, "groups["+i+"]=" + PHENOTYPE);
        CompBitSet geno = phenos.toGenotypes(PHENOTYPE);
//         LOG.report(this, "geno=" + geno);
        if (i == firstIdx) {
          if (overlaps.contains(geno)) {
            firstIdx = groups.length; // also terminate the outer loop
            break;
          }
          res.add(geno);
          done.or(geno); // remember geno in this group
          overlaps.add(geno);
          continue;
        }
        CompBitSet tmp = new CompBitSet(geno);
        tmp.and(done);
        if (tmp.cardinality() > 0)
          continue; // ignore, since at least one getGenotype has been already assigned
//         LOG.report(this, "geno=" + geno);
        res.add(geno);
        done.or(geno); // remember assigned
        overlaps.add(geno);
      }
      parts.add(res);
    }
    Object[] sorted = parts.toArray();
    Arrays.sort(sorted, new Comparator() { // SORT by simpson index
      public int compare(Object obj, Object obj2) {
        Partition p = (Partition) obj;
        Partition p2 = (Partition) obj2;
        return p2.calcSimpsonIndex() - p.calcSimpsonIndex();
      }
    });
    Partition res = new Partition();

//      LOG.report(this, "done=" + done);
//      LOG.report(this, "res=" + res);
    if (res.cardinality() == pop.size())
      return res;
    MS2AlgV2 ms2 = new MS2AlgV2(pop, model);
    ms2.setAssignedPool(res.union());
    Partition resMS2 = ms2.partition();
    for (Iterator it = resMS2.iterator(); it.hasNext();) {
      CompBitSet g = (CompBitSet) it.next();
      res.add(g);
    }
    return res;
  }
}
