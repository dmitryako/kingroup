package tomsk.view.j3dx.utilsx.geometryx;
import com.sun.j3d.utils.geometry.ColorCube;
import tomsk.view.j3dx.utilsx.universex.SimpleUniverseView;

import javax.media.j3d.BranchGroup;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 9, 2004, Time: 1:31:13 PM
 */
public class CubeColorView extends SimpleUniverseView {
  public BranchGroup createSceneGraph() {
    // Create the root of the branch graph
    BranchGroup scene = new BranchGroup();
    scene.addChild(new ColorCube(0.4));
    return scene;
  }
}
