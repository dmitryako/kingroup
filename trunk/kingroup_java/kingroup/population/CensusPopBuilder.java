package kingroup.population;
import kingroup.KinGroupError;
import kingroup.genetics.OldAlleleFreq;
import kingroup.genotype.Genotype;
import kingroup.genotype.GenotypeFactory;
import kingroup.papers.butler2004.ButlerPopBuilder;

/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc152844, Date: Dec 15, 2004, Time: 15:59:00
 */
/**
 * CensusPopBuilder allows the construction of a Population of unrelated Genotypes and and their numReplicates.
 * Replicates may simply be identical genotypes or may be genotypes that contain genotyping errors. Genotyping erros
 * might be either dropouts or misprints. See Kalinowski et al 2004.
 */
public class CensusPopBuilder extends ButlerPopBuilder {
  public CensusPopBuilder(OldAlleleFreq freq) {
    super(freq);
  }
  public OldPop buildFromButler(CensusPopBuilderModel model) {
    OldPop pop = buildButler(model);
    return buildCensus(pop, model);
  }
  public OldPop buildFromSmith(CensusPopBuilderModel model) {
    OldPop pop = buildSmith(model);
    return buildCensus(pop, model);
  }
  public OldPop buildCensus(OldPop pop, CensusPopBuilderModel model) {
    OldPop popA = new OldPop(getAlleleFreq());
    for (int i = 0; i < pop.size(); i++) {
      Genotype geno = pop.getGenotype(i);
      String groupId = nextUniqueId() + "_" + geno.getGroupId();
      PopGroup group = new PopGroup();
      group.setId(groupId);
      geno.setGroupId(groupId); // remember to reset the group getId to the new value!
      group.addGenotype(geno);
      popA.addGroup(group); // each individual is in the group on its own
    }
    OldPop res = popA;
    res = generateReplicates(popA, model.getNumRepeats());
    res = PopErrorFactory.makeLociMutationFrom(res, model.getMisprintRate());
    res.setValid(res.size() > 0, "Zero size population");
    return res;
  }
  public OldPop generateReplicates(OldPop from, int numReplicates) {
    if (numReplicates < 0)
      throw new KinGroupError("numReplicates = " + numReplicates + " [must be >= 0]");
    if (from == null) {
      throw new KinGroupError("Source population can not be null");
    }
    OldPop res = new OldPop(getAlleleFreq()); // must be fresh
    GenotypeFactory factory = GenotypeFactory.getInstance();
    for (int i = 0; i < from.size(); i++) {
      Genotype geno = from.getGenotype(i);
      PopGroup group = new PopGroup();
      group.setId(geno.getGroupId());
      group.addGenotype(geno);
      for (int j = 0; j < numReplicates; j++) {
        Genotype rGeno = factory.replicateGenotype(geno, geno.getId() + "_" + (j + 1));
        rGeno.setGroupId(geno.getGroupId()); // Must be the same group getId for census to work
        group.addGenotype(rGeno);
      }
      res.addGroup(group);
    }
//      LOG.trace(this, "original pop   = " + from.toString());
//      LOG.trace(this, "replicated pop = " + res.toString());
    return res;
  }
}
