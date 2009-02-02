package kingroup_v2.kinship;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.allele.freq.SysAlleleFreqFactory;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import javax.utilx.arrays.mtrx.Mtrx;
import javax.utilx.arrays.vec.Vec;

/**
 * Created by: jc1386591
 * Date: 9/07/2006. Time: 14:32:42
 */
public class KingroupFreqFromR
{
  protected static ProjectLogger log = ProjectLogger.getLogger(KingroupFreqFromR.class.getName());

  public SysAlleleFreq calc(double[][] pedigreeR, SysPop pop) {
    log.info("\nfrom R=\n" + Mtrx.toString(pedigreeR));
    Matrix m = new Matrix(pedigreeR);
    EigenvalueDecomposition ed = m.eig();
    Matrix v = ed.getV();
    double[][] eiv = v.getArray();
    log.info("\nv = " + Mtrx.toString(eiv));

    double[] e = ed.getRealEigenvalues();
    log.info("\ne = " + Vec.toString(e));

    Mtrx.checkOrthonorm(v.getArray());
    double[] xi = Mtrx.sumRows(eiv);
    double eta = calcEta(e, xi);
    log.info("\neta = " + (float)eta);

    double[] c = calcC(e, xi);
    double[] w = calcW(c, eiv);
    Vec.limit(w, 0., 10.);
    Vec.norm(w, 1.);

    //SysAlleleFreq saved = pop.getFreq();
    //log.info("\nsaved\n = " + saved);

    SysAlleleFreq estimFreq = SysAlleleFreqFactory.makeFrom(w, pop);
    log.info("\nestimFreq\n = " + estimFreq);
    return estimFreq;
  }

  private double[] calcW(double[] c, double[][] eiv) {
    double[] w = new double[c.length];
    for (int i = 0; i < c.length; i++) {
      w[i] = Vec.dot(c, eiv[i]);
      //log.info("\nw["+i+"] = "+(float)w[i]);
    }
    log.info("\nw = " + Vec.toString(w));
    return w;
  }

  private double[] calcC(double[] e, double[] xi) {
    double eta = calcEta(e, xi);
    double[] c = new double[e.length];
    for (int i = 0; i < e.length; i++) {
      c[i] = xi[i] / (eta * e[i]);
      //log.info("\nC["+i+"] = "+(float)c[i]);
    }
    return c;
  }

  private double calcEta(double[] e, double[] xi) {
    double sum = 0;
    for (int i = 0; i < e.length; i++) {
      double t = xi[i] * xi[i] / e[i];
      //log.info("\neta["+i+"] = "+(float)t);
      sum += t;
    }
    return sum;
  }
}
