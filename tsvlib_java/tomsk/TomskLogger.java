package tomsk;
import tsvlib.project.ProjectLogger;

import javax.iox.LineStream;
import javax.swingx.LineStreamView;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 8/05/2007, 11:12:16
 */
public class TomskLogger  extends ProjectLogger
{
  private LineStream lineStream;
  private LineStreamView lineStreamView;

  private static TomskLogger instance;
  public static TomskLogger getInstance(String name) {
    if (instance == null) {
      instance = new TomskLogger();
      instance.start(name);
    }
    return instance;
  }
  private TomskLogger() {
    super();    // root logger
    lineStream = new LineStream();
    lineStreamView = new LineStreamView(lineStream);

    addAppender();
    setAll();
    setThresholdAll();

    setup3DViewer();
  }

  public void setup3DViewer() {
    ProjectLogger.getLogger("javax.swingx").setInfo();
    ProjectLogger.getLogger("javax.iox.TableReader").setInfo();
    ProjectLogger.getLogger("javax.iox").setInfo();
//    ProjectLogger.getLogger("javax.utilx.arrays.mtrx.MtrxFactory").setInfo();
    ProjectLogger.getLogger("javax").setInfo();

//    ProjectLogger.getLogger("qsar.bench.qsar.cv").setInfo();
//    ProjectLogger.getLogger(MccvMethod.class).setTrace();
//    ProjectLogger.getLogger(MccvMethod.class).setDebug();

//    ProjectLogger.getLogger("qsar.bench.ucm.alg").setInfo();

//    ProjectLogger.getLogger("qsar.bench.ucm").setInfo();
//    ProjectLogger.getLogger("tomsk").setInfo();

    ProjectLogger.getLogger("tsvlib.project").setInfo();
//    ProjectLogger.getLogger("tsvlib").setInfo();
  }
}

