package kingroup_v2.pop.sample.builder;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;
import kingroup_v2.pop.sample.usr.UsrPopSLOW;
import tsvlib.project.ProjectLogger;

import javax.utilx.arrays.IntVec;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 24/11/2005, Time: 07:48:43
 */
public class SysPopBuilderFullSibEqual extends PopBuilder
{
  private static ProjectLogger log = ProjectLogger.getLogger(SysPopBuilderFullSibEqual.class.getName());

  public SysPop makeSysPop(PopBuilderModel model, SysAlleleFreq freq)
  {
    int[] groups = IntVec.makeArray(model.getNumGroups(), model.getGroupSize());
    SysPop pop = makeFSGroups(groups, freq, model.getIncParents()
      , model.getIncMatPatIds());

    if (model.getShuffled())
      pop = SysPopFactory.shuffle(pop);
//    float error = model.getAlleleErrorRate();
//    if (error != 0f)
//      pop = SysPopFactory.makeWithAlleleError(pop, error);
    return pop;
  }
  public UsrPopSLOW makeUsrPop(SysPop sysPop, PopBuilderModel model)
  {
    int[] groups = IntVec.makeArray(model.getNumGroups(), model.getGroupSize());
    return makeUsrPop(sysPop, groups, model.getIncParents());
  }


  public String toString(PopBuilderModel model)
  {
    StringBuffer buff = new StringBuffer();
//    buff.append("NL").append(model.getNumLoci());
//    buff.append("_NA").append(model.getNumAlleles());
    buff.append("NG").append(model.getNumGroups());
    buff.append("_GS").append(model.getGroupSize());
    return buff.toString();
  }
}
