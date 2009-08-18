package kingroup_v2.cervus;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.allele.freq.SysAlleleFreqFactory;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.utilx.IntHashSet;
import javax.utilx.arrays.vec.Vec;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 3/03/2006, Time: 12:55:39
 */
public class AlleleAnalysisFactory
{
  public static int[] countAlleles(SysPop pop)
  {
    int nLoci = pop.getNumLoci();
    int[] res = new int[nLoci];
    for (int L = 0; L < nLoci; L++)
    {
      IntHashSet set = new IntHashSet();
      for (int i = 0; i < pop.size(); i++)
      {
        int a = pop.getAllele(i, L, pop.MAT);
        int a2 = pop.getAllele(i, L, pop.PAT);
        set.add(a);
        set.add(a2);
      }
      res[L] = set.size();
    }
    return res;
  }

  public static int[] countGenotypes(SysPop pop)
  {
    int nLoci = pop.getNumLoci();
    int[] res = new int[nLoci];
    for (int L = 0; L < nLoci; L++)
    {
      int count = 0;
      for (int i = 0; i < pop.size(); i++)
      {
        int a = pop.getAllele(i, L, pop.MAT);
        int a2 = pop.getAllele(i, L, pop.PAT);
        if (a != -1  && a2 != -1)
          count++;
      }
      res[L] = count;
    }
    return res;
  }

  public static int[] countHeterozygotes(SysPop pop)
  {
    int nLoci = pop.getNumLoci();
    int[] res = new int[nLoci];
    for (int L = 0; L < nLoci; L++)
    {
      int count = 0;
      for (int i = 0; i < pop.size(); i++)
      {
        int a = pop.getAllele(i, L, pop.MAT);
        int a2 = pop.getAllele(i, L, pop.PAT);
        if (a != -1  && a2 != -1
          &&  a != a2) // HETERO
          count++;
      }
      res[L] = count;
    }
    return res;
  }

  public static int[] countHomozygotes(SysPop pop)
  {
    int nLoci = pop.getNumLoci();
    int[] res = new int[nLoci];
    for (int L = 0; L < nLoci; L++)
    {
      int count = 0;
      for (int i = 0; i < pop.size(); i++)
      {
        int a = pop.getAllele(i, L, pop.MAT);
        int a2 = pop.getAllele(i, L, pop.PAT);
        if (a != -1  && a2 != -1
          &&  a == a2) // HOMO
          count++;
      }
      res[L] = count;
    }
    return res;
  }

  public static double[] calcHeterozygosity(int[] aHets, int[] aN)
  {
    double[] res = new double[aHets.length];
    for (int i = 0; i < res.length; i++) {
      res[i] = (double)aHets[i] / aN[i];
    }
    return res;
  }

  public static double[] calcNeiHeteroz(SysPop pop)
  {
    SysAlleleFreq freq = pop.getFreq();
    int n = pop.size();
    int nLoci = pop.getNumLoci();
    double[] res = new double[nLoci];
    for (int L = 0; L < nLoci; L++)
    {
      double[] loc = freq.getLocFreq(L);
      double v = Vec.dot(loc, loc);
      double k2 = 2. * n;
      res[L] = (1. - v) * k2 / (k2 - 1);
    }
    return res;
  }
  public static double calcNeiHeterozAvr(SysPop pop)
  {
    return Vec.mean(calcNeiHeteroz(pop));
  }
  public static double[] calcTrueHeteroz(SysAlleleFreq freq)  {
    int nLoci = freq.getNumLoci();
    double[] res = new double[nLoci];
    for (int L = 0; L < nLoci; L++)
    {
      double[] loc = freq.getLocFreq(L);
      double v = Vec.dot(loc, loc);
      res[L] = (1. - v);
    }
    return res;
  }

  public static double calcVarXAvr(SysPop pop){
    SysAlleleFreq saved = pop.getFreq();
    SysAlleleFreq observedFreq = SysAlleleFreqFactory.makeFrom(pop, true);
    pop.setFreq(observedFreq);
    double[] varL = AlleleAnalysisFactory.calcVarX(pop);
    pop.setFreq(saved);
    return Vec.mean(varL);
  }

  public static double calcTrueHeterozAvr(SysAlleleFreq freq)
  {
    return Vec.mean(calcTrueHeteroz(freq));
  }

  public static double calcObservHeterozAvr(SysPop pop)
  {
    double[] obsHet = AlleleAnalysisFactory.calcObservHeteroz(pop);
    return Vec.mean(obsHet);
  }

  public static double[] calcObservHeteroz(SysPop pop)
  {
    int nLoci = pop.getNumLoci();
    double[] res = new double[nLoci];
    for (int L = 0; L < nLoci; L++)
      res[L] = calcObservHeteroz(L, pop);
    return res;
  }

  public static double[] calcVarX(SysPop pop)
  {
    int nLoci = pop.getNumLoci();
    double[] res = new double[nLoci];
    for (int L = 0; L < nLoci; L++)
      res[L] = calcVarX(L, pop);
    return res;
  }

  public static double calcObservHeteroz(int L, SysPop pop) {
    int n = pop.size();
    double sum = 0;
    for (int i = 0; i < n; i++)
      sum += calcObservHeteroz(i, L, pop);
    return sum / n;
  }

  public static double calcVarX(int L, SysPop pop) {
    int n = pop.size();
    double sum = 0;
    for (int i = 0; i < n; i++)
      sum += calcVarX(i, L, pop);
    return sum / (n - 1);
  }

  public static int calcObservHeteroz(int ix, int L, SysPop pop) {
    int a = pop.getAllele(ix, L, 0);
    int b = pop.getAllele(ix, L, 1);
    return (a == b ? 0 : 1);
  }

  public static double calcVarX(int ix, int L, SysPop pop) {
    double[] freq = pop.getFreq().getLocFreqCopy(L);
    int a = pop.getAllele(ix, L, 0);
    int b = pop.getAllele(ix, L, 1);
    freq[a] -= 0.5;
    freq[b] -= 0.5;
    return 4. * Vec.dot(freq, freq);
  }

//  public static double[] makeVector(int i, SysPop pop)
//  {
//    SysAlleleFreq freq = pop.getFreq();
//    double[] res = new double[calcAlleleBitSet(freq)];
//    int A = 0;
//    int B = 1;
//    int size = 0;
//    for (int L = 0; L < pop.getNumLoci(); L++) {
//      res[size + pop.getAllele(i, L, A)]++;
//      res[size + pop.getAllele(i, L, B)]++;
//      size += freq.getLocusSize(L);
//    }
//    return res;
//  }
//
//  public static int calcAlleleBitSet(SysAlleleFreq freq) {
//    int size = 0;
//    for (int L = 0; L < freq.getNumLoci(); L++)
//      size += freq.getLocusSize(L);
//    return size;
//  }
}
