package javax.awtx;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: May 10, 2004, Time: 2:39:47 PM
 */
public class FuncPlotView extends JPanel {
  private boolean showFrame = true;
  private boolean showX = false;
  private boolean showY = false;
  private int insertSize = 2;
  private int minSizeLen = 20;
  private FuncPlot graph;
  private FuncPlot axisX;
  private FuncPlot axisY;
  public void setShowFrame(boolean b) {
    showFrame = b;
  }
  public void setShowX(boolean b) {
    showX = b;
  }
  public void setShowY(boolean b) {
    showY = b;
  }
  public void setMinSizeLen(int i) {
    minSizeLen = i;
    init();
  }
  public Dimension getMinimumSize() {
    return new Dimension(minSizeLen + 2 * insertSize
      , minSizeLen + 2 * insertSize);
  }
  public FuncPlotView(FuncPlot graph) {
    super();
    init();
    this.graph = graph;
  }
  private void init() {
    //setBorder(BorderFactory.createLoweredBevelBorder());
    setMinimumSize(getMinimumSize());
    setPreferredSize(getMinimumSize());
  }
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (graph == null)
      return;
    Graphics2D g2 = (Graphics2D) g;
    Rectangle frame = drawFrame(g2);
    AffineTransform tr = scale(graph, frame);
    if (axisX != null)
      axisX.plot(g2, tr);
    if (axisY != null)
      axisY.plot(g2, tr);
    graph.plot(g2, tr);
  }
  private AffineTransform scale(FuncPlot graph, Rectangle rec) {
    AffineTransform tr = new AffineTransform();
    double minX = graph.getMinX();
    double maxX = graph.getMaxX();
    if (maxX == minX)
      return tr;
    double sx = (float) rec.width / (maxX - minX);
    double minY = graph.getMinY();
    double maxY = graph.getMaxY();
    if (maxY == minY)
      return null;
    double sy = (float) rec.height / (maxY - minY);
    tr.scale(sx, -sy);
    Point2D modelTL = new Point2D.Float((float)minX, (float)maxY);// TOP-LEFT corner
    Point2D recViewTL = new Point2D.Float(rec.x, rec.y);
    Point2D recModelTL;
    try {
      recModelTL = tr.inverseTransform(recViewTL, null);
    } catch (NoninvertibleTransformException e) {
      return tr;
    }

    // recModelTL must be the same as modelTL (TOP-LEFT corner)
    tr.translate(recModelTL.getX() - modelTL.getX()
      , recModelTL.getY() - modelTL.getY());
    axisX = null;
    if (showX)
      axisX = new FuncPlot(GraphFactory.makeGrid(minY, maxY, 2), 0, 0);
    axisY = null;
    if (showY)
      axisY = new FuncPlot(GraphFactory.makeGrid(0, 0, 2), minX, maxX);
    return tr;
  }
  private Rectangle drawFrame(Graphics2D g2) {
    Rectangle rec = getBounds();
    //LOG.trace(this, "getBounds=" + rec);
    rec.grow(-insertSize, -insertSize);
    //LOG.trace(this, "rec - INSERT =" + rec);
    Rectangle frame = new Rectangle(insertSize, insertSize
      , rec.width - 1, rec.height - 1); // NOTE! without -1 right hand border is missing
    //LOG.trace(this, "frame=" + frame);
    if (showFrame)
      g2.draw(frame);
    Rectangle test = new Rectangle(frame);
    test.grow(2, 2);
    //LOG.trace(this, "test=" + test);
    if (showFrame)
      g2.draw(test);
    return frame;
  }

  public FuncPlot getGraph()
  {
    return graph;
  }

}
