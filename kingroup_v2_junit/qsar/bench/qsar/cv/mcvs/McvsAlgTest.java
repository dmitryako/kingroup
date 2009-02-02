package qsar.bench.qsar.cv.mcvs;
import junit.framework.TestCase;
import qsar.bench.QBench;
import qsar.bench.QBenchProject;
import qsar.bench.qsar.MLR.MlrAlgFactoryMLR;
import qsar.bench.qsar.MLR.QsarAlgFactory;
import qsar.bench.qsar.cv.mcvs.sa.McvsSaAlg;
import tsvlib.project.ProjectLogger;

import javax.iox.TableFormat;
import javax.iox.TableReader;
import javax.iox.table.Table;
import javax.iox.table.TableFactory;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 7/06/2007, 13:51:02
 */
public class McvsAlgTest extends TestCase
{
  private static ProjectLogger log = ProjectLogger.getLogger(McvsAlgTest.class);
  private static final int N_TESTS = 5;
  private static final int N_COLS_IGNORE_KC290 = 1;
  private static final int IDX_Y = 0;

  public static void main( String[] args )
  {
    log.start("testGetRandomSetIdx");
    ProjectLogger.getRootLogger().setAll();
    ProjectLogger.getLogger("tsvlib.project").setInfo();
//    ProjectLogger.getLogger("tsvlib.project.Project").setInfo();
    ProjectLogger.getLogger("qsar.bench.qsar.cv.mccv.Mccv").setInfo();
    ProjectLogger.getLogger("qsar.bench.qsar.cv.mcvs.Mcvs").setInfo();
    ProjectLogger.getLogger("qsar.bench.qsar.MLR").setInfo();

    TableFormat format = makeFormat();
    String FILE_NAME = "E:\\dev\\chem\\papers\\2007c_absorption\\JChemInfModel\\data\\logBB\\Z_KC290_edragon.txt";
    Table table = TableReader.makeFromFile(FILE_NAME, format, true);
    table = TableFactory.sortRows(IDX_Y, table, true);
    double[][] z = table.getMtrx();

    QBench project = QBenchProject.makeInstance("McvsAlgTest", "070607");
    QsarAlgFactory algFactory = new MlrAlgFactoryMLR();

    McvsSaAlg alg = new McvsSaAlg(project, algFactory, null);
    alg.calcStats(z, false);
  }

  public static TableFormat makeFormat() {
    TableFormat res;
    res = new TableFormat();
    res.loadDefaults();
    res.setColumnDelim('\t');

    res.setHeaderRow(1);
    res.setHeaderCol(1);

    res.setFirstRow(2);
    res.setFirstCol(2);

    res.setLastRow(0);
    res.setLastCol(0);
    return res;
  }
}
