package kingroup_v2;
import javax.swing.*;
import java.awt.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 9/09/2005, Time: 15:19:45
 */
public class KinGroupV2Startup {
  public static final String REFERENCE = "Konovalov,Manning&Henshaw(2004)MolEcolNotes4,p779";
  static private String appName = "KINGROUP";
  static private String appVersion = "v2_090501";

//  private final static ProjectLogger log = ProjectLogger.getLogger(KinGroupV2Startup.class.getName());
  private static KingroupLogger log = KingroupLogger.getInstance(appName);

  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
      UIManager.put("swing.boldMetal", Boolean.FALSE);
//      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    } catch (InstantiationException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    } catch (IllegalAccessException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    } catch (UnsupportedLookAndFeelException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }


    try {
      //Schedule a job for the event-dispatching thread: creating and showing this application's GUI.
      //http://java.sun.com/docs/books/tutorial/uiswing/examples/misc/InputVerificationDemoProject/src/misc/InputVerificationDemo.java
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          new KinGroupV2Startup().run();
//          new RunQMStation().run();
        }
      });
    } catch(Exception e) {
      log.error("Exception", e);
    } catch(java.lang.OutOfMemoryError e) {
      log.error("Exception", e);
    }
  }
  public void run()
  {
//    ProjectLogger log = ProjectLogger.getInstance();
//    System.setErr(new PrintStream(log.getOutputStream()));
//    Locale.setDefault(Locale.FRENCH);

    System.out.println(appName + " [starting ...]");
    KinGroupV2Project.makeInstance(appName, appVersion);

    Kingroup project = KinGroupV2Project.getInstance();

    KingroupFrame frame = new KingroupFrame(project);
    frame.pack();
    frame.setSize(new Dimension(300, 300));
    frame.setVisible(true);
  }
}