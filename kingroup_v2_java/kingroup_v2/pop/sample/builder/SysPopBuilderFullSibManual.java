package kingroup_v2.pop.sample.builder;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;
import tsvlib.project.ProjectLogger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 24/11/2005, Time: 07:51:55
 */
public class SysPopBuilderFullSibManual  extends PopBuilder
{
  private static ProjectLogger log = ProjectLogger.getLogger(SysPopBuilderFullSibEqual.class.getName());

  public SysPop makeSysPop(PopBuilderModel model, SysAlleleFreq freq)
  {
    SysPop pop = makeFSGroups(model.getGroupSizes(), freq, model.getIncParents());
    if (model.getShuffled())
      pop = SysPopFactory.shuffle(pop);
//    float error = model.getAlleleErrorRate();
//    if (error != 0f)
//      pop = SysPopFactory.makeWithAlleleError(pop, error);
    return pop;
  }

  public String toString(PopBuilderModel model)
  {
    StringBuffer buff = new StringBuffer();
//    buff.append("NL").append(model.getNumLoci());
//    buff.append("_NA").append(model.getNumAlleles());

    buff.append("NG").append(model.getNumGroups());
    buff.append("_GS");
    int[] sizeArr = model.getGroupSizes();
    for (int i = 0; i < sizeArr.length; i++)
    {
      buff.append("_").append(sizeArr[i]);
    }
    return buff.toString();
  }
}