package javax.iox;
import pattern.ucm.UCController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 25/01/2006, Time: 16:28:23
 */
public class UCViewOutputStream  implements UCController {
//  private static ProjectLogger log = ProjectLogger.getLogger(UCViewOutputStream.class.getName());
  private OutputStream buff;
//  private static final int N_COLUMNS = 40;
  private JFrame f;
  private JTextArea ta;
  private JButton clear;

  public UCViewOutputStream(OutputStream buff) {
    this.buff = buff;
    init();
  }

  private void init() {
    f = new JFrame("Error log");
    ta = new JTextArea();
    clear = new JButton("Clear");

    f.setLayout(new BorderLayout());
    ScrollPane scroll = new ScrollPane();
    scroll.add(ta);
//    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.add(scroll, BorderLayout.CENTER);
    f.add(clear, BorderLayout.SOUTH);

    clear.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ta.setText("");
      }
    });
    f.setBounds(500,100,500,500);
  }

  public boolean run() {
    ta.append(buff.toString());
    f.setVisible(true);
    f.repaint();
    return true;
  }
}
