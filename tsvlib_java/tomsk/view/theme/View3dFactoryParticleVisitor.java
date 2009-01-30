package tomsk.view.theme;
import tomsk.domain.particle.Particle;
import tomsk.domain.particle.ParticleNode;
import tomsk.domain.particle.visitor.ParticleVisitor;
import tsvlib.project.ProjectLogger;

import javax.media.j3d.BranchGroup;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 14/05/2007, 17:22:18
 */
public class View3dFactoryParticleVisitor extends ParticleVisitor
{
  private static final ProjectLogger log = ProjectLogger.getLogger(View3dFactoryParticleVisitor.class);
  protected BranchGroup group;
  protected View3dFactory factory;

  public View3dFactoryParticleVisitor(View3dFactory factory) {
    this.factory = factory;
  }

  public void visit(Particle pl)
  {
    log.trace("visitLeaf");
    BranchGroup view = factory.makeView3d(pl);
    group.addChild(view);
  }

  public BranchGroup makeView3d(ParticleNode pn) {
    log.trace("make3dBG");
    group = new BranchGroup();
    pn.accept(this);
    return group;
  }

}
