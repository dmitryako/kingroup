package javax.iox;
import javax.swing.*;
import java.awt.*;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 14/09/2005, Time: 12:05:05
 */
public class TextFileView extends JPanel
//  implements Observer
{
  public TextFileView(TextFile model) {
    init();
    JList view = makeView(model);
    assemble(view);
//    model.addObserver(this);
  }
//  public void update(Observable o, Object arg) {
//    TextFile model = (TextFile)o;
//    repaint();
//  }
  private JList makeView(TextFile model) {
    Object[] arr = model.toArray();
    return new JList(replaceTabs(arr));
  }
  private void init() {
    setLayout(new BorderLayout());
  }
  public void assemble(JList view) {
    removeAll();
    JScrollPane scroll;
    if (view == null) {
      scroll = new JScrollPane(new JList());
    } else {
      scroll = new JScrollPane(view);
    }
    add(scroll, BorderLayout.CENTER);
  }
  private Object[] replaceTabs(Object[] from) {
    Object[] res = new String[from.length];
    for (int i = 0; i < res.length; i++) {
      String line = (String) from[i];
      res[i] = line.replace('\t', ' ');
    }
    return res;
  }
}