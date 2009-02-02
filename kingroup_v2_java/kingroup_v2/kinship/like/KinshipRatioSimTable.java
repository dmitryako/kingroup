package kingroup_v2.kinship.like;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import java.text.NumberFormat;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 4/10/2005, Time: 12:04:17
 */
public class KinshipRatioSimTable {
  private static ProjectLogger log = ProjectLogger.getLogger(KinshipRatioSimTable.class.getName());
  protected double[] sigLevelLogs; //type I errors
  protected double[] typeTwoErrorLogs; //type II errors
  private KinshipRatioSim typeOne;

  private NumberFormat format_0;
  private NumberFormat format_1;
  private static final float LEVEL_1 = 0.1f;
//  private static final float LEVEL_2 = 0.01f;

  public KinshipRatioSimTable() {
    init();
  }
  public boolean calc(Kinship kinship, SysPop pop) {
    typeOne = new KinshipRatioSim(kinship.getNullIBD(), kinship);
    if (!typeOne.calc(pop.getFreq(), "Type I errors"))
      return false;
    sigLevelLogs = typeOne.calcSigLevelLogs(kinship.getSigLevels());

    KinshipRatioSim typeTwo = new KinshipRatioSim(kinship.getPrimIBD(), kinship);
    if (!typeTwo.calc(pop.getFreq(), "Type II errors"))
      return false;
    typeTwoErrorLogs = typeTwo.calcTypeTwoLogs(sigLevelLogs);
    // log.info("typeTwoErrorLogs=" + DoubleArr.toString(typeTwoErrorLogs));
    return true;
  }
  public double[] getTypeTwoErrorLogs() {
    return typeTwoErrorLogs;
  }
  public double[] getSigLevelLogs() {
    return sigLevelLogs;
  }
  public float calcTypeI(double rLog) {
    return typeOne.calcTypeI(rLog);
  }

  public double[] getLogs()
  {
    return typeOne.getLogs();
  }
  public void merge(KinshipRatioSimTable from)
  {
    double[] arr = from.getSigLevelLogs();
    for (int i = 0; i < arr.length; i++) {
      double v = arr[i];
      if (sigLevelLogs[i] < v)
        sigLevelLogs[i] = v;
    }

    arr = from.getTypeTwoErrorLogs();
    for (int i = 0; i < arr.length; i++) {
      double v = arr[i];
      if (typeTwoErrorLogs[i] < v)
        typeTwoErrorLogs[i] = v;
    }
  }
  private void init() {
    format_0 = NumberFormat.getNumberInstance();
    format_0.setMaximumFractionDigits(0);
    format_1 = NumberFormat.getNumberInstance();
    format_1.setMaximumFractionDigits(1);
  }
  public String toFlag(double logVal) {
    if (logVal >= sigLevelLogs[2])
      return "***";
    if (logVal >= sigLevelLogs[1])
      return "**";
    if (logVal >= sigLevelLogs[0])
      return "*";
    return "ns";
  }
  public double toPValue(double logVal) {
    return calcTypeI(logVal);
  }
  public String formatPValue(double pv) {
    if (pv < LEVEL_1)
      return format_1.format(100. * pv);
    else
      return format_0.format(100. * pv);
  }
}
