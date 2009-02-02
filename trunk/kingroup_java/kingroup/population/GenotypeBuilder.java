package kingroup.population;
import kingroup.KinGroupError;
import kingroup.genetics.OldAlleleFreq;
import kingroup.genotype.Allele;
import kingroup.genotype.Genotype;
import kingroup.genotype.GenotypeFactory;
import kingroup.genotype.Locus;

import javax.iox.LOG;
import javax.utilx.RandomSeed;
import tsvlib.project.ProjectLogger;

public class GenotypeBuilder
{
  private static ProjectLogger log = ProjectLogger.getLogger(GenotypeBuilder.class.getName());
  final private static String FULL_SIB_PREFIX_ID = "fs";
  private static int NOT_USED = -1;
  private static RandomSeed random_ = RandomSeed.getInstance();
  private OldAlleleFreq freq_;
  private int uniqueId_ = NOT_USED;
  private int uniqueGroupId_ = NOT_USED;
  final public int makeId(int from) {
    return (hasUniqueId() ? nextUniqueId() : from);
  }
  final public int nextUniqueId() {
    return uniqueId_++;
  }
  final private boolean hasUniqueId() {
    return uniqueId_ != NOT_USED;
  }
  final public void setUniqueId(boolean b) {
    uniqueId_ = (b ? 1 : NOT_USED);
  }

//   final public int makeGroupId() {
//      return (hasUniqueGroupId() ? nextUniqueGroupId(): NOT_USED);
//   }
  final public int makeGroupId(int from) {
    return (hasUniqueGroupId() ? nextUniqueGroupId() : from);
  }
  final public int makeGroupId() {
    return nextUniqueGroupId();
  }
  final private int nextUniqueGroupId() {
    return uniqueGroupId_++;
  }
  final private boolean hasUniqueGroupId() {
    return uniqueGroupId_ != NOT_USED;
  }
  final public void setUniqueGroupId(boolean b) {
    uniqueGroupId_ = (b ? 1 : NOT_USED);
  }
  public GenotypeBuilder(OldAlleleFreq freq) {
    freq_ = freq;
    setUniqueId(true);
    setUniqueGroupId(true);
  }
  final public OldAlleleFreq getAlleleFreq() {
    return freq_;
  }
  final public Genotype makeOffspring(int num_alleles
    , Genotype parent, Genotype parent2) {
    if (num_alleles != 2) {
      String mssg = "num_alleles != 2";
      LOG.error(this, mssg, "");
      throw new KinGroupError(mssg);
    }
    int num_loci = freq_.getNumLoci();
    Genotype geno = GenotypeFactory.getInstance().makeGenotype();
    for (int L = 0; L < num_loci; L++) {
      Locus parentL = parent.getLocus(L);
      Locus parentL2 = parent2.getLocus(L);
      Locus newLocus = new Locus();
      newLocus.setId(freq_.getLocus(L));              // set its getId
      geno.add(newLocus);                     // store it into geno
      newLocus.add(getRandomAlleleFromLocus(parentL));
      newLocus.add(getRandomAlleleFromLocus(parentL2));
    }
    geno.setHaploid(num_alleles == 1);
    geno.setDiploid(num_alleles == 2);
    return geno;
  }
  final public PopGroup makeFullSibGroup(int groupSize
    , String groupId
    , Genotype parent
    , Genotype parent2) {
    PopGroup group = new PopGroup();
    group.setId(groupId);
    for (int i = 0; i < groupSize; i++) {
      Genotype geno = makeOffspring(Locus.DIPLOID, parent, parent2);
      geno.setId(FULL_SIB_PREFIX_ID + makeId(i + 1));
      geno.setGroupId(groupId);
//      log.info("fullsib = " + geno);
      
      group.addGenotype(geno);
    }
    return group;
  }
  final public PopGroup makeHalfSibGroup(int groupSize
    , String groupId
    , PopGroup parents
    , Genotype parent) {
    String prefixParent = parent.getId() + "m";
    String prefixId = parent.getId() + "h";
    PopGroup group = new PopGroup();
    group.setId(groupId);
    for (int i = 0; i < groupSize; i++) { // i - an individual in a group
      Genotype parent2 = makeRandomGenotypeInOrder(Locus.DIPLOID);
      if (parents != null) {
        parent2.setId(prefixParent + makeId(i + 1));
        parent2.setGroupId(parents.getId());
        parents.addGenotype(parent2);
      }
      Genotype geno = makeOffspring(Locus.DIPLOID, parent, parent2);
      geno.setId(prefixId + (i + 1));
      geno.setGroupId(groupId);
      group.addGenotype(geno);
    }
    return group;
  }
  final public Genotype makeRandomGenotypeInOrder(int num_alleles) {
    int num_loci = freq_.getNumLoci();
    Genotype geno = GenotypeFactory.getInstance().makeGenotype();
    for (int L = 0; L < num_loci; L++) {
      Locus newLocus = new Locus();
      newLocus.setId(freq_.getLocus(L));              // set its getId
      geno.add(newLocus);                     // store it into geno
      for (int a = 0; a < num_alleles; a++)
        newLocus.add(getRandomAllele(L));   // store randomly created getAllele
    }
    geno.setHaploid(num_alleles == 1);
    geno.setDiploid(num_alleles == 2);
    return geno;
  }
  final public Genotype getRandomGenotypeFrom(Genotype x
    , double rm, double rp
    , boolean usePaternalHaploid
    , int num_alleles) {
    if (x == null)
      return null;
    Genotype geno = null;
    double r = rm;
    if (usePaternalHaploid)
      r = rp;
    if (x.isHaploid() && num_alleles == Locus.HAPLOID)    // X,Y - HAPLOIDs
      geno = getRandomHapFromHap(x, r);       // HAPLOID from HAPLOID
    else if (x.isHaploid() && num_alleles == 2)  // X - HAPLOID
      geno = getRandomDipFromHap(x, r, usePaternalHaploid);  // DIPLOID from HAPLOID
    else if (x.isDiploid() && num_alleles == 1)  // Y - HAPLOID  //X - DIPLOID
      geno = getRandomHapFromDip(x, r, usePaternalHaploid); // HAPLOID from DIPLOID
    else if (x.isDiploid() && num_alleles == 2)
      geno = getRandomDipFromDip(x, rm, rp);     // DIPLOID from DIPLOID
    else
      return null;
    return geno;
  }
  private Genotype getRandomHapFromHap(Genotype x, double r) {
//      LOG.trace(this, "getRandomHapFromHap(", x);
//      LOG.trace(this, "               , r=", r);
    Genotype geno = GenotypeFactory.getInstance().makeGenotype();
    int alleleIdx = 0;
    for (int i = 0; i < x.getNumLoci(); i++) {
      Locus xLocus = x.getLocus(i);                          // for each get
      Locus yLocus = new Locus();   // create a haploid get
      yLocus.setId(xLocus);                              // keep the same getId
      Allele allele = getRandomAlleleFrom(i, r, xLocus, alleleIdx);
      yLocus.add(allele);
      geno.add(yLocus);
    }
    geno.setHaploid(true);
    return geno;
  }
  // DIPLOID from HAPLOID
  private Genotype getRandomDipFromHap(Genotype x
    , double r, boolean usePaternal) {
//      LOG.trace(this, "getRandomDipFromHap(", x);
//      LOG.trace(this, "               , r=", r);
//      LOG.trace(this, "      , usePaternal=", usePaternal);
    int alleleIdx = 0;
    Genotype geno = GenotypeFactory.getInstance().makeGenotype();
    for (int i = 0; i < x.getNumLoci(); i++) {
      Locus xLocus = x.getLocus(i);                          // for each get
      Locus yLocus = new Locus();   // create a diploid get
      yLocus.setId(xLocus);                              // keep the same getId
      if (usePaternal) {
        yLocus.add(getRandomAllele(i));           // MATERNAL first!!!!!!
        yLocus.add(getRandomAlleleFrom(i, r, xLocus, alleleIdx));
//            yLocus.set(Locus.MATERNAL_IDX, getRandomAllele(i));           // MATERNAL first
//            yLocus.set(Locus.PATERNAL_IDX, getRandomAlleleFrom(i, r, xLocus, alleleIdx));
      } else {
        yLocus.add(getRandomAlleleFrom(i, r, xLocus, alleleIdx)); // MATERNAL first
        yLocus.add(getRandomAllele(i));
//            yLocus.set(Locus.MATERNAL_IDX, getRandomAlleleFrom(i, r, xLocus, alleleIdx)); // MATERNAL first
//            yLocus.set(Locus.PATERNAL_IDX, getRandomAllele(i));
      }
      geno.add(yLocus);
    }
    geno.setDiploid(true);
    return geno;
  }
  // HAPLOID from DIPLOID
  private Genotype getRandomHapFromDip(Genotype x
    , double r, boolean usePaternal) {
//      LOG.trace(this, "getRandomHapFromDip(", x);
//      LOG.trace(this, "               , r=", r);
//      LOG.trace(this, "      , usePaternal=", usePaternal);
    Genotype geno = GenotypeFactory.getInstance().makeGenotype();
    for (int i = 0; i < x.getNumLoci(); i++) {
      Locus xLocus = x.getLocus(i);                          // for each get
      Locus yLocus = new Locus();   // create a haploid get
      yLocus.setId(xLocus);                              // keep the same getId
      if (usePaternal)
        yLocus.add(getRandomAlleleFrom(i, r, xLocus, Locus.PATERNAL_IDX));
      else
        yLocus.add(getRandomAlleleFrom(i, r, xLocus, Locus.MATERNAL_IDX));
      geno.add(yLocus);
    }
    geno.setHaploid(true);
    return geno;
  }
  // DIPLOID from DIPLOID
  private Genotype getRandomDipFromDip(Genotype x, double rm, double rp) {
//      LOG.trace(this, "getRandomDipFromDip(", x);
//      LOG.trace(this, "               , rm=", rm);
//      LOG.trace(this, "               , rp=", rp);
    Genotype geno = GenotypeFactory.getInstance().makeGenotype();
    for (int i = 0; i < x.getNumLoci(); i++) {
      Locus xLocus = x.getLocus(i);                          // for each get
      Locus yLocus = new Locus();   // create a diploid get
      yLocus.setId(xLocus);                              // keep the same getId
      yLocus.add(getRandomAlleleFrom(i, rm, xLocus, Locus.MATERNAL_IDX)); // MATERNAL first!!!
      yLocus.add(getRandomAlleleFrom(i, rp, xLocus, Locus.PATERNAL_IDX));
      geno.add(yLocus);
    }
    geno.setDiploid(true);
    return geno;
  }

  // PRIVATE---
  private Allele getRandomAlleleFrom(int locusIdx, double r, Locus from, int alleleIdx) {
    LOG.trace(this, "getRandomAlleleFrom(locusIdx=", locusIdx);
    LOG.trace(this, "                 , alleleIdx=", alleleIdx);
    if (r == 0.0)
      return getRandomAllele(locusIdx);
    double x = random_.nextDouble();
    LOG.trace(this, "random [0,1]=", x);
    if (x <= r)
      return from.get(alleleIdx);
    return getRandomAllele(locusIdx);
  }
  // PRECOND: Allele frequencies must setLikeMainView up to 1.0!!!
  private Allele getRandomAllele(int locusIdx) {
    double x = random_.nextDouble();
//      LOG.trace(this, "random value [0,1) x = ", x);
    double prob = 0;
    Locus locus = freq_.getLocus(locusIdx);
    for (int a = 0; a < locus.size(); a++) {
      Allele allele = locus.get(a);
      prob += allele.getProb();
      if (x >= prob)
        continue;
//         LOG.trace(this, "getAllele = ", getAllele);
      return allele;
    }
    return null;
  }
  private Allele getRandomAlleleFromLocus(Locus locus) {
    switch (locus.size()) {
      case 0:
        return null;
      case 1:
        return locus.get(0);
      default:
        return locus.get(random_.nextInt(locus.size()));
    }
  }
}
