/*
--------------------------------------------------------------------------------
Power Windows
Listing 2. A View defines your window to the virtual world, but it requires a Canvas3D object to display the world.

          */
package tomsk.view.j3dx.examples.learning_to_fly;
import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
public class Viewer {
  Transform3D eye;
  View view;
  ViewPlatform platform;
  PhysicalBody body;
  PhysicalEnvironment environment;
  TransformGroup root;
  Light light;
  Canvas3D canvas;
  public Viewer() {
    view = new View();
    platform = new ViewPlatform();
    canvas = null;
    eye = new Transform3D();
    body = new PhysicalBody();
    environment = new PhysicalEnvironment();
    view.setFrontClipDistance(1);
    view.setBackClipDistance(1e10);
    view.setPhysicalBody(body);
    view.setPhysicalEnvironment(environment);
    view.attachViewPlatform(platform);
    light =
      new PointLight(true, new Color3f(1, 1, 1),
        new Point3f(0, 1e10f, 0),
        new Point3f(1, 0, 0));
    root = new TransformGroup();
    root.setCapability(
      TransformGroup.ALLOW_TRANSFORM_WRITE);
    root.setTransform(eye);
    root.addChild(platform);
    root.addChild(light);
  }
  public void addCanvas(Canvas3D canvas) {
    this.canvas = canvas;
    view.addCanvas3D(canvas);
  }
  public void move(Vector3d location) {
    eye.setTranslation(location);
    root.setTransform(eye);
  }
  public Group getRoot() {
    return root;
  }
}


