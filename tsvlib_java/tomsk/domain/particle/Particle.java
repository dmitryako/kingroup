package tomsk.domain.particle;
import tomsk.domain.particle.visitor.ParticleVisitor;
import tsvlib.project.ProjectLogger;

import javax.vecmath.Vector3d;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 11/05/2007, 16:15:32
 * see Leaf
 */
public class Particle implements ParticleNode
{
  private static final ProjectLogger log = ProjectLogger.getLogger(Particle.class);
  private Vector3d vel;
  private Vector3d coord;

  public Particle() {    
  }

  public void accept(ParticleVisitor pv) {
    log.trace("accept(ParticleVisitor)");
    pv.visit(this);
  }
  final public int getNumParticlesSLOW() {
    log.debug("getNumParticlesSLOW()=1");
    return 1;
  }

  public Vector3d getCoord()
  {
    return coord;
  }

  public void addCoordSLOW(Vector3d v) {
    log.debug("addCoord(", v);
    log.debug("before(", coord);
    coord.add(v);
    log.debug("after(", coord);
  }

  public void setCoord(Vector3d coord)
  {
    this.coord = coord;
  }
  public Vector3d getVel()
  {
    return vel;
  }

  public void setVel(Vector3d vel)
  {
    this.vel = vel;
  }

  public String toString() {
    return "coord=" + coord + ", vel=" + vel;
  }
}
