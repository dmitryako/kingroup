package algorithm;

import junit.framework.TestCase;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

/**
 * Copyright KinGroup Team.
 * User: eng-nbb, Date: 11/04/2006, Time: 13:12:31
 */

public class Func2DViewTest extends TestCase {

  private JFrame frame;
  private Func2DView view;
  private boolean run = true;

  protected void setUp() throws Exception {
    frame = new JFrame("Func2DView Test");
    frame.setLayout(new BorderLayout());
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    int w = 500;
    int h = 500;
    frame.setBounds((dim.width-w)/2,(dim.height-h)/2,w,h);
    view = new Func2DView(new Func2DI() {
      public double calc(double x, double y) {
        return Math.sin(x)+Math.sin(y);
      }
    }, new Point2D.Double(-1,-1),new Point2D.Double(1,1), true);
    frame.add(view, BorderLayout.CENTER);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        run = false;
      }
    });
    frame.setBackground(new Color(255,255,255));
  }

  public void testFunc2DView() {
    Random r = new Random();
    for(int i = 0; i < 10000; i ++)
    {
      view.calc((r.nextDouble()*2)-1,(r.nextDouble()*2)-1);
    }
//    for(int i = 0; i < 500; i+=10)
//    {
//      view.calc(i,0);
//      view.calc(i,500);
//      view.calc(0,i);
//      view.calc(500,i);
//    }
    while(run)
    {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
