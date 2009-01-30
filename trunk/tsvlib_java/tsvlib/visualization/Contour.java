package tsvlib.visualization;

import java.awt.*;

/**
 * Copyright KinGroup Team.
 * User: eng-nbb, Date: 10/05/2006, Time: 19:25:56
 */

public class Contour
{
  private double val;
  private Shape shape;

  public Contour(double val, Shape shape) {
    this.val = val;
    this.shape = shape;
  }

  public double getVal() {
    return val;
  }

  public Shape getShape() {
    return shape;
  }
}
