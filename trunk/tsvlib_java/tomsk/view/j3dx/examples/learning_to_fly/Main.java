/*
from
http://www.fawcette.com/javapro/2003_06/magazine/features/dsavarese/
--------------------------------------------------------------------------------
Moving Pictures
Listing 4. Main populates a world with predefined shapes, representing a landing strip. Scenes are usually not hard-coded, but instead generated with a separate tool.

        */
package tomsk.view.j3dx.examples.learning_to_fly;
import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;
import java.awt.*;
public class Main {
  private static final Color3f[] COLORS = {
    new Color3f(0.75f, 0.75f, 0.99f),
    new Color3f(0.88f, 0.88f, 0.88f),
    new Color3f(0.05f, 0.07f, 0.07f),
    new Color3f(0.48f, 0.68f, 0.47f),
    new Color3f(0.53f, 0.58f, 0.29f),
    new Color3f(0.42f, 0.56f, 0.59f),
    new Color3f(0.65f, 0.63f, 0.47f),
    new Color3f(0.56f, 0.25f, 0.25f),
    new Color3f(0.30f, 0.68f, 0.47f),
    new Color3f(0.65f, 0.73f, 0.37f),
    new Color3f(0.46f, 0.45f, 0.25f),
    new Color3f(0.43f, 0.58f, 0.29f)
  };
  private static final double[] SHAPES[][] = {
    // Ground
    {
      {-1E6, -1E6, -1, 1E6, -1E6, -1,
        1E6, 1E6, -1, -1E6, 1E6, -1},
    },
    // Patchwork
    {
      {1500, -500, 0, -1000, -500, 0,
        -1000, -1200, 0, 1500, -1200, 0},
      {600, -500, 0, 1500, -500, 0,
        1500, 1500, 0, 600, 1500, 0},
      {-400, 700, 0, 600, 700, 0,
        600, 1500, 0, -400, 1500, 0},
      {-400, -500, 0, -400, 1500, 0,
        -1000, 1500, 0, -1000, -500, 0},
      {-400, -10, 0, -110, -10, 0,
        -110, 700, 0, -400, 700, 0},
      {200, 400, 0, 200, 700, 0,
        -110, 700, 0, -110, 400, 0},
      {200, 400, 0, 600, 400, 0,
        600, 700, 0, 200, 700, 0},
      {310, -10, 0, 600, -10, 0,
        600, 400, 0, 310, 400, 0},
      {100, -10, 0, 100, -500, 0,
        600, -500, 0, 600, -10, 0},
      {-400, -10, 0, -400, -500, 0,
        100, -500, 0, 100, -10, 0},
      {-110, -10, 0, 310, -10, 0,
        310, 400, 0, -110, 400, 0},
    },
    // Air Strip
    {
      {-100, 290, 0.1, 300, 290, 0.1,
        300, 310, 0.1, -100, 310, 0.1},
      {-10, 0, 0.1, 10, 0, 0.1,
        10, 400, 0.1, -10, 400, 0.1},
    },
    // Air control tower
    {
      {20, 270, 0, 30, 270, 0, 30, 270,
        30, 20, 270, 30},
      {20, 280, 0, 20, 270, 0, 20, 270,
        30, 20, 280, 30},
      {30, 270, 0, 30, 280, 0, 30, 280,
        30, 30, 270, 30},
      {30, 280, 0, 20, 280, 0, 20, 280,
        30, 30, 280, 30},
      {20, 270, 30, 30, 270, 30, 25, 275, 35},
      {20, 280, 30, 20, 270, 30, 25, 275, 35},
      {30, 270, 30, 30, 280, 30, 25, 275, 35},
      {30, 280, 30, 20, 280, 30, 25, 275, 35}
    },
    // Floating polyhedron (Space Invader)
    {
      {1500, 1000, 120, 1000, 1000, 120,
        1300, 1300, 20},
      {1500, 1500, 120, 1500, 1000, 120,
        1300, 1300, 20},
      {1000, 1500, 120, 1500, 1500, 120,
        1300, 1300, 20},
      {1000, 1500, 120, 1300, 1300, 20,
        1000, 1000, 120},
      {1500, 1000, 120, 1300, 1300, 220,
        1000, 1000, 120},
      {1500, 1000, 120, 1500, 1500, 120,
        1300, 1300, 220},
      {1000, 1500, 120, 1300, 1300, 220,
        1500, 1500, 120},
      {1300, 1300, 220, 1000, 1500, 120,
        1000, 1000, 120}
    }
  };
  private static final int[] COLOR_INDICES[] = {
    {3},
    {5, 4, 11, 10, 9, 8, 7, 6, 5, 4, 3},
    {1, 1},
    {5, 4, 1, 2, 6, 7, 8, 2},
    {4, 5, 2, 7, 5, 4, 7, 2},
  };
  private static final BranchGroup createScene() {
    BranchGroup group = new BranchGroup();
    Background sky = new Background(COLORS[0]);
    BoundingSphere bounds = new BoundingSphere();
    bounds.setRadius(1e10);
    sky.setApplicationBounds(bounds);
    group.addChild(sky);
    for (int i = 0; i < SHAPES.length; ++i) {
      GeometryArray geometry;
      Shape3D shape;
      shape = new Shape3D();
      geometry = new QuadArray(
        4, GeometryArray.COORDINATES |
        GeometryArray.COLOR_3);
      for (int j = 0; j < SHAPES[i].length; ++j) {
        int vertices;
        vertices = SHAPES[i][j].length / 3;
        if (vertices == 4) {
          geometry =
            new QuadArray(
              vertices, GeometryArray.COORDINATES |
              GeometryArray.COLOR_3);
        } else {
          geometry =
            new TriangleArray(vertices,
              GeometryArray.COORDINATES |
                GeometryArray.COLOR_3);
        }
        geometry.setCoordinates(0, SHAPES[i][j]);
        for (int k = 0; k < vertices; ++k)
          geometry.setColor(
            k, COLORS[COLOR_INDICES[i][j]]);
        shape.addGeometry(geometry);
      }
      group.addChild(shape);
    }
    return group;
  }
  public static final void main(String[] args) {
    GraphicsEnvironment ge;
    GraphicsDevice gd;
    GraphicsConfigTemplate3D gct =
      new GraphicsConfigTemplate3D();
    Canvas3D canvas;
    JFrame frame = new JFrame("Flyer");
    ge = GraphicsEnvironment.
      getLocalGraphicsEnvironment();
    gd = ge.getDefaultScreenDevice();
    canvas = new Canvas3D(
      gd.getBestConfiguration(gct));
    canvas.setStereoEnable(false);
    canvas.setMonoscopicViewPolicy(
      View.CYCLOPEAN_EYE_VIEW);
    World world = new World();
    Flyer flyer = new Flyer();
    flyer.setFrameRate(10);
    flyer.setVelocity(20);
    flyer.setAcceleration(20);
    flyer.setViewingDirection(new Vector3d(
      -2000, -3000, 500), new Vector3d(20, 30, -1),
      new Vector3d(0, 0, 1));
    flyer.addCanvas(canvas);
    world.addViewer(flyer);
    flyer.addBehavior(world);
    world.addChild(createScene());
    world.activate();
    frame.getContentPane().add(canvas);
    frame.setDefaultCloseOperation(
      JFrame.EXIT_ON_CLOSE);
    frame.setSize(500, 500);
    frame.setVisible(true);
  }
}


