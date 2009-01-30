package javax.swingx.panelx;
import tsvlib.project.ProjectLogger;

import javax.swing.*;
import java.awt.*;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 17/05/2007, 16:17:46
 */
public class ViewWithOpt   extends JPanel
{
  private static ProjectLogger log = ProjectLogger.getLogger(ViewWithOpt.class);
  private JFrame frame;
  private JSplitPane split;
  private JPanel optView;
  private JPanel view;

  public ViewWithOpt() {
    init();
  }
  private void init() {
    setLayout(new BorderLayout());
    split = new JSplitPane();
    split.setOneTouchExpandable(true);
    add(split, BorderLayout.CENTER);

    optView = new JPanel();
    view = new JPanel();
  }
  public void setOptView(JPanel v) {
    optView = v;
  }
  final public void setView(JComponent t) {
    view = new JPanel(new BorderLayout());
    JScrollPane scroll = new JScrollPane(t);
    scroll.setMinimumSize(new Dimension(20, 20));
    view.add(scroll, BorderLayout.CENTER);
  }
  public void assembleWithOpt() {
    assembleWithOpt(getOrientation());
  }
  public void assembleWithOpt(int orientation) {
    // pack it to the top left corner
    JPanel panelW = new JPanel(new BorderLayout());
    panelW.add(optView, BorderLayout.WEST);
    JPanel panelNW = new JPanel(new BorderLayout());
    panelNW.add(panelW, BorderLayout.NORTH);

    JScrollPane scroll = new JScrollPane(panelNW);
    scroll.setMinimumSize(optView.getMinimumSize()); // tested: it must be here

    split.setOrientation(orientation);
    split.setLeftComponent(scroll);
    split.setRightComponent(view);

    // VERY IMPORTANT! apply is running in an separate thread
    // so if frame repaint is not called, old view is displayed even after apply has finished
    if (frame != null)
      frame.repaint();
  }
  public int getOrientation() {
    if (split == null)
      return JSplitPane.VERTICAL_SPLIT;
    return split.getOrientation();
  }
  public void validate() {
//    log.info("options min size = " + optionsView.getMinimumSize());
//    log.info("options pref size = " + optionsView.getPreferredSize());
    super.validate();
    if (split != null)
      split.resetToPreferredSizes();
  }

  public void setFrame(JFrame frame)
  {
    this.frame = frame;
  }
}
