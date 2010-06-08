package jm.papers.jm2005_shifted;
import Jama.EigenvalueDecomposition;
import jm.angular.Spin;
import jm.atom.HMtrx;
import jm.atom.SlaterLcr;
import jm.atom.SysTwoE;
import jm.grid.TransLcrToR;
import jm.grid.WFQuadrLcr;
import jm.laguerre.JmLagrrOrthLcr;
import jm.shell.ConfigArr;
import jm.shell.ConfigArrFactory;
import jm.shell.ShellLS;
import junit.framework.TestCase;

import javax.iox.LOG;
import javax.utilx.arrays.DoubleArrayList;
import javax.utilx.arrays.vec.Vec;
import javax.vecmathx.function.FuncArr;
import javax.vecmathx.grid.StepGrid;
import java.io.File;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 29/03/2005, Time: 13:40:13
 */
public class EnergyConvergence //extends PerformanceChart
{
  protected String DIR = "jm";
  private static final boolean DISPLAY_ENERGIES = true;
  public static void main(String[] args) {
    EnergyConvergence test = new EnergyConvergence();
    LOG.setTrace(true);
    test.testGroundWithL();
    System.exit(0);
  }
  public EnergyConvergence() {
    DIR += File.separator + "energy";
//    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  public void testGroundWithL() {
    ShellLS LS = new ShellLS(0, Spin.SINGLET);
    int MAX_L = 1;
    int[] MAX_N = {5, 5, 5};
//      double EXACT  = -2.903724375; // from Pekeris PhysRev 115, p1216-1221
    double EXACT = -2.9037243770341195983111; // from Korobov PhysRevA66, 024501-1
    double[] Z = {2, 2, 2};
    double NORM_ERROR = 1e-10;
    double FIRST = -4;
    int NUM_STEPS = 381;
    double LAST = 5; // exp(7) = 1096
    StepGrid x = new StepGrid(FIRST, LAST, NUM_STEPS);
    WFQuadrLcr wCR = new WFQuadrLcr(x);
    TransLcrToR xToR = wCR.getLogCRToR();
    DoubleArrayList arrE = new DoubleArrayList();
    DoubleArrayList arrN = new DoubleArrayList();
    int runningN = 1;
    ConfigArr cumBasis = null; // cumulative basis
    for (int L = 0; L <= MAX_L; L++) {
      ConfigArr currBasis = null; // current
      for (int N = 1; N < MAX_N[L]; N++) {
        int alpha = 2 * L + 2;
        double lambda = 2. * Z[L];
        FuncArr arr = new JmLagrrOrthLcr(xToR, N, alpha, lambda);
        double res = wCR.calcMaxOrthonErr(arr);
        TestCase.assertEquals(0, res, NORM_ERROR);
        SlaterLcr slater = new SlaterLcr(wCR);
        SysTwoE sys = new SysTwoE(-2., slater);
        currBasis = ConfigArrFactory.makeTwoElecFrom(cumBasis, LS, N, L, arr);
        HMtrx H = new HMtrx(currBasis, sys);
        EigenvalueDecomposition eig = H.eig();
        LOG.report(this, "H=" + Vec.toString(eig.getRealEigenvalues()));
        double e0 = eig.getRealEigenvalues()[0];
        LOG.report(this, "N=" + N + ", E=" + e0);
        TestCase.assertEquals(true, EXACT < e0);
        e0 = Math.log(e0 - EXACT);
        arrE.add(e0);
        arrN.add(runningN++);
      }
      cumBasis = currBasis; // accumulate configurations
    }
    LOG.saveToFile(arrN.toArray(), arrE.toArray(), DIR, "E_" + LS.toString() + ".csv");
  }
}
