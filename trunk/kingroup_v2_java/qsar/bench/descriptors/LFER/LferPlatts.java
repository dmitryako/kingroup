package qsar.bench.descriptors.LFER;

import javax.iox.table.Table;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 1/04/2007, 11:15:35
 */
public class LferPlatts
{
  private boolean showLferTable;
  public static final String REFERENCE = "Platts et al (1999) J.Chem.Inf.Comput.Sci. 39 835-845";
  private Table lferTable;

  public LferPlatts() {
    loadDefault();
  }

  public void loadDefault()
  {
    double[][] lferMtrx = {
      {0.248, 0.277, 0.071, 0.064, 0.130} //intercept
    , {-0.104, -0.075, 0.007, 0.000, 0.321} };
    String[] colIds = {"E", "S", "A", "B", "L"};
    String[] rowIds = {"intercept"
    , "-CH3"};
    lferTable = new Table(lferMtrx);
    lferTable.setColIds(colIds);
    lferTable.setRowIds(rowIds);
  }

  public boolean getShowLferTable()
  {
    return showLferTable;
  }

  public void setShowLferTable(boolean showLferTable)
  {
    this.showLferTable = showLferTable;
  }

  public Table getLferTable()
  {
    return lferTable;
  }

  public void setLferTable(Table lferTable)
  {
    this.lferTable = lferTable;
  }
}
