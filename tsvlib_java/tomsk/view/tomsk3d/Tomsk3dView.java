package tomsk.view.tomsk3d;
import tomsk.view.tomsk3d.universe.Tomsk3dUniverse3;
import tomsk.view.tomsk3d.universe.Tomsk3dUniverseI;
import tomsk.view.tomsk3d.utils.Tomsk3dBackgroundFactory;
import tomsk.view.tomsk3d.utils.Tomsk3dBehaviourFactory;
import tomsk.view.tomsk3d.utils.Tomsk3dLightFactory;
import tomsk.view.tomsk3d.utils.Tomsk3dShapeFactory;
import tsvlib.project.ProjectLogger;

import javax.media.j3d.*;
import javax.swing.*;
import javax.swingx.utils.UCShowViewInFrame;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 14/05/2007, 14:48:03
 */
public class Tomsk3dView extends JPanel
//  implements ActionListener
{
  private static ProjectLogger log = ProjectLogger.getLogger(Tomsk3dView.class);

  private Tomsk3dModel oldModel;
  private BranchGroup rootBG;
  private BranchGroup background;
//  private Locale locale;
  private Bounds bounds;
  private RotationInterpolator 	rotator;
  private Tomsk3dUniverseI universe;
  private static final int PREF_WIDTH = 512;
  private static final int PREF_HEIGHT = 512;

  public Tomsk3dView( Tomsk3dModel model)
  {
    init( );
//    loadFrom(model);
    BranchGroup sceneBG = Tomsk3dShapeFactory.makeDefaultShape( );
    rebuild(model, sceneBG);
  }

  public void setEnableRotation(boolean b) {
    rotator.setEnable(b);
  }

  public void setNumRotationSecs(int nSec) {
    log.debug("setNumRotationSecs(", nSec);
    Alpha rotationAlpha = new Alpha( -1, nSec * 1000);
    rotator.setAlpha(rotationAlpha);
  }

  public boolean getEnableRotation() {
    return rotator.getEnable();
  }

  public void addMouseListener(MouseListener l) {
    universe.getCanvas().addMouseListener(l);
  }

  public void addMouseMotionListener(MouseMotionListener l) {
    super.addMouseMotionListener(l);
    universe.getCanvas().addMouseMotionListener(l);
  }

  protected void init( )
  {
    setLayout( new BorderLayout( ) );
    setOpaque( false );
    setPreferredSize( new Dimension(PREF_WIDTH, PREF_HEIGHT));
  }

  public void rebuild(BranchGroup sceneBG)
  {
    rebuild(oldModel, sceneBG);
  }

  /**
   * Both model and scene may have changed
   * @param model
   * @param sceneBG
   */
  public void rebuild(Tomsk3dModel model, BranchGroup sceneBG)
  {
    bounds =	Tomsk3dShapeFactory.makeBounds(model);
    rootBG = createSceneBG(model, sceneBG);
    background = Tomsk3dBackgroundFactory.makeBackground(model, bounds);
    if( background != null )
      rootBG.addChild( background );

    universe = new Tomsk3dUniverse3(model);
//    universe = new Tomsk3dUniverse2(model);
    universe.addBranchGraph( rootBG );

    removeAll();
    add(universe.getCanvas(), BorderLayout.CENTER );

    oldModel = model.clone(); // remember MODEL
  }

  private BranchGroup addRotation(Tomsk3dModel model
    , BranchGroup sceneBG) {
    TransformGroup tg = new TransformGroup();
    tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

    BranchGroup res = new BranchGroup();
    res.addChild(tg);
    tg.addChild(sceneBG);

    Transform3D yAxis = new Transform3D( );
    // create an Alpha interpolator to automatically generate
    // modifications to the rotation component of the transformation matrix
    Alpha rotationAlpha = new Alpha( -1, model.getNumRotationSecs() * 1000);
//    Alpha rotationAlpha = new Alpha( -1, Alpha.INCREASING_ENABLE, 0, 0, 4000, 0, 0, 0, 0, 0 );

    rotator = new RotationInterpolator( rotationAlpha, tg, yAxis, 0.0f, (float) Math.PI*2.0f );
    rotator.setSchedulingBounds( bounds );
    rotator.setEnable(false);
    tg.addChild(rotator);

    return res;
  }





  protected BranchGroup createSceneBG(Tomsk3dModel model, BranchGroup sceneBG)
  {
    BranchGroup res = new BranchGroup( );
//    res.setCapability( BranchGroup.ALLOW_DETACH );
    res.setCapability( Group.ALLOW_CHILDREN_EXTEND );
    res.setCapability( Group.ALLOW_CHILDREN_READ );
    res.setCapability( Group.ALLOW_CHILDREN_WRITE );

    // create a TransformGroup to rotate the objects in the scene
    // set the capability bits on the TransformGroup so that it
    // can be modified at runtime
    TransformGroup rootTG = new TransformGroup( );
    rootTG.setCapability( TransformGroup.ALLOW_TRANSFORM_WRITE );
    rootTG.setCapability( TransformGroup.ALLOW_TRANSFORM_READ );

    Light aLgt = Tomsk3dLightFactory.makeAmbientLight(bounds);
    res.addChild( aLgt );

    Light dLgt = Tomsk3dLightFactory.makeDirectionalLight(bounds);
    res.addChild( dLgt );

    sceneBG = Tomsk3dBehaviourFactory.addMouseRotate(sceneBG, bounds);
    sceneBG = addRotation(model, sceneBG);
    sceneBG = Tomsk3dBehaviourFactory.addMouseTranslate(sceneBG, bounds);
    sceneBG = Tomsk3dBehaviourFactory.addMouseZoom(sceneBG, bounds);
    rootTG.addChild( sceneBG );

//    rootTG.addChild( sceneBG );
    res.addChild( rootTG );
    return res;
  }




  /**
   *	main entry point for the application. Creates the parent
   *	JFrame, the JMenuBar and creates the JPanel which is the
   *	application itself.
   */
  public static void main( String[] args )
  {
    JPopupMenu.setDefaultLightWeightPopupEnabled( false );

    ToolTipManager ttm = ToolTipManager.sharedInstance( );
    ttm.setLightWeightPopupEnabled( false );

    Tomsk3dModel model = new Tomsk3dModel();
    Tomsk3dView swingTest = new Tomsk3dView(model);
//    new Tomsk3dUI(swingTest).run();
    new UCShowViewInFrame(swingTest, "Tomsk3dView").run();
  }

  /**
   * Only the viewing model has changed. Scene remains unchanged.
   * @param model
   */
  public void loadFrom(Tomsk3dModel model)
  {
    setEnableRotation(model.getEnableRotation());
    setNumRotationSecs(model.getNumRotationSecs());

    if (oldModel == null ||
      oldModel.getBackgroundIdx() != model.getBackgroundIdx()) {
      rootBG.removeChild(background);  // remove OLD

      background = Tomsk3dBackgroundFactory.makeBackground(model, bounds);
      if( background != null )
        rootBG.addChild( background );
    }

    oldModel = model.clone(); // remember MODEL
  }

  public BufferedImage getImage()
  {
    return universe.getImage();
  }
}
