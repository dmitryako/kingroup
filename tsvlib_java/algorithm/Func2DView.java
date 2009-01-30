package algorithm;

import tsvlib.visualization.Contour;
import tsvlib.visualization.ContourMap;

import javax.swing.*;
import javax.swingx.text_fieldx.IntTextField;
import javax.utilx.triplet.DoubleTriplet;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Vector;

/**
 * DECORATOR
 * Copyright KinGroup Team.
 * User: eng-nbb, Date: 11/04/2006, Time: 12:29:21
 */
public class Func2DView extends JPanel implements Func2DI {
  private Func2DI func;
  private ArrayList<DoubleTriplet> points = new ArrayList<DoubleTriplet>();
  private double max = Double.MIN_VALUE;
  private double min = Double.MAX_VALUE;
  private JToolBar toolBar = new JToolBar();
  private AffineTransform transform = new AffineTransform();
  private JButton reset;
  private JToggleButton zoomTo;
  private JLabel scale = new JLabel();
  private JLabel xLabel = new JLabel();
  private JLabel yLabel = new JLabel();
  private JLabel zLabel = new JLabel();
  private JToggleButton move = new JToggleButton("Move");
  private Vector<JToggleButton> togglers = new Vector<JToggleButton>();
  private JButton next = new JButton("Next");
  private JRadioButton nextRadioButton = new JRadioButton();
  private JRadioButton intervalRadioButton = new JRadioButton();
  private ButtonGroup intermissionBG = new ButtonGroup();
  private JPanel statusBar = new JPanel();
  private DecimalFormat decFormat = new DecimalFormat("#.##");
  private Contour[] contours;
  private IntTextField intervalField = new IntTextField(0,
      Integer.MAX_VALUE);
  private Object lock = new Object();
  private Runnable nextRunnable = new Runnable() {
    public void run() {
      synchronized (lock) {
        try {
          lock.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  };
  private Runnable intervalRunnable = new Runnable() {
    public void run() {
      try {
        Thread.sleep(intervalField.getInput());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  };
  private Runnable intermission = nextRunnable;
  private JPanel view = new JPanel() {
    protected void paintComponent(Graphics g) {
      DoubleTriplet last = null;
      double range = max - min;
      double incr = 255d / Math.abs(range);
      double[] source = new double[4];
      double[] dest = new double[source.length];

      if(contours != null)
      {
        GeneralPath p;
        for (int i = 0; i < contours.length; i++) {
          p = new GeneralPath();
          p.append(contours[i].getShape().getPathIterator(transform),false);
//          System.out.println((incr * Math.abs(contours[i].getVal())));
          g.setColor(new Color((int) (incr * Math.abs(contours[i].getVal())),128,128));
          ((Graphics2D)g).draw(p);
//          System.out.println(p);
        }
      }
      for (int i = 0; i < points.size(); i++) {
        DoubleTriplet p = points.get(i);

        try {
          g.setColor(new Color((int) (incr * (p.c + Math.abs(min))), 128, 128));
        }
        catch (IllegalArgumentException e) {
          System.out.println(
              "Illagle Colour: " + (int) (incr * (p.c + Math.abs(min))));
        }
//      g.drawLine((int)p.a, (int)p.b,(int)p.a,(int)p.b);
        if (last != null && p != null) {
          source[0] = last.a;
          source[1] = last.b;
          source[2] = p.a;
          source[3] = p.b;
          transform.transform(source, 0, dest, 0, source.length / 2);
          g.drawLine((int) dest[0], (int) dest[1], (int) dest[2],
              (int) dest[3]);
        }
        last = p;
      }

    }
  };
  private JPanel glasspane = new JPanel() {
    private Point2D startPoint;
    private Point2D f;
    private Point2D lastf = null;

    {
      addMouseMotionListener(new MouseMotionAdapter() {

        public void mouseDragged(MouseEvent e) {
          if (zoomTo.isSelected()) {
            f = new Point2D.Double(e.getPoint().getX(), e.getPoint().getY());
            glasspane.repaint(0);
          } else if (move.isSelected()) {

            f = e.getPoint();
            if (lastf == null) {
              lastf = startPoint;
            }
            doMove(lastf, f);
            lastf = f;
            doRefresh();
          }
        }
      });
      addMouseListener(new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
          startPoint = new Point2D.Double(e.getPoint().getX(),
              e.getPoint().getY());
          lastf = null;
        }

        public void mouseReleased(MouseEvent e) {
          try {
            if (startPoint != null && f != null) {
              if (zoomTo.isSelected()) {
                doZoom(startPoint, f);
//                zoomTo.setSelected(false);
                doRefresh();
              }
              if (move.isSelected()) {
                doRefresh();
//                move.setSelected(false);
//                view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
              }
            }
            startPoint = null;
            f = null;
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        }

      });
    }

    protected void paintComponent(Graphics g) {
//      g.drawOval(0,0,300,300);
      super.paintComponent(g);
//      g.drawOval(0,0,300,300);
      if (startPoint != null && f != null && zoomTo.isSelected()) {
        g.drawRect(
            (int) (startPoint.getX() > f.getX() ? f.getX() : startPoint.getX()),
            (int) (startPoint.getY() > f.getY() ? f.getY() : startPoint.getY()),
            (int) Math.abs(f.getX() - startPoint.getX()),
            (int) Math.abs(f.getY() - startPoint.getY()));
      }
    }
  };

  private Point2D.Double hintStart = null;
  private Point2D.Double hintEnd = null;

  public Func2DView(Func2DI func) {
    this.func = func;
    setLayout(new BorderLayout());
    setupToolbar();
    setupStatusbar();
    add(toolBar, BorderLayout.NORTH);
    add(statusBar, BorderLayout.SOUTH);
    view.setOpaque(false);
    glasspane.setLayout(new BorderLayout());
    glasspane.add(view, BorderLayout.CENTER);
//    glasspane.setOpaque(false);
    add(glasspane, BorderLayout.CENTER);
  }

  public Func2DView(Func2DI func, Point2D.Double hintStart,
                    Point2D.Double hintEnd) {
    this(func, hintStart, hintEnd, false);
  }

  public Func2DView(Func2DI func, final Point2D.Double hintStart,
                    final Point2D.Double hintEnd, boolean contourMap) {
    this(func);
    this.hintStart = hintStart;
    this.hintEnd = hintEnd;
    glasspane.addComponentListener(new ComponentAdapter() {
      public void componentResized(ComponentEvent e) {
        try {
          doZoom(hintStart, hintEnd);
        } catch (NoninvertibleTransformException ee) {
          ee.printStackTrace();
        }
        glasspane.removeComponentListener(this);
      }
    });
    if (contourMap) {
      makeContourMap();
    }
  }

  private void makeContourMap() {
    int size = 100;

    double[][] data = new double[size][size];
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        data[i][j] = func.calc(hintStart.getX() + (((double) i * (Math.abs(
            hintEnd.getX() - hintStart.getX())) / size)),
            hintStart.getY() + (((double) j * (Math.abs(
                hintEnd.getY() - hintStart.getY())) / size)));
      }
    }
    ContourMap c;
    contours = (c=new ContourMap(10)).constructContour(data, hintStart.getX(),
        hintStart.getY(), hintEnd.getX(), hintEnd.getY());
    max = c.getFunctionMax();
    min = c.getFunctionMin();
  }


  private void setupStatusbar() {
    statusBar.add(new JLabel("Scale: "));
    scale.setText(decFormat.format(1));
    statusBar.add(scale);
    statusBar.add(new JLabel(" X: "));
    statusBar.add(xLabel);
    statusBar.add(new JLabel(" Y: "));
    statusBar.add(yLabel);
    statusBar.add(new JLabel(" Z: "));
    statusBar.add(zLabel);
  }

  private void doMove(Point2D s, Point2D f) {
    translateTransform((f.getX() - s.getX()) / transform.getScaleX(),
        (f.getY() - s.getY()) / transform.getScaleY());
  }

  private Rectangle2D makeRec(Point2D s, Point2D f) throws
      NoninvertibleTransformException {
    Point2D.Double so = new Point2D.Double();
    Point2D.Double fo = new Point2D.Double();
    AffineTransform inverse = transform.createInverse();
    resetTransform();
    inverse.transform(s, so);
    inverse.transform(f, fo);
//    System.out.println(s);
//    System.out.println(so);
    double x = so.getX() > fo.getX() ? fo.getX() : so.getX();
    double y = so.getY() > fo.getY() ? fo.getY() : so.getY();
    double width = Math.abs(fo.getX() - so.getX());
    double height = Math.abs(fo.getY() - so.getY());
    return new Rectangle2D.Double(x, y, width, height);
  }

  private void doZoom(Point2D s, Point2D f) throws
      NoninvertibleTransformException {

    Rectangle2D rec = makeRec(s, f);
//    System.out.println(rec);
    double xRatio = ((double) glasspane.getWidth()) / rec.getWidth();
    double yRatio = ((double) glasspane.getHeight()) / rec.getHeight();

    if (xRatio > yRatio) {
//      System.out.println((double)glasspane.getHeight()+"   -   "+height);
      scaleTransform(yRatio, yRatio);
    } else {
//      System.out.println((double)glasspane.getWidth()+"   -   "+width);
      scaleTransform(xRatio, xRatio);
    }
    translateTransform(-rec.getX(), -rec.getY());
  }

  private void setupToolbar() {
    toolBar.setFloatable(false);
    toolBar.add(zoomTo = new JToggleButton("ZoomTo"));
    toolBar.add(reset = new JButton("Reset"));
    toolBar.add(move);
    togglers.add(move);
    togglers.add(zoomTo);
    reset.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        resetTransform();
      }
    });

    move.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (move.isSelected()) {
          view.setCursor(new Cursor(Cursor.MOVE_CURSOR));
          unsetOtherTogglers(move);
        } else {
          view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
      }
    });
    zoomTo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (zoomTo.isSelected()) {
          view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
          unsetOtherTogglers(zoomTo);
        }
      }
    });
    intermissionBG.add(nextRadioButton);
    intermissionBG.add(intervalRadioButton);
    next.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        synchronized (lock) {
          lock.notifyAll();
        }
      }
    });
    nextRadioButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        intermission = nextRunnable;
//        next.setEnabled(true);
//        intervalField.setEnabled(false);
      }
    });
    intervalRadioButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        intermission = intervalRunnable;
//        next.setEnabled(false);
//        intervalField.setEnabled(true);
        synchronized (lock) {
          lock.notifyAll();
        }
      }
    });
    toolBar.add(nextRadioButton);
    toolBar.add(next);
    toolBar.add(intervalRadioButton);
    toolBar.add(intervalField);
    nextRadioButton.setSelected(true);
//    next.setEnabled(true);
//    intervalField.setEnabled(false);
    intervalField.setValue(200);
  }

  private void translateTransform(double v, double v1) {
    transform.translate(v, v1);
  }

  private void scaleTransform(double xRatio, double yRatio) {
    transform.scale(xRatio, yRatio);
    scale.setText(decFormat.format(xRatio));
  }

  private void resetTransform() {
    transform = new AffineTransform();
    doRefresh();
    scale.setText(decFormat.format(1));
  }

  private void unsetOtherTogglers(JToggleButton but) {
    for (JToggleButton tglr : togglers) {
      if (!tglr.equals(but)) {
        tglr.setSelected(false);
      }
    }
  }

  private void doRefresh() {
    glasspane.repaint(0);
    view.repaint(0);
  }

  public double calc(double x, double y) {
    double toRet = Double.NaN;
    points.add(new DoubleTriplet(x, y, toRet = func.calc(x, y)));
    if (toRet > max) {
      max = toRet;
    }
    if (toRet < min) {
      min = toRet;
    }
    invalidate();
    repaint(0);
    xLabel.setText(decFormat.format(x));
    yLabel.setText(decFormat.format(y));
    zLabel.setText(decFormat.format(toRet));
    intermission.run();
    return toRet;
  }


}
