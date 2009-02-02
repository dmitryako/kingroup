package kingroup.partition;
import kingroup.genotype.Genotype;
import kingroup.genotype.GenotypeGroup;
import kingroup.population.PopGroup;

import javax.utilx.RandomSeed;
import java.util.ArrayList;
import java.util.Arrays;
public class SearchPartitionSpace {
  private static RandomSeed random_ = RandomSeed.getInstance();
  private PopGroup data_; // input data
  public SearchPartitionSpace(PopGroup data) {
    data_ = data;
  }
  public double[] search(PartitionSequence given
    , int limit) {
    if (given == null)
      return null;
    ArrayList arr = new ArrayList(); // store results
    GenotypeGroup pool = data_.duplicate();
    int count = 0;
    while (pool.size() > 0 && count < limit) {
      // randomly select current getGenotype
      int currentIdx = random_.nextInt(pool.size());
      Genotype current = pool.getGenotype(currentIdx); // get current
      pool.remove(currentIdx);  // this index is now assigned

      // remove getGenotype from given pedigree
      PartitionSequence givenDup = new PartitionSequence(given);
      GenotypeGroup fromGroup = givenDup.removeGenotype(current);

      // idx==population.size(), is used to create a new group
      for (int idx = 0; idx <= givenDup.size(); idx++) {
        if (idx == givenDup.size()) { // trying to create a new group
          if (fromGroup.size() == 0)
            continue; // it was one member group, so no point in creating a free standing member
        } else if (fromGroup.equals(givenDup.getGenotypeGroup(idx)))
          continue; // no point in adding to the same group from which only just removed it from
        count++;
        PartitionLikelihood tmp = givenDup.duplicateLikelihoodPartition();
        tmp.add(idx, current); // if idx is not a valid index, new group will be created
        if (!tmp.isPossible())
          continue;
        // NOTE: * (-1) for sorting
        arr.add(new Double((-1) * tmp.getLog()));  // store the result
      }
    }
    // sort
    Object[] arrObj = arr.toArray();
    arr.clear();
    Arrays.sort(arrObj); // sort descending
    double[] res = new double[arrObj.length + 1];
    double base = given.getLog();
    res[0] = 0;
    for (int i = 0; i < arrObj.length; i++) {
      res[i + 1] = (-1) * ((Double) arrObj[i]).doubleValue() - base; // NOTE: restore sign
//            LOG.traceArrayAt(this, "res", (i + 1), res);
    }
    return res;
  }
}

