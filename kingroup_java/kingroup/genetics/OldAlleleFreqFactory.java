package kingroup.genetics;
import kingroup.papers.butler2004.ButlerPopBuilderModel;
import kingroup.papers.butler2004.family.ButlerFamilyData;
import kingroup.population.SmithPopBuilderModel;

import javax.utilx.RandomSeed;

public class OldAlleleFreqFactory {
  public static OldAlleleFreq makeAlleleFreq(ButlerPopBuilderModel model) {
    if (model.getAllelicFreqType() == ButlerFamilyData.SKEWED_BY_1)
      return new AlleleFreqSmithNonequal(model, 1);
    else if (model.getAllelicFreqType() == ButlerFamilyData.SKEWED_BY_2)
      return new AlleleFreqSmithNonequal(model, 2);
    else if (model.getAllelicFreqType() == ButlerFamilyData.SKEWED_BEYER)
      return new AlleleFreqBeyerMay(model);
    return new AlleleFreqEqual(model);
  }
  public static OldAlleleFreq makeAlleleFreq(SmithPopBuilderModel model) {
    if (model.isFreqEqual())
      return new AlleleFreqSmithEqual(model);
    else if (model.isFreqSmithNonequal())
      return new AlleleFreqSmithNonequal(model, 1);
    else if (model.isFreqSmithMixed())
      return new AlleleFreqSmithMixed(model);
    else if (model.isFreqRandom())
      return new AlleleFreqRandom(model, RandomSeed.getInstance());
    return new AlleleFreqSmithEqual(model);
  }

//  public static SysAlleleFreq makeFrom(OldAlleleFreq freq)
//  {
//    // log.info("userPop formatLog=" + freq.toString());
//    int nLoci = freq.getNumLoci();
//    SysAlleleFreq sys = new SysAlleleFreq(nLoci);
//    for (int L = 0; L < nLoci; L++) {
////      int locusSize = freq.getLocusSize(L);
//      Locus fromLocus = freq.get(L);
//      int locusSize = fromLocus.size();
//      double[] locus = new double[locusSize];
//      for (int i = 0; i < locusSize; i++) {
////        AlleleFreqPair allele = freq.get(L, i);
//        Allele allele = fromLocus.get(i);
//        locus[i] = allele.getFreq();
//      }
//      sys.setLocusFreq(L, locus);
//    }
//    // log.info("sys formatLog=\n" + res.toString());
//    return sys;
//  }
}
