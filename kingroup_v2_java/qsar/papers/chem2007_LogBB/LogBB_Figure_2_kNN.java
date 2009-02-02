package qsar.papers.chem2007_LogBB;
import qsar.bench.qsar.MLR.MlrAlg;
import qsar.bench.qsar.MLR.MlrRes;
import qsar.bench.qsar.MLR.QsarAlgKnnMlr;
import qsar.bench.qsar.cv.Loocv;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;
import javax.iox.MtrxReader;
import javax.iox.TextFile;
import javax.stats.Stats;
import javax.stats.StatsRes;
import javax.utilx.arrays.mtrx.Mtrx;
import javax.utilx.arrays.vec.Vec;
import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/01/2007, Time: 17:26:39
 */
public class LogBB_Figure_2_kNN extends LogBB_2007_paper
{
  protected final static ProjectLogger log = ProjectLogger.getAnonymousLogger();

  public void test_kNN_all() {
    String FILE_NAME = "Z:\\dev\\chem\\papers\\2007_LogBB\\data\\Abraham2006ADataBase\\KC291\\LogBB_KC291_LFER_input.txt";
//    INPUT_DIR = "Z:\\dev\\chem\\papers\\2007_LogBB\\data\\Abraham2006ADataBase\\KC291\\LogBB_KC291_LFER_input.txt";
    double[][] z = new MtrxReader("\t").readMtrx(-1, -1, N_COLS_IGNORE_KC291, FILE_NAME);
    int nR = z.length;
//    int K_NN = 30; // k nearest neighbours
//    int K_NN = nR * 4 / 5 ; // k nearest neighbours
    int K_NN = nR-2; // k nearest neighbours

    z = Stats.makeColsMeanZeroVarOne(z);
    log.info("\nz=\n" + Mtrx.toString(z));

    double[] yt = Mtrx.getCol(0, z);
    log.info("\nyt=\n" + Vec.toString(yt));
    double[] ey = new Loocv(new QsarAlgKnnMlr(K_NN, z)).calcLOO(z);

    StatsRes testCV = new MlrRes(yt, ey, z[0].length-1);
    log.info("\ntestCV:\n" + testCV);

    int N_TESTS = 100;
    int STEP_NN = 2;
    int MIN_NN = 30;
    double[] q2 = new double[N_TESTS];
    double[] arrNN = new double[N_TESTS];
    int idx = 0;
    for (int i = 0; i < N_TESTS; i++) {
      int nNN = MIN_NN + i * STEP_NN;
      arrNN[idx] = nNN;
      q2[idx++] = calcQ2_kNN(nNN, z);
    }
    LOG.saveToFile(arrNN, q2, OUTPUT_DIR, "LogBB_KC291_LFERs_q2NN.csv");
//    LOG.saveToFile(arrNN, q2, OUTPUT_DIR, "LogBB_KC291_LFER_q2NN.csv");

    StatsRes testZ = calcRegressMLR(z);
    int dbg = 1;
  }


  public void test_kNN() {
    INPUT_DIR = "Z:\\dev\\chem\\papers\\2007_LogBB\\data\\Abraham2006ADataBase\\KC291\\";
    String FILE_NAME = "LogBB_AI328_LFERIcIv";
//    String FILE_NAME = "LogBB_KC291_LFER";
    File file = new File(INPUT_DIR + FILE_NAME + "_input.txt");
    TextFile from = new TextFile();
    from.read(file, null);
    from.trim();
    log.info("from file = \n" + from);
    double[][] z = new MtrxReader("\t").readMtrx(-1, -1, from);
//    z = randomY(z);

//    double[][] zt = Vec.transpose(z);
//    Stats.makeRowsMeanZeroVarOne(zt);
//    z = Vec.transpose(zt);
    log.info("\nz=\n" + Mtrx.toString(z));

    int kNN = 100;
    double[] yt = Mtrx.getCol(0, z);
    double[] y_kNN = new Loocv(new QsarAlgKnnMlr(kNN, z)).calcLOO(z);
    StatsRes stats_kNN = new MlrRes(yt, y_kNN, z[0].length-1);
    log.info("\nstats_kNN:\n" + stats_kNN);

    MlrAlg reg = new MlrAlg();
    reg.calc(z);
    double[] yMLR = reg.getYPred();
    StatsRes statsMLR = new MlrRes(yt, yMLR, z[0].length-1);
    log.info("\nstatsMLR:\n" + statsMLR);

//    StatsRes testLOOCV = calcLOOCV(z);
//    StatsRes testZ = calcRegress(z);

    LOG.saveToFile(yt, y_kNN, yMLR, INPUT_DIR, FILE_NAME + "_"+kNN +"NN.csv");

    int dbg = 1;
  }

//  public void testRun() {
//    String fileName = INPUT_DIR + File.separator + "Abraham2006ADataBase" + File.separator
////      + "Table_S1_ESABVIcIv_N328.txt";
////      + "Table_S1_ESABVIc_N233.txt";
////      + "Table_S1_ESABV_N95.txt";
//    + "Table_S1_ESABV_N95f.txt";
//    File file = new File(fileName);
//    TextFile from = new TextFile();
//    from.read(file, null);
//    from.trim();
//    log.info("from file = \n" + from);
//
////    double[][] z = new MtrxReader("\t").readDoubleArr(21, -1, from);
//    double[][] z = new MtrxReader("\t").readMtrx(-1, -1, from);
////    double[][] zt = Vec.transpose(z);
////    Stats.makeRowsMeanZeroVarOne(zt);
////    z = Vec.transpose(zt);
//    log.info("z=\n" + Vec.toString(z));
//
////    double[][] aXt = DoubleArr.transpose(aX);
////    Stats.makeRowsMeanZeroVarOne(aXt);
////    aX = DoubleArr.transpose(aXt);
////    log.info("aX=\n" + DoubleArr.toString(aX));
//
//    double[] yt = Vec.getCol(0, z);
//    log.info("yt=\n" + Vec.toString(yt));
//
//    double[] e = new Loocv(new LooKnnMlr(z, 30)).calcLOO(z);
//
//    double R2 = Stats.r2(e, yt);
//    log.info("\n R2 = " + R2);
//
//    int dbg = 1;
//  }
}
