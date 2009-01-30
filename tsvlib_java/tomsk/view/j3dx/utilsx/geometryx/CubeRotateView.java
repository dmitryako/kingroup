package tomsk.view.j3dx.utilsx.geometryx;
import com.sun.j3d.utils.geometry.ColorCube;
import tomsk.view.j3dx.utilsx.universex.SimpleUniverseView;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 9, 2004, Time: 2:30:27 PM
 */
public class CubeRotateView extends SimpleUniverseView {
  public BranchGroup createSceneGraph() {
    // Create the scene of the branch graph
    BranchGroup scene = new BranchGroup();

    // rotate object has composited transformation matrix
    Transform3D rotate = new Transform3D();
    Transform3D tempRotate = new Transform3D();
    rotate.rotX(Math.PI / 4.0d);
    tempRotate.rotY(Math.PI / 5.0d);
    rotate.mul(tempRotate);
    TransformGroup objRotate = new TransformGroup(rotate);
    scene.addChild(objRotate);
    objRotate.addChild(new ColorCube(0.4));

    // Let Java 3D perform optimizations on this scene graph.
    //scene.compile();
    return scene;
  }
}
