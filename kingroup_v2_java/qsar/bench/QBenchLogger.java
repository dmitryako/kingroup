package qsar.bench;
import qsar.bench.qsar.MLR.MlrAlgOld;
import qsar.bench.qsar.cv.mccv.Mccv;
import tsvlib.project.ProjectLogger;

import javax.iox.LineStream;
import javax.swingx.LineStreamView;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 18/04/2007, 16:26:57
 */
public class QBenchLogger extends ProjectLogger
{
//  private static ProjectLogger log = ProjectLogger.getLogger(QBenchLogger.class);
  private LineStream lineStream;
  private LineStreamView lineStreamView;

  private static QBenchLogger instance;
  public static QBenchLogger getInstance(String name) {
    if (instance == null) {
      instance = new QBenchLogger();
      instance.start(name);
    }
    return instance;
  }
  private QBenchLogger() {
    super();    // root logger
    lineStream = new LineStream();
    lineStreamView = new LineStreamView(lineStream);

    addAppender();
    setThresholdInfo();

//    setupHuber();
//    setupMccv();
//    setupMcvs();
  }

  public void setupHuber() {
//    ProjectLogger.getLogger("qsar.bench.qsar").setAll();
    ProjectLogger.getLogger("qsar.bench.qsar.MLR").setAll();
    setThresholdAll();
  }
  public void setupMccv() {
    setAll();
    setThresholdAll();

    ProjectLogger.getLogger("javax.swingx").setInfo();
//    ProjectLogger.getLogger("javax.iox.TableReader").setInfo();
//    ProjectLogger.getLogger("javax.iox").setInfo();
//    ProjectLogger.getLogger("javax.utilx.arrays.mtrx.MtrxFactory").setInfo();
    ProjectLogger.getLogger("javax").setInfo();


//    ProjectLogger.getLogger("qsar.bench.qsar.cv").setInfo();
//    ProjectLogger.getLogger(MccvAlg.class).setTrace();
//    ProjectLogger.getLogger(MccvAlg.class).setDebug();

//    ProjectLogger.getLogger(MlrAlgOld.class).setInfo();
//    ProjectLogger.getLogger("qsar.bench.qsar").setInfo();
//    ProjectLogger.getLogger("qsar.bench.qsar").setDebug();

//    ProjectLogger.getLogger("qsar.bench.ucm.alg").setInfo();

//    ProjectLogger.getLogger("qsar.bench.ucm").setInfo();
//    ProjectLogger.getLogger("qsar.bench").setInfo();
//    ProjectLogger.getLogger("qsar").setInfo();

    ProjectLogger.getLogger("tsvlib.project").setInfo();
    ProjectLogger.getLogger("tsvlib").setInfo();
  }

  public void setupMcvs() {
    setAll();
    setThresholdAll();

    ProjectLogger.getLogger("javax.swingx").setInfo();

//    ProjectLogger.getLogger("javax.iox.TableReader").setInfo();

//    ProjectLogger.getLogger("javax.iox").setInfo();
//    ProjectLogger.getLogger("javax.utilx.arrays.mtrx.MtrxFactory").setInfo();
    ProjectLogger.getLogger("javax").setInfo();

    ProjectLogger.getLogger(Mccv.class).setInfo();
    ProjectLogger.getLogger(QBench.class).setInfo();
//    ProjectLogger.getLogger("qsar.bench.ucm.table").setInfo();

    ProjectLogger.getLogger(MlrAlgOld.class).setInfo();
//    ProjectLogger.getLogger("qsar.bench.qsar").setInfo();
//    ProjectLogger.getLogger("qsar.bench.qsar").setDebug();

//    ProjectLogger.getLogger("qsar.bench.ucm.alg").setInfo();
//    ProjectLogger.getLogger("qsar.bench").setInfo();

//    ProjectLogger.getLogger("qsar").setInfo();
//    ProjectLogger.getLogger(McvsSaAlg.class).setDebug();


//    ProjectLogger.getLogger("qsar").setDebug();
    ProjectLogger.getLogger("tsvlib.project").setInfo();
    ProjectLogger.getLogger("tsvlib").setInfo();
  }
}
