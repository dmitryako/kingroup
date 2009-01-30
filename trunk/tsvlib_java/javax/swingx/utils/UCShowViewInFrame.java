package javax.swingx.utils;
import pattern.ucm.UCController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 14/05/2007, 12:48:40
 */
public class UCShowViewInFrame implements UCController
{
  private JFrame frame;
//  private boolean run = true;

  public UCShowViewInFrame(JPanel view, String title) {
    frame = new JFrame(title);
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    int w = 500;
    int h = 500;
    frame.setBounds((dim.width-w)/2,(dim.height-h)/2,w,h);

    frame.add(view, BorderLayout.CENTER);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
//        run = false;
        System.exit( 1 );
      }
    });
    frame.setBackground(new Color(255,255,255));
  }

  /*
   *	Registers a window listener to handle ALT+F4 window closing.
   *	@param frame the JFrame for which  we want to intercept close
   *	messages.
   */
  static protected void registerWindowListener( JFrame frame )
  {
    // disable automatic close support for Swing frame.
    frame.setDefaultCloseOperation( WindowConstants.DO_NOTHING_ON_CLOSE );

    // adds the window listener
    frame.addWindowListener( new WindowAdapter( )
      {
      // handles the system exit window message
      public void windowClosing( WindowEvent e )
      {
        System.exit( 1 );
      }
    }
    );
  }

  public boolean run()
  {
    frame.setVisible(true);
    return true;
  }
}
