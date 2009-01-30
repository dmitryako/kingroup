package javax.swingx;
import pattern.ucm.ClearableViewI;
import pattern.ucm.UCClearView;
import pattern.ucm.AdapterUCCToALThread;
import tsvlib.project.ProjectLogger;

import javax.iox.LineStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 17/04/2007, 09:35:48
 */
public class LineStreamView extends OutputStream
  implements ClearableViewI
{
  private static ProjectLogger log = ProjectLogger.getLogger(LineStreamView.class.getName());
  private JPanel view;
  private JTextField line;
  private JButton open;
  private JButton clear;
  private LineStream out;

  public LineStreamView(LineStream out) {
    init();
    assemble();
    this.out = out;
  }
  private void init() {
    view = new JPanel(new BorderLayout());

    line = new JTextField("logging text");
    line.setEditable(false);
    open = new JButton("open");
    open.setToolTipText("full view of log messages");
    open.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e)
      {
        log.info("open ");
//        line.setText(outputStream.toString());
//        repaint();
      }
    });

    clear = new JButton("clear");
    clear.setToolTipText("clear log messages");
    clear.addActionListener(new AdapterUCCToALThread(new UCClearView(this)));
  }
  private void assemble() {
    view.add(line, BorderLayout.CENTER);
    view.add(open, BorderLayout.EAST);
    view.add(clear, BorderLayout.WEST);
  }
  public JPanel getView() {return view;}

  public void clear()
  {
    try {
      out.write('\n');
    } catch (IOException e) {
      log.severe(e.toString());
    }
    line.setText("");
  }

  public void write(int b) throws IOException {
    out.write(b);
    line.setText(out.toString());
    line.repaint();
  }

}
