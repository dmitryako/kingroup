package papers.kingroup2005a_distance;
import junit.framework.TestCase;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PartitionFactory;
import kingroup_v2.partition.distance.AlmudevarField1999.AlmudevarFieldDistance;
import kingroup_v2.partition.distance.DistanceAlgorithm;
import kingroup_v2.partition.distance.LitowDistance;

import javax.iox.LOG;
import javax.langx.SystemX;
import javax.utilx.DoubleArrayByInt;
import java.io.File;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Jan 13, 2005, Time: 11:34:05 AM
 */
public class PerformanceFigureTestRM //extends PerformanceChart
{
  private DoubleArrayByInt allTimes = new DoubleArrayByInt();
  private DoubleArrayByInt allTimes2 = new DoubleArrayByInt();
  protected String DIR = "distance";
  private final int NUM_REPEATS = 100; // 100-paper
  private int FIRST_X = 5; // 30-paper
  private int LAST_X = 15; // 30-paper
  private DistanceAlgorithm ALG_AF = new AlmudevarFieldDistance();
  private DistanceAlgorithm ALG_LITOW = new LitowDistance();
  public static void main(String[] args) {
    PerformanceFigureTestRM test = new PerformanceFigureTestRM();
    System.exit(0);
  }
  public PerformanceFigureTestRM() {
//    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    javax.iox.LOG.setTrace(true);
    makeFigureWithGroups(5, 10, "figure2_5_10_AF.txt", "figure2_5_10_java.txt");
    makeFigureWithGroups(5, 40, "figure2_5_40_AF.txt", "figure2_5_40_java.txt");
    makeFigureWithGroups(10, 10, "figure2_10_10_AF.txt", "figure2_10_10_java.txt");
    makeFigureWithGroups(20, 10, "figure2_20_10_AF.txt", "figure2_20_10_java.txt");
  }
  private void init() {
    allTimes = new DoubleArrayByInt();
    allTimes2 = new DoubleArrayByInt();
  }
  public void makeFigureWithGroups(int numGroups, int groupSize, String fileName, String fileName2) {
    init();
    DistanceAlgorithm alg = ALG_AF;
    DistanceAlgorithm alg2 = ALG_LITOW;
    final long TIME_SCALE = 1000000;
    int popSize = numGroups * groupSize;
    PartitionFactory factory = new PartitionFactory(popSize);
    for (int c = 0; c < NUM_REPEATS; c++) {
//         System.gc();
      for (int x = FIRST_X; x <= LAST_X; x++) {
        Partition A = factory.makeByGroupSize(groupSize);
//            javax.iox.LOG.trace(this, "A=", A);
        Partition B = factory.makeByMoving(A, x);
//            javax.iox.LOG.trace(this, "B=", B);
//            int size = alg.calcEffectiveComlpexitySize(A, B);
        long time = SystemX.time();
        int dist = alg.distance(A, B);
        double d = (double) (SystemX.time() - time) / TIME_SCALE;
        allTimes.add(x, Math.log(d));
        javax.iox.LOG.trace(this, "dist=" + dist);
        time = SystemX.time();
        int dist2 = alg2.distance(A, B);
        d = (double) (SystemX.time() - time) / TIME_SCALE;
        allTimes2.add(x, Math.log(d));
        TestCase.assertEquals(dist, dist2);
      }
    }
    LOG.saveToFile(allTimes, DIR, fileName);
    LOG.saveToFile(allTimes, DIR + File.separator + NUM_REPEATS, fileName);
    LOG.saveToFile(allTimes2, DIR, fileName2);
    LOG.saveToFile(allTimes2, DIR + File.separator + NUM_REPEATS, fileName2);
  }
}