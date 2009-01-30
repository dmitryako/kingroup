package pattern.ucm;
import javax.swing.*;
import java.awt.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 13/03/2006, Time: 12:57:50
 */
public class UCShowHelpMssg implements UCController {
  private final String txt;
  private final Component parent;
  public UCShowHelpMssg(String txt, Component parent)
  {
    this.txt = txt;
    this.parent = parent;
  }

  public boolean run()
  {
    JOptionPane.showMessageDialog(parent, txt
      , "Help", JOptionPane.INFORMATION_MESSAGE);
    return true;
  }
}
