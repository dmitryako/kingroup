package papers.kingroup2005a_distance;
import junit.framework.TestCase;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PartitionFactory;
import kingroup_v2.partition.distance.AlmudevarField1999.AlmudevarFieldDistance;
import kingroup_v2.partition.distance.DistanceAlgorithm;
import kingroup_v2.partition.distance.LitowDistance;
import kingroup_v2.partition.distance.LitowDistanceJNI;

import javax.iox.LOG;
import javax.langx.SystemX;
import javax.utilx.DoubleArrayByInt;
import javax.utilx.pair.SumByInt;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Nov 10, 2004, Time: 7:50:36 AM
 */
public class PerformanceFigureTestsR //extends PerformanceChart
{
  private DoubleArrayByInt allTimes_ = new DoubleArrayByInt();
  private SumByInt totalTime_ = new SumByInt();
  private SumByInt totalLog_ = new SumByInt();
  private SumByInt totalCount_ = new SumByInt();
  private DoubleArrayByInt allTimes2_ = new DoubleArrayByInt();
  private SumByInt totalTime2_ = new SumByInt();
  private SumByInt totalLog2_ = new SumByInt();
  private SumByInt totalCount2_ = new SumByInt();
  private int GROUP_SIZE_ = 1;
  private final int MIN_EFFECT_SIZE_ = 4; // 4-paper. the first may be removed leaving 5 in the figures
  private int MAX_EFFECT_SIZE_ = 10;
  private final boolean REMOVE_LOW_COUNT_ = true;
  private final int MIN_COUNT_ = 100; // 100-paper
  public static void main(String[] args) {
    PerformanceFigureTestsR test = new PerformanceFigureTestsR();
    System.exit(0);
  }
  public PerformanceFigureTestsR() {
//    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //javax.iox.LOG.setTrace(true);
    DistanceAlgorithm algAF = new AlmudevarFieldDistance();
    DistanceAlgorithm algJNI = new LitowDistanceJNI();
    DistanceAlgorithm alg = new LitowDistance();
    GROUP_SIZE_ = 1;
    MAX_EFFECT_SIZE_ = 55; // 55-paper
    makeFigureWithGroups(algJNI, alg, "figure_1_high_jni.txt", "figure_1_high_java.txt");
    MAX_EFFECT_SIZE_ = 30; // 30-paper
    makeFigureWithGroups(algAF, alg, "figure_1_low_AF.txt", "figure_1_low_java.txt");
    GROUP_SIZE_ = 10;
    MAX_EFFECT_SIZE_ = 55; // 55-paper
    makeFigureWithGroups(algJNI, alg, "figure_10_high_jni.txt", "figure_10_high_java.txt");
    MAX_EFFECT_SIZE_ = 12; // 12-paper
    makeFigureWithGroups(algAF, alg, "figure_10_low_AF.txt", "figure_10_low_java.txt");
  }
  public void makeFigureWithGroups(DistanceAlgorithm alg, DistanceAlgorithm alg2
    , String fileName, String fileName2) {
    init();
    final long TIME_SCALE = 1000000;
    int doneMinSize = MIN_EFFECT_SIZE_ - 1;
    int doneMaxSize = MAX_EFFECT_SIZE_ + 1;
    int startPopSize = GROUP_SIZE_ * MIN_EFFECT_SIZE_ / GROUP_SIZE_;
    int endPopSize = 2 * GROUP_SIZE_ * MAX_EFFECT_SIZE_;
    while (doneMinSize < doneMaxSize) {
      for (int popSize = startPopSize; popSize < endPopSize; popSize += GROUP_SIZE_) {
        PartitionFactory factory = new PartitionFactory(popSize);
        Partition A = factory.makeByGroupSize(GROUP_SIZE_);
        Partition B = factory.makeRandomByIncrement();
//            javax.iox.LOG.error(this, "A=", A);
//            javax.iox.LOG.error(this, "B=", B);
        int size = alg.calcEffectiveComlpexitySize(A, B);
        int currCount = totalCount_.getInt(size);
        if (totalCount_.getInt(doneMinSize + 1) >= MIN_COUNT_)
          doneMinSize++;
        if (totalCount_.getInt(doneMaxSize - 1) >= MIN_COUNT_)
          doneMaxSize--;
        if (size > doneMaxSize || size > MAX_EFFECT_SIZE_  // ignore higher pop sizes in the loop
          || doneMinSize >= doneMaxSize) // all done
          break;
        if (size < MIN_EFFECT_SIZE_ || currCount >= MIN_COUNT_)
          continue;
        System.gc();
//            javax.iox.LOG.report(this, "size=" + size +", currCount=" + currCount);
        long time = SystemX.time();
        int dist = alg.distance(A, B);
        double d = (double) (SystemX.time() - time) / TIME_SCALE;
        collect(size, d, allTimes_, totalTime_, totalLog_, totalCount_);
        time = SystemX.time();
        int dist2 = alg2.distance(A, B);
        d = (double) (SystemX.time() - time) / TIME_SCALE;
        collect(size, d, allTimes2_, totalTime2_, totalLog2_, totalCount2_);
        TestCase.assertEquals(dist, dist2);
      }
    }
    if (REMOVE_LOW_COUNT_) {
      removeLowCount(allTimes_, totalTime_, totalLog_, totalCount_);
      removeLowCount(allTimes2_, totalTime2_, totalLog2_, totalCount2_);
    }
    divideByCount();
    saveAllToFile(fileName, fileName2);
  }
  private void saveAllToFile(String fileName, String fileName2) {
    LOG.saveToFile(allTimes_, fileName);
    LOG.saveToFile(allTimes_, fileName + "minCount_" + MIN_COUNT_);
    LOG.saveToFile(allTimes2_, fileName2);
    LOG.saveToFile(allTimes2_, fileName2 + "minCount_" + MIN_COUNT_);
  }
  private void removeLowCount(DoubleArrayByInt allTimes, SumByInt totalTime
    , SumByInt totalLog, SumByInt totalCount) {
    Set keys = totalCount.keySet();
    ArrayList removes = new ArrayList();
    for (Iterator it = keys.iterator(); it.hasNext();) {
      Object key = it.next();
      double count = totalCount.getDouble(key);
      if (count < MIN_COUNT_)
        removes.add(key);
    }
    for (int i = 0; i < removes.size(); i++) {
      Object key = removes.get(i);
      totalCount.remove(key);
      allTimes.remove(key);
      totalLog.remove(key);
      totalTime.remove(key);
    }
  }
  private void collect(int size, double v
    , DoubleArrayByInt allTimes, SumByInt totalTime
    , SumByInt totalLog, SumByInt totalCount) {
    allTimes.add(size, Math.log(v));
    totalTime.sum(size, v);
    totalLog.sum(size, Math.log(v));
    totalCount.sum(size, 1);
  }
  private void init() {
    allTimes_ = new DoubleArrayByInt();
    totalTime_ = new SumByInt();
    totalLog_ = new SumByInt();
    totalCount_ = new SumByInt();
    allTimes2_ = new DoubleArrayByInt();
    totalTime2_ = new SumByInt();
    totalLog2_ = new SumByInt();
    totalCount2_ = new SumByInt();
  }
  private void divideByCount() {
    totalTime_.divide(totalCount_);
    totalLog_.divide(totalCount_);
    totalTime2_.divide(totalCount2_);
    totalLog2_.divide(totalCount2_);
  }
}