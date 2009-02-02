package kingroup_v2.pop.allele.freq;

import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistAlg;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.utilx.arrays.vec.Vec;

/**
 * Created by: jc1386591
 * Date: 8/06/2006. Time: 10:30:42
 */
public class SimilarityIndex {

  public static double[] calcUnbiasedFromFreq(SysPop group) {
    SysAlleleFreq freq = group.getFreq();
    if (freq == null  ||  freq.getNumLoci() == 0)
      return null;
    double[] res = Vec.makeArray(0., freq.getNumLoci());
    double n = 2 * group.size(); // NOTE!!!!! 2*
    double n1 = n - 1.;
    for (int L = 0; L < res.length; L++) {
      double a2 = 0;
      double a3 = 0;
      for (double v: freq.getLocFreq(L)) {
        a2 += v * v;
        a3 += v * v * v;
      }
      a2 = (a2 * n - 1.) / n1;
      a3 = (a3 * n * n - 3. * n1 * a2 - 1.) / (n1 * (n - 2.));
      res[L] = 2. * a2 - a3;
    }
    return res;
  }
  public static double averageOverLoci(double[] arr) {
    if (arr == null  ||  arr.length == 0)
      return 0;
    return Vec.avr(arr);
  }
  public static double[] calcFromFreq(SysAlleleFreq freq) {
    if (freq == null  ||  freq.getNumLoci() == 0)
      return null;
    double[] res = Vec.makeArray(0., freq.getNumLoci());
    for (int L = 0; L < res.length; L++) {
      double a2 = 0;
      double a3 = 0;
      for (double v: freq.getLocFreq(L)) {
        a2 += v * v;
        a3 += v * v * v;
      }
      res[L] = 2. * a2 - a3;
    }
    return res;
  }
  /*
  public static double calcObserved(SysPop pop, int ia, int ib)
  {
    assert(pop.getNumLoci() > 0);
    double res = 0;
    for (int L = 0; L < pop.getNumLoci(); L++) {
      res += calcFromPop(pop, L, ia, ib);
    }
    return res / pop.getNumLoci();
  }
  */
  public static double calcSimilarity(SysPop pop, int locIdx, int x, int y) {
    int a = pop.getAllele(x, locIdx, pop.PAT);
    int b = pop.getAllele(x, locIdx, pop.MAT);
    int c = pop.getAllele(y, locIdx, pop.PAT);
    int d = pop.getAllele(y, locIdx, pop.MAT);
    return calcSimilarity(a, b, c, d);
  }

  public static double calcSimilarity(int a, int b, int c, int d) {
    int countA = GenotypeDistAlg.calcAsymmDist(a, b, c, d);
    int countB = GenotypeDistAlg.calcAsymmDist(c, d, a, b);
    return 1. - 0.25 *(countA + countB);
  }

  public static double[] calcFromPop(SysPop pop) {
    if (pop == null  ||  pop.size() < 2  ||  pop.getNumLoci() <= 0)
      return null;
    double[] arr = Vec.makeArray(0., pop.getNumLoci());
    for (int L = 0; L < arr.length; L++) {
      int count = 0;
      for (int i = 0; i < pop.size(); i++) {
        for (int i2 = i + 1; i2 < pop.size(); i2++) {
          arr[L] += calcSimilarity(pop, L, i, i2);
          count++;
        }
      }
      arr[L] /= count;
    }
    return arr;
  }
}
