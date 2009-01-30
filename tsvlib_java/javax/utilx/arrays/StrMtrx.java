package javax.utilx.arrays;
import tsvlib.project.ProjectLogger;

import javax.langx.SysProp;
import javax.swing.*;
import java.util.ArrayList;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 29/03/2007, 17:12:51
 */
public class StrMtrx
{
  private static final ProjectLogger log = ProjectLogger.getLogger(StrMtrx.class);

  private String[][] mtrx;
  private static final int MAX_STR_LEN = 50;

  public StrMtrx() {
    mtrx = new String[0][0];
  }
  public StrMtrx(String[][] from) {
    mtrx = from;
  }
  public StrMtrx(int nRows, int nCols) {
    mtrx = new String[nRows][nCols];
  }
  public int getNumRows() {
    return mtrx.length;
  }
  public int getNumCols() {
    return mtrx[0].length;
  }
  public String toString() {
    return toString(mtrx);
  }
  public String[][] getByRows() {return mtrx;}
  public void set(int r, int c, String v) {mtrx[r][c] = v;}
  public String get(int r, int c) {return mtrx[r][c];}
  public String[] getRow(int r) { return mtrx[r];}
  public String[] getColCopy(int c) {
    String[] res = new String[getNumRows()];
    for (int r = 0; r < res.length; r++) {
      res[r] = mtrx[r][c];
    }
    return res;
  }
  public String[][] getRows(int idxFirst, int idxExcl) {
    return getRows(idxFirst, idxExcl, mtrx);
  }

  public String[][] getCols(int idxFirst, int idxExcl) {
    return getCols(idxFirst, idxExcl, mtrx);
  }

  public static String[][] selectColsByName(String[][] arr, String[] names)
  {
    int[] orderIdx = StrVec.find(names, arr[0]);
    return getCols(arr, orderIdx);
  }

  public static void set(String[][] mtrx, String val) {
    for (int r = 0; r < mtrx.length; r++) {
      String[] vec = mtrx[r];
      StrVec.set(vec, val);
    }
  }

  public static String[][] getRows(int idxFirst, int idxExcl, String[][] arr) {
    int n = idxExcl - idxFirst;
    String[][] res = new String[n][0];
    int count = 0;
    for (int r = idxFirst; r < idxExcl; r++) {
        res[count++] = arr[r];
    }
    return res;
  }
  public static String[][] getCols(int idxFirst, int idxExcl, String[][] arr) {
    int nCol = idxExcl - idxFirst;
    String[][] res = new String[arr.length][nCol];
    for (int r = 0; r < res.length; r++) {
      for (int c = idxFirst; c < idxExcl; c++)
        res[r][c - idxFirst] = arr[r][c];
    }
    return res;
  }
  public static String[][] getCols(String[][] arr, int[] idxArr)
  {
    int nR = arr.length;
    int n = idxArr.length;
    String[][] res = new String[nR][n];
    for (int i = 0; i < n; i++) {
      for (int r = 0; r < nR; r++) {
        res[r][i] = arr[r][idxArr[i]];
      }
    }
    return res;
  }

  public String[][] getRows(int firstRowIdx, int lastRowIdx, boolean showError)
  {
    int fromRows = getNumRows();           log.debug("fromRows = ", fromRows);
    if (lastRowIdx == -1)
      lastRowIdx = fromRows - 1;

    int nRows = fromRows - firstRowIdx;    log.debug("nRows = ", nRows);

    if (nRows <= 0) {
      String error = "Unable to extract submatrix starting from row #" + firstRowIdx
        + " \nfrom matrix with "+fromRows+" row(s)";
      log.severe(error);
      if (showError)
        JOptionPane.showMessageDialog(null, error);
      return null;
    }

    if (lastRowIdx >= firstRowIdx && lastRowIdx >= fromRows) {
      String error = "Unable to extract submatrix endinging row #" + lastRowIdx
        + " \nfrom matrix with "+fromRows+" row(s)";
      log.severe(error);
      if (showError)
        JOptionPane.showMessageDialog(null, error);
    }

    if (lastRowIdx >= firstRowIdx) {
      nRows = lastRowIdx - firstRowIdx + 1;
      log.debug("if (lastRowIdx >= firstRowIdx)\n nRows = ", nRows);
    }

    return getRows(firstRowIdx, lastRowIdx + 1);
  }

  public String[][] getCols(int firstColIdx, int lastColIdx, boolean showError)
  {
    int fromCols = getNumCols();
    int nCols = fromCols - firstColIdx;

    log.debug("fromCols = ", fromCols);
    log.debug("nCols = ", nCols);

    if (nCols <= 0) {
      String error = "Unable to extract submatrix starting from column #" + firstColIdx
        + " \nfrom matrix with "+fromCols+" column(s)";
      log.severe(error);
      if (showError)
        JOptionPane.showMessageDialog(null, error);
      return null;
    }

    if (lastColIdx >= firstColIdx && lastColIdx >= fromCols) {
      String error = "Unable to extract submatrix endinging col #" + lastColIdx
        + " \nfrom matrix with "+fromCols+" column(s)";
      log.severe(error);
      if (showError)
        JOptionPane.showMessageDialog(null, error);
    }

    if (lastColIdx >= firstColIdx) {
      nCols = lastColIdx - firstColIdx + 1;
      log.debug("if (lastColIdx >= firstColIdx)\n nCols = ", nCols);
    }

    int nRows = getNumRows();
    String [][] res = new String[nRows][nCols];
    for (int r = 0; r < nRows; r++) {
      for (int c = 0; c < nCols; c++) {
        res[r][c] = mtrx[r][c + firstColIdx];
      }
    }
    return res;
  }

  public static String toString(String[][] a) {
    StringBuffer buff = new StringBuffer();
//    int n = Math.min(ProjectLogger.MAX_N_ROWS_TO_STRING, a.length);
    int n = a.length;
    for (int i = 0; i < a.length; i++) {
      if (i < a.length - 1  && // show last
        i >= ProjectLogger.MAX_N_ROWS_TO_STRING) {
        if (i == ProjectLogger.MAX_N_ROWS_TO_STRING)
          buff.append("... see ProjectLogger.MAX_N_ROWS_TO_STRING").append(SysProp.EOL);
        continue;
      }
      String[] arr = a[i];
      buff.append("["+i+"][...]=").append(StrVec.toString(arr)).append(SysProp.EOL);
    }
    return buff.toString();
  }

  public static String[][] appendRows(String[][] to, String[][] from)
  {
    int nR = to.length;
    int nR2 = from.length;
    String[][] res = new String[nR + nR2][0];
    int idx = 0;
    for (int i = 0; i < nR; i++)
      res[idx++] = to[i];
    for (int i = 0; i < nR2; i++)
      res[idx++] = from[i];
    return res;
  }

  public static String[] getCol(int colIdx, String[][] from, boolean showError)
  {
    int nRows = from.length;
    int nCols = from[0].length;
    if (colIdx < 0  ||  colIdx >= nCols) {
      String error = "Unable to get column #" + (colIdx + 1)
        + " \nfrom matrix with "+nCols +" column(s)";
      log.severe(error);
      if (showError)
        JOptionPane.showMessageDialog(null, error);
      return null;
    }

    String [] res = new String[nRows];
    for (int r = 0; r < nRows; r++) {
      res[r] = from[r][colIdx];
    }
    return res;
  }
  public static String[] getRow(int rowIdx, String[][] from, boolean showError)
  {
    int nRows = from.length;
    if (rowIdx < 0  ||  rowIdx >= nRows) {
      String error = "Unable to get row #" + (rowIdx + 1)
        + " \nfrom matrix with "+nRows +" row(s)";
      log.severe(error);
      if (showError)
        JOptionPane.showMessageDialog(null, error);
      return null;
    }
    return from[rowIdx];
  }

  public boolean hasSameNumColsPerRow(boolean showMissing)
  {
    int nRows = getNumRows();
    int nCols = getNumCols();
    for (int r = 0; r < nRows; r++) {
      if (mtrx[r].length != nCols) {
        if (showMissing) {
          String first = StrVec.toCSV(mtrx[0]);
          if (first.length() > MAX_STR_LEN)
            first = first.substring(0, MAX_STR_LEN) + ", ...";

          String diff = StrVec.toCSV(mtrx[r]);
          if (diff.length() > MAX_STR_LEN)
            diff = diff.substring(0, MAX_STR_LEN) + ", ...";

          String error = "Different rows:\n"
            + "\nFirst row size = " + mtrx[0].length
            + "\n" + first + "\n"
            + "\nRow #" + (r+1) + " size = "  + mtrx[r].length
            + "\n" + diff;

          log.severe(error);
          JOptionPane.showMessageDialog(null, error);
        }
        return false;
      }
    }
    return true;
  }

  public void removeDiffSizeRows(boolean showMissing)
  {
    ArrayList<String[]> arr = new ArrayList<String[]>();
    int nRows = getNumRows();
    int nCols = getNumCols();
    for (int r = 0; r < nRows; r++) {
      if (mtrx[r].length == nCols) {
        arr.add(mtrx[r]);
      }
      else {
        String diff = StrVec.toCSV(mtrx[r]);
        if (diff.length() > MAX_STR_LEN)
          diff = diff.substring(0, MAX_STR_LEN) + ", ...";
        log.severe(diff);
      }
    }

    mtrx = new String[arr.size()][0];
    for (int r = 0; r < arr.size(); r++) {
      mtrx[r] = arr.get(r);
    }
  }

}
