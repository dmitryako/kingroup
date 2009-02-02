package kingroup.project;
import javax.iox.LOG;

/* Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 */

// This is a general template on how to create an application specific Project
public class KinGroupProjectV1 extends KinGroupProjectFacade {
  //                             |
  //                             +- Project
  //                                |
  //                                +- ProjectModel
  private static KinGroupProjectFacade instance;
  private KinGroupProjectV1() {
  }
  public static void makeInstance(String appName, String appVersion) {
    instance = new KinGroupProjectFacade();
    instance.loadDefault(appName, appVersion);
    instance.loadFromDisk();
  }

// Note: Tomsk is returned, NOT TomskProject!!!
//       XMLEncoder didnot like static-recursive
  public static KinGroupProjectFacade getInstance() {
    if (instance != null)
      return instance;
    LOG.throwError(null, "call KinGroupProjectV1.makeInstance() first", "");
    return null;
  }
}







