package kingroup_v2;
import tsvlib.project.ProjectLogger;

import javax.iox.LineStream;
import javax.swingx.LineStreamView;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/07/2007, Time: 16:50:41
 */
public class KingroupLogger extends ProjectLogger
{
//  private static ProjectLogger log = ProjectLogger.getLogger(QBenchLogger.class);
  private LineStream lineStream;
  private LineStreamView lineStreamView;

  private static KingroupLogger instance;
  public static KingroupLogger getInstance(String name) {
    if (instance == null) {
      instance = new KingroupLogger();
      instance.start(name);
    }
    return instance;
  }
  private KingroupLogger() {
    super();    // root logger
    lineStream = new LineStream();
    lineStreamView = new LineStreamView(lineStream);
    addAppender();
    setThresholdInfo();

//    setupMccv();
//    setupMcvs();
  }

  public void setupMccv() {
    setAll();
    setThresholdAll();

    ProjectLogger.getLogger("javax.swingx").setInfo();
    ProjectLogger.getLogger("javax.iox.TableReader").setInfo();
    ProjectLogger.getLogger("javax.iox").setInfo();
//    ProjectLogger.getLogger("javax.utilx.arrays.mtrx.MtrxFactory").setInfo();
    ProjectLogger.getLogger("javax").setInfo();

//    ProjectLogger.getLogger("qsar.bench").setInfo();
//    ProjectLogger.getLogger("qsar").setInfo();

    ProjectLogger.getLogger("tsvlib.project").setInfo();
    ProjectLogger.getLogger("tsvlib").setInfo();
  }
}
