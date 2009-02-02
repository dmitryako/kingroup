package kingroup_v2.fsr.bound;
import tsvlib.project.ProjectLogger;

import javax.utilx.arrays.vec.Vec;
import static java.lang.Math.min;
import static java.lang.Math.pow;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 15/11/2005, Time: 08:25:23
 */
public class FsrLBoundApproxGSLarge   extends FsrLBound //FsrLBoundApproxGSOne
{
  private static ProjectLogger log = ProjectLogger.getLogger(FsrLBoundApproxGSLarge.class.getName());

  public FsrLBoundApproxGSLarge(int r, int nAlleles, int nLoci)
  {
    super(r, nAlleles, nLoci);
  }

  public FsrLBoundResults calc(int gs)
  {
    FsrLBoundResults res = new FsrLBoundResults();

    double effSize = min(gs, pow(4, L)) * r;
    if (L == 1) {
      double[] qm = calcPhenotypeProb();
      res.setPhenotypeProb(qm);
      effSize = calcEffectiveGS(qm);
    }
    FsrLBoundApproxGSOne gsLarge = new FsrLBoundApproxGSOne((int)effSize, na, L);
    double nErrs = gsLarge.calcNumErrors2();
    res.setNumErrors(nErrs);
    res.setAccuracyError(nErrs/effSize);

    return res;
  }

  public double[] calcPhenotypeProb() {
    double[] res = Vec.makeArray(0., N_PHENOTYPES);
    res[0] = ina * ina;
    res[1] = 2. * ina * (1. - ina);
    res[2] = res[1] * ina;
    res[3] = 1. - res[0] - res[1] - res[2];
    return res;
  }

  public double calcEffectiveGS(double[] qm) {
    double res = 0;
    for (int i = 0; i < qm.length; i++) {
      res += qm[i] * (i+1);
    }
    res *= r;
    log.info("effective number of groups = " + (float)res);
    return res;
  }
}
