package qsar.papers.chem2007b_PValue.submission_0708.tmp;
import qsar.dragon.DragonResultsFile;
import qsar.dragon.DragonResultsFileReader;
import qsar.papers.chem2007b_PValue.submission_0708.PValueCommon;
import tsvlib.project.ProjectLogger;

import javax.iox.TextFile;
import javax.stats.Stats;
import javax.swing.*;
import javax.utilx.arrays.mtrx.Mtrx;
import javax.utilx.arrays.vec.Vec;
import java.io.File;
import java.util.BitSet;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/10/2006, Time: 11:46:33
 */
public class AbsorptionCollectDragonFiles   extends PValueCommon
{
  protected final static ProjectLogger log = ProjectLogger.getAnonymousLogger();

  public void testCollectDragonFiles() {
    HashMap<String, Double> mapMolNameToAbsorption = loadDeconinckTable_1();

    // TO
    TextFile textFile = new TextFile();
    TextFile textFile2 = new TextFile();
    TextFile textFile3 = new TextFile();

    // FROM
    String dirName = INPUT_DIR + File.separator + "deconick_edragon";
    File dir = new File(dirName);
    dir.isDirectory();
    File[] files = dir.listFiles();
    int nRows = files.length;
    int nCols = 0;
    double[][] mols = new double[nRows][0];

    for (int i = 0; i < nRows; i++) {
      File file = files[i];
//      log.info("file=" + file.getName());

      DragonResultsFile res = DragonResultsFileReader.read(file);
      if (i == 0) {
        String line = " MolName, molId, Absorption, " + res.formatNamesToCSV();
        textFile2.addLine(line);
        nCols = res.getNames().size();

        textFile3.addLine(line.replace(",", "\t"));
        double[] eW = Vec.makeArray(1.0, res.getNames().size());
        line = " MolName, molId, Absorption, " + Vec.toCSV(eW);
        textFile3.addLine(line.replace(",", "\t"));
      }

      if (nCols != res.getNames().size()) {
        String error = "Different number of data columns in file =" + file.getName();
        log.severe(error);
        JOptionPane.showMessageDialog(null, error);
      }

      Double absorp = mapMolNameToAbsorption.get(res.getMolName());
      if (absorp == null) {
        String error = "Missing Absorption Val for =[" + res.getMolName() + "]";
        log.severe(error);
        JOptionPane.showMessageDialog(null, error);
      }
      res.addFirst(absorp, res.getMolName()); // STORE absorption in first column

      mols[i] = Vec.asArray(res.getArr()); // STORE mol
      String line = res.formatArrToCSV();
      textFile.addLine(line);

      line = res.getMolName() + ", "+ res.getMolId() + ", " + res.formatArrToCSV();
      textFile2.addLine(line);

      line = res.getMolName() + ", "+ res.getMolId() + ", 1.0, "
        + (i+1) + ", " + res.formatArrToCSV();
      textFile3.addLine(line.replace(",", "\t"));
    }
    File file = new File(OUTPUT_DIR + File.separator + "deconick_edragon.csv");
    textFile.write(file, null);

    file = new File(OUTPUT_DIR + File.separator + "edragon_with_names.csv");
    textFile2.write(file, null);

    file = new File(OUTPUT_DIR + File.separator + "edragon_for_CLUSTER.txt");
    textFile3.write(file, null);

    file = new File(OUTPUT_DIR + File.separator + "edragon_ZeroMeanOneVar_molByCols.csv");
    textFile2 = new TextFile();
    double[][] vars = Mtrx.trans(mols); // variables/features in rows
    BitSet cols = Mtrx.getNonConstCols(vars);
    vars = Mtrx.getCols(cols, vars);
    vars = Mtrx.removeConstRows(vars);
    Stats.makeRowsMeanZeroVarOne(vars);
    textFile2.addLines(Mtrx.toCSV(vars));
    textFile2.write(file, null);

    file = new File(OUTPUT_DIR + File.separator + "edragon_ZeroMeanOneVar_molByRows.csv");
    textFile2 = new TextFile();
    mols = Mtrx.trans(vars);
    textFile2.addLines(Mtrx.toCSV(mols));
    textFile2.write(file, null);

    file = new File(OUTPUT_DIR + File.separator + "edragon_ZeroMinOneMax_molByRows.csv");
    textFile2 = new TextFile();
    Mtrx.normRowsToRange(vars, 0, 1);
    mols = Mtrx.trans(vars);
    textFile2.addLines(Mtrx.toCSV(mols));
    textFile2.write(file, null);
  }

  public HashMap<String, Double> loadDeconinckTable_1() {
    HashMap<String, Double> map = new HashMap<String, Double>();

    // FROM
    String fromFileName = INPUT_DIR + File.separator + "Deconick_2005_table_1.txt";
    File fromFile = new File(fromFileName);
    if (!fromFile.isFile())
      return null;

    TextFile from = new TextFile();
    from.setFileName(fromFile.getName());
    from.read(fromFile, null);

    for (int i = 0; i < from.size(); i++) {
      String line = from.getLine(i);
      log.info("line=" + line);
      StringTokenizer tokens = new StringTokenizer(line, " ", false);
      String token = tokens.nextToken();
      String name = tokens.nextToken().trim();
      token = tokens.nextToken().trim();
      try {
        Double val = new Double(token);
        if (map.put(name, val) != null) {
          String error = "Duplicate found. name=[" + name + "]";
          log.severe(error);
          JOptionPane.showMessageDialog(null, error);
          return null;
        }
      }
      catch (NumberFormatException e) {
        String error = "Unable to import double from text=[" + token.trim() + "]";
        log.severe(error);
        JOptionPane.showMessageDialog(null, error);
        return null;
      }
    }
    return map;
  }
}
