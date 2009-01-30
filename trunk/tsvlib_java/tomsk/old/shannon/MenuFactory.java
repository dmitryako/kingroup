package tomsk.old.shannon;
import tomsk.ucm.world.tests.TestSpheresHnd;

import javax.swing.*;
import java.awt.event.KeyEvent;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Oct 28, 2004, Time: 9:43:30 AM
 */
public class MenuFactory {
  private static JMenuItem addMenuTestLJ(JMenu menu) {
    JMenuItem menuItem = new JMenuItem("LOD Sphere Test");
    menuItem.setMnemonic(KeyEvent.VK_S);
    menu.add(menuItem);
    return menuItem;
  }
  public static void addMenuItems(JMenu menu) {
    JMenuItem atoms = addMenuTestLJ(menu);
    atoms.addActionListener(new TestSpheresHnd());
  }
}
