package tomsk.view.tomsk3d.utils;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.Sphere;
import tomsk.view.tomsk3d.Tomsk3dModel;
import tsvlib.project.ProjectLogger;

import javax.media.j3d.*;
import javax.vecmath.*;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 23/05/2007, 10:01:55
 */
public class Tomsk3dShapeFactory
{
  private static ProjectLogger log = ProjectLogger.getLogger(Tomsk3dShapeFactory.class);
  public final static int BOX_SIZE = 100;

  private final static float				LAND_WIDTH = 10.0f;
  private final static float				LAND_HEIGHT = 3.0f;
  private final static float				LAND_START = 0.0f;
  private final static float				LAND_END = -10.0f;
//  private static final int N_BOX_VERTICES = 16;

  public static BranchGroup makeLand2( Appearance app) {

    Point2f p = new Point2f(-1, -1);
    Point2f p2 = new Point2f(1, 1);
    GeometryArray geo = makeXYPlate(p, p2);
    Shape3D sh = new Shape3D( geo, app );
//    sh.setCapability(Shape3D.ALLOW_APPEARANCE_READ);
//    sh.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);

    Transform3D t3d = new Transform3D();
    t3d.rotX(-Math.PI/2);
//    Vector3d v3d = new Vector3d(new double[] {0.5, 1, 0.5});
//    t3d.set(v3d);
    TransformGroup tg = new TransformGroup(t3d);
    tg.addChild( sh );

    t3d = new Transform3D();
    Vector3d v3d = new Vector3d(new double[] {0, -1, 0});
    t3d.set(v3d);
    TransformGroup tg2 = new TransformGroup(t3d);
    tg2.addChild( tg );

    BranchGroup res = new BranchGroup( );
    res.addChild( tg2 );
    return res;

  }
  /**
   * creates a single Quad geometry with 4 TextureCoordinateMaps, for multitexture use.<br>
   * Dimension is scale*(2m , 1m)
   * @return quad geometry for multitexture use
   */
  public static GeometryArray makeXYPlate(Point2f p, Point2f p2) {
    float[] coord =    {p.x, p.y, 0, p2.x, p.y, 0, p2.x, p2.y, 0, p.x, p2.y,  0,  };  // vertex coordinates
//    double[] coord =    {-1, -1, 0,   1, -1, 0,     1, 1, 0,       -1, 1,  0,  };  // vertex coordinates
    float[] texCoords =	{0, 0,        1, 0,         1, 1,          0, 1    };
    int[] texCoordSetMap = {0,0,0,0}; // all texture units will use texCoords from unit 0
    Vector3f normal = new Vector3f( 0.0f,  0.0f,  1.0f);
    Vector3f[] normals = { normal,  normal,  normal,  normal} ;

    // create geometry  using GeometryInfo
    GeometryInfo gi = new GeometryInfo(GeometryInfo.QUAD_ARRAY);
    gi.setCoordinates(coord);
    gi.setNormals(normals);
    // preparing for multitexture
    // To get up to 4 TUS, it needs 4 sets of 2D texture
    gi.setTextureCoordinateParams(4, 2);
    gi.setTexCoordSetMap(texCoordSetMap);

    // this demo needs just 3 TUS, but geometry
    // is prepared for up to 4 TUS stages
    gi.setTextureCoordinates(0, texCoords);
    gi.setTextureCoordinates(1, texCoords);
    gi.setTextureCoordinates(2, texCoords);
    gi.setTextureCoordinates(3, texCoords);
    return gi.getGeometryArray();
  }

  public static Bounds makeBounds(Tomsk3dModel model)
  {
    double dist = 0.5 * model.getBoxSize();
    log.debug("dist=", dist);
    Point3d lower = new Point3d(-dist, -dist, -dist);
    log.debug("lower=", lower);
    Point3d upper = new Point3d(dist, dist, dist);
    log.debug("upper=", upper);
    return new BoundingBox( lower, upper );
//    return new BoundingSphere( new Point3d( 0.0,0.0,0.0 ), 100.0 );
  }

  public static BranchGroup makeLand( Appearance app)
  {
    QuadArray quadArray = new QuadArray( 4, GeometryArray.COORDINATES | GeometryArray.TEXTURE_COORDINATE_2 );
    float[] coord =	{
      -LAND_WIDTH, LAND_HEIGHT, 0,
      LAND_WIDTH, LAND_HEIGHT, 0,
      LAND_WIDTH, LAND_HEIGHT, LAND_START,
      -LAND_WIDTH, LAND_HEIGHT, LAND_START
    };
    float[] texArray = 	{	0, 0,
      1, 0,
      1, 1,
      0, 1
    };
    quadArray.setCoordinates( 0, coord, 0, 4 );
    quadArray.setTextureCoordinates( 0, 0, texArray, 0, 4 );
    Shape3D sh = new Shape3D( quadArray, app );
    BranchGroup res = new BranchGroup( );
    res.addChild( sh );
    return res;
  }

  public static BranchGroup makeDefaultShape() {
    BranchGroup res = new BranchGroup( );
    res.setCapability( Group.ALLOW_CHILDREN_EXTEND );
    res.setCapability( Group.ALLOW_CHILDREN_READ );
    res.setCapability( Group.ALLOW_CHILDREN_WRITE );

    res.addChild( makeCube( ) );

    Transform3D t3d = new Transform3D();
    Vector3d v3d = new Vector3d(new double[] {0.5, 1, 0.5});
    t3d.set(v3d);
    TransformGroup tg = new TransformGroup(t3d);
    tg.addChild(makeSphere( ));
    res.addChild(tg);
    return res;
  }

  public static BranchGroup makeCube( )
  {
    BranchGroup res = new BranchGroup( );
//    res.setCapability( BranchGroup.ALLOW_DETACH );
    res.addChild( new ColorCube( ) );
    res.setUserData( "Cube" );
    return res;
  }

  public static BranchGroup makeFrameBox( double boxSize, Color3f color  ) {
    Appearance app =  makeLineAppearance(color);
//    Shape3D sh = new Shape3D( makeBoxGeometry(boxSize), app );
    Shape3D sh = new Shape3D();
    sh.setGeometry(makeBoxGeometry(boxSize));
    sh.setAppearance(app);
    BranchGroup res = new BranchGroup( );
    res.addChild( sh );
    return res;
  }
  public static GeometryArray makeBoxGeometry( double boxSize)
  {
    double x = -boxSize/2;
    double y = -boxSize/2;
    double z = -boxSize/2;
    double x2 = x + boxSize;
    double y2 = y + boxSize;
    double z2 = z + boxSize;
    double[] coord = {
      x, y, z,   x, y, z2
      , x, y, z2,   x, y2, z2
      , x, y, z2,   x2, y, z2

      , x, y, z,   x, y2, z
      , x, y2, z,   x, y2, z2
      , x, y2, z,   x2, y2, z

      , x, y, z,   x2, y, z
      , x2, y, z,   x2, y2, z
      , x2, y, z,   x2, y, z2

      , x2, y2, z2,   x2, y2, z

      , x2, y2, z2,   x2, y, z2
      , x2, y2, z2,   x, y2, z2
    };
    LineArray res = new LineArray(coord.length, GeometryArray.COORDINATES | GeometryArray.COLOR_3);
    res.setCoordinates(0, coord);
    return res;
  }

  public static Appearance makeDefaultAppearance( ){
    Appearance res = new Appearance( );
    Color3f objColor = new Color3f( 1.0f, 0.7f, 0.8f );
    Color3f black = new Color3f( 0.0f, 0.0f, 0.0f );
    res.setMaterial( new Material( objColor, black, objColor, black, 80.0f ) );
    return res;
  }

  public static Appearance makeLineAppearance(Color3f color ) {
    LineAttributes la = new LineAttributes();
    // la.setLineWidth( LINEWIDTH );    // causes z-ordering bug
    // la.setLineAntialiasingEnable(true);
    Appearance res = new Appearance( );
    res.setLineAttributes(la);
//    Color3f black = new Color3f( 0.0f, 0.0f, 0.0f );
//    res.setMaterial( new Material( color, black, color, black, 80.0f ) );
    return res;
  }

  public static BranchGroup makeSphere( )
  {
    BranchGroup res = new BranchGroup( );
//    res.setCapability( BranchGroup.ALLOW_DETACH );
    Appearance app = makeDefaultAppearance();
    res.addChild( new Sphere( 1, app ) );
    res.setUserData( "Sphere" );
    return res;
  }
}
