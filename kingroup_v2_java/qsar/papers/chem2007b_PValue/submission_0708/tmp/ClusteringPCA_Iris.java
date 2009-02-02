package qsar.papers.chem2007b_PValue.submission_0708.tmp;
import qsar.papers.chem2007b_PValue.submission_0708.PValueCommon;
import tsvlib.project.ProjectLogger;

import javax.iox.CSVReader;
import javax.iox.TextFile;
import javax.stats.StatsPCA;
import javax.utilx.arrays.mtrx.Mtrx;
import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/11/2006, Time: 11:15:39
 */
public class ClusteringPCA_Iris    extends PValueCommon
{
  protected final static ProjectLogger log = ProjectLogger.getAnonymousLogger();

  // Principal Components Analysis
  public void testPCA() {
    // TO
    String toFileName = OUTPUT_DIR + File.separator + "iris_PCA.csv";
    File toFile = new File(toFileName);
    TextFile to = new TextFile();
    to.setFileName(toFileName);

    // FROM
    String fileName = INPUT_DIR + File.separator + "iris_data" + File.separator + "iris.data.txt";
    File file = new File(fileName);
    TextFile from = new TextFile();
    from.setFileName(file.getName());
    from.read(file, null);
    log.info("from file = \n" + from);

    int nC = 4; // num of cols
    int nR = 149; // num of rows
//    double[][] arr = CSVReader.readDoubleArr(-1, N_COLS, from);
    double[][] z = CSVReader.readDoubleArr(nR, nC, from);
    log.info("aX=\n" + Mtrx.toString(z));

    z = Mtrx.trans(z);
    log.info("trans(aX)=\n" + Mtrx.toString(z));

    // ADD RANDOM NOISE
//    double[] noiseArr = DoubleArr.makeGaussian(nR, 0.0);
//    aX = DoubleArr.appendRowByShallowCopy(noiseArr, aX);
//    noiseArr = DoubleArr.makeRandom(nR);
//    aX = DoubleArr.appendRowByShallowCopy(noiseArr, aX);
//    noiseArr = DoubleArr.makeRandom(nR);
//    aX = DoubleArr.appendRowByShallowCopy(noiseArr, aX);
//    noiseArr = DoubleArr.makeRandom(nR);
//    aX = DoubleArr.appendRowByShallowCopy(noiseArr, aX);
//    noiseArr = DoubleArr.makeRandom(nR);
//    aX = DoubleArr.appendRowByShallowCopy(noiseArr, aX);
//    noiseArr = DoubleArr.makeRandom(nR);
//    aX = DoubleArr.appendRowByShallowCopy(noiseArr, aX);
//    noiseArr = DoubleArr.makeRandom(nR);
//    aX = DoubleArr.appendRowByShallowCopy(noiseArr, aX);
//    log.info("aX+noise=\n" + DoubleArr.toString(aX));

    double[][] aY = StatsPCA.calc(z);
    to.addLines(Mtrx.toCSV(aY));

    int dbg = 1;
    to.write(toFile, null);
  }
}
