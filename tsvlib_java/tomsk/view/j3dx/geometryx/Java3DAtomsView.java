package tomsk.view.j3dx.geometryx;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import java.awt.*;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 22, 2004, Time: 1:07:08 PM
 */
public class Java3DAtomsView extends JApplet {
  private static Point3d TARGET = new Point3d(0, 0, 0);
  private static Vector3d CAMERA_TOP = new Vector3d(0, 1, 0);
  private static double BOX_LEN = 2; // centered at 0,0,0
  private SimpleUniverse simpleU_;
  private Canvas3D canvas3D_;
  private BranchGroup scene_;
  public Java3DAtomsView(Java3DAtomsModel model) {
    initSetup();
    scene_ = createSceneGraph(model);
    //scene.addChild(new BehaviorEveryFrame());
    buildView(scene_);
  }
  public void addBehavior(Behavior b) {
    scene_.addChild(b);
  }
  private void initSetup() {
    GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
    canvas3D_ = new Canvas3D(config);
    simpleU_ = new SimpleUniverse(canvas3D_);
    double cameraZ = 5;
    initCamera(cameraZ);
    initCanvas(cameraZ);
  }
  private void initCanvas(double cameraZ) {
    View cameraLens = canvas3D_.getView();
    //cameraLens.setFieldOfView(fov);
    //cameraLens.setFrontClipDistance(cameraZ - 0.5 * BOX_LEN);
    //cameraLens.setBackClipDistance(cameraZ + 0.5 * BOX_LEN);
  }
  private void initCamera(double cameraZ) {
    Point3d eye = new Point3d(0, 0, cameraZ);
    setCamera(eye, TARGET, CAMERA_TOP);
  }
  private void buildView(BranchGroup scene) {
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add("Center", canvas3D_);
    simpleU_.addBranchGraph(addMouseAction(scene));
  }
  public BranchGroup createSceneGraph(Java3DAtomsModel model) {
    if (model == null)
      return null;
    ColorPointArrayGeometry geom = new ColorPointArrayGeometry(model);
    BranchGroup scene = new BranchGroup(); // Create the scene of the branch graph
    scene.addChild(createPointShape(geom));
    return scene;
  }
  protected Shape3D createPointShape(Geometry geom) {
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
  private BranchGroup addMouseAction(BranchGroup scene) {
    BranchGroup root = new BranchGroup();
    TransformGroup mouseTransform = new TransformGroup();
    mouseTransform.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    mouseTransform.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
    MouseRotate mouseRotate = new MouseRotate(mouseTransform);
    MouseZoom mouseZoom = new MouseZoom(mouseTransform);
    mouseTransform.addChild(mouseRotate);
    mouseTransform.addChild(mouseZoom);
    mouseRotate.setSchedulingBounds(new BoundingSphere());
    mouseZoom.setSchedulingBounds(new BoundingSphere());
    root.addChild(mouseTransform);
    mouseTransform.addChild(scene);
    return root;
  }
  private void setCamera(Point3d eye, Point3d target, Vector3d up) {
    TransformGroup viewTransform = simpleU_.getViewingPlatform().getViewPlatformTransform();
    Transform3D cameraTransform = new Transform3D();
    cameraTransform.lookAt(eye, target, up);
    cameraTransform.invert();
    viewTransform.setTransform(cameraTransform);
  }
}
