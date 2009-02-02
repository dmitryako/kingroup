package kingroup_v2.partition.simpson.exact;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.iox.LOG;
import javax.utilx.arrays.BooleanArrays;
import javax.utilx.bitset.CompBitSet;
import java.util.ArrayList;
import java.util.BitSet;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 6/07/2005, Time: 15:42:45
 */
public class SimpsBKAlg extends SimpsExactAlg {
  public SimpsBKAlg(SysPop pop, SimpsExactAlgModel model) {
    super(pop, model);
  }
  public Partition partition() {
    SimpsPhenotypeSet phenoSet = new SimpsPhenotypeSet(pop);
    LOG.report(this, "phenoSet=\n" + phenoSet);
    SimpsPhenoGroup[] groups = buildGroupSet(phenoSet);
    LOG.report(this, "groups=\n" + SimpsPhenoGroup.toString(groups));
    int n = groups.length;
    boolean[][] connected = new boolean[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < i; j++) {
        BitSet pI = (BitSet) groups[i];
        BitSet pJ = (BitSet) groups[j];
        BitSet tmp = new BitSet();
        tmp.or(pI);
        tmp.and(pJ);
        boolean b = (tmp.cardinality() == 0);
        connected[i][j] = b;
        connected[j][i] = b;
      }
      connected[i][i] = true;
    }
    LOG.report(this, "connected=\n" + BooleanArrays.toString(connected, n));
    BronKerboschAlg457 alg = new BronKerboschAlg457();
    ArrayList bk = alg.run(connected, n);
    return findBestPartition(bk, groups, phenoSet);
  }
  private Partition findBestPartition(ArrayList bk, SimpsPhenoGroup[] phenoGroups
    , SimpsPhenotypeSet phenoSet) {
    Partition best = null;
    for (int i = 0; i < bk.size(); i++) {
      int[] clique = (int[]) bk.get(i);
      Partition part = new Partition();

      // BUILD GENOTYPE GROUP FROM CLIQUE
      BitSet usedPhenos = new BitSet();
      for (int g = 0; g < clique.length; g++) {
        SimpsPhenoGroup phenoG = phenoGroups[clique[g]]; // phenotype group
        LOG.report(this, "phenoG=" + phenoG);
        usedPhenos.or(phenoG); // collect all used photypes
        CompBitSet genoG = phenoSet.toGenotypes(phenoG); // getGenotype group
        LOG.report(this, "genoG=" + genoG);
        part.add(genoG);
      }
      part = buildTail(part, usedPhenos, phenoSet);
      if (best == null || best.calcSimpsonIndex() < part.calcSimpsonIndex()) {
        best = part;
      }
    }
    if (best == null) {
      best = new Partition();
      BitSet usedPhenos = new BitSet();
      best = buildTail(best, usedPhenos, phenoSet);
    }
    return best;
  }
  private Partition buildTail(Partition part, BitSet usedPhenos, SimpsPhenotypeSet phenoSet) {
    LOG.report(this, "buildTail(in part=" + part);
    SimpsPhenoGroup phenoG = null;
    for (; ;) {
      phenoG = phenoSet.mergeTwoLargestPhenotypes(usedPhenos);
      if (phenoG == null)
        break;
      usedPhenos.or(phenoG);
      CompBitSet genoG = phenoSet.toGenotypes(phenoG);
      LOG.report(this, "genoG=" + genoG);
      part.add(genoG);
    }
    LOG.report(this, "BuildTail(out part=" + part);
    return part;
  }
}

