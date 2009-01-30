package tsvlib.visualization;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Copyright KinGroup Team.
 * User: eng-nbb, Date: 10/05/2006, Time: 11:03:44
 */

/**
 * <code>ContourMap</code>
 * <p/>
 * Date: 10/05/2006
 * Time: 11:03:43
 *
 * @author Nigel Blair
 */
public class ContourMap {
  private double numOfContours = 0;
  private double theMax = Double.MIN_VALUE;
  private double theMin = Double.MAX_VALUE;
  public ContourMap(double numOfContours) {
    this.numOfContours = numOfContours;
  }

  public double getFunctionMax() {
    return theMax;
  }

  public double getFunctionMin() {
    return theMin;
  }

  public Contour[] constructContour(final double[][] values, final double xStart,
                                    final double yStart, final double xFinish,
                                    final double yFinish) {
    ArrayList<Contour> shapes = new ArrayList<Contour>();
    Func f = new Func() {
      public Point2D.Double calc(Point p) {
        return calc(new Point2D.Double(p.getX(), p.getY()));
      }

      public Point2D.Double calc(Point2D p) {
         Point2D.Double toRet = new Point2D.Double(
            xStart + ((p.getX() * Math
                .abs(xFinish - xStart)) / values.length),
            yStart + ((double) p.getY() * Math
                .abs(yFinish - yStart)) / values.length);
//        System.out.println("\t"+toRet);
        return toRet;
      }
    };
    double max = Double.MIN_VALUE;
    double min = Double.MAX_VALUE;
    double inc = 0;
    double val = 0;
    for (int i = 0; i < values.length; i++) {
      for (int j = 0; j < values[i].length; j++) {
        double v = values[i][j];
        if (v > max) {
          max = v;
        } else if (v < min) {
          min = v;
        }
      }
    }
    theMax = max; theMin = min;
    inc = (max - min) / (numOfContours + 1);
    int[][] map;
    for (int i = 1; i <= numOfContours; i ++) {
      val = (i * inc) + min;
      map = makemap(values, val);
      Point s;
      while ((s = findPoint(map)) != null) {
        shapes.add(new Contour(val,createShape(getPath(map, s.getX(), s.getY()), f)));
      }
    }
    Contour[] toRet = new Contour[shapes.size()];
    shapes.toArray(toRet);
    return toRet;
  }

  private Shape createShape(final ArrayList<Point2D.Double> path, final Func f) {
    GeneralPath toRet = new GeneralPath();
    toRet.append(new PathIterator() {
      private Iterator<Point2D.Double> i = path.listIterator();
      private Point2D.Double current = null;
      private boolean first = true;
      {
        next();
      }
      public int getWindingRule() {
        return PathIterator.WIND_NON_ZERO;
      }

      public boolean isDone() {
        return !i.hasNext();
      }

      public void next() {
        current = f.calc(i.next());
      }

      public int currentSegment(float[] coords) {
        coords[0] = (float) current.getX();
        coords[1] = (float) current.getY();
        if(first){first = false; return PathIterator.SEG_MOVETO;}
        return PathIterator.SEG_LINETO;
      }

      public int currentSegment(double[] coords) {
        coords[0] = current.getX();
        coords[1] = current.getY();
        if(first){first = false; return PathIterator.SEG_MOVETO;}
        return PathIterator.SEG_LINETO;
      }
    }, false);
    return toRet;
  }


  private ArrayList<Point2D.Double> getPath(int[][] values, double x,
                                            double y) {
    ArrayList<Point2D.Double> points = new ArrayList<Point2D.Double>();
    Point2D.Double cur = new Point2D.Double(x, y);
    Point2D.Double temp;
    do {
      points.add(cur);
      values[((int) cur.getX())][((int) cur.getY())] = 0;
      temp = null;
      for (int c = 1; c < 11; c++) {
        temp = find(values, cur, c);
        if (temp != null) {
          cur = temp;
//          System.out.println(cur);
          break;
        }
      }
    } while (temp != null);
    return points;
  }

  private Point2D.Double find(int[][] values, Point2D.Double cur, int c) {
    for (int i = (int) (cur.getX() + c); i >= cur.getX() - c; i--) {
      if (getValue(values, ((int) cur.getX() + i), ((int) cur.getY() + c))) {
        return new Point2D.Double(cur.getX() + i, cur.getY() + c);
      }
    }
    for (int i = (int) (cur.getY() + c); i >= cur.getY() - c; i--) {
      if (getValue(values, ((int) cur.getX() - c), ((int) cur.getY() + i))) {
        return new Point2D.Double(cur.getX() - c, cur.getY() + i);
      }
    }
    for (int i = (int) (cur.getX() - c); i <= cur.getX() + c; i++) {
      if (getValue(values, ((int) cur.getX() + i), ((int) cur.getY() - c))) {
        return new Point2D.Double(cur.getX() + i, cur.getY() - c);
      }
    }
    for (int i = (int) (cur.getY() - c); i <= cur.getY() + c; i++) {
      if (getValue(values, ((int) cur.getX() + c), ((int) cur.getY() + i))) {
        return new Point2D.Double(cur.getX() + c, cur.getY() + i);
      }
    }
    return null;
  }

  private boolean getValue(int[][] values, int x, int y) {
    if (y < 0) {
      return false;
    }
    if (x < 0) {
      return false;
    }
    if (x >= values.length) {
      return false;
    }
    if (y >= values[((int) x)].length) {
      return false;
    }
    return values[x][y] == 1;
  }

  private Point findPoint(int[][] map) {
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[i].length; j++) {
        if (map[i][j] == 1) {
          return new Point(i, j);
        }
      }
    }
    return null;
  }

  private int[][] makemap(double[][] values, double val) {
    int[][] toRet = new int[values.length][values[0].length];
//    System.out.println("" + val);
    for (int i = 0; i < values.length; i++) {
      double[] ints = values[i];
      for (int j = 1; j < ints.length; j++) {
        if (ints[j] == val) {
          toRet[i][j] = 1;
        } else if ((Math.abs(ints[j] - ints[j - 1]) > Math
            .abs(ints[j] - val)) && (ints[j] > val)) {
          toRet[i][j] = 1;
        } else {
          toRet[i][j] = 0;
        }
      }
    }
    return toRet;
  }


  private interface Func {
    public Point2D.Double calc(Point p);

    public Point2D.Double calc(Point2D p);
  }
}

