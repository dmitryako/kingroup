package kingroup_v2.pedigree.ratio;
import kingroup.genetics.Like;

import javax.utilx.arrays.vec.Vec;
import java.util.ArrayList;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 6/03/2006, Time: 17:19:26
 */
public class PedigreeRatioHist
{
  private int numBins;
  private double minR;
  private double maxR;
  private ArrayList<double[]> list;

  public PedigreeRatioHist() {
    init();
  }

  private void init()
  {
    list = new ArrayList<double[]>();
    numBins = 10;
    minR = Like.MIN_LOG;
    maxR = Like.MIN_LOG;
  }

  public void add(double[] arr)
  {
    list.add(arr);
    int idx = Vec.findLastIdx(arr, Like.MIN_LOG);
    double newMin = arr[idx+1];
    double newMax = arr[arr.length-1];
    if (newMin < minR)
        newMin = minR;
    if (newMax > maxR)
        newMax = maxR;
  }
//  private void ddo() {
//    double minR = r[0];
//    double maxR = r[r.length-1];
//    int SIZE = 100;
//    double STEP = (maxR - minR) / (SIZE-1);
//    double[] bins = DoubleArr.makeStep(0, STEP, SIZE);
//    double[] y = DoubleArr.histL(r, bins);
//    DoubleArr.norm(y, 1.0);
//  }

  public int getNumBins()
  {
    return numBins;
  }

  public double getMinR()
  {
    return minR;
  }

  public double getMaxR()
  {
    return maxR;
  }
}
