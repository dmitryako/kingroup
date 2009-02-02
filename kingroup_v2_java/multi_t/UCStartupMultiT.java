package multi_t;
import multi_t.ucm.settings.UCShowSettingsUI;
import multi_t.ucm.settings.UCReadPrimerFile;

import java.awt.*;
import tsvlib.project.ProjectLogger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 15/11/2006, Time: 16:30:56
 */
public class UCStartupMultiT  {
  private final static ProjectLogger log = ProjectLogger.getLogger(UCStartupMultiT.class.getName());
  public static final String REFERENCE = "reference ?";
  static private String appName = "MultiT";
  static private String appVersion = "v1_061122b";
  public static void main(String[] args) {
    new UCStartupMultiT().startup(args);
  }
  public void startup(String[] args)
  {
//    Locale.setDefault(Locale.FRENCH);
//    System.setErr(new PrintStream(FixedSizeStringStream.getInstance()));

//    ProjectLogger.getLogger("").addHandler(new GUIHandler());

    System.out.println(appName + " [starting ...]");
    MultiTProject.makeInstance(appName, appVersion);

    MultiT project = MultiTProject.getInstance();

    MultiTFrame frame = new MultiTFrame(project);
    frame.pack();
    frame.setSize(new Dimension(300, 300));
    frame.setVisible(true);


    new UCShowSettingsUI().run();
    MultiTMainUI ui = MultiTMainUI.getInstance();
    new UCReadPrimerFile(ui.getSettingsView()).run();
  }
}

