package tomsk.view.theme;
import tomsk.domain.particle.Particle;
import tomsk.domain.particle.ParticleGroup;
import tomsk.domain.particle.ParticleNode;
import tomsk.domain.particle.visitor.ParticleVisitor;
import tsvlib.project.ProjectLogger;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 14/05/2007, 17:22:57
 */
public class SpheresThemeFactory extends ParticleVisitor
{
  private static final ProjectLogger log = ProjectLogger.getLogger(SpheresThemeFactory.class);
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
