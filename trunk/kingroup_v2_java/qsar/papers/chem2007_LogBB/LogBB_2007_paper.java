package qsar.papers.chem2007_LogBB;
import junit.framework.TestCase;
import qsar.bench.qsar.MLR.MlrAlg;
import qsar.bench.qsar.MLR.MlrRes;
import qsar.bench.qsar.MLR.QsarAlgKnnMlr;
import qsar.bench.qsar.QsarTableFormat;
import qsar.bench.qsar.cv.LooMlr;
import qsar.bench.qsar.cv.Loocv;
import tsvlib.project.ProjectLogger;

import javax.iox.TableFormat;
import javax.stats.StatsRes;
import javax.utilx.arrays.mtrx.Mtrx;
import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 25/01/2007, Time: 10:39:17
 */
public class LogBB_2007_paper  extends TestCase
{
  public static final String REFERENCE = "Konovalov et al (2007) J.Chem.Inf.Model.47 p1648";
  public static final String REFERENCE_HTML = "Konovalov DA, Coomans D, Deconinck E, Vander Heyden Y (2007)<br>"
  + "Benchmarking of QSAR models for Blood-Brain Barrier Permeation. J.Chem.Inf.Model. 47, 1648-1656.";

  protected static final ProjectLogger log = ProjectLogger.getLogger(LogBB_2007_paper.class);

  int N_COLS_IGNORE_KC291 = 3;

  String BASE_DIR = "Z:\\dev\\chem\\papers\\2007_LogBB\\data\\Abraham2006ADataBase\\KC291";
  public String INPUT_DIR = BASE_DIR;
  public String OUTPUT_DIR = BASE_DIR + File.separator + "output";
//  public String INPUT_DIR = BASE_DIR + File.separator + "input";
//  public String OUTPUT_DIR = BASE_DIR + File.separator + "output";

  public TableFormat TABLE_FORMAT;

  public LogBB_2007_paper(){
    log.start("LogBB_2007_paper");

    TABLE_FORMAT = new TableFormat();
    TABLE_FORMAT.loadDefaults();
    TABLE_FORMAT.setColumnDelim('\t');

    TABLE_FORMAT.setHeaderRow(1);
    TABLE_FORMAT.setHeaderCol(3);

    TABLE_FORMAT.setFirstRow(2);
    TABLE_FORMAT.setFirstCol(4);

    TABLE_FORMAT.setLastRow(0);
    TABLE_FORMAT.setLastCol(0);
  }

  public StatsRes calcRegressMLR(double[][] z)
  {
    double[] yt = Mtrx.getCol(0, z);
    MlrAlg reg = new MlrAlg();
    reg.calc(z);
    double[] ye = reg.getYPred();
    StatsRes res = new MlrRes(ye, yt, z[0].length-1);
    log.info(res.toString());
    return res;
  }

  public StatsRes calcLOOCV(double[][] z)
  {
    double[] yt = Mtrx.getCol(0, z);
//    log.info("\nyt=\n" + Vec.toString(yt));
    double[] ey = new Loocv(new LooMlr()).calcLOO(z);
    StatsRes res = new MlrRes(yt, ey, z[0].length-1);
    log.info("\nLOOCV:\n" + res);
    return res;
  }

  public double calcQ2_kNN(int nNN, double[][] z) {
    double[] yt = Mtrx.getCol(0, z);
    double[] ey = new Loocv(new QsarAlgKnnMlr(nNN, z)).calcLOO(z);
    StatsRes testCV = new MlrRes(yt, ey, z[0].length-1);
    log.info("\nq2_LCV:\n" + testCV);
    return testCV.getR2();
  }

  public static void loadTableFormat(QsarTableFormat qtf)
  {
    qtf.setHeaderRow(1);
    qtf.setHeaderCol(3);

    qtf.setFirstRow(2);
    qtf.setFirstCol(4);

    qtf.setColumnDelim('\t');
  }
}

