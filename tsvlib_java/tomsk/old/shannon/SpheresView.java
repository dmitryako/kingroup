package tomsk.old.shannon;
import com.sun.j3d.utils.geometry.Sphere;
import tomsk.view.j3dx.geometryx.Java3DBrownianGeometry;
import tomsk.view.j3dx.geometryx.Java3DParticlesView;

import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 22, 2004, Time: 9:13:05 AM
 */
public class SpheresView extends Java3DParticlesView {
  private Java3DBrownianGeometry particles_;
  private Atom atoms[];
  // Hi Shannon,
  // Please make this class display spheres
  public SpheresView(SpheresModel model) {
    super(model);
    particles_ = model;
  }
  public BranchGroup createSceneGraph() {
    int NUM_PARTICLES;
    BranchGroup root = new BranchGroup(); // Create the root of the branch graph
    if (particles_ == null) {
      NUM_PARTICLES = 1000;
      particles_ = new Java3DBrownianGeometry(NUM_PARTICLES);
    } else
      NUM_PARTICLES = particles_.size();
    atoms = new Atom[NUM_PARTICLES];
    for (int x = 0; x < NUM_PARTICLES; x++) {
      atoms[x] = new Atom();
      atoms[x].setPosition(particles_.getPosition(x));
      root.addChild(atoms[x]);
    }
    return root;
  }
  public void updateOnElapsedFrame() {
    if (particles_ == null)
      return;
    particles_.calcNextFrame();
    for (int x = 0; x < particles_.size(); x++) {
      atoms[x].setPosition(particles_.getPosition(x));
    }
  }
}
class Atom extends BranchGroup {
  private TransformGroup transform = new TransformGroup();
  private static Appearance defaultAppearance = createAppearance(true, new Color3f(1.0f, 0.0f, 0.0f), new Color3f(0.5f, 0.0f, 0.0f));
  public Atom() {
    transform.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
    transform.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    addChild(transform);
    initLOD();
    compile();
  }
  private void initLOD() {
    // create a switching node with 3 different paths
    Switch sphereSwitch = new Switch(0);
    sphereSwitch.setCapability(Switch.ALLOW_SWITCH_READ);
    sphereSwitch.setCapability(Switch.ALLOW_SWITCH_WRITE);
    sphereSwitch.addChild(new Sphere(0.02f, Sphere.GENERATE_NORMALS, 16, defaultAppearance));
    sphereSwitch.addChild(new Sphere(0.02f, Sphere.GENERATE_NORMALS, 12, defaultAppearance));
    sphereSwitch.addChild(new Sphere(0.02f, Sphere.GENERATE_NORMALS, 8, defaultAppearance));
    transform.addChild(sphereSwitch);
    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);

    // create Level-Of-Detail node based on the Switch above
    float distances[] = new float[3];
    distances[0] = 1.5f;
    distances[1] = 3.0f;
    distances[2] = 6.0f;
    DistanceLOD lod = new DistanceLOD(distances);
    lod.addSwitch(sphereSwitch);
    lod.setSchedulingBounds(bounds);
    transform.addChild(lod);
  }
  private static Appearance createAppearance(boolean useLights, Color3f diffuse, Color3f ambient) {
    Appearance appearance = new Appearance();
    appearance.setColoringAttributes(new ColoringAttributes(diffuse, ColoringAttributes.NICEST));
    if (useLights) {
      Material material = new Material();
      material.setDiffuseColor(diffuse);
      material.setAmbientColor(ambient);
      appearance.setMaterial(material);
    }
    return appearance;
  }
  public void setPosition(Point3d newPosition) {
    Transform3D transform3d = new Transform3D();
    transform3d.setTranslation(new Vector3d(newPosition));
    transform.setTransform(transform3d);
  }
}
