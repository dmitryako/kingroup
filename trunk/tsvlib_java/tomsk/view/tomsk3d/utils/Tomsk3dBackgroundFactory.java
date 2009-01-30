package tomsk.view.tomsk3d.utils;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.image.TextureLoader;
import tomsk.view.tomsk3d.Tomsk3dModel;
import tsvlib.project.ProjectLogger;

import javax.media.j3d.*;
import javax.vecmath.Color3f;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 18/05/2007, 15:52:13
 */
public class Tomsk3dBackgroundFactory
{
  private static ProjectLogger log = ProjectLogger.getLogger(Tomsk3dBackgroundFactory.class);
  private static final int IMAGE_WIDTH = 16;
  private static final int IMAGE_HEIGHT = 16;


  public static BranchGroup makeBackground(Tomsk3dModel model, Bounds bounds) {
    if (model.getBackgroundIdx() == Tomsk3dModel.BACKGROUND_WHITE)
      return makeWhite(bounds, model);

    if (model.getBackgroundIdx() == Tomsk3dModel.BACKGROUND_BLACK)
      return makeBlack(bounds);

    if (model.getBackgroundIdx() == Tomsk3dModel.BACKGROUND_SKY)
      return makeSky(bounds);

    if (model.getBackgroundIdx() == Tomsk3dModel.BACKGROUND_SPACE)
      return makeSpace(bounds);

    if (model.getBackgroundIdx() == Tomsk3dModel.BACKGROUND_LAB)
      return makeLab(bounds);

    if (model.getBackgroundIdx() == Tomsk3dModel.BACKGROUND_CHESS)
      return makeChess(bounds);

    if (model.getBackgroundIdx() == Tomsk3dModel.BACKGROUND_TSV)
      return makeTsv(bounds);

    return makeGray(bounds);   // DEFAULT
  }

  /**
   * White-to-Blue gradient image
   * @param W
   * @param H
   */
  public static BufferedImage makeSkyImage(int W, int H) {
    BufferedImage res = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);

//    Text2D t;
//    http://www.exampledepot.com/egs/java.awt.image/DrawOnImage.html?l=rel
    Graphics2D g2 = res.createGraphics();
    GradientPaint gp = new GradientPaint(0, 0, Color.BLUE, 0, H-1, Color.WHITE);
    g2.setPaint(gp);
    g2.fill (new Rectangle(W, H));
//    g2.fill (new Ellipse2D.Double(0, 0, 100, 50));
    return res;
  }

  public static BufferedImage makeSpaceImage(int W, int H) {
    BufferedImage res = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
//    http://www.exampledepot.com/egs/java.awt.image/DrawOnImage.html?l=rel
    Graphics2D g2 = res.createGraphics();
    GradientPaint gp = new GradientPaint(0, 0, Color.BLACK, 0, H-1, Color.BLUE);
    g2.setPaint(gp);
    g2.fill (new Rectangle(W, H));
    return res;
  }

  public static BufferedImage makeChessImage(int W, int H) {
    BufferedImage res = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
//    http://www.exampledepot.com/egs/java.awt.image/DrawOnImage.html?l=rel
    Graphics2D g2 = res.createGraphics();

//    GradientPaint gp = new GradientPaint(0, 0, Color.BLACK, 0, H-1, Color.BLUE);
    g2.setPaint(Color.BLACK);
    g2.fill (new Rectangle(W/2, H/2));
    g2.fill (new Rectangle(new Point(W/2, H /2), new Dimension(W - W / 2, H - H /2)));

    g2.setPaint(Color.WHITE);
    g2.fill (new Rectangle(new Point(0, H /2), new Dimension(W / 2, H - H /2)));
    g2.fill (new Rectangle(new Point(W/2, 0), new Dimension(W - W / 2, H /2)));

    return res;
  }

  public static BufferedImage makeTsvImage(int W, int H) {
    BufferedImage res = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2 = res.createGraphics();
    GradientPaint gp = new GradientPaint(0, 0, Color.BLUE, 0, H /2, Color.WHITE);
    g2.setPaint(gp);
    g2.fill (new Rectangle(W, H /2));

    gp = new GradientPaint(0, H /2, Color.WHITE, 0, H-1, Color.CYAN);
    g2.setPaint(gp);
    g2.fill (new Rectangle(new Point(0, H /2), new Dimension(W, H - H /2)));
    return res;
  }

  public static BranchGroup makeWhite(Bounds bounds, Tomsk3dModel model)
  {
    Background back = new Background( new Color3f( 1.0f, 1.0f, 1.0f ) );
    back.setApplicationBounds( bounds );

    BranchGroup res = new BranchGroup( );
    res.setCapability( BranchGroup.ALLOW_DETACH);
    res.addChild( back );
    BranchGroup box = Tomsk3dShapeFactory.makeFrameBox(model.getBoxSize(), new Color3f(0, 0, 0));
    res.addChild( box );
    return res;
  }

  public static BranchGroup makeGray( Bounds bounds )
  {
    Background back = new Background( new Color3f( 0.9f, 0.9f, 0.9f ) );
    back.setApplicationBounds( bounds );

    BranchGroup rootBG = new BranchGroup( );
    rootBG.setCapability( BranchGroup.ALLOW_DETACH);
    rootBG.addChild( back );
    return rootBG;
  }

  public static BranchGroup makeBlack( Bounds bounds )
  {
    Background back = new Background( new Color3f( 0.0f, 0.0f, 0.0f ) );
    back.setApplicationBounds( bounds );

    BranchGroup res = new BranchGroup( );
    res.setCapability( BranchGroup.ALLOW_DETACH);
    res.addChild( back );
    return res;
  }

  public static BranchGroup makeSky( Bounds bounds )
  {
    return makeFromImage(bounds, makeSkyImage(IMAGE_WIDTH, IMAGE_HEIGHT));
  }

  public static BranchGroup makeSpace( Bounds bounds )
  {
    return makeFromImage(bounds, makeSpaceImage(IMAGE_WIDTH, IMAGE_HEIGHT));
  }

  public static BranchGroup makeTsv( Bounds bounds )
  {
    return makeFromImage(bounds, makeTsvImage(IMAGE_WIDTH, IMAGE_HEIGHT));
  }

  public static Appearance makeAppearence(BufferedImage img ) {
    Appearance res = new Appearance( );
    Texture tex = new TextureLoader(img, "RGB").getTexture( );
    res.setTexture( tex );
    return res;
  }
  public static BranchGroup makeFromImage( Bounds bounds, BufferedImage img )
  {
    Appearance app = makeAppearence(img);

    // create the Sphere geometry with radius 1.0
    // we tell the Sphere to generate texture coordinates
    // to enable the texture image to be rendered
    // and because we are *inside* the Sphere we have to generate
    // Normal coordinates inwards or the Sphere will not be visible.

//    Sphere sphere = new Sphere( 1.0f, 	Primitive.GENERATE_TEXTURE_COORDS |
//      Primitive.GENERATE_NORMALS_INWARD, app );
//    Box sphere = new Box(1.0f, 1.0f, 1.0f, 	Primitive.GENERATE_TEXTURE_COORDS |
//      Primitive.GENERATE_NORMALS_INWARD, app );
    Cylinder prim = new Cylinder(1.0f, 1.0f, 	Primitive.GENERATE_TEXTURE_COORDS |
      Primitive.GENERATE_NORMALS_INWARD, app );

    BranchGroup geometryBG = new BranchGroup( );
    geometryBG.addChild( prim );

//    Background back = new Background( new Color3f( 0.0f, 0.0f, 0.0f ) );
    Background back = new Background();
    back.setApplicationBounds( bounds );
    back.setGeometry( geometryBG );

    BranchGroup res = new BranchGroup( );
    res.setCapability( BranchGroup.ALLOW_DETACH);
    res.addChild( back );
    return res;
  }
  public static BranchGroup makeFromImage2( Bounds bounds, BufferedImage img )
  {
    Appearance app = new Appearance( );
    Texture tex = new TextureLoader(img, "RGB").getTexture( );
    app.setTexture( tex );
    BranchGroup res = Tomsk3dShapeFactory.makeLand(app);
    res.setCapability( BranchGroup.ALLOW_DETACH);
    return res;
  }

  public static BranchGroup makeLab( Bounds bounds )
  {
    return makeFromImage(bounds, makeSkyImage(IMAGE_WIDTH, IMAGE_HEIGHT));
  }

  public static BranchGroup makeChess( Bounds bounds )
  {
//    BranchGroup res = new BranchGroup( );

//    Appearance app = Tomsk3dShapeFactory.makeAppearance();
    Appearance app = makeAppearence(makeChessImage(IMAGE_WIDTH, IMAGE_HEIGHT));

//    BranchGroup res = Tomsk3dShapeFactory.makeLand(app);
    BranchGroup res = Tomsk3dShapeFactory.makeLand2(app);
    res.setCapability( BranchGroup.ALLOW_DETACH);

    res.addChild( makeFromImage(bounds, makeTsvImage(IMAGE_WIDTH, IMAGE_HEIGHT)));
//    res.addChild( makeFromImage2(bounds, makeChessImage(IMAGE_WIDTH, IMAGE_HEIGHT)));

    res.addChild(Tomsk3dShapeFactory.makeFrameBox(2.5, new Color3f(0, 0, 0)));

    return res;
  }



}
