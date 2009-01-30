package tomsk.ucm.world.tests;
import tomsk.TomskMainUI;
import tomsk.old.shannon.SpheresModel;
import tomsk.old.shannon.SpheresView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 22, 2004, Time: 9:04:24 AM
 */
public class TestSpheresHnd implements ActionListener {
  public void actionPerformed(ActionEvent event) {
    SpheresModel model = new SpheresModel(1000);
    TomskMainUI.getInstance().addWorldView(new SpheresView(model).makeView());

    //TomskMainUI.getInstance().addWorldView(new SpheresView().makeView());
  }
}
