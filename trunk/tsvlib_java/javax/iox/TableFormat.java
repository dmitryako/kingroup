package javax.iox;
/**
 * Copyright www.DmitryKonovalov.org
 * User: jc138691, Date: 6/03/2007, Time: 13:42:02
 */
public class TableFormat
{
  private char columnDelim;
  private int firstCol;
  private int firstRow;
  private int lastCol;
  private int lastRow;
  private int headerRow;
  private int headerCol;
  private String colName;

  public TableFormat() {
    loadDefaults();
  }
  public void loadDefaults() {
    columnDelim = '\t';
    setHeaderRow(1);
    setHeaderCol(1);
    setFirstRow(2);
    setFirstCol(2);
    setLastRow(0);
    setLastCol(0);
  }

  public char getColumnDelim()
  {
    return columnDelim;
  }

  public void setColumnDelim(char columnDelim)
  {
    this.columnDelim = columnDelim;
  }

  public int getFirstCol()
  {
    return firstCol;
  }

  public int getFirstColIdx()
  {
    return firstCol - 1;
  }

  public int getLastColIdx()
  {
    return lastCol - 1;
  }

  public void setFirstCol(int firstCol)
  {
    this.firstCol = firstCol;
  }

  public int getFirstRow()
  {
    return firstRow;
  }

  public int getFirstRowIdx()
  {
    return firstRow - 1;
  }

  public int getLastRowIdx()
  {
    return lastRow - 1;
  }

  public void setFirstRow(int firstRow)
  {
    this.firstRow = firstRow;
  }


  public int getHeaderRow()
  {
    return headerRow;
  }
  public int getHeaderRowIdx()
  {
    return headerRow - 1;
  }

  public void setHeaderRow(int headerRow)
  {
    this.headerRow = headerRow;
  }

  public int getHeaderCol()
  {
    return headerCol;
  }
  public int getHeaderColIdx()
  {
    return headerCol - 1;
  }

  public void setHeaderCol(int headerCol)
  {
    this.headerCol = headerCol;
  }

  public int getLastCol()
  {
    return lastCol;
  }

  public void setLastCol(int lastCol)
  {
    this.lastCol = lastCol;
  }

  public int getLastRow()
  {
    return lastRow;
  }

  public void setLastRow(int lastRow)
  {
    this.lastRow = lastRow;
  }

  public String getColName() {
    return colName;
  }

  public void setColName(String colName) {
    this.colName = colName;
  }
}
