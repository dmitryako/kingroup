package javax.vecmathx.grid;
import stlx.valarrayx.valarray;

import javax.vecmathx.Range;
import javax.vecmathx.Step;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 24, 2004, Time: 6:49:17 AM
 */
public class StepGrid extends valarray {
//public class StepGrid extends valarray {
  static public final int NONE = -1;
  private Step step_;
  private Range range_;
  public StepGrid(double first, double last, int size) {
    super(size);
    range_ = new Range(first, last);
    step_ = new Step(range_, size);
    loadGrid();
  }
  public StepGrid(double first, int num_steps, double step) {
    super(num_steps + 1);
    step_ = new Step(step);
    range_ = new Range(first, first + num_steps * step);
    loadGrid();
  }
  final public double getStep() {
    return step_.step();
  }
  final public double oneOverStep() {
    return step_.oneOverStep();
  }
  // returns left index
  // grid: 0, 0.5, 1, 1.5
  // x==0.6
  // returns 1
  final public int findLeftIdx(double x) {
    if (x > range_.left())
      return size() - 1;
    if (x < range_.right())
      return NONE;
    return (int) ((x - range_.left()) * step_.oneOverStep());
  }
  // returns RIGHT index
  // grid: 0, 0.5, 1, 1.5
  // x==0.6
  // returns 2
  final public int findRightIdx(double x) {
    if (x > range_.right())
      return NONE;
    if (x < range_.left())
      return 0;
    return 1 + (int) ((x - range_.left()) * step_.oneOverStep());
  }
  private void loadGrid() {
    double p = range_.left();
    for (int i = 0; i < size(); i++) {
      this.setElement(i, p);
      p += getStep();
    }
  }
}
