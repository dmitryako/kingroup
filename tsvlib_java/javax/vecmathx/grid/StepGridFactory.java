package javax.vecmathx.grid;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 1, 2004, Time: 9:00:30 AM
 */
public class StepGridFactory {
  public static final StepGrid makeStepGridFromLog(double first, double last, int size) {
    return new StepGrid(Math.log(first), Math.log(last), size);
  }
  public static final StepGrid makeStepGridFromExp(double first, double last, int size) {
    return new StepGrid(Math.exp(first), Math.exp(last), size);
  }
}
