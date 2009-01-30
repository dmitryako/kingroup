package tomsk.domain.particle;
import tomsk.io.pdb.PDBAtom;
import tomsk.io.pdb.PDBModel;
import tsvlib.project.ProjectLogger;

import java.util.Iterator;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 15/05/2007, 13:36:03
 */
public class ParticleGroupFactory
{
  private static ProjectLogger log = ProjectLogger.getLogger(ParticleGroupFactory.class);
  public static ParticleGroup makeFromPDB(PDBModel model)
  {
    ParticleGroup res = new ParticleGroup();
    for (Iterator<PDBAtom> it = model.iterator(); it.hasNext(); ){
      PDBAtom a = it.next();
      Particle p = ParticleFactory.makeFrom(a);
      res.add(p);
    }
    log.debug("makeFromPDB()=", res);
    return res;
  }
}
