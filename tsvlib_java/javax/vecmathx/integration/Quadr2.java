package javax.vecmathx.integration;
import stlx.FastLoop;
import stlx.valarrayx.valarray;

import javax.mathx.MathX;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 4, 2004, Time: 6:26:33 PM
 */
public class Quadr2 {
  final public valarray w;
  static private int START_IDX = 0;
  public Quadr2(valarray w) {
    this.w = w;
  }
  final public double calc(valarray f, valarray f2) {
    int endIdx = MathX.min(w.size() - 1, f.size() - 1, f2.size() - 1);
    return FastLoop.dot(START_IDX, endIdx, w, f, f2);
  }
  final public double calc(valarray f, valarray f2, valarray f3) {
    int endIdx = MathX.min(w.size() - 1, f.size() - 1, f2.size() - 1, f3.size() - 1);
    return FastLoop.dot(START_IDX, endIdx, w, f, f2, f3);
  }
}
