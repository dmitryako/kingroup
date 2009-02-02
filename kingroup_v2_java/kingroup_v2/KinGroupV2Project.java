package kingroup_v2;
import tsvlib.project.ProjectLogger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 9/09/2005, Time: 15:22:53
 */
public class KinGroupV2Project extends Kingroup {
  private final static ProjectLogger log = ProjectLogger.getLogger(KinGroupV2Project.class.getName());
  private static Kingroup instance;
  private KinGroupV2Project() {
  }
  public static void makeInstance(String appName, String appVersion) {
    instance = new Kingroup();
    instance.loadDefault(appName, appVersion);
    instance.loadFromDisk();
  }

// Note: KinGroupV2Project is returned, NOT TomskProject!!!
//       XMLEncoder didnot like static-recursive
  public static Kingroup getInstance() {
    if (instance != null)
      return instance;
    log.severe("call KinGroupV2Project.makeInstance() first");
    return null;
  }
}
