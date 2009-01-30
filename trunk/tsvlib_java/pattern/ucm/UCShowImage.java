package pattern.ucm;

import tsvlib.project.ProjectFrame;

import javax.swing.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 20/03/2006, Time: 11:51:20
 */
public class UCShowImage  implements UCController {
  private final String title;
  private final String filePath;
  private final JFrame parent;
  public UCShowImage(String title, String filePath, JFrame parent)
  {
    this.title = title;
    this.filePath = filePath;
    this.parent = parent;
  }
  public boolean run()
  {
    ImageIcon icon = ProjectFrame.loadImageIcon(filePath);
    JOptionPane.showMessageDialog(parent, icon
      , title, JOptionPane.PLAIN_MESSAGE);
    return true;
  }
}

