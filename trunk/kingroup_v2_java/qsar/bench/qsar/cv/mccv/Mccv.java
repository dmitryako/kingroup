package qsar.bench.qsar.cv.mccv;
import tsvlib.project.ProjectLogger;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 20/04/2007, 15:48:18
 */
public class Mccv
{
  private int validSize;
  private int numIter;
  private boolean saveCalibValidErrs;

  private static int type_count = 0;
  public static final int MED_AE = type_count++;
  public static final int MSE = type_count++;
  public static final int TMSE = type_count++; // trimmed mean of squared errors
  public static final int MAE = type_count++;
  public static final int TMAE = type_count++;
  public static final int ROB_CORR = type_count++;
  private int errorType;

  public Mccv() {
    init();
  }

  private void init()
  {
    loadDefault();
  }

  public void loadDefault()
  {
    setValidSize(10);
    setNumIter(100);
  }

  public int getValidSize()
  {
    return validSize;
  }

  public void setValidSize(int v)
  {
    ProjectLogger.getLogger(Mccv.class).trace("setValidSize(", v);
    this.validSize = v;
  }

  public int getNumIter()
  {
    return numIter;
  }

  public void setNumIter(int v)
  {
    ProjectLogger.getLogger(Mccv.class).trace("setNumIter(", v);
    this.numIter = v;
  }

  public String getInfo()
  {
    return "MCCV(N=" + numIter + " n_v=" + validSize + ")";
  }

  public boolean getSaveCalibValidErrs()
  {
    return saveCalibValidErrs;
  }

  public void setSaveCalibValidErrs(boolean saveCalibValidErrs)
  {
    this.saveCalibValidErrs = saveCalibValidErrs;
  }

  public int getErrorType()
  {
    return errorType;
  }

  public void setErrorType(int errorType)
  {
    this.errorType = errorType;
  }
}
