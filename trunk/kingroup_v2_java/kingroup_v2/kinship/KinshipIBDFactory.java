package kingroup_v2.kinship;
import javax.utilx.RandomSeed;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/10/2005, Time: 11:57:40
 */
public class KinshipIBDFactory
{
  public static KinshipIBD[] makeAll(KinshipIBDComplex hypo) {
    if (!hypo.getComplex()) {
      KinshipIBD[] res = new KinshipIBD[1];
      res[0] = new KinshipIBD(hypo.getRm(), hypo.getRp());
      return res;
    }
    int nRms = hypo.getNumRms();
    if (hypo.getRm() == hypo.getRmMax())
      nRms = 1;
    int nRps = hypo.getNumRps();
    if (hypo.getRp() == hypo.getRpMax())
      nRps = 1;

    double stepRm = 1;
    if (nRms > 1)
      stepRm = (hypo.getRmMax() - hypo.getRm()) / (nRms - 1);
    double stepRp = 1;
    if (nRps > 1)
      stepRp = (hypo.getRpMax() - hypo.getRp()) / (nRps - 1);

    KinshipIBD[] res = new KinshipIBD[nRms * nRps];
    int storeIdx = 0;
    for (int m = 0; m < nRms; m++) {
      double rm = hypo.getRm() + m * stepRm;
      for (int p = 0; p < nRps; p++) {
        double rp = hypo.getRp() + p * stepRp;
        res[storeIdx++] = new KinshipIBD(rm, rp);
      }
    }
    return res;
  }

  public static KinshipIBD[] remove(KinshipIBD[] nullPairs, KinshipIBD excludePair) {
    KinshipIBDArr arr = new KinshipIBDArr();
    for (KinshipIBD ibd : nullPairs) {
      if (!ibd.equals(excludePair))
        arr.add(ibd);
    }
    return arr.toArray();
  }

  public static KinshipIBDComplex makeUnrelated()
  {
    KinshipIBDComplex res = new KinshipIBDComplex();
    res.loadNullDefault();
    res.setComplex(false);
    res.setRm(0);
    res.setRp(0);
    return res;
  }

  public static KinshipIBDComplex makeFullSib()
  {
    KinshipIBDComplex res = new KinshipIBDComplex();
    res.loadPrimDefault();
    res.setComplex(false);
    res.setRm(0.5);
    res.setRp(0.5);
    return res;
  }
  public static KinshipIBDComplex makeRand()
  {
    KinshipIBDComplex res = new KinshipIBDComplex();
    res.loadPrimDefault();
    res.setComplex(false);
    RandomSeed rand = RandomSeed.getInstance();
    res.setRm(rand.nextDouble(0, 1));
    res.setRp(rand.nextDouble(0, 1));
    return res;
  }
  public static KinshipIBDComplex makeHalfSib()
  {
    KinshipIBDComplex res = new KinshipIBDComplex();
    res.loadPrimDefault();
    res.setComplex(false);
    res.setRm(0.5);
    res.setRp(0);
    return res;
  }
  public static KinshipIBDComplex makeParentOffspring()
  {
    KinshipIBDComplex res = new KinshipIBDComplex();
    res.loadPrimDefault();
    res.setComplex(false);
    res.setRm(1);
    res.setRp(0);
    return res;
  }

  public static boolean find(KinshipIBDComplex ibd, KinshipIBD[] arr)
  {
    for (KinshipIBD v: arr){
      if (v.equals(ibd))
        return true;
    }
    return false;
  }

}
