package kingroup_v2.pop.sample.builder;
import kingroup_v2.pop.UserGenotype;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;
import kingroup_v2.pop.sample.usr.UsrPopSLOW;
import tsvlib.project.ProjectLogger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 7/05/2006, Time: 11:53:13
 */
public class SysPopBuilderHalfSib extends PopBuilder
{
  private static ProjectLogger log = ProjectLogger.getLogger(SysPopBuilderHalfSib.class.getName());

  public SysPop makeSysPop(PopBuilderModel model, SysAlleleFreq freq)
  {
    SysPop pop = makeHalfSibs(model, freq);
    if (model.getShuffled())
      pop = SysPopFactory.shuffle(pop);
//    float error = model.getAlleleErrorRate();
//    if (error != 0f)
//      pop = SysPopFactory.makeWithAlleleError(pop, error);
    return pop;
  }

  public void loadUsrNames(UsrPopSLOW usrPop, PopBuilderModel model)
  {
    int nSires = model.getNumSires();
    int nDams = model.getNumDams();
    int nOffspring = model.getNumOffspring();
    boolean incParents = model.getIncParents();
    int idx = 0;
    for (int s = 0; s < nSires; s++) {
      if (incParents)  {
        UserGenotype geno = usrPop.getGenotype(idx++);
        geno.setId("s" + (s+1));
        geno.setGroupId(geno.getId());
      }
      for (int d = 0; d < nDams; d++) {
        if (incParents)  {
          UserGenotype geno = usrPop.getGenotype(idx++);
          geno.setId("s" + (s+1) + "d" + (d+1));
          geno.setGroupId(geno.getId());
        }
        for (int k = 0; k < nOffspring; k++) {
          UserGenotype geno = usrPop.getGenotype(idx++);
          geno.setId("s" + (s+1) + "d" + (d+1) + "_" + (k+1));
          geno.setGroupId("s" + (s+1) + "d" + (d+1) + "_fs");
        }
      }
    }
  }

  public String toString(PopBuilderModel model)
  {
    StringBuffer buff = new StringBuffer();
//    buff.append("NL").append(model.getNumLoci());
//    buff.append("_NA").append(model.getNumAlleles());

    buff.append("N???").append(model.getNumGroups());
    buff.append("_GS");
    int[] sizeArr = model.getGroupSizes();
    for (int i = 0; i < sizeArr.length; i++)
    {
      buff.append("_").append(sizeArr[i]);
    }
    return buff.toString();
  }

  private SysPop makeHalfSibs(PopBuilderModel model, SysAlleleFreq freq)
  {
    int nSires = model.getNumSires();
    int nDams = model.getNumDams();
    int nOffspring = model.getNumOffspring();
    boolean incParents = model.getIncParents();
    int n = countNumHalfSibs(nSires, nDams, nOffspring, incParents);

    int nLoci = freq.getNumLoci();
    SysPop res = new SysPop(n, nLoci);
    res.setFreq(freq);
    int sire = 0; // parent #1
    int dam = 1; // parent #2
    int N_PARENTS = 2;
    int id = 0;
    int groupId = 1;

    SysPop sires = SysPopFactory.makeUnrelated(nSires, freq);
    for (int i = 0; i < nSires; i++) {
      sires.setId(i, id++);
      sires.setGroupId(i, groupId++);
    }
//    log.info("\nsires = \n" + sires);

    SysPop dams = SysPopFactory.makeUnrelated(nDams, freq);
    for (int i = 0; i < nDams; i++) {
      dams.setId(i, id++);
      dams.setGroupId(i, groupId++);
    }
//    log.info("\ndams = \n" + dams);

    for (int s = 0; s < nSires; s++) {
      if (incParents)
        res.addGenotype(s, sires);
      for (int d = 0; d < nDams; d++) {
        if (incParents)
          res.addGenotype(d, dams);
        SysPop parents = new SysPop(N_PARENTS, nLoci);
        parents.setFreq(freq);
        parents.addGenotype(s, sires);
        parents.addGenotype(d, dams);
        parents.setSize(N_PARENTS);
//        log.info("\nparents = \n" + parents);

        SysPop group = new SysPop(nOffspring, nLoci);
        group.setSize(nOffspring);
        group.setFreq(freq);

        SysPopFactory.makeFullSibs(sire, dam, parents, group);
        for (int k = 0; k < nOffspring; k++) {
          group.setId(k, id++);
          group.setGroupId(k, groupId);
        }
        groupId++;
//        log.info("\ngroup = \n" + group);
        res.add(group);
      }
    }
    res.setSize(n);
    res.resetIdIdx();
    res.resetGroupIdx();
    return res;
  }

  private int countNumHalfSibs(int nSires, int nDams, int nOff, boolean incParents)
  {
    int hs = nSires * nDams * nOff;
    int parents = nSires + nSires * nDams;
    if (incParents)
      return hs + parents;
    else
      return hs;
  }
}
