package kingroup.model;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 29, 2004, Time: 4:03:24 PM
 */
public class RunSimModel {
  private int numRuns;
  public void loadDefaults() {
    numRuns = 10;
  }
  final public int getNumRuns() {
    return numRuns;
  }
  final public void setNumRuns(int numRuns) {
    this.numRuns = numRuns;
  }
}
