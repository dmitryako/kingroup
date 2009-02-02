package qsar.papers.chem2007_LogBB;
import qsar.bench.QBench;
import qsar.bench.qsar.MLR.MlrAlgFactoryMLR;
import qsar.bench.qsar.MLR.MlrRes;
import qsar.bench.qsar.MLR.QsarAlgKnnMlr;
import qsar.bench.qsar.MLR.QsarAlgMlr;
import qsar.bench.qsar.QsarAlg;
import qsar.bench.qsar.cv.Loocv;
import qsar.bench.qsar.cv.LoocvMethod;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;
import javax.iox.TableReader;
import javax.iox.table.Table;
import javax.stats.Stats;
import javax.stats.StatsRes;
import javax.utilx.arrays.mtrx.Mtrx;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 25/02/2007, Time: 11:34:59
 */
public class LogBB_Figure_1_KC291  extends LogBB_2007_paper
{
  private static final ProjectLogger log = ProjectLogger.getLogger(LogBB_Figure_1_KC291.class);

  public void test_Figure_1() {

//    INPUT_DIR = "Z:\\dev\\chem\\papers\\2007_LogBB\\data\\Abraham2006ADataBase\\KC291\\";
    INPUT_DIR = "E:\\dev\\chem\\papers\\2007_LogBB\\JChemInfModel\\data\\";
    String FILE_NAME = "KC291";

//    N_COLS_IGNORE_KC291 = 0;
//    INPUT_DIR = "Z:\\dev\\chem\\papers\\2007_LogBB\\data\\Abraham2006ADataBase\\KC78\\";
//    String FILE_NAME = "LogBB_KC78_LFER";
//    String FILE_NAME = "LogBB_KC78_PCMD_V1";
//    String FILE_NAME = "LogBB_KC78_PCMD_V2";
//    String FILE_NAME = "LogBB_KC78_PCMD_V3";

//    INPUT_DIR = "Z:\\dev\\chem\\papers\\2007_LogBB\\data\\Abraham2006ADataBase\\KC291\\PCMD\\";
//    String FILE_NAME = "LogBB_KC291_PCMD_V1";
//    String FILE_NAME = "LogBB_KC291_PCMD_V2";
//    String FILE_NAME = "LogBB_KC291_PCMD_V3";

//    String FILE_NAME = "LogBB_AI328_LFERIcIv";
//    String FILE_NAME = "LogBB_KC90_LFER";
//    String FILE_NAME = "LogBB_KC201_LFER";
//    String FILE_NAME = "LogBB_KC291_LFER";
//    String FILE_NAME = "LogBB_KC290_LFER";
//    String FILE_NAME = "LogBB_KC291_LFER_correct279";
//    String FILE_NAME = "LogBB_KC291_LFER_correct102";
//    String FILE_NAME = "LogBB_KC291_LFER_correct105";
//    String FILE_NAME = "LogBB_KC291_LFER_correct218";
//    String FILE_NAME = "LogBB_KC291_LFER_correct128";
//    String FILE_NAME = "LogBB_KC291_LFER_correct140";
//    int N_COLS_IGNORE_KC291 = 3;

    ProjectLogger.getRootLogger().setAll();

    Table table = TableReader.makeFromFile(INPUT_DIR + FILE_NAME + ".txt", TABLE_FORMAT, true);
    double[][] z = table.getMtrx();
//    double[][] z = new MtrxReader("\t").readMtrx(-1, -1, N_COLS_IGNORE_KC291, INPUT_DIR + FILE_NAME + "_input.txt");
    double[] Y = Mtrx.getCol(0, z);
    int nR = z.length;
//    z = randomY(z);

    QsarAlg alg = new QsarAlgMlr(z);
    double[] yMLR = alg.calcYFromXZ(z);
    StatsRes statsMLR = new MlrRes(Y, yMLR, z[0].length-1);

    QBench project = new QBench();
    LoocvMethod cv = new LoocvMethod(project, new MlrAlgFactoryMLR());
    StatsRes statsLOO = cv.calcStats(z, true);

    z = Stats.makeColsMeanZeroVarOne(z);
    Mtrx.setCol(0, Y, z); // restore y
    log.info("\nz=\n" + Mtrx.toString(z));

    int kNN = 70;
    double[] y_kNN = new Loocv(new QsarAlgKnnMlr(kNN, z)).calcLOO(z);
    StatsRes stats_kNN = new MlrRes(Y, y_kNN, z[0].length-1);
    log.info("\nstats_kNN:\n" + stats_kNN);

    int kLOO = nR - 1;
    double[] yLOO = new Loocv(new QsarAlgKnnMlr(kLOO, z)).calcLOO(z);
    StatsRes stats_kLOO = new MlrRes(Y, yLOO, z[0].length-1);
    log.info("\nstats_kLOO:\n" + stats_kLOO);

//    MlrAlg reg = new MlrAlg();
//    reg.calc(z);
//    double[] yMLR = reg.calcY();
//    StatsRes statsMLR = new MlrRes(Y, yMLR, z[0].length-1);
//    log.info("\nstatsMLR:\n" + statsMLR);

//    StatsRes testLOOCV = calcLOOCV(z);
//    StatsRes testZ = calcRegress(z);

    LOG.saveToFile(Y, yMLR, yLOO, y_kNN, INPUT_DIR, FILE_NAME + "_"+kNN +"NN_Figure_1.csv");

    int dbg = 1;
  }
}
