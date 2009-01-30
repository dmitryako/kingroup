package algorithm;
import javax.swing.*;
import java.awt.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 3/05/2006, Time: 12:09:53
 */
public class Func2DFrame extends Func2DView
{
  public Func2DFrame(Func2DI func)
  {
    super(func);
    JFrame frame = new JFrame("Func2DView Test");
    frame.setLayout(new BorderLayout());
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    int w = 500;
    int h = 500;
    frame.setBounds((dim.width-w)/2,(dim.height-h)/2,w,h);
    frame.add(this, BorderLayout.CENTER);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    frame.setBackground(new Color(255,255,255));
  }
}
