package stlx.valarrayx;
import numeric.functor.Func;
import stlx.FastLoop;

import javax.iox.LOG;
import javax.vecmath.GVector;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 24, 2004, Time: 5:12:38 PM
 */
public class valarray extends GVector {

  public static double[] asArray(valarray arr) {
    double[] res = new double[arr.size()];
    for (int i = 0; i < res.length; i++) {
      res[i] = arr.get(i);
    }
    return res;
  }

  public valarray(double[] x) {
    super(x);
  }
  public valarray(valarray x) {
    super(x);
  }
  public valarray(int size) {
    super(size);
  }
  final public double getLast() {
    return get(size() - 1);
  }
  final public double getFirst() {
    return get(0);
  }
  final public int size() {
    return getSize();
  }
  final public void set(final int i, final double val) {
    setElement(i, val);
  }
  final public double get(final int i) {
    return getElement(i);
  }
  final public double duff_dot(valarray f) {
    LOG.error(this, "GVector.dot() is faster", " re-run valarray_junit");
    return FastLoop.dot(this, f);
  }
  final public double duff_dot(GVector f) {
    LOG.error(this, "GVector.dot() is faster", " re-run valarray_junit");
    return FastLoop.dot(this, f);
  }
  final public void calc(Func f) {
    FastLoop.calc(this, f);
  }
  final public void mul(final int i, final double byVal) {
    final double tmp = get(i);
    setElement(i, tmp * byVal);
  }
  public void safeDivide(valarray r) {
    int validSize = Math.min(r.size(), size());
    for (int i = 0; i < validSize; i++) {
      double top = get(i);
      double bottom = r.get(i);
      if (top != 0 && bottom != 0)
        set(i, top / bottom);
    }
  }
  final public void mult(valarray f) {
    FastLoop.mult(this, f);
  }
  public void addSafe(valarray from) {
    if (size() >= from.size()) {
      addShorter(from);
      return;
    }
    valarray tmp = new valarray(this);
    setSize(from.size());
    zero();
    addShorter(tmp);
    addShorter(from);
  }
  private void addShorter(valarray from) {
    for (int j = 0; j < from.size(); j++) {
      this.setElement(j, get(j) + from.get(j));
    }
  }
}
