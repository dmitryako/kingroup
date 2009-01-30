package tomsk.domain;
import tomsk.view.j3dx.utilsx.universex.SimpleUniverseView;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.PointAttributes;
import javax.media.j3d.Shape3D;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 16/04/2007, 13:06:16
 */
public class ParticleArrView  extends SimpleUniverseView {
  private ParticleArrGeometry geometry;

  public ParticleArrView(ParticleArrGeometry geometry) {
    this.geometry = geometry;
  }
  public BranchGroup createSceneGraph() {
    BranchGroup root = new BranchGroup(); // Create the root of the branch graph
//    if (geometry == null) {
//      int NUM_PARTICLES = 10;
//      geometry = new ParticleArrGeometry(NUM_PARTICLES);
//    }
    root.addChild(createPointShape(geometry));
    return root;
  }
  protected Shape3D createPointShape(ParticleArrGeometry geom) {
    int pointSize = 3;
    Appearance app = new Appearance();
    app.setCapability(app.ALLOW_POINT_ATTRIBUTES_READ);
    app.setCapability(app.ALLOW_POINT_ATTRIBUTES_WRITE);
    PointAttributes pattrib = new PointAttributes();
    pattrib.setPointSize(pointSize);
    app.setPointAttributes(pattrib);

    Shape3D pShape = new Shape3D(geom, app);
    pShape.setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
    pShape.setCapability(Shape3D.ALLOW_APPEARANCE_READ);
    return pShape;
  }

  public void updateOnElapsedFrame() {
    if (geometry == null)
      return;
//    geometry.calcNextFrame();
    new UCCalcNextFrame(geometry).run();
  }
}

