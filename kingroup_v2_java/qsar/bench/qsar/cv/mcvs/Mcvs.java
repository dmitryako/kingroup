package qsar.bench.qsar.cv.mcvs;
/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 6/06/2007, 11:32:21
 */
public class Mcvs
{
  private int numFixedXCols;
  private int numVars;
  private int numUpdatesPerView;
  private int numBest;
  private boolean maeNorm; // Mean abs error

  public void loadDefault()
  {
    setMaeNorm(false);
    setNumVars(2);
    setNumFixedXCols(0);
    setNumUpdatesPerView(100);
    setNumBest(10);
  }

  public String getInfo()
  {
    return "MCVS(k=" + numVars +")";
  }
  public int getNumVars()
  {
    return numVars;
  }

  public void setNumVars(int numVars)
  {
    this.numVars = numVars;
  }

  public void setNumUpdatesPerView(int numUpdatesPerView)
  {
    this.numUpdatesPerView = numUpdatesPerView;
  }

  public int getNumUpdatesPerView()
  {
    return numUpdatesPerView;
  }

  public int getNumBest()
  {
    return numBest;
  }

  public void setNumBest(int numBest)
  {
    this.numBest = numBest;
  }

  public int getNumFixedXCols()
  {
    return numFixedXCols;
  }

  public void setNumFixedXCols(int numFixedXCols)
  {
    this.numFixedXCols = numFixedXCols;
  }

  public boolean getMaeNorm()
  {
    return maeNorm;
  }

  public void setMaeNorm(boolean maeNorm)
  {
    this.maeNorm = maeNorm;
  }
}
