package tomsk.view.tomsk3d.universe;
import tsvlib.project.ProjectLogger;

import javax.media.j3d.Canvas3D;
import javax.media.j3d.GraphicsConfigTemplate3D;
import javax.media.j3d.ImageComponent;
import javax.media.j3d.ImageComponent2D;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 24/05/2007, 16:21:25
 */
public abstract class Tomsk3dUniverse implements Tomsk3dUniverseI
{
  private static ProjectLogger log = ProjectLogger.getLogger(Tomsk3dUniverse2.class);
  private Canvas3D offScreenCanvas; //  *	an offscreen Canvas3D that is used to  *	perform screen captures
  private Canvas3D canvas;

  private ImageComponent2D 		imageComponent;
  private static final int		OFF_SCREEN_WIDTH = 400;
  private static final int		OFF_SCREEN_HEIGHT = 400;

  public BufferedImage getImage()
  {
    offScreenCanvas.renderOffScreenBuffer( );
    offScreenCanvas.waitForOffScreenRendering( );
    log.trace( "Rendered to offscreen" );
    return imageComponent.getImage();
  }
  public Canvas3D getCanvas()   {     return canvas;        }
  public void setCanvas(Canvas3D visibleCanvas)     {           this.canvas = visibleCanvas;   }

  public Canvas3D getOffScreenCanvas()      {        return offScreenCanvas;     }
  public void setOffScreenCanvas(Canvas3D offScreenCanvas)   {      this.offScreenCanvas = offScreenCanvas;}

//  public Locale getLocale()   {     return locale;    }
//  public void setLocale(Locale locale)    {     this.locale = locale;        }


  protected Canvas3D createOffscreenCanvas3D( )
  {
    Canvas3D res = createCanvas3D(true);
    res.getScreen3D( ).setSize( OFF_SCREEN_WIDTH, OFF_SCREEN_HEIGHT );
    res.getScreen3D( ).setPhysicalScreenHeight( 0.0254/90 * OFF_SCREEN_HEIGHT );
    res.getScreen3D( ).setPhysicalScreenWidth( 0.0254/90 * OFF_SCREEN_WIDTH );

    RenderedImage renderedImage = new BufferedImage( OFF_SCREEN_WIDTH, OFF_SCREEN_HEIGHT, BufferedImage.TYPE_3BYTE_BGR );
    imageComponent = new ImageComponent2D( ImageComponent.FORMAT_RGB8, renderedImage );
    imageComponent.setCapability( ImageComponent2D.ALLOW_IMAGE_READ );
    res.setOffScreenBuffer( imageComponent );
    return res;
  }

  protected Canvas3D createCanvas3D( boolean offscreen )
  {
    GraphicsConfigTemplate3D template = new GraphicsConfigTemplate3D( );
    template.setSceneAntialiasing( GraphicsConfigTemplate.PREFERRED );
    GraphicsDevice gd[] = GraphicsEnvironment.getLocalGraphicsEnvironment( ).getScreenDevices( );
    Canvas3D res = new Canvas3D( gd[0].getBestConfiguration( template ), offscreen );
    res.setSize( 500, 500 );
    return res;
  }

  public Canvas3D rebuild(Canvas3D from)
  {
    MouseListener[] mouseLArr = null;
    MouseMotionListener[] motionLArr = null;
    if (from != null) {
      mouseLArr = from.getMouseListeners();
      motionLArr = from.getMouseMotionListeners();
    }

    Canvas3D res = createCanvas3D(false);
    if (mouseLArr != null) {
      for (int i = 0; i < mouseLArr.length; i++) {
        res.addMouseListener(mouseLArr[i]);
        log.trace("mouseLArr[i]=", mouseLArr[i]);
      }
    }
    if (motionLArr != null) {
      for (int i = 0; i < motionLArr.length; i++) {
        res.addMouseMotionListener(motionLArr[i]);
        log.trace("motionLArr[i]=", motionLArr[i]);
      }
    }
    return res;

  }

}
