package kingroup.genetics;
import kingroup.KinGroupError;
import kingroup.genotype.Allele;
import kingroup.genotype.Locus;
import kingroup.population.PopBuilderModelOLD;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 5/06/2005, Time: 16:47:31
 */
public class AlleleFreqBeyerMay extends AlleleFreqSmithEqual {
  public AlleleFreqBeyerMay(PopBuilderModelOLD model) {
    super(model);
    if (model == null)
      return;
    if (getMaxAlleleCount() == 8)
      loadNonequalProbs(0, getNumLoci() - 1, new double[]{0.3, 0.3, 0.1, 0.1, 0.05, 0.05, 0.05, 0.05});
    else {
      throw new KinGroupError("not ready for (getMaxAlleleCount() != 8)");
    }
  }
  public void loadNonequalProbs(int startLocus, int endLocus, double[] weights) {
    for (int i = startLocus; i <= endLocus; i++) {
      Locus locus = getLocus(i);
      if (locus.size() <= 0)
        continue;
      for (int a = 0; a < locus.size(); a++) {
        Allele allele = locus.get(a);
        allele.setProb(weights[a]);
      }
    }
  }
}
