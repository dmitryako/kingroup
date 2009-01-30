package tomsk.view.j3dx.utilsx.universex;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import java.awt.*;
import java.util.Enumeration;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 15, 2004, Time: 2:32:44 PM
 */
public class SimpleUniverseView {
  private static Point3d TARGET = new Point3d(0, 0, 0);
  private static Vector3d CAMERA_TOP = new Vector3d(0, 1, 0);
  private SimpleUniverse simpleU;
  private Canvas3D canvas3D;
  public BranchGroup createSceneGraph() {
    return new BranchGroup();
  }
  public void updateOnElapsedFrame() {
  }
  public JApplet makeView() {
    init();
    BranchGroup scene = createSceneGraph();
    scene.addChild(new BehaviorEveryFrame());
    return buildView(scene);
  }
  protected void init() {
    GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
    canvas3D = new Canvas3D(config);
    simpleU = new SimpleUniverse(canvas3D);
    double cameraZ = 5;
    initCamera(cameraZ);
    initCanvas(cameraZ);
  }
  private void initCanvas(double cameraZ) {
    View cameraLens = canvas3D.getView();
    //cameraLens.setFieldOfView(fov);
    //cameraLens.setFrontClipDistance(cameraZ - 0.5 * BOX_LEN);
    //cameraLens.setBackClipDistance(cameraZ + 0.5 * BOX_LEN);
  }
  private void initCamera(double cameraZ) {
    Point3d eye = new Point3d(0, 0, cameraZ);
    setCamera(eye, TARGET, CAMERA_TOP);
  }
  protected JApplet buildView(BranchGroup scene) {
    JApplet view = new JApplet();
    view.getContentPane().setLayout(new BorderLayout());
    view.getContentPane().add("Center", canvas3D);

    // This will move the ViewPlatform back a bit so the
    // objects in the scene can be viewed.
    //simpleU_.getViewingPlatform().setNominalViewingTransform();
    simpleU.addBranchGraph(addMouseAction(scene));
    return view;
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
    TransformGroup viewTransform = simpleU.getViewingPlatform().getViewPlatformTransform();
    Transform3D cameraTransform = new Transform3D();
    cameraTransform.lookAt(eye, target, up);
    cameraTransform.invert();
    viewTransform.setTransform(cameraTransform);
  }
  final private class BehaviorEveryFrame extends Behavior {
    private final WakeupCondition wakeupCond_ = new WakeupOnElapsedFrames(0);
    public void initialize() {
      setSchedulingBounds(new BoundingSphere(new Point3d(), 1000));
      wakeupOn(wakeupCond_);
    }
    public void processStimulus(Enumeration criteria) {
      updateOnElapsedFrame();
      wakeupOn(wakeupCond_);
    }
  }
}


