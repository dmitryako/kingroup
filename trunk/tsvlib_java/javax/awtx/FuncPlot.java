package javax.awtx;
import javax.utilx.arrays.vec.Vec;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: May 10, 2004, Time: 2:41:14 PM
 */
public class FuncPlot {
  private double[] y;
  private double[] x;
  private FuncPlot chain;
  public FuncPlot(double[] y, double minX, double maxX) {
    if (y.length == 0)
      return;
    this.y = y;
    x = GraphFactory.makeGrid(minX, maxX, y.length);
  }
  public FuncPlot(double[] x, double[] y) {
    if (y == null || y.length == 0)
      return;
    if (x == null || x.length == 0)
      return;
    if (x.length != y.length)
      return;
    this.y = y;
    this.x = x;
  }
  public FuncPlot(double[] y) {
    if (y.length == 0)
      return;
    this.y = y;
    x = GraphFactory.makeGrid(0f, 1.0f, y.length);
  }
  public double getMinX() {
    int idx = Vec.minIdx(x, x.length);
    if (idx == -1)
      return 0f;
    double x = this.x[idx];
    if (chain == null)
      return x;
    else
      return Math.min(x, chain.getMinX());
  }
  public double getMaxX() {
    int idx = Vec.maxIdx(x, x.length);
    if (idx == -1)
      return 0f;
    double x = this.x[idx];
    if (chain == null)
      return x;
    else
      return Math.max(x, chain.getMaxX());
  }
  public double getMinY() {
    int idx = Vec.minIdx(y, y.length);
    if (idx == -1)
      return 0f;
    double y = this.y[idx];
    if (chain == null)
      return y;
    else
      return Math.min(y, chain.getMinY());
  }
  public double getMaxY() {
    int idx = Vec.maxIdx(y, y.length);
    if (idx == -1)
      return 0f;
    double y = this.y[idx];
    if (chain == null)
      return y;
    else
      return Math.max(y, chain.getMaxY());
  }
  public void addGraph(FuncPlot g) {
    if (chain == null)
      chain = g;
    else
      chain.addGraph(g);
  }
  public void plot(Graphics2D g2, AffineTransform tr) {
    for (int i = 1; i < x.length && i < y.length
      ; i++) {
      Point2D p = new Point2D.Float((float)x[i - 1], (float)y[i - 1]);
      p = tr.transform(p, null);
      Point2D p2 = new Point2D.Float((float)x[i], (float)y[i]);
      p2 = tr.transform(p2, null);
      Line2D.Float L = new Line2D.Float(p, p2);
      g2.draw(L);
    }
    if (chain != null)
      chain.plot(g2, tr);
  }
}
