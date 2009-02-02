package qsar.bench.qsar.cv;
import qsar.bench.QBench;
import qsar.bench.QBenchMainUI;
import qsar.bench.qsar.MLR.MlrRes;
import qsar.bench.qsar.MLR.QsarAlgFactory;
import qsar.bench.qsar.QsarAlg;
import tsvlib.project.ProjectLogger;

import javax.stats.StatsRes;
import javax.swingx.ProgressWnd;
import javax.utilx.arrays.mtrx.Mtrx;
import javax.utilx.arrays.mtrx.MtrxFactory;
import javax.utilx.arrays.mtrx.MtrxPair;
import javax.utilx.arrays.vec.Vec;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 29/04/2007, 16:19:39
 */
public class LoocvMethod {
  private final static ProjectLogger log = ProjectLogger.getLogger(LoocvMethod.class);
  public static final String REFERENCE = "MCCV";
  private QsarAlgFactory algFactory;
  private ProgressWnd progress = null;
  private QBench project;
  private QsarAlg alg;

  public LoocvMethod(QBench model, QsarAlgFactory algFactory) {
    this.project = model;
    this.algFactory = algFactory;
  }

  /**
   * @param z
   * @return average squared prediction error (ASPE)
   */
  public double[] calcY(double[][] z, boolean showProgress)
  {
    log.trace("calcY");
    if (showProgress)
      progress = new ProgressWnd(QBenchMainUI.getInstance(), "calculating LOOCV ...");
    Mtrx mZ = new Mtrx(z);
    log.trace("z = \n", mZ);

    int n = z.length;
    double[] Yp = new double[n];
//    int nFreedom = 0;
    for (int i = 0; i < n; i++) {
      MtrxPair split;
      split = MtrxFactory.selectLOO(i, z);
      Mtrx V = split.getA(); // validate/test
      Mtrx C = split.getB(); // calibrate/train

      alg = algFactory.makeAlg(project, C.getByRows());
      Yp[i] = alg.calcYFromXZ(V.getByRows())[0];
      log.debug("yp[i] = ", Yp[i]);

      if (progress != null
        && progress.isCanceled(i, 0, n)) {
        cleanup();
        return null;
      }
    }
    double[] Y = mZ.getColCopy(0);
    log.debug("Y = ", new Vec(Y));
    log.debug("Yp = ", new Vec(Yp));
    if (progress != null)
      progress.close();
    progress = null;
    return Yp;
  }
  private void cleanup() {
    if (progress != null)
      progress.close();
//    progress = null;
  }

  public String getName()
  {
    return "LOOCV-" + algFactory.getName();
  }

  public String getLastError()
  {
    return alg.getLastError();
  }

  public StatsRes calcStats(double[][] z, boolean b)
  {
    double[] Yp = calcY(z, b);
    Mtrx mZ = new Mtrx(z);
    double[] Y = mZ.getColCopy(0);
    return new MlrRes(Y, Yp, z[0].length - 1);
  }
}
