package kingroup_v2.pop.sample.builder;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;
import tsvlib.project.ProjectLogger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 19/09/2006, Time: 09:55:01
 */
public class SysPopBuilderSignleFSFamily extends PopBuilder
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
    int groupSize = model.getNumFullSibs();
    int n = model.getGroupSize();  // using group size as the total number of genos
    int nUN = n - groupSize; // number of unrelated genos
    int nLoci = freq.getNumLoci();
    SysPop res = new SysPop(n, nLoci);
    res.setFreq(freq);

    SysPop ps = SysPopFactory.makeUnrelated(nUN, freq);
//    log.info("\nps = \n" + ps); // parents
    res.add(ps);
    res.setSize(ps.size());
//    log.info("\nres = \n" + res); // result

    SysPopFactory.appendFullSibs(0, 1, res, groupSize); // using the first two unrelated as parents
//      log.info("\nres = \n" + res); // result
    res.resetIdIdx();
    return res;
  }
}

