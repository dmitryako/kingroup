package tomsk.domain.particle.visitor;
import tomsk.domain.particle.Particle;
import tomsk.domain.particle.ParticleNode;
import tsvlib.project.ProjectLogger;

import javax.vecmath.Vector3d;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 16/05/2007, 10:58:32
 */
public class AvrCoordVisitor extends ParticleVisitor
{
  private static final ProjectLogger log = ProjectLogger.getLogger(AvrCoordVisitor.class);
  private Vector3d coord;

  public void visit(Particle pl)
  {
    log.trace("visit");
    coord.add(pl.getCoord());
  }

  public Vector3d calcCoord(ParticleNode pn) {
    log.trace("calcCoord");
    coord = new Vector3d();
    int n = pn.getNumParticlesSLOW();
    if (n == 0)
      return coord;
    pn.accept(this);
    log.debug("before=", coord);
    coord.scale(1./n);
    log.debug("after=", coord);
    return coord;
  }
}

