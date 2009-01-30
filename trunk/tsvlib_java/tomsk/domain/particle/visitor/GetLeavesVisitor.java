package tomsk.domain.particle.visitor;
import tomsk.domain.particle.Particle;
import tomsk.domain.particle.ParticleGroup;
import tomsk.domain.particle.ParticleNode;
import tsvlib.project.ProjectLogger;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 11/05/2007, 16:48:24
 */
public class GetLeavesVisitor extends ParticleVisitor
{
  private static final ProjectLogger log = ProjectLogger.getLogger(GetLeavesVisitor.class);
  private ParticleGroup group;

  public void visit(Particle pl)
  {
    log.trace("visitLeaf");
    group.add(pl);
  }

  public ParticleGroup getLeaves(ParticleNode pn) {
    log.trace("getLeaves");
    group = new ParticleGroup();
    pn.accept(this);
    return group;
  }
}
