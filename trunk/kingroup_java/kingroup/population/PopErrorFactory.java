package kingroup.population;
import kingroup.KinGroupError;
import kingroup.genotype.Allele;
import kingroup.genotype.Genotype;
import kingroup.genotype.Locus;

import javax.utilx.RandomSeed;
import java.util.ArrayList;
import java.util.List;
import tsvlib.project.ProjectLogger;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Jan 10, 2005, Time: 10:45:30 AM
 */
public class PopErrorFactory {
  private static ProjectLogger log = ProjectLogger.getLogger(PopErrorFactory.class.getName());
  private static final int MAX_PERCENT_ERROR = 100;
//  public static OldPop makeLociDropoutFrom(OldPop pop, float percentError) {
//    if (percentError > MAX_PERCENT_ERROR || percentError <= 0) {
//      throw new KinGroupError("errorPercentage = " + percentError
//        + " [0, " + Integer.toString(MAX_PERCENT_ERROR) + "]");
//    }
//    OldPop res = new OldPop(pop);
//    List loci = getLoci(res);
//    int numLoci = loci.size();
//    int numMutations = calcNumMutations(numLoci, percentError);
//    Random random = RandomSeed.getInstance();
//    for (int i = 0; i < numMutations; i++) {
//      int idx = random.nextInt(loci.size());
//      Locus locus = (Locus) loci.remove(idx); // latent get - the one that should be there
//      locus.removeAll();
//    }
//    return res;
//  }

  /**
   * Mutates a population by altering random alleles in random loci to be any other possible getAllele for that get.
   * This prevents the possibility that a1/a2 becomes a2/a1 (which might occur in mutatePopulationByAlleles() )
   *
   * @param popA         - the <code>Population</code> to be mutated
   * @param percentError - a <code>float</code> being the percentage of alleles which will be altered
   * @return the mutated <code>Population</code>
   * @throws KinGroupError
   */
  public static OldPop makeLociMutationFrom(OldPop popA, float percentError) {
    if (percentError > MAX_PERCENT_ERROR || percentError < 0) {
      throw new KinGroupError("Maximum allowed percentage is less or equal to " + Integer.toString(MAX_PERCENT_ERROR)
        + ".\nWas give " + percentError);
    }
    OldPop res = new OldPop(popA);
    List loci = getLoci(res);
    int numLoci = loci.size();
    int numMutations = calcNumMutations(numLoci, percentError);
//    // log.info("numMutations = " + numMutations);
    if (numMutations == 0)
      return res;

    // TODO: Should we look into mutations based on the allelic frequencies?
    RandomSeed random = RandomSeed.getInstance();
    for (int i = 0; i < numMutations; i++) {
      int idx = random.nextInt(loci.size());
      Locus locus = (Locus) loci.remove(idx); // latent get - the one that should be there
      int alleleIdx = random.nextInt(locus.size());
      Allele allele = locus.get(alleleIdx);
      Locus freqLocus = res.getAlleleFreq().find(locus.getId());
      int locusSize = freqLocus.size();
      Allele replace = freqLocus.get(random.nextInt(locusSize));
      while (allele.equals(replace)) {
        replace = freqLocus.get(random.nextInt(locusSize));
      }
//      // log.info("locus = " + locus);
//      // log.info("locus.replace(" + alleleIdx + ", " + replace);
      locus.replace(alleleIdx, replace);
    }
    return res;
  }
  public static int calcNumMutations(int num, float percentageMutation) {
    int result = Math.round(percentageMutation * 0.01f * num);
    return result;
  }
  /**
   * Generates a list of loci <i>not dependent</i> on calculations using the initial number of loci
   * used to set up the population.
   *
   * @param pop - a <code>Population</code> source for the loci
   * @return a <code>List</code> containing the population's loci
   */
  public static List getLoci(OldPop pop) {
    List loci = new ArrayList();
    int numGenotype = pop.size();
    for (int i = 0; i < numGenotype; i++) {
      Genotype genotype = pop.getGenotype(i);
      int numLoci = genotype.getNumLoci();
      for (int j = 0; j < numLoci; j++) {
        Locus locus = genotype.getLocus(j);
        loci.add(locus);
      }
    }
    return loci;
  }
}
