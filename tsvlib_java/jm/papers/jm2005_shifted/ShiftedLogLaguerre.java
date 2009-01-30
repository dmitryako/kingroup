package jm.papers.jm2005_shifted;
import jm.grid.TransLogCRToR;
import jm.grid.TransLogRToR;
import jm.grid.WFQuadrLogCR;
import jm.grid.WFQuadrLogR;
import jm.laguerre.LagrrLogCR;
import jm.laguerre.LaguerreLogR;
import junit.framework.Test;
import junit.framework.TestSuite;
import stlx.valarrayx.valarray;

import javax.iox.LOG;
import javax.vecmathx.function.FuncArr;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 5/04/2005, Time: 10:58:05
 */
public class ShiftedLogLaguerre extends LogCRTestCase {
  public static Test suite() {
    return new TestSuite(ShiftedLogLaguerre.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testLaguerreLogCR() {
    WFQuadrLogCR w = new WFQuadrLogCR(x);
    TransLogCRToR xToR = w.getLogCRToR();
    int N = 10;
    int alpha = 2;
    double lambda = 4;
    FuncArr arr = new LagrrLogCR(xToR, N, alpha, lambda);
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(arr.get(0)), "wf", "LaguerreLogCR_n0.csv");
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(arr.get(0).getDeriv()), "wf"
      , "LaguerreLogCR_n0_deriv.csv");
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(arr.get(N - 1)), "wf"
      , "LaguerreLogCR_n" + (N - 1) + ".csv");
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(arr.get(N - 1).getDeriv()), "wf"
      , "LaguerreLogCR_n" + (N - 1) + "_deriv.csv");
    double res = w.calcMaxOrthonErr(arr);
    assertEquals(0, res, 1e-8);
  }
  public void testLaguerreLogR() {
    WFQuadrLogR w = new WFQuadrLogR(x);
    TransLogRToR xToR = w.getLogRToR();
    int N = 20;
    int alpha = 2;
    double lambda = 9.5;
    FuncArr arr = new LaguerreLogR(xToR, N, alpha, lambda);
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(arr.get(0)), "wf", "LaguerreLogR_n0.csv");
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(arr.get(0).getDeriv())
      , "wf", "LaguerreLogR_n0_deriv.csv");
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(arr.get(N - 1)), "wf"
      , "LaguerreLogR_n" + (N - 1) + ".csv");
    LOG.saveToFile(valarray.asArray(x), valarray.asArray(arr.get(N - 1).getDeriv()), "wf"
      , "LaguerreLogR_n" + (N - 1) + "_deriv.csv");
    double res = w.calcMaxOrthonErr(arr);
    assertEquals(0, res, 2e-3);
  }
}