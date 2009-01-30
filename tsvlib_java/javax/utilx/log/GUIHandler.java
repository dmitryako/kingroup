package javax.utilx.log;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * <code>GUIHandler</code>
 * <p/>
 * Date: 3/02/2006
 * Time: 10:24:17
 *
 * @author Nigel Blair
 */
public class GUIHandler extends Handler {
  private JFrame f = new JFrame("Logging handler.");
  private JList list;
  private JButton clear;
//  private JComp
  private Vector<LogRecord> logRecords = new Vector<LogRecord>();

  public GUIHandler() {
    f.setLayout(new BorderLayout());
    list = new JList();
    clear = new JButton("Clear");
    ScrollPane scroll = new ScrollPane();
    scroll.add(list);
//    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.add(scroll, BorderLayout.CENTER);
    f.add(clear, BorderLayout.SOUTH);
    f.setBounds(0,100,500,500);
    f.setVisible(true);
    list.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {

        }
      }
    });
    clear.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        synchronized (logRecords) {
          logRecords.clear();
        }
      }
    });
  }

  public synchronized void publish(LogRecord record) {
    int ind = list.getSelectedIndex();
    synchronized (logRecords) {
      logRecords.add(record);
    }
    list.setListData(logRecords);
    list.setCellRenderer(new LoggingCellRender());
    list.setSelectedIndex(ind);
  }

  public void flush() {

  }

  public void close() throws SecurityException {

  }
}
