package qsar.papers.chem2007_LogBB;
import qsar.bench.qsar.MLR.MlrAlg;
import qsar.bench.qsar.MLR.MlrRes;
import tsvlib.project.ProjectLogger;

import javax.iox.MtrxReader;
import javax.iox.TextFile;
import javax.stats.Stats;
import javax.stats.StatsPCA;
import javax.stats.StatsRes;
import javax.utilx.arrays.StrMtrx;
import javax.utilx.arrays.StrVec;
import javax.utilx.arrays.mtrx.Mtrx;
import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 4/02/2007, Time: 13:53:07
 */
public class LogBBRegressPCMD extends LogBBRegressPCMDCommon   {
  protected final static ProjectLogger log = ProjectLogger.getAnonymousLogger();
  private static int N_COLS_IGNORE_AUREUS = 3;
  private static int N_COLS = 6;
//  String DIR = INPUT_DIR + File.separator + "Abraham2006ADataBase" + File.separator;
  private static final int PCMD_IDX_Y = 0;
  private static final int PCMD_IDX_Iv = 7;
  private static double MAX_R2 = 0.81;

  public void testTransposePCMD() {
    INPUT_DIR = "Z:\\dev\\chem\\papers\\2007_LogBB\\data\\Abraham2006ADataBase\\KC78\\";
    N_COLS_IGNORE_PCMD_INPUT = 0;
    String name = "LogBB_KC78_PCMD";
//    String name = "LogBB_N291_PCMD_setA_2";
//    String name = "LogBB_N291_PCMD_setA";
//    String name = "LogBB_N291_PCMD_setB";
    String[][] text = loadPCMDNames(INPUT_DIR + name + ".txt");
    log.info("\nMD=" + StrVec.toString(text[0]));
    savePCMDByCols(text, name + "t.txt");
  }

  public void testMergeMD() {
    N_COLS_IGNORE_PCMD_INPUT = 0;
    String name = "LogBB_N291_PCMD_setA";
    String[][] txt = loadPCMDNames(INPUT_DIR + name + ".txt");

    name = "LogBB_N291_PCMD_setB";
    String[][] txt2 = loadPCMDNames(INPUT_DIR + name + ".txt");

    txt = StrMtrx.appendRows(txt, txt2);
    remove999(txt);

    String toFileName = OUTPUT_DIR + File.separator + "LogBB_N291_PCMD_input.txt";
    File toFile = new File(toFileName);
    TextFile to = new TextFile();
    to.setFileName(toFileName);
    to.addLines(StrVec.toString(txt, "\t"));
    to.write(toFile, null);

    int dbg = 1;
  }

  public void testCleanupMD() {
    String name = "LogBB_N90_PCMD";
//    String name = "LogBB_N201_PCMD_setA";
//    String name = "LogBB_N201_PCMD_setB";
    String[][] txt = loadPCMDNames(INPUT_DIR + name + ".txt");
    remove999(txt);

    String toFileName = OUTPUT_DIR + File.separator + name + "_input.txt";
    File toFile = new File(toFileName);
    TextFile to = new TextFile();
    to.setFileName(toFileName);
    to.addLines(StrVec.toString(txt, "\t"));
    to.write(toFile, null);

    int dbg = 1;
  }

  public void testAureus() {
    N_COLS = 21;
    MAX_R2 = 0.75 * 0.75;
    MAX_R2 = 0.5 * 0.5;
    double[][] dataA = loadAureus(INPUT_DIR + "dragonAureus2_input.csv");
    StatsRes resA = Stats.selectColsByCorr(dataA, N_COLS, MAX_R2);
    StatsRes testA = calcRegressMLR(resA.getArr());
    int dbg = 1;

    String toFileName = OUTPUT_DIR + File.separator + "dragonAureus2_z.csv";
    File toFile = new File(toFileName);
    TextFile to = new TextFile();
    to.setFileName(toFileName);
    to.addLines(Mtrx.toCSV(resA.getArr()));
    to.write(toFile, null);
  }
  public void testPCMD() {
    N_COLS = 11;
    MAX_R2 = 0.75 * 0.75;
    N_COLS_IGNORE_PCMD_INPUT = 0;
    String[][] text = loadPCMDNames(INPUT_DIR + "LogBB_N291_PCMD_input.txt");
    log.info("\nMD=" + StrVec.toString(text[0]));
    savePCMDByCols(text, "LogBB_N202_PCMD_Bt.txt");

    N_COLS_IGNORE_PCMD_INPUT = 1;
    double[][] dataA = loadPCMD(INPUT_DIR + "LogBB_N202_PCMD_A_input.txt"
      , INPUT_DIR + "LogBB_N202_LFER_A_input.txt"
      );
    StatsRes resA = Stats.selectColsByCorr(dataA, N_COLS, MAX_R2);
    String[] usedNames = StrVec.order(text[0], resA.getIdxArr());
    log.info("\nusedNames=" + StrVec.toString(usedNames));

    StatsRes testA = calcRegressMLR(resA.getArr());
    int dbg = 1;
  }

  public void testNarayanan2005() {
    INPUT_DIR = "Z:\\dev\\chem\\papers\\2007_LogBB\\data\\Narayanan2005\\";
    N_COLS_IGNORE_PCMD_INPUT = 1;
    double[][] z = loadPCMD(INPUT_DIR + "Narayanan_N78_PCMD_V3_input.txt"
      , INPUT_DIR + "LogBB_N291_LFER_input.txt"
      );
    log.info("\ndataA=" + Mtrx.toString(z));

    StatsRes testLOOCV = calcLOOCV(z);
    StatsRes testA = calcRegressMLR(z);
    int dbg = 1;
  }


  private void savePCMDByCols(String[][] text, String name)
  {
    OUTPUT_DIR = INPUT_DIR;
    String[][] textT = StrVec.transpose(text);
    String toFileName = OUTPUT_DIR + File.separator + name;
    File toFile = new File(toFileName);
    TextFile to = new TextFile();
    to.setFileName(toFileName);
    to.addLines(StrVec.toString(textT, "\t"));
    to.write(toFile, null);
  }

  public void testLinearRegress() {
    N_COLS = 10;
    N_COLS_IGNORE_AUREUS = 0;
    MAX_R2 = 0.99;
    String DIR = INPUT_DIR + File.separator;
    double[][] dataA = loadAureus(DIR + "test_linear_regress_var0p01.txt");

    StatsRes resA = Stats.selectColsByCorr(dataA, N_COLS, MAX_R2);
    StatsRes testA = calcRegressMLR(resA.getArr());
    int dbg = 1;
  }

  public void testDragon() {
    N_COLS = 41;
//    double[][] dataA = loadDragon(DIR + "LogBB_N291_DRAGON_input.txt"
    double[][] dataA = loadPCMD(INPUT_DIR + "LogBB_N202_DRAGON_input.txt"
//      + "Table_S1_ESABVIc_N207.txt"
      , INPUT_DIR + "LogBB_N202_LFER_input.txt"
//      + "LogBB_N291_LFER_input.txt"
      );
    double[][] dataB = loadPCMD(INPUT_DIR + "LogBB_N90_DRAGON_input.txt"
      , INPUT_DIR
//      + "Table_S1_ESABVIcIv_N328.txt";
//      + "Table_S1_ESABVIc_N233.txt";
//      + "Table_S1_ESABV_N90.txt"
      + "LogBB_N90_LFER_input.txt"
      );
    StatsRes resA = Stats.selectColsByCorr(dataA, N_COLS, MAX_R2);

    dataA = Mtrx.getCols(dataA, resA.getIdxArr(), N_COLS);// just for testing
    dataB = Mtrx.getCols(dataB, resA.getIdxArr(), N_COLS);

//    StatsRes testA = testRegress(resA.getArr());
    StatsRes testA = calcRegressMLR(dataA);
    StatsRes testB = calcRegressMLR(dataB);

    // ESTIMATE Y from testA
    MlrAlg regA = new MlrAlg();
    regA.calc(dataA);
    double[] yt = Mtrx.getCol(0, dataB);
    double[] yB = MlrAlg.calcY(dataB, regA.getMlrCoeffs());
    StatsRes testBA = new MlrRes(yB, yt, dataA[0].length-1);

    String toFileName = OUTPUT_DIR + File.separator + "N90_PCA.csv";
    File toFile = new File(toFileName);
    TextFile to = new TextFile();
    to.setFileName(toFileName);
    double[][] aY = StatsPCA.calc(Mtrx.trans(resA.getArr()));
    to.addLines(Mtrx.toCSV(aY));
    to.write(toFile, null);
  }
  public double[][] loadAureus(String zName) {
    double[][] z = new MtrxReader(",").readMtrx(-1, -1, N_COLS_IGNORE_AUREUS, zName);
    log.info("\nmd z=\n" + Mtrx.toString(z));
    if (MlrAlg.findSameXRows(z)) {
      int stopHere = 1;
    }
    return z;
  }


  public double[][] loadPCMD(String xName, String yName) {
    double[][] xPCMD = new MtrxReader("\t").readMtrx(-1, -1, N_COLS_IGNORE_PCMD_INPUT, xName);
    log.info("\nmd x=\n" + Mtrx.toString(xPCMD));
    if (MlrAlg.findSameXRows(xPCMD)) {
      int stopHere = 1;
    }

    double[][] zLFER = new MtrxReader("\t").readMtrx(-1, -1, yName);
    log.info("\ny from z=\n" + Mtrx.toString(zLFER));
    if (MlrAlg.findSameXRows(zLFER)) {
      int stopHere = 1;
    }

    double[] Iv = Mtrx.getCol(PCMD_IDX_Iv, zLFER);
    double[][] z = Mtrx.addCol(0, Iv, xPCMD);

    double[] y = Mtrx.getCol(PCMD_IDX_Y, zLFER);
    z = Mtrx.addCol(0, y, z);
    log.info("\nfinal z=\n" + Mtrx.toString(z));
    return z;
  }



}
