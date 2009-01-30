package tomsk.view.j3dx.utilsx.geometryx;
import com.sun.j3d.utils.geometry.ColorCube;
import tomsk.view.j3dx.utilsx.universex.SimpleUniverseView;

import javax.media.j3d.*;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 9, 2004, Time: 3:07:15 PM
 */
public class CubeSpinView extends SimpleUniverseView {
  public BranchGroup createSceneGraph() {
    // Create the root of the branch graph
    BranchGroup scene = new BranchGroup();

    // Create the transform group node and initialize it to the
    // identity. Add it to the root of the subgraph.
    TransformGroup objSpin = new TransformGroup();
    objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    scene.addChild(objSpin);

    // Create a simple shape leaf node, addLine it to the scene graph.
    // ColorCube is a Convenience Utility class
    objSpin.addChild(new ColorCube(0.4));

    // create time varying function to drive the animation
    Alpha rotationAlpha = new Alpha(-1, 4000);

    // Create a new Behavior object that performs the desired
    // operation on the specified transform object and addLine it into
    // the scene graph.
    RotationInterpolator rotator =
      new RotationInterpolator(rotationAlpha, objSpin);

    // a bounding sphere specifies a region a behavior is active
    BoundingSphere bounds = new BoundingSphere();
    rotator.setSchedulingBounds(bounds);
    objSpin.addChild(rotator);
    return scene;
  }
}
