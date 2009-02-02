package kingroup_v2.pop.allele.freq;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.usr.UsrPopSLOW;
import tsvlib.project.ProjectLogger;

import javax.utilx.RandomSeed;
import javax.utilx.arrays.vec.Vec;
import java.util.Arrays;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 11/09/2005, Time: 09:34:34
 */
public class SysAlleleFreqFactory
{
  private static ProjectLogger log = ProjectLogger.getLogger(SysAlleleFreqFactory.class.getName());
  public static SysAlleleFreq makeSysAlleleFreq(PopBuilderModel builder,UsrPopSLOW usrPop)
  {
    SysAlleleFreq res = makeEmptyFreq(builder);

    if (builder.getParentAlleleFreqType() == builder.FREQ_RANDOM)
      loadRandom(res);
    else if (builder.getParentAlleleFreqType() == builder.FREQ_TRIANGULAR)
      loadTriangular(res);
    else if (builder.getParentAlleleFreqType() == builder.FREQ_DIERKES_2005)
      loadDeirkes2005(res);
    else if (builder.getParentAlleleFreqType() == builder.FREQ_ONE_COMMON)
      loadOneCommon(res);
    else if (builder.getParentAlleleFreqType() == builder.FREQ_CALC_SAMPLE_FREQ)
      throw new RuntimeException("Process freq from usr pop in a uccontroller");
    else
      loadEquifrequent(res);
    return res;
  }

  private static void loadDeirkes2005(SysAlleleFreq res) {
//    int NL = 5;
//    SysAlleleFreq res = new SysAlleleFreq(NL);
    int L = 0;
    res.setLocusFreq(L++, new double[] {
      0.002445,
      0.001222,
      0.007335,
      0.026895,
      0.001222,
      0.023227,
      0.070905,
      0.077017,
      0.064792,
      0.132029,
      0.00978 ,
      0.026895,
      0.01956 ,
      0.01467 ,
      0.002445,
      0.001222,
      0.012225,
      0.041565,
      0.003667,
      0.025672,
      0.057457,
      0.033007,
      0.007335,
      0.080685,
      0.143038, // from 0.143032
      0.025672,
      0.011002,
      0.018337,
      0.001222,
      0.017115,
      0.002445,
      0.006112,
      0.002445,
      0.001222,
      0.002445,
      0.00978 ,
      0.008557,
      0.003667,
      0.003667} );
    res.setLocusFreq(L++, new double[] {
      0.026699,
      0.001214,
      0.001214,
      0.087379,
      0.053398,
      0.007282,
      0.11165 ,
      0.001214,
      0.114074,  //from 0.114078
      0.001214,
      0.009709,
      0.001214,
      0.003641,
      0.048544,
      0.021845,
      0.06068 ,
      0.020631,
      0.044903,
      0.010922,
      0.001214,
      0.010922,
      0.012136,
      0.023058,
      0.031553,
      0.046117,
      0.06432 ,
      0.078883,
      0.044903,
      0.03034 ,
      0.010922,
      0.009709,
      0.003641,
      0.003641,
      0.001214} );
    res.setLocusFreq(L++, new double[] {
      0.063433,
      0.25    ,
      0.006219,
      0.119403,
      0.222637,
      0.002488,
      0.027363,
      0.003731,
      0.0199  ,
      0.017413,
      0.139304, // from 0.139303
      0.0199  ,
      0.011194,
      0.001244,
      0.001244,
      0.002488,
      0.003731,
      0.007463,
      0.004975,
      0.001244,
      0.003731,
      0.032338,
      0.004975,
      0.001244,
      0.021144,
      0.006219,
      0.001244,
      0.003731} );
    res.setLocusFreq(L++, new double[] {
      0.022892,
      0.003614,
      0.014458,
      0.03253,
      0.714458,
      0.03012,
      0.081928,
      0.010843,
      0.033735,
      0.016867,
      0.014458,
      0.010843,
      0.00241,
      0.006024,
      0.001205,
      0.00241,
      0.001205}    );
    res.setLocusFreq(L++, new double[] {
      0.103194,
      0.002457,
      0.656019 , //from 0.65602
      0.004914,
      0.101966,
      0.006143,
      0.108108,
      0.004914,
      0.007371,
      0.004914} );
//    log.info("res freq = \n" + res);
//    res.normalize(1., false);
//    log.info("norm freq = \n" + res);
    /*121	 0.022892	 266	 0.103194	 155	 0.002445	 192	 0.026699	 135	 0.063433
  127	 0.003614	 268	 0.002457	 164	 0.001222	 194	 0.001214	 137	 0.25
  129	 0.014458	 270	 0.65602	 167	 0.007335	 195	 0.001214	 139	 0.006219
  131	 0.03253	 272	 0.004914	 169	 0.026895	 197	 0.087379	 141	 0.119403
  133	 0.714458	 274	 0.101966	 177	 0.001222	 198	 0.053398	 143	 0.222637
  135	 0.03012	 278	 0.006143	 179	 0.023227	 200	 0.007282	 145	 0.002488
  137	 0.081928	 282	 0.108108	 181	 0.070905	 202	 0.11165	 146	 0.027363
  139	 0.010843	 286	 0.004914	 183	 0.077017	 204	 0.001214	 149	 0.003731
  143	 0.033735	 291	 0.007371	 185	 0.064792	 206	 0.114078	 151	 0.0199
  145	 0.016867	 303	 0.004914	 187	 0.132029	 208	 0.001214	 153	 0.017413
  152	 0.014458	 	 	 188	 0.00978	 216	 0.009709	 155	 0.139303
  154	 0.010843	 	 	 189	 0.026895	 218	 0.001214	 157	 0.0199
  156	 0.00241	 	 	 190	 0.01956	 220	 0.003641	 159	 0.011194
  161	 0.006024	 	 	 192	 0.01467	 222	 0.048544	 173	 0.001244
  162	 0.001205	 	 	 194	 0.002445	 224	 0.021845	 181	 0.001244
  163	 0.00241	 	 	 196	 0.001222	 226	 0.06068	 183	 0.002488
  168	 0.001205	 	 	 198	 0.012225	 228	 0.020631	 185	 0.003731
              200	 0.041565	 232	 0.044903	 187	 0.007463
              202	 0.003667	 234	 0.010922	 189	 0.004975
              204	 0.025672	 235	 0.001214	 191	 0.001244
              205	 0.057457	 237	 0.010922	 193	 0.003731
              206	 0.033007	 239	 0.012136	 195	 0.032338
              207	 0.007335	 241	 0.023058	 197	 0.004975
              209	 0.080685	 243	 0.031553	 201	 0.001244
              211	 0.143032	 245	 0.046117	 205	 0.021144
              213	 0.025672	 247	 0.06432	 207	 0.006219
              215	 0.011002	 249	 0.078883	 209	 0.001244
              217	 0.018337	 251	 0.044903	 211	 0.003731
              219	 0.001222	 253	 0.03034
              221	 0.017115	 255	 0.010922
              225	 0.002445	 257	 0.009709
              231	 0.006112	 259	 0.003641
              233	 0.002445	 261	 0.003641
              235	 0.001222	 272	 0.001214
              237	 0.002445
              242	 0.00978
              246	 0.008557
              248	 0.003667
              250	 0.003667	 	*/
  }
  private static SysAlleleFreq makeEmptyFreq(PopBuilderModel bean)
  {
    if (bean.getLociBuilderIdx() == PopBuilderModel.MANUAL_LOCI) {
      int[] arr = bean.getLocusSizes();
      SysAlleleFreq res = new SysAlleleFreq(arr.length);
      for (int L = 0; L < arr.length; L++) {
        int nAlleles = arr[L];
        res.setLocusFreq(L, new double[nAlleles]);
      }
      return res;
    }
    return new SysAlleleFreq(bean.getNumLoci(), bean.getNumAlleles());
  }

  public static void loadEquifrequent(SysAlleleFreq model) {
    for (int L = 0; L < model.getNumLoci(); L++) {
      double[] locus = model.getLocFreq(L);
      Vec.set(1.0 / locus.length, locus);
    }
  }
  public static SysAlleleFreq makeEquifrequent(int nLoci, int nAlleles) {
    SysAlleleFreq res = new SysAlleleFreq(nLoci, nAlleles);
    loadEquifrequent(res);
    return res;
  }
  public static SysAlleleFreq makeTriangular(int nLoci, int nAlleles) {
    SysAlleleFreq res = new SysAlleleFreq(nLoci, nAlleles);
    loadTriangular(res);
    return res;
  }
  public static SysAlleleFreq makeRandom(int nLoci, int nAlleles) {
    SysAlleleFreq res = new SysAlleleFreq(nLoci, nAlleles);
    loadRandom(res);
    return res;
  }
  public static void loadRandom(SysAlleleFreq freq) {
    RandomSeed rand = RandomSeed.getInstance();
    for (int L = 0; L < freq.getNumLoci(); L++) {
      double[] locus = freq.getLocFreq(L);
      for (int a = 0; a < locus.length; a++) {
        locus[a] = -rand.nextDouble();
      }
      Arrays.sort(locus);
      Vec.mult(-1, locus);
    }
    freq.normalize(1., false);
  }
  public static void loadTriangular(SysAlleleFreq freq) {
    for (int L = 0; L < freq.getNumLoci(); L++) {
      double[] locus = freq.getLocFreq(L);
      for (int a = 0; a < locus.length; a++) {
        locus[a] = -(a+1);
      }
      Arrays.sort(locus);
      Vec.mult(-1, locus);
    }
    freq.normalize(1., false);
  }
  public static void loadOneCommon(SysAlleleFreq freq) {
//    double COMMON_FREQ = 0.8;
    double COMMON_FREQ = 0.6;
    for (int L = 0; L < freq.getNumLoci(); L++) {
      double[] locus = freq.getLocFreq(L);
      double f = (1. - COMMON_FREQ) / (locus.length - 1);
      for (int a = 0; a < locus.length; a++) {
        if (a == 0)
          locus[a] = COMMON_FREQ;
        else
          locus[a] = f;
      }
    }
  }
  public static SysAlleleFreq makeSysAlleleFreqFrom(UsrAlleleFreq usrFreq) {
    // log.info("userPop formatLog=" + freq.toString());
    int nLoci = usrFreq.getNumLoci();
    SysAlleleFreq sysFreq = new SysAlleleFreq(nLoci);
    for (int L = 0; L < nLoci; L++) {
      int locusSize = usrFreq.getLocusSize(L);
      double[] locus = new double[locusSize];
      for (int i = 0; i < locusSize; i++) {
        AlleleFreqPair allele = usrFreq.get(L, i);
        locus[i] = allele.getFreq();
      }
      sysFreq.setLocusFreq(L, locus);
    }
    sysFreq.normalize(1., false);
    return sysFreq;
  }

  public static void copyToUserFreq(UsrAlleleFreq toUsr, SysAlleleFreq fromSys) {
    int nLoci = fromSys.getNumLoci();
    for (int L = 0; L < nLoci; L++) {
      int locusSize = toUsr.getLocusSize(L);
      for (int i = 0; i < locusSize; i++) {
        AlleleFreqPair allele = toUsr.get(L, i);
        double freq = fromSys.getFreq(L, i);
        allele.setFreq(freq);
      }
    }
  }


  public static boolean hasZeroHeterLocus(SysPop sysPop)
  {
    int n = sysPop.size();
    int M = 0;
    int P = 1;
    for (int L = 0; L < sysPop.getFreq().getNumLoci(); L++)
    {
      int a = -1;
      int count = 0;
      for (int i = 0; i < n; i++)
      {
        int m = sysPop.getAllele(i, L, M);
        int p = sysPop.getAllele(i, L, P);
        if (m != p)
          break; // different alleles
        if (a == -1)
          a = m;
        else if (a != m)
          break;   // different allele
        count += 2;
      }
      if (count == 2 * n)   // all alleles are the same!!!
        return true;
    }
    return false;
  }

  public static SysAlleleFreq makeFrom(SysPop sysPop, boolean norm)
  {
    int M = 0;
    int P = 1;
    int nAlleles = sysPop.getFreq().getMaxNumAlleles();
    int nLoci = sysPop.getFreq().getNumLoci();
    SysAlleleFreq sysFreq = new SysAlleleFreq(nLoci, nAlleles);
//    log.info("sysPop=\n" + sysPop);
    for (int L = 0; L < nLoci; L++)
    {
      for (int i = 0; i < sysPop.size(); i++)
      {
        int m = sysPop.getAllele(i, L, M);
        int p = sysPop.getAllele(i, L, P);
        sysFreq.setFreq(L, m, sysFreq.getFreq(L, m) + 1);
        sysFreq.setFreq(L, p, sysFreq.getFreq(L, p) + 1);
//        log.info("res=\n" + sysFreq);
      }
    }
    if (norm)
      sysFreq.normalize(1., false);
    return sysFreq;
  }

  public static SysAlleleFreq makeFrom(double[] w, SysPop sysPop)
  {
    int M = 0;
    int P = 1;
    int nAlleles = sysPop.getFreq().getMaxNumAlleles();
    int nLoci = sysPop.getFreq().getNumLoci();
    SysAlleleFreq sysFreq = new SysAlleleFreq(nLoci, nAlleles);
//    log.info("sysPop=\n" + sysPop);
    for (int L = 0; L < nLoci; L++)
    {
      for (int i = 0; i < sysPop.size(); i++)
      {
        int m = sysPop.getAllele(i, L, M);
        int p = sysPop.getAllele(i, L, P);
        sysFreq.setFreq(L, m, sysFreq.getFreq(L, m) + w[i]);
        sysFreq.setFreq(L, p, sysFreq.getFreq(L, p) + w[i]);
//        log.info("res=\n" + sysFreq);
      }
    }
    sysFreq.normalize(1., false);
    return sysFreq;
  }

  public static SysAlleleFreq makeCumulative(SysAlleleFreq freq)
  {
    int nLoci = freq.getNumLoci();
    SysAlleleFreq res = new SysAlleleFreq(nLoci);
    for (int L = 0; L < nLoci; L++)
    {
      double[] locFreqs = freq.getLocFreq(L);
      double[] cumFreqs = Vec.makeCumulative(locFreqs);
      res.setLocusFreq(L, cumFreqs);
    }
    return res;
  }

  public static SysAlleleFreq makeBiasByPair(int i, int j, SysPop group, SysPop largePop)
  {
    SysAlleleFreq freq = makeFrom(largePop, false);
//    log.info("pop=\n" + pop);
//    log.info("freq=\n" + freq);
    subtract(i, group, freq);
    subtract(j, group, freq);
//    log.info("freq=\n" + freq);
    freq.normalize(1., false);
//    log.info("freq=\n" + freq);
    return freq;
  }

  public static int subtract(int i, SysPop pop, SysAlleleFreq from)
  {
    int count = 0;
    for (int type = 0; type < pop.N_TYPES; type++) {
      for (int L = 0; L < from.getNumLoci(); L++) {
        int a = pop.getAllele(i, L, type);
        if (a != -1) {
          double fa = from.getFreq(L, a);
          if ((float)fa == 1f)
            count++;
          from.setFreq(L, a, fa-1);
        }
      }
    }
    return count;
  }

  public static int subtract(SysPop group, SysAlleleFreq from)
  {
    int count = 0;
    for (int i = 0; i < group.size(); i++) {
      count += subtract(i, group, from);
    }
    return count;
  }

  public static SysAlleleFreq makeDeepCopy(SysAlleleFreq from)
  {
    SysAlleleFreq res = new SysAlleleFreq(from.getNumLoci());
    for (int L = 0; L < res.getNumLoci(); L++) {
      double[] loc = from.getLocFreq(L);
      double[] locCopy = Vec.copy(loc);
      res.setLocusFreq(L, locCopy);
    }
    return res;
  }

  public static void replaceZero(SysAlleleFreq freq, SysAlleleFreq from)
  {
    for (int L = 0; L < freq.getNumLoci(); L++) {
      for (int i = 0; i < freq.getLocusSize(L); i++) {
        double f = freq.getFreq(L, i);
        if ((float)f == 0f)
          freq.setFreq(L, i, from.getFreq(L, i));
      }
    }
  }
  public static double[] calcIdendixWeight(SysAlleleFreq freq)
  {
    int nLoci = freq.getNumLoci();
    double[] res = new double[nLoci];
    for (int L = 0; L < nLoci; L++)
    {
      double[] loc = freq.getLocFreq(L);
      double v = Vec.dot(loc, loc);
      res[L] = v;
    }
    return res;
  }

  public static void applyError(SysAlleleFreq freq, int freq_error)
  {
    double err = 0.01 * freq_error;
    RandomSeed rand = RandomSeed.getInstance();
    for (int L = 0; L < freq.getNumLoci(); L++)
    {
      double[] loc = freq.getLocFreq(L);
      for (int i = 0; i < loc.length; i++) {
        double v = loc[i];
        int sign = 2 * rand.nextInt(2) - 1; // -1 or +1
        loc[i] = v * (1. + sign * err);
      }
    }
    freq.normalize(1, false);
  }

  public static boolean hasAllAlleles(SysAlleleFreq testFreq, SysAlleleFreq refFreq) {
    for (int L = 0; L < testFreq.getNumLoci(); L++) {
      double[] testLocus = testFreq.getLocFreq(L);
      double[] refLocus = refFreq.getLocFreq(L);
      for (int i = 0; i < testLocus.length; i++) {
        double test = testLocus[i];
        double ref = refLocus[i];
        if ((float)test == (float)0  // missing an allele
          &&  (float)ref != (float)0) // reference pop has this allele
          return false;
      }
    }
    return true;
  }
}
