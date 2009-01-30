package javax.iox.table;
import javax.textx.FractionDigitsModel;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 3/06/2007, 11:16:55
 */
public class TableDisplayOpt
{
  private FractionDigitsModel digitsModel;
  private int maxNumCols;
  private int maxNumRows;
  private boolean countOn;
  public static final int DEFAULT_MAX_NUM_ROWS = 1000;
  public static final int DEFAULT_MAX_NUM_COLS = 100;

  public TableDisplayOpt() {
    digitsModel = new FractionDigitsModel();
  }

  public FractionDigitsModel getDigitsModel()
  {
    return digitsModel;
  }

  public void setDigitsModel(FractionDigitsModel digitsModel)
  {
    this.digitsModel = digitsModel;
  }

  public int getMaxNumCols()
  {
    return maxNumCols;
  }

  public void setMaxNumCols(int maxNumCols)
  {
    this.maxNumCols = maxNumCols;
  }

  public int calcMaxNumCols(int nCols)
  {
    if (maxNumCols <= 0)
      return nCols;
    return Math.min(nCols, maxNumCols);
  }

  public int calcMaxNumRows(int nRows)
  {
    if (maxNumRows <= 0)
      return nRows;
    return Math.min(nRows, maxNumRows);
  }

  public boolean viewAll(int nCols)
  {
    if (maxNumCols <= 0)
      return true;
    return (nCols <= maxNumCols);
  }

  public void loadDefault()
  {
    setMaxNumCols(DEFAULT_MAX_NUM_COLS);
    setMaxNumRows(DEFAULT_MAX_NUM_ROWS);
    digitsModel.loadDefault();
    setCountOn(true);
  }

  public int getMaxNumRows()
  {
    return maxNumRows;
  }
  public void setMaxNumRows(int maxNumRows)
  {
    this.maxNumRows = maxNumRows;
  }

  public void setCountOn(boolean countOn)
  {
    this.countOn = countOn;
  }

  public boolean getCountOn()
  {
    return countOn;
  }
}
