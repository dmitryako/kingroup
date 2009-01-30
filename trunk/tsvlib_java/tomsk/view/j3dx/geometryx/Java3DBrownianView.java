package tomsk.view.j3dx.geometryx;
import javax.media.j3d.BranchGroup;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 17, 2004, Time: 11:36:06 AM
 */
//brownian motion
public class Java3DBrownianView extends Java3DParticlesView {
  private Java3DBrownianGeometry model_;
  public Java3DBrownianView(Java3DBrownianGeometry model) {
    super(null);
    model_ = model;
  }
  public BranchGroup createSceneGraph() {
    BranchGroup root = new BranchGroup(); // Create the root of the branch graph
    if (model_ == null) {
      int NUM_PARTICLES = 100;
      model_ = new Java3DBrownianGeometry(NUM_PARTICLES);
    }
    root.addChild(createPointShape(model_));
    return root;
  }
  public void updateOnElapsedFrame() {
    if (model_ == null)
      return;
    model_.calcNextFrame();
  }
}
