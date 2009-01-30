package javax.vecmathx.function;
import numeric.functor.Func;
import stlx.valarrayx.valarray;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 10, 2004, Time: 2:08:07 PM
 */
public class ApplyToArr {
  public static void mult(final FuncArr arr, final Func func) {
    valarray grid = arr.getX();
    for (int i = 0; i < grid.size(); i++) {
      double fx = func.calc(grid.get(i));
      for (int n = 0; n < arr.size(); n++) {
        FuncVec f = arr.get(n);
        f.set(i, f.get(i) * fx); // set (n)'th function
      }
    }
  }
}
