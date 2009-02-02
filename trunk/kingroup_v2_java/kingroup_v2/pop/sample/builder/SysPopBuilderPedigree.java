package kingroup_v2.pop.sample.builder;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;
import tsvlib.project.ProjectLogger;

/**
 * Created by: jc1386591
 * Date: 23/06/2006. Time: 08:57:48
 */
public class SysPopBuilderPedigree extends PopBuilder
{
  private static ProjectLogger log = ProjectLogger.getLogger(SysPopBuilderPedigree.class.getName());

  public SysPop makeSysPop(PopBuilderModel model, SysAlleleFreq freq)
  {
    SysPop pop = makePedigree(model, freq);
//    if (model.getShuffled())
//      pop = SysPopFactory.shuffle(pop);
    return pop;
  }

  public String toString(PopBuilderModel model) {
    return null;
  }

  private SysPop makePedigree(PopBuilderModel model, SysAlleleFreq freq)
  {
    int nParents = model.getNumGroups() + 1;  // using nGroups as the number of parents
    int nUnrelated = model.getNumUnrelated();
    int firstGroupSize = model.getGroupSize();
    int n = countSampleSize(nParents, firstGroupSize) + nUnrelated;
    int nLoci = freq.getNumLoci();
    SysPop res = new SysPop(n, nLoci);
    res.setFreq(freq);

    SysPop ps = SysPopFactory.makeUnrelated(nParents + nUnrelated, freq);
//    log.info("\nps = \n" + ps); // parents
    res.add(ps);
    res.setSize(ps.size());
//    log.info("\nres = \n" + res); // result

    for (int i = 0; i < nParents - 1; i++) {
      int groupSize = firstGroupSize;
      SysPopFactory.appendFullSibs(i, i+1, res, groupSize);
//      log.info("\nres = \n" + res); // result
    }
    res.resetIdIdx();
    return res;
  }

  private int countSampleSize(int nParents, int groupSize)
  {
    return nParents * (groupSize + 1) + 1;
  }
}

