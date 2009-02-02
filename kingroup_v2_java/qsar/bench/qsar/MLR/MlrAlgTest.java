package qsar.bench.qsar.MLR;
import junit.framework.TestCase;
import tsvlib.project.ProjectLogger;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 29/04/2007, 08:53:58
 */
public class MlrAlgTest extends TestCase
{
  private static ProjectLogger log = ProjectLogger.getLogger(MlrAlg.class);

  public void testQR() {
    double[][] Z = {
      {1, 2, 3}
//      , {4, 5.5, 6.5}   // singular
      , {4, 5.5, 6.9}     ///    a = {0.25, 2.25, -1.25}
      , {7, 8, 9}
    };
    double[][] X = {
      {2, 3}
      , {5, 6}
      , {8, 9}
    };

    log.start("MlrAlgTest");
    log.setAll();

    ProjectLogger.getLogger(MlrAlg.class).setAll();
    MlrAlg alg = new MlrAlg();
    alg.calc(Z);

    ProjectLogger.getLogger(MlrAlgOld.class).setAll();
//    MlrAlgOld alg = new MlrAlgOld(Z);

    double[] Y = alg.calcYFromXZ(X);

    int dbg = 1;
  }
}
