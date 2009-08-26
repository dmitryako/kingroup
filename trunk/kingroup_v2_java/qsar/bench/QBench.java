package qsar.bench;
import qsar.bench.descriptors.LFER.LferPlatts;
import qsar.bench.qsar.QsarTableFormat;
import qsar.bench.qsar.QsarTypeI;
import qsar.bench.qsar.cv.mccv.Mccv;
import qsar.bench.qsar.cv.mcvs.Mcvs;
import qsar.papers.chem2007_LogBB.LogBB_2007_paper;
import tsvlib.project.Project;
import tsvlib.project.ProjectLogger;
import tsvlib.project.ProjectModel;

import javax.iox.TableFormat;
import javax.iox.table.TableDisplayOpt;
import javax.textx.FractionDigitsModel;
import java.awt.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 6/03/2007, Time: 11:06:45
 */
public class QBench    extends Project
  implements QsarTypeI
{
  private final static ProjectLogger log = ProjectLogger.getLogger(QBench.class);

  private String trainTableName;
  private String predictTableName;
  private String testTableName;
  private String resultsFileName;
  private QsarTableFormat trainTableFormat;
  private QsarTableFormat testTableFormat;
  private QsarTableFormat predictTableFormat;
  private FractionDigitsModel digitsModel = new FractionDigitsModel();
  private TableDisplayOpt tableDisplayOpt;

  private static int alg_count = 0;
  public static final int ALG_MLR = alg_count++;
  public static final int ALG_kNN = alg_count++;
  private int qsarAlgId;

  private static int robust_count = 0;
  public static final int ROBUST_MCLTS = robust_count++;
  public static final int ROBUST_LMEDA = robust_count++;
  public static final int ROBUST_CLTS = robust_count++;
  public static final int ROBUST_LTA = robust_count++;
  public static final int ROBUST_HUBER = robust_count++;
  private int robustType;

  private int cltsNumStarts;
  private int robustParamInt;

  private static int cv_count = -1; // CROSS-VALIDATION
  public static final int CV_NONE = cv_count++;
  public static final int CV_LOO = cv_count++;
  public static final int CV_MCCV = cv_count++;
  public static final int CV_MCVS_SA = cv_count++;
  public static final int CV_MCVS_GA = cv_count++;
  private int crossValidId;

  private boolean meanZeroVarOne;
  private int knn;

  private static int table_count = 0;
  public static final int QSAR_CALIB = table_count++;   // calibration
  public static final int QSAR_VALID = table_count++;   // validation
  public static final int QSAR_PREDICT = table_count++;
  private int qsarType;
  private String smilesFileName;
  private TableFormat smilesTableFormat;
  private LferPlatts lferPlatts;

  private Mccv mccv;
  private Mcvs mcvs;
  private TableFormat selectTable;
  private Color colorConsole;

  public QBench() {
    init();
  }
  private void init() {
    trainTableName = new String();
    resultsFileName = new String();

    QsarTableFormat qtf = new QsarTableFormat();
    qtf.setQsarType(QSAR_CALIB);
    LogBB_2007_paper.loadTableFormat(qtf); // FOR logBB paper
    setTrainTableFormat(qtf);

    qtf = new QsarTableFormat();
    qtf.setQsarType(QSAR_VALID);
    setTestTableFormat(qtf);

    qtf = new QsarTableFormat();
    qtf.setQsarType(QSAR_PREDICT);
    setPredictTableFormat(qtf);

    TableFormat tf = new TableFormat();
    tf.setFirstRow(1);
    tf.setFirstCol(1);
    tf.setHeaderRow(0);
    tf.setHeaderCol(0);
    setSmilesTableFormat(tf);

    tf = new TableFormat();
    tf.setFirstRow(1);
    tf.setLastRow(1);
    tf.setFirstCol(1);
    tf.setLastCol(1);
    setSelectTable(tf);

    LferPlatts t = new LferPlatts();
    setLferPlatts(t);

//    setTrainTableName("E:\\dev\\chem\\papers\\2007_LogBB\\JChemInfModel\\data\\KC291.txt");
//    setTrainTableName("E:\\dev\\chem\\papers\\2007c_absorption\\JChemInfModel\\data\\logBB\\Table_S1_KC290_SMILES_LogBB_Iv.txt");
//    setTrainTableName("E:\\dev\\chem\\papers\\2007b_PValue\\JChemInfModel\\data\\HIA\\KS127\\Table_S1_KS127_SMILES.txt");
//    setTrainTableName("E:\\dev\\chem\\papers\\2007_LogBB\\JChemInfModel\\data\\AI328.txt");
    setTrainTableName("E:\\dev\\chem\\papers\\2008_Robust\\data\\logBB\\Table_S1_KS289_logBB_Iv_EDrgn.txt");    

    setTestTableName(getTrainTableName());
    setPredictTableName(getTrainTableName());

    setSmilesFileName("C:\\Documents and Settings\\jc138691\\My Documents\\dev\\chem\\QSARBench\\smiles\\LogBB_KC291_LFER_input.txt");
//    setSmilesFileName(getTrainTableName());

    FractionDigitsModel digits = new FractionDigitsModel();
    digits.loadDefault();
    setDigitsModel(digits);

    TableDisplayOpt tableOpt = new TableDisplayOpt();
    tableOpt.loadDefault();
    setTableDisplayOpt(tableOpt);

    Mccv mc = new Mccv();
    mc.loadDefault();
    setMccv(mc);

    Mcvs vs = new Mcvs();
    vs.loadDefault();
    setMcvs(vs);
  }
  public void loadDefault(String appName, String appVersion) {
    log.trace("loadDefault(", appName, appVersion);
    super.loadDefault(appName, appVersion);

    setErrorLogFileName("ErrorLog.txt");
    digitsModel.loadDefault();
    tableDisplayOpt.loadDefault();
    tableDisplayOpt.getDigitsModel().setSelectedIdx(7);

    colorConsole = new Color(255, 255, 255);

    setMeanZeroVarOne(false);
    setKnn(70);
    setQsarType(QSAR_CALIB);
    setCrossValidId(CV_NONE);
    setQsarAlgId(ALG_MLR);
    setRobustType(ROBUST_LTA);
    lferPlatts.loadDefault();
    mccv.loadDefault();
    setProjectFileExtension("qbp");

    setCltsNumStarts(-1);
    setLookFeel(ProjectModel.SYSTEM_LOOK);
  }
  public void copyTo(ProjectModel to) {
    super.copyTo(to);
    if (!(to instanceof QBench)) {
      log.severe("Copy destination is not a QSAR-BENCH object");
    }
    QBench model = (QBench) to;

    model.setErrorLogFileName(getErrorLogFileName());

    model.setTrainTableName(getTrainTableName());
    model.setTestTableName(getTestTableName());
    model.setPredictTableName(getPredictTableName());
    model.setSelectTable(getSelectTable());

    model.setResultsFileName(getResultsFileName());
    model.setLastProjectFileName(getLastProjectFileName());

    model.setTrainTableFormat(getTrainTableFormat());
    model.setTestTableFormat(getTestTableFormat());
    model.setPredictTableFormat(getPredictTableFormat());

//    bean.setTrainTableFormat(getTrainTableFormat());

    model.setDigitsModel(getDigitsModel());
    model.setTableDisplayOpt(getTableDisplayOpt());
    model.setMeanZeroVarOne(getMeanZeroVarOne());
    model.setQsarAlgId(getQsarAlgId());
    model.setCrossValidId(getCrossValidId());
    model.setMccv(getMccv());
    model.setMcvs(getMcvs());
    model.setKnn(getKnn());
    model.setQsarType(getQsarType());

    model.setCltsNumStarts(getCltsNumStarts());
    model.setRobustType(getRobustType());
    model.setRobustParamInt(getRobustParamInt());

    model.setSmilesFileName(getSmilesFileName());
    model.setSmilesTableFormat(getSmilesTableFormat());
    model.setLferPlatts(getLferPlatts());

  }
  public String getTrainTableName() {
    return trainTableName;
  }
  public void setTrainTableName(String lastTrainingTableName) {
    this.trainTableName = lastTrainingTableName;
  }
  public String getResultsFileName() {
    return resultsFileName;
  }
  public void setResultsFileName(String resultsFileName) {
    this.resultsFileName = resultsFileName;
  }

  public QsarTableFormat getTrainTableFormat()
  {
    return trainTableFormat;
  }

  public void setTrainTableFormat(QsarTableFormat trainTableFormat)
  {
    this.trainTableFormat = trainTableFormat;
  }

  public int getQsarAlgId()
  {
    return qsarAlgId;
  }

  public int getCrossValidId()
  {
    return crossValidId;
  }

  public void setQsarAlgId(int qsarAlgId)
  {
    this.qsarAlgId = qsarAlgId;
  }
  public FractionDigitsModel getDigitsModel() {
    return digitsModel;
  }

  public void setDigitsModel(FractionDigitsModel digitsModel)
  {
    this.digitsModel = digitsModel;
  }

  public boolean getMeanZeroVarOne()
  {
    return meanZeroVarOne;
  }

  public void setMeanZeroVarOne(boolean meanZeroVarOne)
  {
    this.meanZeroVarOne = meanZeroVarOne;
  }

  public int getKnn()
  {
    return knn;
  }

  public void setKnn(int knn)
  {
    this.knn = knn;
  }

  public QsarTableFormat getTestTableFormat()
  {
    return testTableFormat;
  }

  public QsarTableFormat getPredictTableFormat()
  {
    return predictTableFormat;
  }

  public void setTestTableFormat(QsarTableFormat testTableFormat)
  {
    this.testTableFormat = testTableFormat;
  }

  public String getTestTableName()
  {
    return testTableName;
  }

  public void setTestTableName(String testTableName)
  {
    this.testTableName = testTableName;
  }

  public void setPredictTableFormat(QsarTableFormat predictTableFormat)
  {
    this.predictTableFormat = predictTableFormat;
  }

  public String getPredictTableName()
  {
    return predictTableName;
  }

  public void setPredictTableName(String predictTableName)
  {
    this.predictTableName = predictTableName;
  }
  public int getQsarType()
  {
    return qsarType;
  }

  public void setQsarType(int qsarType)
  {
    this.qsarType = qsarType;
  }

  public char getColumnDelim()
  {
    return getTrainTableFormat().getColumnDelim();
  }
  public void setColumnDelim(char c) {
    getTrainTableFormat().setColumnDelim(c);
    getTestTableFormat().setColumnDelim(c);
    getPredictTableFormat().setColumnDelim(c);
  }

  public String getSmilesFileName()
  {
    return smilesFileName;
  }

  public void setSmilesFileName(String smilesFileName)
  {
    this.smilesFileName = smilesFileName;
  }

  public TableFormat getSmilesTableFormat()
  {
    return smilesTableFormat;
  }

  public void setSmilesTableFormat(TableFormat smilesTableFormat)
  {
    this.smilesTableFormat = smilesTableFormat;
  }

  public LferPlatts getLferPlatts()
  {
    return lferPlatts;
  }

  public void setLferPlatts(LferPlatts lferPlatts)
  {
    this.lferPlatts = lferPlatts;
  }

  public void setCrossValidId(int cValidId)
  {
    this.crossValidId = cValidId;
  }

  public Mccv getMccv()
  {
    return mccv;
  }

  public void setMccv(Mccv mccv)
  {
    this.mccv = mccv;
  }

  public TableDisplayOpt getTableDisplayOpt()
  {
    return tableDisplayOpt;
  }

  public void setTableDisplayOpt(TableDisplayOpt tableDisplayOpt)
  {
    this.tableDisplayOpt = tableDisplayOpt;
  }

  public void setMcvs(Mcvs mcvs)
  {
    this.mcvs = mcvs;
  }

  public Mcvs getMcvs()
  {
    return mcvs;
  }

  public TableFormat getSelectTable()
  {
    return selectTable;
  }

  public void setSelectTable(TableFormat selectTable)
  {
    this.selectTable = selectTable;
  }

  public int getRobustType()
  {
    return robustType;
  }

  public void setRobustType(int robustType)
  {
    this.robustType = robustType;
  }

  public int getCltsNumStarts()
  {
    return cltsNumStarts;
  }

  public void setCltsNumStarts(int cltsNumStarts)
  {
    this.cltsNumStarts = cltsNumStarts;
  }

  public int getRobustParamInt()
  {
    return robustParamInt;
  }

  public void setRobustParamInt(int robustParamInt)
  {
    this.robustParamInt = robustParamInt;
  }

  public Color getColorConsole() {
    return colorConsole;
  }

  public void setColorConsole(Color colorConsole) {
    this.colorConsole = colorConsole;
  }
}

