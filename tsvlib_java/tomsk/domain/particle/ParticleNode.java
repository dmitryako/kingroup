package tomsk.domain.particle;
import tomsk.domain.particle.visitor.ParticleVisitor;

import javax.vecmath.Vector3d;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 11/05/2007, 16:13:34
 */
public interface ParticleNode
{
  public void accept(ParticleVisitor pv);
  public int getNumParticlesSLOW();
  public void addCoordSLOW(Vector3d v);

}
