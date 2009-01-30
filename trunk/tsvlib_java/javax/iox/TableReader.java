package javax.iox;
import tsvlib.project.ProjectLogger;

import javax.iox.table.Table;
import javax.swing.*;
import javax.swingx.ProgressWnd;
import javax.utilx.arrays.StrMtrx;
import javax.utilx.arrays.StrVec;
import java.io.File;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Copyright www.DmitryKonovalov.org
 * User: jc138691, Date: 6/03/2007, Time: 15:16:10
 */
public class TableReader
{
  private static final ProjectLogger log = ProjectLogger.getLogger(TableReader.class);
  private static ProgressWnd progress = null;

  public static Table makeFromFile(TextFile fileModel, TableFormat format, boolean showError)
  {
    log.trace("makeFromFile");
    log.trace("fileModel = \n", fileModel);
    StrMtrxReader reader = new StrMtrxReader("" + format.getColumnDelim());
    String[][] from = reader.readMtrx(-1, -1, fileModel, 0, showError);
    if (from == null)
      return null;
    log.trace("String[][] text = \n", new StrMtrx(from));

    StrMtrx mtrx = new StrMtrx(from);
    String[][] dataRows = mtrx.getRows(format.getFirstRowIdx(), format.getLastRowIdx(), true);

    mtrx = new StrMtrx(dataRows);
    if (!mtrx.hasSameNumColsPerRow(true)) {
      int n = JOptionPane.showConfirmDialog(null, "YES - Abort input.\nNo - Ignore errors.");
      if (n == JOptionPane.YES_OPTION)
        return null;
      mtrx.removeDiffSizeRows(true);
    }
    dataRows = mtrx.getByRows();
    mtrx = new StrMtrx(dataRows);
    String[][] data = mtrx.getCols(format.getFirstColIdx(), format.getLastColIdx(), showError);

    if (data == null)
      return null;
    log.trace("data = \n", new StrMtrx(data));

    Table res = makeFromStrMtrx(data, showError);
    if (res == null)
      return null;
    if (format.getHeaderRowIdx() >= 0) {
      String[] colIds = StrMtrx.getRow(format.getHeaderRowIdx(), from, showError);
      if (colIds == null)
        return null;
      log.trace("colIds = ", new StrVec(colIds));
      colIds = StrVec.get(format.getFirstColIdx(), colIds.length, colIds);
      res.setColIds(colIds);
    }
    if (format.getHeaderColIdx() >= 0) {
      String[] rowIds = StrMtrx.getCol(format.getHeaderColIdx(), dataRows, showError);
      if (rowIds == null)
        return null;
      log.trace("rowIds = ", new StrVec(rowIds));
//      rowIds = StrVec.get(format.getFirstRowIdx(), rowIds.length, rowIds); // working with dataRows NOT from
      res.setRowIds(rowIds);
    }
    res.setName(fileModel.getFileName());
    log.trace("res = \n", res);
    return res;
  }

  public static Table makeFromStrMtrx(String[][] from, boolean showError) {
    progress = new ProgressWnd(null, "Loading table ... ");
    int nRows = from.length;
    int nCols = from[0].length;
    NumberFormat numberFormat = NumberFormat.getNumberInstance();

    Table res = new Table(nRows, nCols);
    for (int r = 0; r < nRows; r++) {
      if (progress != null
        && progress.isCanceled(r, 0, nRows)) {
        return new Table();
      }
      for (int c = 0; c < nCols; c++) {
        double v = 0;
        try {
          v = numberFormat.parse(from[r][c]).doubleValue();
        } catch (ParseException e) {
          String error = "Unable to extract a number from"
            + "\n>" + from[r][c] + "<";
          log.severe(error);
          if (showError)
            JOptionPane.showMessageDialog(null, error);
          return null;
        }
        res.set(r, c, v);
      }
    }
    if (progress != null)
      progress.close();
    return res;
  }

  public static Table makeFromFile(String fileName, TableFormat format, boolean showError)
  {
    File file = new File(fileName);
    TextFile from = new TextFile();
    from.read(file, null);
    from.trim();
    return makeFromFile(from, format, showError);
  }
}
