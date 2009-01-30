package tomsk.ucm.world.tests;
import tomsk.TomskMainUI;
import tomsk.view.j3dx.geometryx.Java3DParticlesGeometry;
import tomsk.view.j3dx.geometryx.Java3DParticlesView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 15, 2004, Time: 3:28:58 PM
 */
public class TestParticlesHnd implements ActionListener {
  public void actionPerformed(ActionEvent event) {
    final int SIZE = 100;
    Java3DParticlesGeometry geometry = new Java3DParticlesGeometry(SIZE);
    TomskMainUI.getInstance().addWorldView(new Java3DParticlesView(geometry).makeView());
  }
}