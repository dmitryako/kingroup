package tomsk.domain.particle;
import tomsk.io.pdb.PDBAtom;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 15/05/2007, 13:48:12
 */
public class ParticleFactory
{
  public static Particle makeFrom(PDBAtom a)
  {
    Particle res = new Particle();
    res.setCoord(a.getCoord());
    return res;
  }
}
