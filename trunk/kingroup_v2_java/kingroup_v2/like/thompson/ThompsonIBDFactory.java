package kingroup_v2.like.thompson;
import kingroup_v2.kinship.KinshipIBD;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/03/2006, Time: 10:35:26
 */
public class ThompsonIBDFactory
{
  public static ThompsonIBD[] remove(ThompsonIBD[] nullPairs, ThompsonIBD excludePair) {
    ThompsonIBDArr arr = new ThompsonIBDArr();
    for (ThompsonIBD ibd : nullPairs) {
      if (!ibd.equals(excludePair))
        arr.add(ibd);
    }
    return arr.toArray();
  }
  public static ThompsonIBD makeFullSib() {
    ThompsonIBD res = new ThompsonIBD();
    res.set(res.K_0, 0.25);
    res.set(res.K_1, 0.5);
    res.set(res.K_2, 0.25);
    return res;
  }
  public static ThompsonIBD makeUnrelated() {
    ThompsonIBD res = new ThompsonIBD();
    res.set(res.K_0, 1);
    res.set(res.K_1, 0);
    res.set(res.K_2, 0);
    return res;
  }

  public static ThompsonIBD makeValid(double k1, double k2)
  {
    if (k1 > 1)
      k1 = 1;
    if (k2 > 1)
      k2 = 1;
    if (k1 + k2 > 1)
      k2 = 1 - k1;
    double k0 = 1. - k1 - k2;
    if (k0 < 0)
      k0 = 0;
    ThompsonIBD ibd = new ThompsonIBD();
    ibd.set(ibd.K_0, k0);
    ibd.set(ibd.K_1, k1);
    ibd.set(ibd.K_2, k2);
    return ibd;
  }

  public static ThompsonIBD makeFrom(KinshipIBD kinIBD)
  {
    ThompsonIBD res = new ThompsonIBD();
    double Rm = kinIBD.getRm();
    double Rp = kinIBD.getRp();
    double k1 = Rm * (1. - Rp) + Rp * (1. - Rm);
    double k2 = Rm * Rp;
    res.set(ThompsonIBD.K_1, k1);
    res.set(ThompsonIBD.K_2, k2);
    res.set(ThompsonIBD.K_0, 1. - k1 - k2);
    return res;
  }
}
