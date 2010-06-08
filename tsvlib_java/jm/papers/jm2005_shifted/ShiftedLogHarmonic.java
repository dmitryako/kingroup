package jm.papers.jm2005_shifted;
import jm.grid.TransLcrToR;
import jm.grid.WFQuadrLcr;
import jm.harmonic.HarmonicLogCR;
import junit.framework.Test;
import junit.framework.TestSuite;
import stlx.valarrayx.valarray;

import javax.iox.LOG;
import javax.vecmathx.function.FuncArr;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/04/2005, Time: 10:21:58
 */
public class ShiftedLogHarmonic extends LogCRTestCase {
  public static Test suite() {
    return new TestSuite(ShiftedLogLaguerre.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testLaguerreLogCR() {
    WFQuadrLcr w = new WFQuadrLcr(x);
    TransLcrToR xToR = w.getLogCRToR();
    int N = 10;
    int L = 0;
    double B = 0.5;
    FuncArr arr = new HarmonicLogCR(xToR, N, L, B);
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(arr.get(0)), "wf", "HarmonicLogCR_n0.csv");
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(arr.get(0).getDeriv()), "wf"
      , "HarmonicLogCR_n0_deriv.csv");
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(arr.get(N - 1)), "wf"
      , "HarmonicLogCR_n" + (N - 1) + ".csv");
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(arr.get(N - 1).getDeriv()), "wf"
      , "HarmonicLogCR_n" + (N - 1) + "_deriv.csv");
    double res = w.calcMaxOrthonErr(arr);
    assertEquals(0, res, 1e-8);
  }
}