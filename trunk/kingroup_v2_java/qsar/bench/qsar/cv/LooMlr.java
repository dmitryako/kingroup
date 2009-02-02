package qsar.bench.qsar.cv;
import qsar.bench.qsar.MLR.MlrAlg;
import qsar.kriging.LooAlgI;

import javax.utilx.arrays.mtrx.Mtrx;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 8/02/2007, Time: 17:04:35
 */
public class LooMlr  implements LooAlgI
{
  public double calc(int idx, double[][] z){
    double[][] sz = Mtrx.excludeRows(idx, idx+1, z);
    MlrAlg reg = new MlrAlg();
    reg.calc(sz);
    double ey = reg.calcYFromXZ(z[idx]); // estimate
    return ey;
  }
}
