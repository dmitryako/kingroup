package tomsk.domain.particle.visitor;
import tomsk.domain.particle.Particle;
import tomsk.domain.particle.ParticleGroup;
import tomsk.domain.particle.ParticleNode;
import tsvlib.project.ProjectLogger;

import java.util.Iterator;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 11/05/2007, 16:45:58
 * GoF VISITOR pattern
 */
public abstract class ParticleVisitor
{
  private static final ProjectLogger log = ProjectLogger.getLogger(ParticleVisitor.class);
  public void visitGroup(ParticleGroup pg)
  {
    log.trace("visitGroup(ParticleGroup)");
    for (Iterator<ParticleNode> it = pg.iterator(); it.hasNext(); ) {
      it.next().accept(this);
    }
  }
  abstract public void visit(Particle pg);
}
