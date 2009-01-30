package javax.swingx;
import tsvlib.project.ProjectLogger;

import javax.swing.*;
import java.awt.*;
/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: May 24, 2004, Time: 10:42:48 AM
 */
public class JTabbedPaneX extends JTabbedPane {
  private final static ProjectLogger log = ProjectLogger.getLogger(JTabbedPaneX.class);
  public JTabbedPaneX(int tabPlacement) {
    super(tabPlacement);
//    add(new JPanel());
//    addTab("Tab 1", null, new JPanel(),
//                      "Does nothing");

  }
  public int processView(Component view, int idx, boolean focus
    , String title, String tip) {
    log.trace("processView(view=", view);
    if (view == null)
      return idx;
    log.trace("idx=", idx);
    log.trace("focus=", focus);
    log.trace("title=", title);
    log.trace("tip=", tip);

    if (idx == -1) {
      idx = getTabCount();
      addTab(title, null, view, tip);
//      addTab(title, view);
    } else  {
      setComponentAt(idx, view);
    }
    if (focus)
      setSelectedIndex(idx);
    return idx;
  }
}
