package javax.iox;
import javax.utilx.arrays.StrMtrx;
import javax.langx.SysProp;
/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 29/03/2007, 17:41:09
 */
public class StrTable  extends StrMtrx
{
  private String name;
  private String[] rowIds;
  private String[] colIds;
  private static final int MAX_N_VIEW_ROWS = 100;

  public StrTable(int nRows, int nCols)
  {
    super(nRows, nCols);
    name = "";
  }

  public StrTable(String[][] from)
  {
    super(from);
    name = "";
  }

  public String toString() {
    StringBuffer buff = new StringBuffer();
    buff.append(colIdsToCSV());
    buff.append(SysProp.EOL);
    buff.append(toCSV(MAX_N_VIEW_ROWS));
    return buff.toString();
  }

  private String colIdsToCSV()
  {
    StringBuffer buff = new StringBuffer();
    buff.append(getName() + " , ");
    for (int c = 0; c < getNumCols(); c++) {
      buff.append(getColId(c));
      if (c != getNumCols()-1)
        buff.append(", ");
    }
    return buff.toString();
  }

  public String toCSV(int nRows) {
    StringBuffer buff = new StringBuffer();
    int nView = getNumRows();
    if (nRows != -1)
      nView = Math.min(nRows, nView);
    for (int r = 0; r < nView; r++) {
      buff.append(getRowId(r));
      buff.append(", ");
//      buff.append(Vec.toCSV(getRow(r)));
      buff.append(SysProp.EOL);
    }
    return buff.toString();
  }

  public String getColId(int c) {
    if (colIds == null  ||  colIds.length <= c  || c < 0)
      return Integer.toString(c+1);
    return colIds[c];
//    return Integer.toString(c+1) + "-" + colIds[c];
  }
  public String getRowId(int r) {
    if (rowIds == null  ||  rowIds.length <= r  || r < 0)
      return Integer.toString(r+1);
    return rowIds[r];
//    return Integer.toString(r+1) + "-" + rowIds[r];
  }

  public String getName()
  {
    return name;
  }
  public void setName(String name)
  {
    this.name = name;
  }

  public void setRowIds(String[] rowIds)
  {
    this.rowIds = rowIds;
  }

  public void setColIds(String[] colIds)
  {
    this.colIds = colIds;
  }
}

