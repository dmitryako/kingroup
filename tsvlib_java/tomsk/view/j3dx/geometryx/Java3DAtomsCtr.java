package tomsk.view.j3dx.geometryx;
import tomsk.view.j3dx.behaviorx.BehaviorEveryFrame;
import tomsk.view.j3dx.behaviorx.BehaviorEveryFrameCtrI;

import javax.swing.*;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 23, 2004, Time: 8:30:57 AM
 */
public class Java3DAtomsCtr implements BehaviorEveryFrameCtrI {
  private Java3DAtomsModel model_;
  public Java3DAtomsCtr(Java3DAtomsModel model) {
    model_ = model;
  }
  public JApplet makeView() {
    Java3DAtomsView view = new Java3DAtomsView(model_);
    view.addBehavior(new BehaviorEveryFrame(this));
    return view;
  }
  public void updateOnElapsedFrame() {
  }
}
