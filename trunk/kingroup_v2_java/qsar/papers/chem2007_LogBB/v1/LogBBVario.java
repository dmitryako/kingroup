package qsar.papers.chem2007_LogBB.v1;
import qsar.papers.chem2007_LogBB.LogBB_2007_paper;
import tsvlib.project.ProjectLogger;

import javax.iox.MtrxReader;
import javax.iox.TextFile;
import javax.utilx.arrays.mtrx.Mtrx;
import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 25/01/2007, Time: 10:40:32
 */
public class LogBBVario extends LogBB_2007_paper
{
  protected final static ProjectLogger log = ProjectLogger.getAnonymousLogger();

  // Principal Components Analysis
  public void testCalcVariogram() {
    // FROM
    String fileName = INPUT_DIR + File.separator
      + "Abraham2006ADataBase" + File.separator + "Table_S1_ESABV_N95.txt";
    File file = new File(fileName);
    TextFile from = new TextFile();
    from.read(file, null);
    from.trim();
    log.info("from file = \n" + from);

//    int nC = 4; // num of cols
//    int nR = 149; // num of rows
    double[][] aX = new MtrxReader("\t").readMtrx(-1, -1, from);
    log.info("aX=\n" + Mtrx.toString(aX));
    int nC = aX[0].length;
    int nR = aX.length;

//    aX = DoubleArr.transpose(aX);
    double[][] z = Mtrx.getCols(0, 1, aX);
//    double[] z = DoubleArr.getCol(0, aX);
    log.info("z=\n" + Mtrx.toString(z));
    double[][] x = Mtrx.getCols(1, nC, aX);
    log.info("x=\n" + Mtrx.toString(x));

    double[] dx = Mtrx.pDistByRows(x);
    double[] dz = Mtrx.pDistByRows(z);
    double[][] dxz = Mtrx.appendRow(dx, dz);
    double[][] dxzT = Mtrx.trans(dxz);
    log.info("dxzT=\n" + Mtrx.toString(dxzT));
    Mtrx.sortByRows(0, dxzT);
    log.info("sorted dxzT=\n" + Mtrx.toString(dxzT));

    String toFileName = OUTPUT_DIR + File.separator + "dxz.csv";
    File toFile = new File(toFileName);
    TextFile to = new TextFile();
    to.addLines(Mtrx.toCSV(dxzT));
    to.write(toFile, null);

    int dbg = 1;
  }
}
