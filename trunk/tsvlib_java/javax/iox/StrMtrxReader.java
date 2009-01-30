package javax.iox;
import javax.swing.*;
import javax.utilx.arrays.StrVec;
import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Logger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 9/02/2007, Time: 12:17:59
 */
public class StrMtrxReader
{
  protected static final Logger log = Logger.getAnonymousLogger();
  private String delim;
  public StrMtrxReader(String delim) {
    this.delim = delim;
  }
//  public double[][] readMtrx(int nRows, int nCols, TextFile from) {
//    return readMtrx(nRows, nCols, from, 0);
//  }
//
//  public double[][] readMtrx(int nRows, int nCols, String fileName) {
//    return readMtrx(nRows, nCols, 0, fileName);
//  }

  public String[][] readMtrx(int nRows, int nCols, int ignoreCols, String fileName) {
    File file = new File(fileName);
    TextFile from = new TextFile();
    from.read(file, null);
    from.trim();
//    log.info("\nfrom file = \n" + from);
    return readMtrx(nRows, nCols, from, ignoreCols, true);
  }

  public String[][] readMtrx(int nRows, int nCols, TextFile from, int ignoreCols, boolean showErrors)
  {
    if (nRows > from.size()) {
      String error = "Unable to import:\nAsking to import " + nRows
        + " rows from " + " a file with " + from.size() + " lines";
      log.severe(error);
      if (showErrors)
        JOptionPane.showMessageDialog(null, error);
      return null;
    }
    if (nRows == -1)
      nRows = from.size();
    String[][] arr = new String[nRows][0];
    for (int r = 0; r < nRows; r++) {
      String line = from.getLine(r).trim();
      if (line == null  || line.length() == 0)
        continue;
      String[] rArr = readArr(ignoreCols, nCols, line);
      arr[r] = rArr;
    }
    return arr;
  }

  private String[] readArr(int ignoreCols, int nCols, String line)
  {
//    log.info("line=" + line);
    StringTokenizer tokens = new StringTokenizer(line, delim, false);
    if (nCols > -1  &&  nCols > tokens.countTokens()) {
      String error = "Error in readDoubleArr: nCols > tokens.countTokens()";
      log.severe(error);
      return null;
    }

    String token;
    ArrayList<String> tmpArr = new ArrayList<String>();
    int countCols = 1;
    while (tokens.hasMoreTokens()  &&  (tmpArr.size() < nCols  ||  nCols == -1)) {
      token = tokens.nextToken().trim();
      if (countCols++ <= ignoreCols)
        continue;
      tmpArr.add(token);
    }
    return StrVec.asArray(tmpArr, nCols);
  }

}
