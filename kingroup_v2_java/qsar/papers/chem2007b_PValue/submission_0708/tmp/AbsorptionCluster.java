package qsar.papers.chem2007b_PValue.submission_0708.tmp;
import qsar.papers.chem2007b_PValue.submission_0708.PValueCommon;
import tsvlib.project.ProjectLogger;

import javax.iox.CSVReader;
import javax.iox.TextFile;
import javax.stats.Stats;
import javax.stats.StatsPCA;
import javax.stats.StatsRes;
import javax.utilx.arrays.mtrx.Mtrx;
import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 4/12/2006, Time: 11:14:15
 */
public class AbsorptionCluster extends PValueCommon
{
  protected final static ProjectLogger log = ProjectLogger.getAnonymousLogger();

  public void testDragonPCA() {
//    String a = "a";
//    String b = "b";
////    String b = "a";
//    int x = 0;

    // TO
    String fileName = OUTPUT_DIR + File.separator + "edragon_PCA_by_mols.csv";
    File toFile = new File(fileName);
    TextFile textFile = new TextFile();
    textFile.setFileName(fileName);

//    fileName = OUTPUT_DIR + File.separator + "edragon_covByMols.csv";
//    File toFileCov = new File(fileName);
//    TextFile toCov = new TextFile();
//    toCov.setFileName(fileName);

    // FROM
    fileName = OUTPUT_DIR + File.separator + "edragon_ZeroMeanOneVar_molByCols.csv";
    File file = new File(fileName);
    TextFile from = new TextFile();
    from.setFileName(file.getName());
    from.read(file, null);
//    log.info("from file = \n" + from);

    double[][] z = CSVReader.readDoubleArr(-1, -1, from);
//    double[][] zt = Vec.transpose(z);

    int N_VARS = 250;
    StatsRes res = Stats.selectRowsByCorr(z, N_VARS, 1.); //
    z = res.getArr();

//    double[][] C = Stats.corr(aX); // correlation
//    log.info("cov= \n" + DoubleArr.toString(C));
//
//    double[][] abspDist = calcAbsorpDist(aX);
//    log.info("abspDist= \n" + DoubleArr.toString(abspDist));

    double[][] mols = Mtrx.trans(z);    // transpose to get PCA by descriptors
    double[][] aY = StatsPCA.calc(mols);    // PCA
    textFile.addLines(Mtrx.toCSV(aY));
    textFile.write(toFile, null);

    file = new File(OUTPUT_DIR + File.separator + "edragon_ZeroMinOneMax_molByRows_noNoise.csv");
    textFile = new TextFile();
    Mtrx.normRowsToRange(z, 0, 1);
    textFile.addLines(Mtrx.toCSV(mols));
    textFile.write(file, null);
    int dbg = 1;
  }

}
