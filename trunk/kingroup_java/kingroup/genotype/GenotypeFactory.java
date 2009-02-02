package kingroup.genotype;
import javax.iox.LOG;
import javax.utilx.IntTreeSet;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 14, 2004, Time: 3:24:58 PM
 */
public class GenotypeFactory
  extends Genotype // THIS IS a hack to allow access to protected Genotype.setUniqueKey()
{
  private static GenotypeFactory instance_ = null;
  private int autoKey_ = 0;
  private final IntTreeSet keySet_ = new IntTreeSet();
  public static GenotypeFactory getInstance() {
    if (instance_ == null)
      instance_ = new GenotypeFactory();
    return instance_;
  }
  public static void init() {
    instance_ = null;
  }
  private GenotypeFactory() {
    super();
  }
  public Genotype makeGenotype() {
    Genotype res = new Genotype();
    setUniqueKey(res, autoKey_++);
    return res;
  }
  public Genotype replicateGenotype(Genotype from, String newId) {
    Genotype res = makeGenotype();
    from.shareAllelesWith(res);
    res.setId(newId);
    return res;
  }
  public void setUniqueKey(Genotype geno, int key) {
    if (!keySet_.addInt(key)) {
      String mssg = "This key=[" + key + "] exists already. Check calls to GenotypeFactiory.init().";
      LOG.throwError(this, mssg, "");
    }
    geno.setUniqueKey(key);
  }
}
