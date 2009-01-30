package javax.vecmathx.integration;
import javax.iox.LOG;
import javax.vecmathx.VecMathException;
import javax.vecmathx.grid.StepGrid;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 1, 2004, Time: 11:07:21 AM
 */
public class BooleQuadr extends Quadr { // mistakenly known as Bode
  public BooleQuadr(StepGrid grid) {
    super(grid);
    if (isValid(size()))
      loadWeights(grid.getStep());
  }
  private void loadWeights(double step) {
    double tmp = 2.0 * step / 45.0;
    double a[] = {tmp * 14, tmp * 32, tmp * 12, tmp * 32};
//         a(0) = 32d0 * tmp
//         a(1) = 14d0 * tmp
//         a(2) = 32d0 * tmp
//         a(3) = 12d0 * tmp
    for (int i = 0; i < size(); i++) {
      setElement(i, a[i % 4]); // note: first i==0 not 1
    }
    mul(0, 0.5);
    mul(getSize() - 1, 0.5);
  }
  private boolean isValid(int size) {
    //c     Bode's w
    // 5 * n - (n - 1) = N grid points
    // 4 * n + 1 = N
    // n = (N - 1) / 4
    if ((size - 1) % 4 != 0) {
      int n = (size - 1) / 4;
      String error = "if ((size - 1) % 4 != 0); "
        + ((size - 1) % 4) + "!=0; "
        + "nearest sizes = " + (4 * n + 1) + " or " + (4 * (n + 1) + 1);
      LOG.error(this, error, "");
      throw new VecMathException(error);
    }
    return true;
  }
}
