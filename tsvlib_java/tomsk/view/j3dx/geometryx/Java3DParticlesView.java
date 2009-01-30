package tomsk.view.j3dx.geometryx;
import tomsk.view.j3dx.utilsx.universex.SimpleUniverseView;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.PointAttributes;
import javax.media.j3d.Shape3D;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 15, 2004, Time: 3:24:43 PM
 */
public class Java3DParticlesView extends SimpleUniverseView {
  private Java3DParticlesGeometry geometry_;
  public Java3DParticlesView(Java3DParticlesGeometry geometry) {
    geometry_ = geometry;
  }
  public BranchGroup createSceneGraph() {
    BranchGroup root = new BranchGroup(); // Create the root of the branch graph
    if (geometry_ == null) {
      int NUM_PARTICLES = 10;
      geometry_ = new Java3DParticlesGeometry(NUM_PARTICLES);
    }
    root.addChild(createPointShape(geometry_));
    return root;
  }
  protected Shape3D createPointShape(Java3DParticlesGeometry pointShape) {
    int pointSize = 3;
    Appearance app = new Appearance();
    app.setCapability(app.ALLOW_POINT_ATTRIBUTES_READ);
    app.setCapability(app.ALLOW_POINT_ATTRIBUTES_WRITE);
    PointAttributes pattrib = new PointAttributes();
    pattrib.setPointSize(pointSize);
    app.setPointAttributes(pattrib);
    Shape3D pShape = new Shape3D(pointShape, app);
    pShape.setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
    pShape.setCapability(Shape3D.ALLOW_APPEARANCE_READ);
    return pShape;
  }
}


