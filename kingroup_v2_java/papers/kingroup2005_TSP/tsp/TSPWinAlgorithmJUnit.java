package papers.kingroup2005_TSP.tsp;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.iox.LOG;
import java.io.File;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 1/06/2005, Time: 16:21:18
 */
public class TSPWinAlgorithmJUnit extends TestCase {
  private static final double EPS = 1e-10;
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public static Test suite() {
    return new TestSuite(TSPWinAlgorithmJUnit.class);
  }
  public void setUp() {
  }
  public void testSolve() {
    int CAPACITY = 9;
    int WINDOW_SIZE = 30;
    int NUM_TRIALS = 10;
    TSPMap map = null;
    TSPBruteAlgorithm brute = null;
    TSPWinAlgorithm win = null;
    TSPPath resBru = null;
    TSPPath resWin = null;
    try {
      for (int i = 0; i < NUM_TRIALS; i++) {
        map = TSPMapFactory.makeMap(CAPACITY);
        brute = new TSPBruteAlgorithm(map);
        win = new TSPWinAlgorithm(map, WINDOW_SIZE);
        resBru = brute.solve();
        resWin = win.solve();
        LOG.report(this, "brute=" + (float) resBru.distance() + ", win=" + (float) resWin.distance());
        if (i == 0) {
          LOG.saveToFile(resWin.buildX(), resWin.buildY(), "tsp" + File.separator + "path.csv");
        }
        assertEquals(resBru.distance(), resWin.distance(), EPS);
      }
    } catch (junit.framework.AssertionFailedError e) {
      LOG.saveToFile(resWin.buildX(), resWin.buildY(), "tsp" + File.separator + "path_win.csv");
      LOG.saveToFile(resBru.buildX(), resBru.buildY(), "tsp" + File.separator + "path_brute.csv");
    }
  }
}