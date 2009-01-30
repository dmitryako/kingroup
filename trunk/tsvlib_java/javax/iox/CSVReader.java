package javax.iox;
import java.util.logging.Logger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/11/2006, Time: 11:31:27
 */
public class CSVReader
{
  protected static final Logger log = Logger.getAnonymousLogger();
  public static double[][] readDoubleArr(int nRows, int nCols, TextFile from)
  {
    return new MtrxReader(",").readMtrx(nRows, nCols, from);
  }
}
