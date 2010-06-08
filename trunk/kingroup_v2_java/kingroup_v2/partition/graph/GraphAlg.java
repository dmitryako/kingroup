package kingroup_v2.partition.graph;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.partition.PartitionAlg;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.utilx.arrays.IntVec;
import javax.utilx.pair.IntPair2;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 4/07/2005, Time: 12:38:29
 */
public class GraphAlg extends PartitionAlg {
  private final SysPop pop;
  public GraphAlg(SysPop pop) {
    this.pop = pop;
  }
  public Partition partition() {
    runTests();
    Partition res = new Partition();
    return res;
  }
  public void runTests() {
    GraphAlgJNI jni = new GraphAlgJNI();
    //     jni.partitionTestHelloWorld();
    int SIZE = 3;
    int[] arr = IntVec.makeIdxArr(SIZE);
//        jni.partitionTestIntArray(arr);
    int[][] array2D = get2DIntArray();
    jni.partition(get2DIntArray());
  }
  private int[][] get2DIntArray() {
    int numLoci = pop.getNumLoci();
    int[][] array = new int[pop.size()][(numLoci * 2) + 1];
    System.err.println("row size = " + array.length + " | col size = " + array[0].length);
    IntPair2 pair = null;
    for (int rows = 0; rows < array.length; rows++) {
      array[rows][0] = rows;
      System.err.print("Indiv " + rows + ": ");
      for (int cols = 1; cols <= numLoci; cols++) {
        // [dk20060509] changed IntPairSorted to Int2
        pair = pop.getAllelePair((short) rows, (int) (cols - 1));
        array[rows][cols * 2] = pair.b;
        array[rows][(cols * 2) - 1] = pair.a;
        System.err.println("{" + pair.a +
          "|" + pair.b + "}");
      }
    }
    return array;
  }
}
