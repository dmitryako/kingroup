package cp3110;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
class MathX {
  public static final int NOT_FOUND = -1;
  public static int findMinFaulty(int[] y) {
    int res = 0;
    for (int i = 0; i < y.length; i++) {
      if (res > y[i])
        res = y[i];
    }
    return res;
  }
  public static int findMin(int[] y) {
    assert(y != null && y.length != 0);  // correct
    if (y == null || y.length == 0) {
      throw new RuntimeException("findMin(invalid input)"
        + "(y == null || y.length == 0)"); // still a bad choice #3
    }
    if (y == null || y.length == 0)
      return 0; // bad choice #1
    if (y == null || y.length == 0)
      return Integer.MIN_VALUE; // bad choice #2
    int res = y[0];
    for (int i = 0; i < y.length - 1; i++) {
      if (res > y[i])
        res = y[i];
    }
    return res;
  }
  public static int findMinIdx(int[] y) {
    if (y == null || y.length == 0)
      return -1;          // correct
    if (y == null || y.length == 0) {
      throw new RuntimeException("findMin(invalid input)"
        + "(y == null || y.length == 0)"); // wrong
    }
    assert(y != null && y.length != 0);  // correct
    int minVal = y[0];
    int res = 0;
    for (int i = 0; i < y.length; i++) {
      if (minVal > y[i]) {
        res = i;
        minVal = y[i];
      }
    }
    return res;
  }
}
public class ch6 extends TestCase {
  public static Test suite() {
    return new TestSuite(ch6.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testFindMin() {
    int[] y = {-1, 3, 2};
    assertEquals(-1, MathX.findMinFaulty(y));
    assertEquals(-1, MathX.findMin(y));
    assertEquals(0, MathX.findMinIdx(y));
  }
  public void testFindMin2() {
    int[] y = {-1, -3, -2};
    assertEquals(-3, MathX.findMinFaulty(y));
    assertEquals(-3, MathX.findMin(y));
    assertEquals(1, MathX.findMinIdx(y));
  }
  public void testFindMin3() {
    int[] y = {1, 3, 2};
    assertEquals(1, MathX.findMinFaulty(y));
    assertEquals(1, MathX.findMin(y));
    assertEquals(0, MathX.findMinIdx(y));
  }
  public void testFindMin4() {
    int[] y = {-1, 3, 0};
    assertEquals(-1, MathX.findMinFaulty(y));
    assertEquals(-1, MathX.findMin(y));
    assertEquals(0, MathX.findMinIdx(y));
  }
  public void testFindMinZero() {
    int[] y = {};
    assertEquals(0, MathX.findMinFaulty(y)); // wrong test
    assertEquals(-1, MathX.findMin(y));
    assertEquals(0, MathX.findMinIdx(y));
  }
  public void testFindMinZero2() {
    int[] y = null;
    assertEquals(-1, MathX.findMinIdx(y));
    assertEquals(MathX.NOT_FOUND, MathX.findMinIdx(y));
  }
}
