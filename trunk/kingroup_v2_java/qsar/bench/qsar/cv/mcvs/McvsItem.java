package qsar.bench.qsar.cv.mcvs;
import tsvlib.project.ProjectLogger;

import javax.utilx.bitset.CompBitSet;
import java.util.BitSet;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 11/06/2007, 12:55:45
 */
public class McvsItem extends CompBitSet
{
  private final static ProjectLogger log = ProjectLogger.getLogger(McvsItem.class);
  private int calcCount;
  private double sumLoss;
  private double avrLoss;   // average MSE (of prediction) = mseSum / mseCount

  private double lastLoss;
  private double lastF;

  private int bestCount;
  private double pVal = 1;

  public McvsItem(BitSet set)
  {
    super(set);
  }
  public int getBestCount()  {    return bestCount;  }
  public double getPVal()  {    return pVal;  }
  public double getLastLoss()  {    return lastLoss;  }
  public String toString() {
    return super.toString()
      + " last=" + (float) lastLoss
      + ", best#=" + bestCount
      + ", p=" + (float)pVal
      + ", amse=" + (float) avrLoss
      + ", calc#=" + calcCount
      + ", sum=" + (float) sumLoss;
  }
  public int getCalcCount()
  {
    return calcCount;
  }

  public double getAvrLoss()
  {
    return avrLoss;
  }

  public void addLoss(double mse)
  {
    lastLoss = mse;
    this.sumLoss += mse;
    this.calcCount++;
    avrLoss = sumLoss / calcCount; 
    log.trace("addMse() = ", this);
  }
  public void addBestCount()
  {
    this.bestCount++;
    pVal = (1.0 * calcCount - bestCount) / calcCount;
  }

  public double getLastF()
  {
    return lastF;
  }

  public void setLastF(double lastF)
  {
    this.lastF = lastF;
  }
}
