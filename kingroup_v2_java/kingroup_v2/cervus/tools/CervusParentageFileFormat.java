package kingroup_v2.cervus.tools;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 29/01/2006, Time: 10:35:33
 */
public class CervusParentageFileFormat {
  private int idColumn;
  private int parentColumn;
  private char columnDelim;
  private int numProgenies;

  public void loadDefaults() {
    setColumnDelim(','); // CSV-file
    setIdColumn(1);
    setParentColumn(9);
    setNumProgenies(10);
  }
  public char getColumnDelim() {
    return columnDelim;
  }
  public void setColumnDelim(char s) {
    columnDelim = s;
  }
  public int getIdColumn() {
    return idColumn;
  }
  public void setIdColumn(int i) {
    idColumn = i;
  }
  public int getParentColumn() {
    return parentColumn;
  }

  public void setParentColumn(int parentColumn) {
    this.parentColumn = parentColumn;
  }

  public String getColumnDelimStr() {
    return Character.toString(columnDelim);
  }

  public int getNumProgenies() {
    return numProgenies;
  }

  public void setNumProgenies(int numProgenies) {
    this.numProgenies = numProgenies;
  }
}