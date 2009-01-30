package tomsk;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Aug 20, 2004, Time: 2:42:46 PM
 */
import pattern.ucm.UCController;
import tomsk.project.Tomsk;
import tomsk.project.TomskProject;
import tsvlib.project.ProjectFrame;

import java.awt.*;

public class UCStartTomsk implements UCController {
  public static final String REFERENCE = "reference ?";
  static private String appName = "TOMSK";
  static private String appVersion = "v1_070511";
  private static TomskLogger log = TomskLogger.getInstance(appName);

  public static void main(String[] args) {
    new UCStartTomsk().run();
  }
  public boolean run() {
//    ProjectLogger log = ProjectLogger.getInstance();
//    System.setErr(new PrintStream(log.getOutputStream()));
//    Locale.setDefault(Locale.FRENCH);

    System.out.println(appName + " [starting ...]");
    Tomsk project = TomskProject.makeInstance(appName, appVersion);

    ProjectFrame frame = new TomskFrame(project);
    frame.pack();
    frame.setSize(new Dimension(300, 300));
    frame.setVisible(true);

    return true;
  }
}