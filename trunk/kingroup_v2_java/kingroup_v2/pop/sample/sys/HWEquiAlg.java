package kingroup_v2.pop.sample.sys;

import tsvlib.project.ProjectLogger;

import javax.mathx.FactorialLog;
import javax.swingx.ProgressWnd;
import javax.utilx.arrays.IntVec;
import javax.utilx.arrays.vec.Vec;
import javax.vecmathx.matrix.IntSymmMtrx;

/**
 * Created by: jc1386591
 * Date: 15/06/2006. Time: 11:03:14
 */
public class HWEquiAlg {
  private static ProjectLogger log = ProjectLogger.getLogger(HWEquiAlg.class.getName());
  private ProgressWnd progress = null;

  public void setProgress(ProgressWnd progress)  {    this.progress = progress;  }

  public double[] calc(SysPop pop) {
    int NL = pop.getNumLoci();
    double[] res = Vec.makeArray(0.0, NL);
    int N = 17000;
    for (int L = 0; L < NL; L++) {
      if (progress != null
        && progress.isCanceled(L, 0, NL)) {
        return res;
      }
      int nAlleles = pop.getFreq().getLocFreq(L).length;
      int[] allAlleles = SysPopFactory.loadAllAllelels(L, pop);  // array od alleles
//    log.info("allAlleles=\n" + ByteArr.toString(allAlleles));

      int[] idxArr = IntVec.makeIdxArr(allAlleles.length);
      IntSymmMtrx m = makeMtrxFromOrder(allAlleles, idxArr, nAlleles);
      //log.info("original m=\n" + m);

      double givenPr = calcLogPr(m);
      int K = 0;
      for (int i = 0; i < N; i++) {
        idxArr = IntVec.makeRandIdxArr(allAlleles.length);
        m = makeMtrxFromOrder(allAlleles, idxArr, nAlleles);
//      log.info("m=\n" + m);
        double currPr = calcLogPr(m);
        if (currPr <= givenPr)
          K++;
      }
      res[L] = (double)K / N;
    }
    if (progress != null)
      progress.close();
    return res;
  }

  private double calcLogPr(IntSymmMtrx m) {
    int N_SAMPLE = 100;
    FactorialLog fact = new FactorialLog(N_SAMPLE);
    double z = 0; // num of heterozygotes
    double sum = 0;
    for (int r = 0; r < m.size(); r++) {
      for (int c = 0; c <= r; c++) {
        if (c != r)
          z += m.get(r, c);
        sum += fact.calc(m.get(r, c));
      }
    }
    return z * fact.calc(2) - sum;  // used Ln(2!) for Ln(2)
  }

  private IntSymmMtrx makeMtrxFromOrder(int[] allAlleles, int[] idxArr, int nAlleles) {
    IntSymmMtrx m = new IntSymmMtrx(nAlleles);
    for (int j = 0; j < idxArr.length; ) {
      int a = allAlleles[idxArr[j++]];
      int a2 = allAlleles[idxArr[j++]];
      m.add(a, a2, 1);
    }
    return m;
  }
}
