package qsar.bench;
import pattern.ucm.UCController;
import qsar.papers.chem2007_LogBB.LogBB_2007_paper;

import javax.swing.*;
import java.awt.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 6/03/2007, Time: 11:02:43
 */
public class UCStartQSARBench implements UCController {
  public static final String REFERENCE = LogBB_2007_paper.REFERENCE;

  static private final String appName = "QSAR_BENCH";
  static private final String appVersion = "v2_090817";

  private static QBenchLogger log = QBenchLogger.getInstance(appName);

  public static void main(String[] args) {
    // http://java.sun.com/docs/books/tutorial/uiswing/examples/misc/InputVerificationDemoProject/src/misc/InputVerificationDemo.java
    // Turn off metal's use of bold fonts
    UIManager.put("swing.boldMetal", Boolean.FALSE);

    try {
      //Schedule a job for the event-dispatching thread:
      //creating and showing this application's GUI.
      //http://java.sun.com/docs/books/tutorial/uiswing/examples/misc/InputVerificationDemoProject/src/misc/InputVerificationDemo.java
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          new UCStartQSARBench().run();
        }
      });
    } catch(Exception e) {
      log.error("Exception", e);
    } catch(java.lang.OutOfMemoryError e) {
      log.error("Exception", e);
    }
  }

  public boolean run()
  {
//    ProjectLogger logger = ProjectLogger.getInstance();
//    System.setErr(new PrintStream(logger.getOutputStream()));

//    Locale.setDefault(Locale.FRENCH);

    QBench project = QBenchProject.makeInstance(appName, appVersion);

    QBenchFrame frame = new QBenchFrame(project);
    frame.pack();
    frame.setSize(new Dimension(300, 300));
    frame.setVisible(true);

    return true;
  }
}
