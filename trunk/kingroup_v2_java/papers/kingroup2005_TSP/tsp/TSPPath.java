package papers.kingroup2005_TSP.tsp;
import java.util.BitSet;
import java.util.Iterator;
import java.util.LinkedList;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 31/05/2005, Time: 13:35:03
 */
public class TSPPath {
  private final TSPMap map;
  private BitSet bitSet;
  private final LinkedList path;
  private double dist;
  public TSPPath(TSPMap src) {
    map = src;
    path = new LinkedList();
    bitSet = new BitSet();
  }
  public TSPPath(TSPPath src) {
    map = src.map;
    path = new LinkedList(src.path);
    bitSet = new BitSet();
    bitSet.or(src.bitSet);
  }
  public int size() {
    return path.size();
  }
  public double distance() {
    return dist;
  }
  public String toString() {
    StringBuffer buf = new StringBuffer();
//      buf.append(IntVec.toString(path));
    buf.append(path.toString());
    return buf.toString();
  }
  public boolean add(int idx, int i) {
    if (bitSet.get(i)) {
      throw new RuntimeException("this id has been set already"); // already used
    }
    path.add(idx, new Integer(i));
    bitSet.set(i);
    return true;
  }
  public void loadDistance() {
    dist = 0;
    int first = -1;
    int from = -1;
    int to = -1;
    for (Iterator it = path.iterator(); it.hasNext();) {
      from = to;
      to = ((Integer) it.next()).intValue();
      if (from == -1) {
        first = to;
      }
      if (to != -1 && from != -1) {
        double d = map.distance(from, to);
//            LOG.report(this, "D("+from+", "+to+")=" + (float)d);
        dist += d;
      }
    }
    double d = map.distance(first, to);
//      LOG.report(this, "D("+from+", "+first+")=" + (float)d);
    dist += d;
  }
  public double[] buildX() {
    double[] res = new double[size() + 1];
    int i = 0;
    for (Iterator it = path.iterator(); it.hasNext(); i++) {
      int idx = ((Integer) it.next()).intValue();
      res[i] = map.getX(idx);
    }
    res[i] = res[0];
    return res;
  }
  public double[] buildY() {
    double[] res = new double[size() + 1];
    int i = 0;
    for (Iterator it = path.iterator(); it.hasNext(); i++) {
      int idx = ((Integer) it.next()).intValue();
      res[i] = map.getY(idx);
    }
    res[i] = res[0];
    return res;
  }
}
