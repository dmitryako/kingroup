package qsar.bench.qsar.cv;
import qsar.kriging.LooAlgI;
import tsvlib.project.ProjectLogger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/01/2007, Time: 17:29:11
 */
public class Loocv
{
  private static ProjectLogger log = ProjectLogger.getLogger(Loocv.class);

  private LooAlgI alg;
  public Loocv(LooAlgI alg) {
    this.alg = alg;
  }
  public double[] calcLOO(double[][] z) {
    return null; // do not use!!!!!!!!!!!
//
//    int nR = z.length;
//    double[] res = new double[nR];
//    for (int i = 0; i < nR; i++) {
//      res[i] = alg.calc(i, z);
//      if (res[i] == Double.NaN) {
//        Level saved = log.setAll();
//        log.debug("Unable to calc i=", i);
//        log.setLevel(saved);
//
//        ProjectLogger tmpLog = ProjectLogger.getLogger(alg.getClass());
//        saved = tmpLog.setAll();
//        res[i] = alg.calc(i, z);
//        tmpLog.setLevel(saved);
//        return null;
//      }
//    }
//    return res;
  }

}
