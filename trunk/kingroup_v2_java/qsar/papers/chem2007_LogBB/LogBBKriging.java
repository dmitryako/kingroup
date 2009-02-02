package qsar.papers.chem2007_LogBB;
import qsar.bench.qsar.cv.Loocv;
import qsar.kriging.KrigingCV;
import tsvlib.project.ProjectLogger;

import javax.iox.MtrxReader;
import javax.iox.TextFile;
import javax.stats.Stats;
import javax.utilx.arrays.mtrx.Mtrx;
import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 28/01/2007, Time: 15:43:36
 */
public class LogBBKriging   extends LogBB_2007_paper
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

    double[][] z = new MtrxReader("\t").readMtrx(10, -1, from);
//    double[][] aX = new MtrxReader("\t").readDoubleArr(-1, -1, from);
//    aX = avrDuplicate(aX);
    log.info("aX=\n" + Mtrx.toString(z));

    double[][] zt = Mtrx.trans(z);
    Stats.makeRowsMeanZeroVarOne(zt);
    z = Mtrx.trans(zt);
    log.info("aX=\n" + Mtrx.toString(z));

    int nC = z[0].length;
    int nR = z.length;

    double[][] y = Mtrx.getCols(0, 1, z);
    double[] yt = Mtrx.getCol(0, z);
    log.info("z=\n" + Mtrx.toString(y));
    double[][] x = Mtrx.getCols(1, nC, z);
    log.info("x=\n" + Mtrx.toString(x));

    double[] e = new Loocv(new KrigingCV()).calcLOO(z); // first row is the LogBB values

    double R2 = Stats.r2(e, yt);
    log.info("\n R2 = " + R2);

    int dbg = 1;
  }

//  private double[][] avrDuplicate(double[][] x)
//  {
//    int n = x.length;
//    ArrayList<double[]> list = new ArrayList<double[]>();
//    ArrayList<Integer> count = new ArrayList<Integer>();
//
//    for (int i = 0; i < n; i++) {
//      double[] vi = x[i];
//      add(vi, list, count);
//    }
//    avr(list, count);
//    return DoubleArr.asArray2D(list);
//  }
//
//  private void avr(ArrayList<double[]> list, ArrayList<Integer> count)
//  {
//    for (int s = 0; s < list.size(); s++) {
//      double[] vs = list.get(s);
//      vs[0] /= count.get(s).intValue();
//    }
//  }
//
//  private void add(double[] vi, ArrayList<double[]> list, ArrayList<Integer> count)
//  {
//    for (int s = 0; s < list.size(); s++) {
//      double[] vs = list.get(s);
//      if (equal(vs, vi)) {
//        vs[0] += vi[0];
//        int c = count.get(s).intValue();
//        count.set(s, c + 1);
//        return;
//      }
//    }
//    list.add(vi);
//    count.add(new Integer(1));
//  }
//
//  private boolean equal(double[] vs, double[] vi)
//  {
//    for (int i = 1; i < vs.length; i++) { // NOTE i=1!
//      if (Double.compare(vs[i], vs[i]) != 0)
//        return false;
//    }
//    return true;
//  }
}
