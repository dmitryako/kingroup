package qsar.papers.chem2007_LogBB;
import javax.iox.MtrxReader;
import javax.iox.TextFile;
import javax.stats.StatsRes;
import javax.utilx.arrays.mtrx.Mtrx;
import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 21/02/2007, Time: 14:24:07
 */
public class LogBBRegressLOOCV  extends LogBB_2007_paper
{
  public void testLOOCV() {
    INPUT_DIR = "Z:\\dev\\chem\\papers\\2007_LogBB\\data\\Abraham2006ADataBase\\";

    String FILE_NAME = INPUT_DIR + "KC291\\LogBB_AI328_LFERIcIv_input.txt";
//    String FILE_NAME = INPUT_DIR + "KC291\\LogBB_KC291_LFER_input.txt";
//    String FILE_NAME = INPUT_DIR + "KC291\\PCMD\\LogBB_KC291_PCMD_V1_input.txt";
//    String FILE_NAME = INPUT_DIR + "KC291\\PCMD\\LogBB_KC291_PCMD_V2_input.txt";

//    String FILE_NAME = INPUT_DIR + "KC291\\PCMD\\LogBB_KC291_PCMD_V3_input.txt";

//    String FILE_NAME = INPUT_DIR + "KC78\\LogBB_KC78_PCMD_V1_input.txt";
//    String FILE_NAME = INPUT_DIR + "KC78\\LogBB_KC78_PCMD_V2_input.txt";
//    String FILE_NAME = INPUT_DIR + "KC78\\LogBB_KC78_PCMD_V3_input.txt";

    File file = new File(FILE_NAME);
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

    StatsRes testLOOCV = calcLOOCV(z);
    StatsRes testZ = calcRegressMLR(z);
    int dbg = 1;
  }

}
