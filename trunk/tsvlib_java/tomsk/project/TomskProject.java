package tomsk.project;
import tsvlib.project.ProjectLogger;

/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Aug 20, 2004, Time: 3:34:56 PM
 */

// This is a general template on how to create an application specific Project
public class TomskProject extends Tomsk {
  //                             |
  //                             +- Project
  //                                |
  //                                +- ProjectModel
  private static final ProjectLogger log = ProjectLogger.getLogger(TomskProject.class.getName());
  private static Tomsk instance;
  private TomskProject() {
  }
  public static Tomsk makeInstance(String appName, String appVersion) {
    if (instance != null) {
      log.severe("call TomskProject.makeInstance() only once");
      System.exit(1);
    }
    instance = new Tomsk();
    instance.loadDefault(appName, appVersion);
    instance.loadFromDisk();
    return instance;
  }

// Note: Tomsk is returned, NOT TomskProject!!!
//       XMLEncoder didnot like static-recursive
  public static Tomsk getInstance() {
    if (instance != null)
      return instance;
    log.severe("call TomskProject.makeInstance() first");
    return null;
  }
}