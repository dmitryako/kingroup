package kingroup_v2.pop.sample.builder;
import kingroup_v2.kinship.KinshipIBD;
import kingroup_v2.kinship.KinshipSysPopFactory;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.usr.UsrPopSLOW;
import tsvlib.project.ProjectLogger;

import javax.utilx.arrays.IntVec;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 5/05/2006, Time: 16:22:17
 */
public class SysPopBuilderKinshipPairs extends PopBuilder
{
  private static ProjectLogger log = ProjectLogger.getLogger(SysPopBuilderKinshipPairs.class.getName());

  public SysPop makeSysPop(PopBuilderModel model, SysAlleleFreq freq)
  {
    int n = 2 * model.getNumKinshipPairs();
    int nLoci = freq.getNumLoci();
    SysPop res = new SysPop(n, nLoci);
    res.setFreq(freq);

    KinshipIBD ibd = model.getKinshipIBD();
    for (int i = 0; i < model.getNumKinshipPairs(); i++) {
      SysPop pair = KinshipSysPopFactory.makePair(ibd, freq);
      int idx = 0;
      pair.setId(idx, 2 * i + idx);  // FIRST
      pair.setGroupId(idx++, i);

      pair.setId(idx, 2 * i + idx);  // SECOND
      pair.setGroupId(idx++, i);

      res.add(pair);
    }
    res.setSize(n);
    res.resetIdIdx();
    res.resetGroupIdx();       
//    float error = model.getAlleleErrorRate();
//    if (error != 0f)
//      pop = SysPopFactory.makeWithAlleleError(pop, error);
    return res;
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

