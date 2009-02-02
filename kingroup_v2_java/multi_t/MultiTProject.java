package multi_t;
import tsvlib.project.ProjectLogger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 15/11/2006, Time: 16:33:52
 */
public class MultiTProject  extends MultiT {
  private final static ProjectLogger log = ProjectLogger.getLogger(MultiTProject.class.getName());
  private static MultiT instance;
  private MultiTProject() {
  }
  public static void makeInstance(String appName, String appVersion) {
    instance = new MultiT();
    instance.loadDefault(appName, appVersion);
    instance.loadFromDisk();
  }

// Note: KinGroupV2Project is returned, NOT TomskProject!!!
//       XMLEncoder didnot like static-recursive
  public static MultiT getInstance() {
    if (instance != null)
      return instance;
    log.severe("call MultiTProject.makeInstance() first");
    return null;
  }
}

