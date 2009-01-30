package javax.utilx.arrays.mtrx;
import tsvlib.project.ProjectLogger;

import javax.utilx.arrays.IntVec;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 24/04/2007, 11:18:38
 */
public class MtrxFactory
{
  private final static ProjectLogger log = ProjectLogger.getLogger(MtrxFactory.class);

  public static MtrxPair selectLOO(int idx, Mtrx src) {
    return selectLOO(idx, src.getByRows());
  }

  public static MtrxPair splitRandRows(int nRows, Mtrx src) {
    return selectRandomRows(nRows, src.getByRows());
  }
  public static MtrxPair selectRandomRows(int nRows, double[][] z) {
    int n = z.length;
    int[] idxArr = IntVec.makeRandIdxArr(n);
    return selectRows(nRows, idxArr, z);
  }

  public static MtrxPair selectRows(int nRows, int[] idxArr, double[][] z) {
    log.trace("idxArr=", new IntVec(idxArr));
    int n = z.length;                      log.debug("n = ", n);
    double[][] A = new double[nRows][];
    double[][] B = new double[n - nRows][];

    for (int i = 0; i < A.length; i++) {
      A[i] = z[idxArr[i]];
    }
    for (int i = 0; i < B.length; i++) {
      B[i] = z[idxArr[i + A.length]];
    }
    MtrxPair res = new MtrxPair(z);
    res.setA(A);
    res.setB(B);
    log.trace("res=\n", res);
    return res;
  }

  public static MtrxPair selectLOO(int idx, double[][] z) {
    log.debug("selectLOO(idx=", idx);
    int n = z.length;
    log.debug("n=z.length = ", n);
    int nRows = 1;
    double[][] A = new double[nRows][];
    double[][] B = new double[n - nRows][];

    A[0] = z[idx];
    int currIdx = 0;
    for (int i = 0; i < n; i++) {
      if (i == idx)
        continue; // LOO
      B[currIdx++] = z[i];
    }
    MtrxPair res = new MtrxPair(z);
    res.setA(A);
    res.setB(B);
    log.trace("res=\n", res);
    return res;
  }

  public static MtrxPair selectKennardStoneRows(int nRows, double[][] z) {
    //todo!!!!!!!!!!
//    log.trace("selectRandomRows(");
//    log.debug("nRows = ", nRows);
//    int n = z.length;
//    log.debug("n = ", n);
//    double[][] A = new double[nRows][];
//    double[][] B = new double[n - nRows][];
//
//    int[] idxArr = IntVec.makeRandomIdxArr(n);
//    log.debug("idxArr=", new IntVec(idxArr));
//    for (int i = 0; i < A.length; i++) {
//      A[i] = z[idxArr[i]];
//    }
//    for (int i = 0; i < B.length; i++) {
//      B[i] = z[idxArr[i + A.length]];
//    }
//    MtrxPair res = new MtrxPair(z);
//    res.setA(A);
//    res.setB(B);
//    log.debug("res=\n", res);
//    return res;
    return null;
  }
}
