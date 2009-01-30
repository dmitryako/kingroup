package tomsk.view.tomsk3d.universe;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import java.awt.image.BufferedImage;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 25/05/2007, 11:09:36
 */
public interface Tomsk3dUniverseI
{
  public BufferedImage getImage();

  public Canvas3D getCanvas();
  public void setCanvas(Canvas3D visibleCanvas);

  public Canvas3D getOffScreenCanvas();
  public void setOffScreenCanvas(Canvas3D offScreenCanvas);

  void addBranchGraph(BranchGroup rootBG);
}
