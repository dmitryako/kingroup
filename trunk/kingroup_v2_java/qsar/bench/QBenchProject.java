package qsar.bench;
import tsvlib.project.ProjectLogger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 6/03/2007, Time: 11:06:07
 */
public class QBenchProject  extends QBench {
  private final static ProjectLogger log = ProjectLogger.getLogger(QBenchProject.class);
  private static QBench instance;
  private QBenchProject() {
  }
  public static QBench makeInstance(String appName, String appVersion) {
    log.trace("makeInstance(", appName, ", ", appVersion);
    if (instance != null) {
      log.severe("call QBenchProject.makeInstance() only once");
      System.exit(1);
    }
    instance = new QBench();
    instance.loadDefault(appName, appVersion);
    instance.loadFromDisk();
    return instance;
  }

// Note: KinGroupV2Project is returned, NOT TomskProject!!!
//       XMLEncoder didnot like static-recursive
  public static QBench getInstance() {
    if (instance != null)
      return instance;
    log.severe("call QBenchProject.makeInstance() first");
    return null;
  }

}

