package tomsk.domain;
import pattern.ucm.UCController;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 16/04/2007, 13:34:31
 */
public class UCCalcNextFrame implements UCController
{
  private ParticleArrGeometry geometry;
  public UCCalcNextFrame(ParticleArrGeometry geometry)
  {
    this.geometry = geometry;
  }

  public boolean run()
  {
    geometry.updateData(new ParticleArrGeometryUpdater());
    return true;
  }
}
