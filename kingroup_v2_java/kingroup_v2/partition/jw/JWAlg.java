package kingroup_v2.partition.jw;
import kingroup.genetics.OldAlleleFreq;
import kingroup.genotype.Allele;
import kingroup.genotype.Locus;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.utilx.bitset.CompBitSet;
import javax.utilx.pair.IntPair2;
import java.io.File;
import java.io.IOException;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 6/07/2005, Time: 11:21:56
 */
public class JWAlg {
  private final SysPop pop;
  private final OldAlleleFreq freq;
  static {
//    System.getProperties().list(System.out);
//    System.out.println(System.getProperty("java.library.path"));
    String os = System.getProperty("os.name");
    String library_lin = null;
    String library_win = null;
    String library_mac = null;
    try {
      library_lin = new File("./kingroup_v2_native/jw/libJWAlg.so").getCanonicalFile().getCanonicalPath();
      library_win = new File("./kingroup_v2_native/jw/JWAlg.dll").getCanonicalFile().getCanonicalPath();
      library_mac = new File("./kingroup_v2_native/jw/libJWAlg.jnilib").getCanonicalFile().getCanonicalPath();
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (os.indexOf("Windows") != -1) {
      System.load(library_win);
    } else if (os.indexOf("Linux") != -1) {
      System.load(library_lin);
    } else {
      throw new RuntimeException("Operating System Not Supported.");
    }
  }
  public JWAlg(SysPop pop, OldAlleleFreq freq) {
    this.pop = pop;
    this.freq = freq;
  }
  public Partition partition() {
    Partition res = new Partition();
    int[] vals = new int[0];
    try {
      vals = doPartition(makeIntArray(), makeFreqArray(), new int[][]{new int[]{0, 1}, new int[]{2, 3}});
    } catch (Throwable t) {
      t.printStackTrace();
    }
    int max = 1;
    boolean done = false;
    CompBitSet toAdd;
    for (int j = 0; j < max; j++) {
      toAdd = new CompBitSet();
      res.add(toAdd);
      for (int i = 0; i < vals.length; i++) {
        int val = vals[i];
        if (!done && val > max) {
          max = val;
        }
        if (val == (j + 1)) {
          toAdd.flip(i);
        }
      }
      done = true;
    }
    System.out.println(res);
    return res;
  }
  private double[][] makeFreqArray() {
    Locus l;
    Allele a;
    double[][] toRet = new double[freq.getNumLoci() * 2][freq.getMaxAlleleCount() + 1];
    for (int locus = 1; locus < freq.getNumLoci() + 1; locus++) {
      l = freq.getLocus(locus - 1);
      toRet[(locus * 2) - 2][0] = l.size();
      toRet[(locus * 2) - 1][0] = l.size();
      for (int allele = 1; allele < l.size() + 1; allele++) {
        a = l.get(allele);
        toRet[(locus * 2) - 2][allele] = allele;
        toRet[(locus * 2) - 1][allele] = l.get(allele - 1).getProb();
      }
    }
    return toRet;
  }
  private native int[] doPartition(int[][] genotypes, double[][] allelicFreqs, int[][] results);
  private int[][] makeIntArray() {
    int[][] toPartition = new int[pop.size()][(pop.getNumLoci() * 2) + 4];
    IntPair2 p = null;
    for (int i = 0; i < toPartition.length; i++) {
      toPartition[i][0] = i;
      toPartition[i][1] = 2;
      toPartition[i][2] = -1;
      toPartition[i][3] = -1;
      for (int j = 2; j < 2 + (((toPartition[i].length - 4) / 2)); j++) {
        // [dk20060509] changed IntPairSorted to IntPair
        p = pop.getAllelePair((short) i, (int) (j - 2));
        toPartition[i][j * 2] = p.a + 1;
        toPartition[i][j * 2 + 1] = p.b + 1;
      }
    }
    return toPartition;
  }
}
