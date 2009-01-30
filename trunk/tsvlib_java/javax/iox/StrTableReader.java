package javax.iox;
import tsvlib.project.ProjectLogger;

import javax.utilx.arrays.StrMtrx;
import javax.utilx.arrays.StrVec;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 29/03/2007, 17:46:19
 */
public class StrTableReader
{
  private static final ProjectLogger log = ProjectLogger.getLogger(StrTableReader.class.getName());

  public static StrTable makeFromFile(TextFile fileModel, TableFormat format, boolean showError)
  {
    StrMtrxReader reader = new StrMtrxReader("" + format.getColumnDelim());
    String[][] text = reader.readMtrx(-1, -1, fileModel, 0, showError);
    if (text == null)
      return null;
//    log.info("\ntext\n" + StrVec.toString(text));

    StrMtrx mtrx = new StrMtrx(text);
    String[][] data = mtrx.getRows(format.getFirstRowIdx(), format.getLastRowIdx(), showError);
    mtrx = new StrMtrx(data);
    data = mtrx.getCols(format.getFirstColIdx(), format.getLastColIdx(), showError);
    if (data == null)
      return null;
//    log.info("\ndata\n" + StrVec.toString(data));
    StrTable res = makeFromStrMtrx(data, showError);
    if (res == null)
      return null;
    if (format.getHeaderRowIdx() >= 0) {
      String[] colIds = StrMtrx.getRow(format.getHeaderRowIdx(), text, showError);
      if (colIds == null)
        return null;
//      log.info("\ncolIds\n" + StrVec.toString(colIds));
      colIds = StrVec.get(format.getFirstColIdx(), colIds.length, colIds);
      res.setColIds(colIds);
    }
    if (format.getHeaderColIdx() >= 0) {
      String[] rowIds = StrMtrx.getCol(format.getHeaderColIdx(), text, showError);
      if (rowIds == null)
        return null;
//      log.info("\nrowIds\n" + StrVec.toString(rowIds));
      rowIds = StrVec.get(format.getFirstRowIdx(), rowIds.length, rowIds);
      res.setRowIds(rowIds);
    }
    res.setName(fileModel.getFileName());
//    log.info("\nres\n" + res);
    return res;
  }
  public static StrTable makeFromStrMtrx(String[][] from, boolean showError) {
    return new StrTable(from);
  }
}
