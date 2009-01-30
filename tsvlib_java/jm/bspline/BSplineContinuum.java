package jm.bspline;
import stlx.valarrayx.valarray;

import javax.vecmathx.function.FuncVec;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 19/04/2005, Time: 12:40:08
 */
public class BSplineContinuum extends BSplineBasis {
  public static int NUM_MISSING_HEAD = 1;
  public static int NUM_MISSING_TAIL = 0;
  public static int NUM_MISSING = 1;
  public BSplineContinuum(valarray x, valarray t, int k) {
    super(x, t, k);
    loadBSplineArr();
    FuncVec[] saved = getArray();
    setArr(new FuncVec[calcBasisSize(t, k)]); // the first and the last two are ignored
    for (int i = 0; i < size(); i++) {
      set(i, saved[i + NUM_MISSING_HEAD]);
    }
//      JMatrix.trimTailSLOW(this);
  }
  public static int calcBasisSize(valarray t, int k) {
    return calcArraySize(t, k) - NUM_MISSING;  // removing the first and the last two
  }
  // reverese of calcBasisSize
  public static int calcKnotsNumFromBasisSize(int basisSize, int k) {
    return calcKnotsNum(basisSize + NUM_MISSING, k);
  }
}
