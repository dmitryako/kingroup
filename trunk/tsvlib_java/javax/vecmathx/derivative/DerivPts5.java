package javax.vecmathx.derivative;
import javax.iox.LOG;
import javax.vecmath.GMatrix;
import javax.vecmathx.VecMathException;
import javax.vecmathx.function.FuncVec;
import javax.vecmathx.grid.StepGrid;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 6, 2004, Time: 4:01:27 PM
 */
public class DerivPts5 extends FuncVec {
  static double coeff[] = { // from p708
    -50, 96, -72, 32, -6,
    -6, -20, 36, -12, 2,
    2, -16, 0, 16, -2,
    -2, 12, -36, 20, 6,
    6, -32, 72, -96, 50
  };
  private static int NCOL = 5;
  private static int NROW = 5;
  private static int SIZE_1 = NROW - 1;
  static GMatrix A = new GMatrix(NROW, NCOL, coeff);
  public DerivPts5(FuncVec f) {
    super(f.x, f.size());
    calc(f, this);
  }
  private void calc(final FuncVec f, final FuncVec d) {
    if (!(f.x instanceof StepGrid)) {
      String mssg = "DerivPts5 can only work with StepGrid";
      LOG.error(this, mssg, "");
      throw new VecMathException(mssg);
    }
    if (f.size() < 5) {
      String mssg = "DerivPts5 needs at least 5 grid points";
      LOG.error(this, mssg, "");
      throw new VecMathException(mssg);
    }
    StepGrid grid = (StepGrid) f.x;
    double h2 = 1. / (24. * grid.getStep());
    calc_h(h2, f, d);
  }
  protected void calc_h(double h2, final FuncVec f, final FuncVec d) {
    int max_size = Math.min(f.size(), d.size());
    int k = 0;
    d.set(k, h2 * deriv(k, 0, f));
    k++;
    d.set(k, h2 * deriv(k, 0, f));
    k++;
    for (int i = 0; i < max_size - 2 * k; i++) {
      d.set(i + k, h2 * deriv(k, i, f));
    }
    int i = max_size - 1 - SIZE_1;
    k++;
    d.set(i + k, h2 * deriv(k, i, f));
    k++;
    d.set(i + k, h2 * deriv(k, i, f));
  }
  private double deriv(int idx, int i, final FuncVec f) {
    int k = 0;
    double res = A.getElement(idx, k++) * f.get(i++); // 0
    res += A.getElement(idx, k++) * f.get(i++);  // 1
    res += A.getElement(idx, k++) * f.get(i++);  // 2
    res += A.getElement(idx, k++) * f.get(i++);  // 3
    res += A.getElement(idx, k++) * f.get(i++);  // 4
    return res;
  }
}

