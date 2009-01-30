package tomsk.view.tomsk3d.universe;
import tomsk.view.tomsk3d.Tomsk3dModel;
import tsvlib.project.ProjectLogger;

import javax.media.j3d.*;
import javax.vecmath.Vector3d;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 24/05/2007, 15:54:12
 */
public class Tomsk3dUniverse2 extends Tomsk3dUniverse
{
  private static ProjectLogger log = ProjectLogger.getLogger(Tomsk3dUniverse2.class);
  private VirtualUniverse universe;
  private Locale locale;

  public Tomsk3dUniverse2(Tomsk3dModel model)
  {
    universe = new VirtualUniverse();
    locale = new Locale( universe );
//    setLocale( locale );

    ViewPlatform vp = makeViewPlatform( );
    BranchGroup viewBG = createViewBG( getViewTransformGroupArray( ), vp );
    addViewBranchGroup( locale, viewBG );
    createView( vp );
  }

  public void addBranchGraph(BranchGroup rootBG) {
    locale.addBranchGraph( rootBG );
  }

  protected ViewPlatform makeViewPlatform( )
  {
    ViewPlatform res = new ViewPlatform( );
    res.setViewAttachPolicy( View.RELATIVE_TO_FIELD_OF_VIEW );
    res.setActivationRadius( getViewPlatformActivationRadius( ) );
    return res;
  }

  public TransformGroup[] getViewTransformGroupArray( )
  {
    TransformGroup[] res = new TransformGroup[1];
    res[0] = new TransformGroup( );

    // move the camera BACK a little...
    // note that we have to invert the matrix as
    // we are moving the viewer
    Transform3D t3d = new Transform3D( );
    t3d.setScale( getScale( ) );
    t3d.setTranslation( new Vector3d( 0.0, 0.0, -20.0 ) );
    t3d.invert( );
    res[0].setTransform( t3d );

    return res;
  }

  protected double getScale( )
  {
    return 3;
  }

  protected void addViewBranchGroup( Locale locale, BranchGroup bg )
  {
    locale.addBranchGraph( bg );
  }

  protected BranchGroup createViewBG( TransformGroup[] tgArray, ViewPlatform vp )
  {
    BranchGroup res = new BranchGroup( );

    if( tgArray != null && tgArray.length > 0 )
    {
      Group parentGroup = res;
      TransformGroup curTg = null;

      for( int n = 0; n < tgArray.length; n++ )
      {
        curTg = tgArray[n];
        parentGroup.addChild( curTg );
        parentGroup = curTg;
      }

      tgArray[tgArray.length-1].addChild( vp );
    }
    else
      res.addChild( vp );

    return res;
  }

  protected float getViewPlatformActivationRadius( )
  {
    return 100;
  }

  protected double getBackClipDistance( )
  {
    return 100.0;
  }

  protected double getFrontClipDistance( )
  {
    return 1.0;
  }

  protected View createView( ViewPlatform vp )
  {
    View res = new View( );

    PhysicalBody pb = makePhysicalBody( );
    PhysicalEnvironment pe = makePhysicalEnvironment( );

    res.setPhysicalEnvironment( pe );
    res.setPhysicalBody( pb );

    if( vp != null )
      res.attachViewPlatform( vp );

    res.setBackClipDistance( getBackClipDistance( ) );
    res.setFrontClipDistance( getFrontClipDistance( ) );

    setCanvas( rebuild( getCanvas() ));
//    log.trace("visibleCanvas.getMouseListeners=", visibleCanvas.getMouseListeners());
    res.addCanvas3D( getCanvas() );

    setOffScreenCanvas( createOffscreenCanvas3D() );
    res.addCanvas3D( getOffScreenCanvas() );

    return res;
  }

  protected PhysicalBody makePhysicalBody( )
  {
    return new PhysicalBody( );
  }

  protected PhysicalEnvironment makePhysicalEnvironment( )
  {
    return new PhysicalEnvironment( );
  }

}
