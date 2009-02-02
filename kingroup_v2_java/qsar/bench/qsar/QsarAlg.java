package qsar.bench.qsar;
import qsar.bench.qsar.MLR.MlrRes;

import javax.swingx.ProgressWnd;
/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 9/03/2007, 13:05:17
 */
public abstract class QsarAlg implements QsarAlgI
{
  private MlrRes mlrRes = new MlrRes();
  private String name;
  private String lastError;
  protected double[][] z;
  protected ProgressWnd progress = null;

  public QsarAlg(double[][] zTrain) {
    this.z = zTrain;
  }
  public String getName() {return name;}
  public void setName(String name)
  {
    this.name = name;
  }
  public String getLastError()
  {
    return lastError;
  }
  public void setLastError(String lastError)
  {
    this.lastError = lastError;
  }

  public int getNumPredictors()
  {
    return z[0].length - 1;
  }

  public MlrRes getMlrRes()
  {
    return mlrRes;
  }

  public void setMlrRes(MlrRes mlrRes)
  {
    this.mlrRes = mlrRes;
  }

  public double[][] getZ() {
    return z;
  }

  protected void cleanup() {
    if (progress != null)
      progress.close();
//    progress = null;
  }
  
}
