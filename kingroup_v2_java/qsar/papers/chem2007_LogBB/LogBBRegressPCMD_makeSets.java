package qsar.papers.chem2007_LogBB;
import javax.iox.TextFile;
import javax.utilx.arrays.StrMtrx;
import javax.utilx.arrays.StrVec;
import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 21/02/2007, Time: 14:19:22
 */
public class LogBBRegressPCMD_makeSets extends LogBBRegressPCMDCommon
{
  public void testMakeDataSetNarayanan2005_2() {

    INPUT_DIR = "Z:\\dev\\chem\\papers\\2007_LogBB\\data\\Abraham2006ADataBase\\KC291\\PCMD\\";
//    makeFromAB(new String[] {"#", "TPSA", "SsssN", "ALOGP"}, "V1", "LogBB_KC291_PCMD");
//    makeFromAB(new String[] {"#", "S1K", "SsssN", "ALOGP"}, "V2", "LogBB_KC291_PCMD");
//    makeFromAB(new String[] {"#", "Xu", "SsssN", "ALOGP"}, "V3", "LogBB_KC291_PCMD");

    INPUT_DIR = "Z:\\dev\\chem\\papers\\2007_LogBB\\data\\Abraham2006ADataBase\\KC78\\";
//    makeDataSetPCMD(new String[] {"#", "TPSA", "SsssN", "ALOGP"}, "V1", "LogBB_KC78_PCMD");
    makeDataSetPCMD(new String[] {"#", "S1K", "SsssN", "ALOGP"}, "V2", "LogBB_KC78_PCMD");
//    makeDataSetPCMD(new String[] {"#", "Xu", "SsssN", "ALOGP"}, "V3", "LogBB_KC78_PCMD");

    INPUT_DIR = "Z:\\dev\\chem\\papers\\2007_LogBB\\data\\Narayanan2005\\";
//    makeDataSetPCMD(new String[] {"#", "Xu", "SsssN", "ALOGP"}, "V3", "Narayanan_N78_PCMD");
//    makeDataSetPCMD(new String[] {"#", "Xu", "SsssN", "ALOGP"}, "V3", "PCMD\LogBB_N291_PCMD_setB");

    INPUT_DIR = "Z:\\dev\\chem\\papers\\2007_LogBB\\Abraham2006ADataBase\\paper_LogBB\\";
//    makeDataSetPCMD(new String[] {"#", "S1K", "SsssN", "ALOGP"}, "V2", "LogBB_N291_PCMD_setA");
//    makeDataSetPCMD(new String[] {"#", "S1K", "SsssN", "ALOGP"}, "V2", "LogBB_N291_PCMD_setB");
  }
  public String[][] makeDataSetPCMD(String[] names, String V_NUM, String fileName) {
    OUTPUT_DIR = INPUT_DIR;
    N_COLS_IGNORE_PCMD_INPUT = 0;
    String[][] txt = loadPCMDNames(INPUT_DIR + fileName + ".txt");
    txt = StrMtrx.selectColsByName(txt, names);
    log.info("\nsetA=" + StrMtrx.toString(txt));
    remove999(txt);
    log.info("\nsetA=" + StrMtrx.toString(txt));

    String toFileName = OUTPUT_DIR + File.separator + fileName + "_"+V_NUM+".txt";
    File toFile = new File(toFileName);
    TextFile to = new TextFile();
    to.setFileName(toFileName);
    to.addLines(StrVec.toString(txt, "\t"));
    to.write(toFile, null);

    int dbg = 1;
    return txt;
  }

  public String[][] makeFromAB(String[] names, String V_NUM, String fileName) {
    String[][] A = makeDataSetPCMD(names, V_NUM, fileName + "_setA");
    String[][] B = makeDataSetPCMD(names, V_NUM, fileName + "_setB");
    String[][] txt = StrMtrx.appendRows(A, B);

    String toFileName = OUTPUT_DIR + File.separator + fileName + "_"+V_NUM+".txt";
    File toFile = new File(toFileName);
    TextFile to = new TextFile();
    to.setFileName(toFileName);
    to.addLines(StrVec.toString(txt, "\t"));
    to.write(toFile, null);

    int dbg = 1;
    return txt;
  }

}
