package tomsk.ucm.world;
import tomsk.TomskMainUI;
import tomsk.view.j3dx.utilsx.geometryx.CubeColorView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Aug 26, 2004, Time: 3:36:58 PM
 */
public class BuildWorldHnd implements ActionListener {
  public void actionPerformed(ActionEvent event) {
    TomskMainUI.getInstance().addWorldView(new CubeColorView().makeView());
  }
}
