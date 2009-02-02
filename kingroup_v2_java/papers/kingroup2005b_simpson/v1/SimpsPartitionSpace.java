package papers.kingroup2005b_simpson.v1;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.iox.LOG;
import javax.mathx.BinomialCoeff;
import javax.mathx.Factorial;
import javax.mathx.MathX;
import java.io.File;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 15/05/2005, Time: 12:27:02
 */
public class SimpsPartitionSpace extends TestCase {
  private static final String DIR = "papers" + File.separator + "kingroup2005b_simpson" + File.separator
    + "output" + File.separator + "simpson";
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public static Test suite() {
    return new TestSuite(SimpsPartitionSpace.class);
  }
  public void setUp() {
//      int POP_SIZE = 50;
  }
  /*
  http://www.math.uaa.alaska.edu/~smiley/stir2eul2.html
  The associated Stirling numbers of the second kind were developed in J. Riordan's book,
  "An Introduction to Combinatorial Analysis" (Wiley, 1958).
  Now denoted by {{n,k}}, they count the ways in which n distinct objects may be partitioned into
  exactly k true heaps (a true heap has more than one object).
  */
  /*
  http://mathworld.wolfram.com/StirlingNumberoftheSecondKind.html
  The number of ways of partitioning a set of n elements into m nonempty sets (i.e., m set blocks),
  also called a Stirling set number.
  For example, the set {1,2,3} can be partitioned into three subsets in one way: {{1}{2}{3}};
  into two subsets in three ways: {{12}{3}}, {{1}{23}}, and {{13}{2}};
  and into one subset in one way: {{123}}.
  */
  public void testPopSpace() {
    LOG.setTrace(true);
    int MAX_POP_SIZE = 50;
    double[] arr = new double[MAX_POP_SIZE + 1];
    double[] Bn = new double[MAX_POP_SIZE + 1];
    double[] pArr = new double[MAX_POP_SIZE + 1];
    double[] p2 = new double[MAX_POP_SIZE + 1];
    double[] expp = new double[MAX_POP_SIZE + 1];
    double[] factp = new double[MAX_POP_SIZE + 1];
    double[] Ng = new double[MAX_POP_SIZE + 1];
    double[] xNg = new double[MAX_POP_SIZE + 1];
    BinomialCoeff C = new BinomialCoeff(arr.length);
    Factorial F = new Factorial(arr.length);
    arr[0] = 1;
    arr[1] = 1;
    for (int n = 1; n <= MAX_POP_SIZE; n++) {
      double sum = 0;
      for (int k = 1; k <= n; k++) {
        sum += arr[k - 1] * C.nCk(n - 1, k - 1);
      }
      arr[n] = sum;
      LOG.report(this, "N(" + n + ")=" + arr[n]);
      pArr[n] = n;
      Bn[n] = sum;
      p2[n] = n * n * n;
      expp[n] = Math.exp(n - 1);
      factp[n] = F.calc(n);
      if (n % 2 == 0) {
        Ng[n / 2] = F.calc(n) / (F.calc(n / 2) * MathX.pow(2., n / 2));
        xNg[n / 2] = n;
      }
    }
    LOG.saveToFile(pArr, Bn, DIR, "Bn.csv");
    LOG.saveToFile(pArr, p2, DIR, "n3.csv");
    LOG.saveToFile(pArr, expp, DIR, "exp_n.csv");
    LOG.saveToFile(pArr, factp, DIR, "factorial_n.csv");
    LOG.saveToFile(xNg, Ng, DIR, "Ng_n.csv");
  }
}
