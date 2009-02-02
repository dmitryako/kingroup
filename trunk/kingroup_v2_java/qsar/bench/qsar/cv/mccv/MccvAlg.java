package qsar.bench.qsar.cv.mccv;
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
 * Copyright: www.DmitryKonovalov.org, jc138691, 24/04/2007, 10:56:32
 */
public class MccvAlg
{
  private final static ProjectLogger log = ProjectLogger.getLogger(MccvAlg.class);
  public static final String REFERENCE = "MCCV";
  private QBench project;
  private QsarAlgFactory algFactory;
  private ProgressWnd progress = null;

  public MccvAlg(QBench model, QsarAlgFactory algFactory) {
    this.project = model;
    this.algFactory = algFactory;
  }

  /**
   * @param z
   * @return average squared prediction error (ASPE)
   */
  public StatsRes calcStats(double[][] z, boolean showProgress)
  {                                                log.trace("calcStats");  log.trace("z = \n", new Mtrx(z));
    if (showProgress)
      progress = new ProgressWnd(QBenchMainUI.getInstance(), "calculating MCCV ...");
    StatsRes res = new StatsRes();
    Mccv model = project.getMccv();
    int nIter = model.getNumIter();
    int nV = model.getValidSize();

    for (int i = 0; i < nIter; i++) {
      MtrxPair split;
      split = MtrxFactory.selectRandomRows(nV, z);
      Mtrx zV = split.getA(); // validate/test
      Mtrx zC = split.getB(); // calibrate/train
      double[][] xV = zV.getCols(1, zV.getNumCols());   log.trace("xV = \n", new Mtrx(xV));
      QsarAlg alg = algFactory.makeAlg(project, zC.getByRows());
      double[] yVPred = alg.calcYFromXZ(xV);              log.debug("yp = ", new Vec(yVPred));
      if (yVPred == null) {  //ignore errors
        continue;
      }

      StatsRes mlrV = new MlrRes(zV.getColCopy(0), yVPred, alg.getNumPredictors());
      res.add(mlrV);

      if (progress != null
        && progress.isCanceled(i, 0, nIter)) {
        cleanup();
        return res;
      }

      // [080222: for robust MCVS paper]
      if (model.getSaveCalibValidErrs()) {
        MlrRes mlrC = alg.getMlrRes();
        double[][] xC = zC.getCols(1, zC.getNumCols());   log.trace("xCalib = \n", new Mtrx(xC));
        double[] yCPred = alg.calcYFromXZ(xC);
        mlrC.calcMLR(zC.getColCopy(0), yCPred, alg.getNumPredictors());

        double eC = mlrC.getMse(); // calibration error
        double eV = mlrV.getMse(); // validation error
        if (model.getErrorType() == Mccv.MAE) {
          eC = mlrC.getMae();
          eV = mlrV.getMae();
        }
        else if (model.getErrorType() == Mccv.MED_AE) {
          eC = mlrC.getMedAE();
          eV = mlrV.getMedAE();
        }
        else if (model.getErrorType() == Mccv.TMAE) {
          eC = mlrC.getTmae();
          eV = mlrV.getTmae();
        }
        else if (model.getErrorType() == Mccv.TMSE) {
          eC = mlrC.getTmse();
          eV = mlrV.getTmse();
        }
        else if (model.getErrorType() == Mccv.ROB_CORR) {
          eC = mlrC.getRobCorr();
          eV = mlrV.getRobCorr();
        }
        res.save(eV, eC);
      }
    }
    res.norm(nIter);
    if (progress != null)
      progress.close();
    progress = null;
    return res;
  }
  private void cleanup() {
    if (progress != null)
      progress.close();
//    progress = null;
  }
}

