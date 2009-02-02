package qsar.papers.chem2007_LogBB;
import javax.iox.MtrxReader;
import javax.iox.TextFile;
import javax.stats.StatsRes;
import javax.utilx.arrays.IntVec;
import javax.utilx.arrays.mtrx.Mtrx;
import javax.utilx.arrays.vec.Vec;
import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 1/02/2007, Time: 15:52:19
 */
public class LogBBRegressMLR  extends LogBB_2007_paper   {

  String FILE_NAME = "Z:\\dev\\chem\\papers\\2007_LogBB\\data\\Abraham2006ADataBase\\KC291\\LogBB_KC291_LFER_input.txt";
  public void testLFER() {
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

    StatsRes testA = calcRegressMLR(z);
    int dbg = 1;
  }


  public void testKnownLinear() {
  String DIR = INPUT_DIR + File.separator;
    String fileName = DIR
    + "test_linear_regress_var0p01.txt";
    File file = new File(fileName);
    TextFile from = new TextFile();
    from.read(file, null);
    from.trim();
    log.info("from file = \n" + from);

    double[][] z = new MtrxReader(",").readMtrx(-1, -1, from);
    log.info("\nz=\n" + Mtrx.toString(z));

    StatsRes testA = calcRegressMLR(z);
    int dbg = 1;
  }

  private double[][] randomY(double[][] z)
  {
    int nR = z.length;
    double[] y = Mtrx.getCol(0, z);

    int[] idxOrder = IntVec.makeRandIdxArr(nR);
    y = Vec.order(y, idxOrder);
    Mtrx.setCol(0, y, z);
    return z;
  }
}

