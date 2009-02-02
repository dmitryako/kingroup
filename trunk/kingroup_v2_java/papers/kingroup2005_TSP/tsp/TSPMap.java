package papers.kingroup2005_TSP.tsp;
import javax.mathx.MathX;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 31/05/2005, Time: 13:26:49
 */
public class TSPMap {
  private int size;
  private final double[] x;
  private final double[] y;
  public TSPMap(int capacity) {
    x = new double[capacity];
    y = new double[capacity];
  }
  public TSPMap(TSPMap from) {
    x = from.x;
    y = from.y;
  }
  public int size() {
    return size;
  }
  public void add(double xx, double yy) {
    x[size] = xx;
    y[size++] = yy;
  }
  public double distance(int from, int to) {
    return Math.sqrt(MathX.pow(x[from] - x[to], 2) + MathX.pow(y[from] - y[to], 2));
  }
  public double getX(int idx) {
    return x[idx];
  }
  public double getY(int idx) {
    return y[idx];
  }
}
