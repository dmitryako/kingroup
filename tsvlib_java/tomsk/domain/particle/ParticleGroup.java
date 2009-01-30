package tomsk.domain.particle;
import tomsk.domain.particle.visitor.ParticleVisitor;
import tsvlib.project.ProjectLogger;

import javax.vecmath.Vector3d;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 11/05/2007, 16:13:55
 * GoF COMPOSITE and VISITOR patterns
 */
public class ParticleGroup extends ArrayList<ParticleNode>
  implements ParticleNode
{
  private static final ProjectLogger log = ProjectLogger.getLogger(ParticleGroup.class);

  public void accept(ParticleVisitor pv) {
    log.trace("accept(ParticleVisitor)");
    pv.visitGroup(this);
  }
  final public int getNumParticlesSLOW() {
    int res = 0;
    for (Iterator<ParticleNode> it = this.iterator(); it.hasNext(); ) {
      res += it.next().getNumParticlesSLOW();
    }
    log.debug("getNumParticlesSLOW()=res=", res);
    return res;
  }

  public String toString() {
    String res = "group:\n";
    for (Iterator<ParticleNode> it = this.iterator(); it.hasNext(); ) {
      res += (it.next().toString() + "\n");
    }
//    return new ToStringVisitor().toString(this);
    return res;
  }

  public void addCoordSLOW(Vector3d v) {
    for (Iterator<ParticleNode> it = this.iterator(); it.hasNext(); ) {
      it.next().addCoordSLOW(v);
    }
  }
}
