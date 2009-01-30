package tomsk.view.tomsk3d.universe;
import com.sun.j3d.utils.universe.SimpleUniverse;
import tomsk.view.tomsk3d.Tomsk3dModel;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import java.awt.*;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 24/05/2007, 15:53:16
 */
public class Tomsk3dUniverse3 extends Tomsk3dUniverse
{
  private SimpleUniverse universe;
  public Tomsk3dUniverse3(Tomsk3dModel model)
  {
    init();
//    initV2();
  }

  private void init() {
    // If this is not called, a separate frame appears.
    GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
    Canvas3D canvas = new Canvas3D(config);
    canvas.setFocusable(true);

    universe = new SimpleUniverse(canvas);
    universe.getViewingPlatform().setNominalViewingTransform();
    setCanvas(canvas);
  }

  public void addBranchGraph(BranchGroup rootBG) {
    universe.addBranchGraph( rootBG );
  }


  private void initWRONG() {
    universe = new SimpleUniverse();
    universe.getViewingPlatform().setNominalViewingTransform();
    setCanvas(universe.getCanvas());
  }


//  private static final int BOUNDSIZE = 100;  // larger than world
//  private static final Point3d USERPOSN = new Point3d(0,5,20);
//    // initial user position
//
//  private SimpleUniverse su;
//  private BranchGroup sceneBG;
//  private BoundingSphere bounds;   // for environment nodes
//
//
//  public WrapParticles3D(int numParticles, int fountainChoice)
//  {
//    GraphicsConfiguration config =
//          SimpleUniverse.getPreferredConfiguration();
//    Canvas3D canvas3D = new Canvas3D(config);
//    add("Center", canvas3D);
//    canvas3D.setFocusable(true);
//    canvas3D.requestFocus();    // the canvas now has focus, so receives key events
//
//    su = new SimpleUniverse(canvas3D);
//
//    createSceneGraph(numParticles, fountainChoice);
//    initUserPosition();        // set user's viewpoint
//    orbitControls(canvas3D);   // controls for moving the viewpoint
//
//    su.addBranchGraph( sceneBG );
//  } // end of WrapParticles3D()
//
}
