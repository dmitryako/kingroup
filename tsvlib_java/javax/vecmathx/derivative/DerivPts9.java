package javax.vecmathx.derivative;
import javax.iox.LOG;
import javax.vecmath.GMatrix;
import javax.vecmathx.VecMathException;
import javax.vecmathx.function.FuncVec;
import javax.vecmathx.grid.StepGrid;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 6, 2004, Time: 2:41:45 PM
 */
public class DerivPts9 extends FuncVec {
  static double coeff[] = { // from p24 of Bickley MathGaz 25 (1941)
    -109584., 322560., -564480, 752640, -705600
    , -5040., -64224., 141120, -141120, 117600
    , 720., -11520., -38304, 80640, -50400
    , -240., 2880., -20160, -18144, 50400
    , 144., -1536., 8064, -32256, 0
    , -144., 1440., -6720, 20160, -50400
    , 240., -2304., 10080, -26880, 50400
    , -720., 6720., -28224, 70560, -117600
    , 5040., -46080., 188160, -451584, 705600
  };
  private static int NCOL = 5;
  private static int NROW = 9;
  private static int SIZE_1 = NROW - 1;
  static GMatrix A = new GMatrix(NROW, NCOL, coeff);
  public DerivPts9(FuncVec f) {
    super(f.x, f.size());
    calc(f, this);
  }
  private void calc(final FuncVec f, final FuncVec d) {
    if (!(f.x instanceof StepGrid)) {
      String mssg = "DerivPts9 can only work with StepGrid";
      LOG.error(this, mssg, "");
      throw new VecMathException(mssg);
    }
    if (f.size() < 9) {
      String mssg = "DerivPts9 needs at least 9 grid points";
      LOG.error(this, mssg, "");
      throw new VecMathException(mssg);
    }
    StepGrid grid = (StepGrid) f.x;
    double h2 = 0.5 / (20160. * grid.getStep());
    calc_h(h2, f, d);
  }
  protected void calc_h(double h2, final FuncVec f, final FuncVec d) {
    int max_size = Math.min(f.size(), d.size());
    int k = 0;
    d.set(k, h2 * deriv(k, 0, f));
    k++;
    d.set(k, h2 * deriv(k, 0, f));
    k++;
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
    k++;
    d.set(i + k, h2 * deriv(k, i, f));
    k++;
    d.set(i + k, h2 * deriv(k, i, f));
  }

//=============================
//   First derivative error -> step^9
//============================
  private double deriv(int idx, int i, final FuncVec f) {
    int k = 0;
    double res = A.getElement(idx, k++) * f.get(i++); // 0
    res += A.getElement(idx, k++) * f.get(i++);  // 1
    res += A.getElement(idx, k++) * f.get(i++);  // 2
    res += A.getElement(idx, k++) * f.get(i++);  // 3
    res += A.getElement(idx, k--) * f.get(i++);  // 4
    int mid = A.getNumRow() / 2;
    idx = mid + mid - idx;
    res -= A.getElement(idx, k--) * f.get(i++);  // 5 -> 3  // NOTE -= not += !!!!!!!!!!!
    res -= A.getElement(idx, k--) * f.get(i++);  // 6 -> 2
    res -= A.getElement(idx, k--) * f.get(i++);  // 7 -> 1
    res -= A.getElement(idx, k--) * f.get(i++);  // 8 -> 0
    return res;
  }
}
