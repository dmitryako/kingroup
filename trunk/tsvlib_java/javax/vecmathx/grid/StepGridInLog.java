package javax.vecmathx.grid;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 24, 2004, Time: 6:29:50 AM
 */
public class StepGridInLog extends StepGrid {
  public StepGridInLog(double firstR, double lastR, int size) {
    super(Math.log(firstR), Math.log(lastR), size);
  }
}
