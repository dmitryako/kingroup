package tomsk.ucm.world.tests;
import pattern.ucm.UCController;
import tomsk.TomskMainUI;
import tomsk.view.j3dx.geometryx.Java3DBrownianGeometry;
import tomsk.view.j3dx.geometryx.Java3DBrownianView;

/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 17, 2004, Time: 12:44:39 PM
 */
public class UCRunBrownianMotionTest implements UCController {
  public boolean run()
  {
    final int SIZE = 10000;
    Java3DBrownianGeometry model = new Java3DBrownianGeometry(SIZE);
    TomskMainUI.getInstance().addWorldView(new Java3DBrownianView(model).makeView());
    return true;
  }
}