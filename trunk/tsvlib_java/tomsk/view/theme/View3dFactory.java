package tomsk.view.theme;
import tomsk.domain.particle.Particle;

import javax.media.j3d.*;
import javax.vecmath.Color3f;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 15/05/2007, 09:42:32
 */
public class View3dFactory
{
  /**
   * @param part
   * @return BranchGroup because it allows a dynamically insertable and removable Group
   */
  public BranchGroup makeView3d(Particle part)
  {
    return createSphere(part);
  }

  protected BranchGroup createSphere(Particle part)
  {
    BranchGroup bg = new BranchGroup( );
    bg.setCapability( BranchGroup.ALLOW_DETACH );

    Appearance app = new Appearance( );
    Color3f objColor = new Color3f( 1.0f, 0.7f, 0.8f );
    Color3f black = new Color3f( 0.0f, 0.0f, 0.0f );
    app.setMaterial( new Material( objColor, black, objColor, black, 80.0f ) );

    Transform3D t3d = new Transform3D();
    t3d.set(part.getCoord());
    TransformGroup tg = new TransformGroup(t3d);
    tg.addChild( new com.sun.j3d.utils.geometry.Sphere( 1, app ) );
    bg.addChild(tg);
    bg.setUserData( "Sphere" );
    return bg;
  }

}
