package tomsk.view.tomsk3d.utils;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Bounds;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Light;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 23/05/2007, 10:40:01
 */
public class Tomsk3dLightFactory
{

  public static Light makeAmbientLight(Bounds bounds) {
    Color3f alColor = new Color3f( 0.2f, 0.2f, 0.2f );
    AmbientLight res = new AmbientLight( alColor );
    res.setInfluencingBounds( bounds );
    return res;
  }

  public static Light makeDirectionalLight(Bounds bounds) {
    Color3f lColor1 = new Color3f( 0.7f, 0.7f, 0.7f );
    Vector3f lDir1  = new Vector3f( -1.0f, -1.0f, -1.0f );
    DirectionalLight res = new DirectionalLight( lColor1, lDir1 );
    res.setInfluencingBounds( bounds );
    return res;
  }

}
