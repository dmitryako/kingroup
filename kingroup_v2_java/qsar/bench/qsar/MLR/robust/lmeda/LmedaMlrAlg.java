package qsar.bench.qsar.MLR.robust.lmeda;
import qsar.bench.qsar.MLR.MlrAlg;
import qsar.bench.qsar.MLR.robust.lta.LtaMlrAlg;
import tsvlib.project.ProjectLogger;

import javax.utilx.arrays.vec.Vec;
import java.util.Arrays;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 21/05/2008, Time: 16:48:22
 */
public class LmedaMlrAlg  extends LtaMlrAlg
{
  private static ProjectLogger log = ProjectLogger.getLogger(LmedaMlrAlg.class);

  protected double calcErr(MlrAlg alg, double[] y, double[][] z)
  {
    double[] yp = alg.calcYFromXZ(z);   log.debug("yp = ", new Vec(yp));
    Vec.add(yp, -1., y);                log.debug("yp-y = ", new Vec(yp));
    Vec.abs(yp);                        log.debug("abs(yp) = ", new Vec(yp));
    Arrays.sort(yp);
    double err = Vec.calcMedianFromSorted(yp); log.debug("err = ", err);
//    double err = Vec.medianSLOW(yp);    log.debug("err = ", err);
    return err;
  }
}
