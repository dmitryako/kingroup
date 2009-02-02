package kingroup_v2.pop.sample.builder;
import kingroup_v2.pop.UserGenotype;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;
import kingroup_v2.pop.sample.usr.UsrPopFactory;
import kingroup_v2.pop.sample.usr.UsrPopSLOW;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 24/11/2005, Time: 07:42:36
 */
abstract public class PopBuilder
{
  abstract public SysPop makeSysPop(PopBuilderModel model, SysAlleleFreq freq);
  public UsrPopSLOW makeUsrPop(SysPop sysPop, PopBuilderModel model)
  {
    return null;
  }
  abstract public String toString(PopBuilderModel model);

  public SysPop makeFSGroups(int[] groups, SysAlleleFreq freq
    , boolean incParents) {
    return makeFSGroups(groups, freq, incParents, false);
  }
  public SysPop makeFSGroups(int[] groups, SysAlleleFreq freq
    , boolean incParents, boolean incMatPatIds)
  {
    int n = countTotalSize(groups, incParents);

    int nLoci = freq.getNumLoci();
    SysPop res = new SysPop(n, nLoci);
    res.setFreq(freq);
    int p = 0; // parent #1
    int p2 = 1; // parent #2
    int N_PARENTS = 2;
    int id = 0;
    int groupId = 1;
    for (int i = 0; i < groups.length; i++) {
      SysPop parents = new SysPop(N_PARENTS, nLoci);
      parents.setSize(N_PARENTS);
      parents.setFreq(freq);

      int pId = id++;
      int pId2 = id++;
      parents.setId(p, pId);
      parents.setId(p2, pId2);
      parents.setGroupId(p, groupId++);
      parents.setGroupId(p2, groupId++);

      SysPop group = new SysPop(groups[i], nLoci);
      group.setSize(groups[i]);
      group.setFreq(freq);

      SysPopFactory.makeRandomDiploid(p, parents);
      SysPopFactory.makeRandomDiploid(p2, parents);
      SysPopFactory.makeFullSibs(p, p2, parents, group);
      for (int k = 0; k < group.size(); k++) {
        group.setId(k, id++);
        group.setGroupId(k, groupId);
      }
      groupId++;
//      log.info("group = \n" + group);
      if (incParents) {
        res.add(parents);
        if (incMatPatIds) {
          for (int k = 0; k < group.size(); k++) {
            group.setMatId(k, pId);
            group.setPatId(k, pId2);
          }
        }
      }
      res.add(group);
    }
    res.setSize(n);
    res.resetIdIdx();
    res.resetGroupIdx();
    return res;
  }

  public UsrPopSLOW makeUsrPop(SysPop sysPop, int[] groups
    , boolean incParents
  )
  {
    UsrPopSLOW usr = UsrPopFactory.makeUsrPopFrom(sysPop);
    int count = 0;
    int nParents = 0;
    if (incParents)
      nParents = 2;
    for (int i = 0; i < groups.length; i++) {
      for (int k = 0; k < groups[i] + nParents; k++) {
        UserGenotype geno = usr.getGenotype(count++);
        geno.setGroupId("F" + (i+1));
        if (k < nParents)
          geno.setId(geno.getGroupId() + "_P" + (k+1));
        else
          geno.setId(geno.getGroupId() + "_" + (k-1));
      }
    }
    return usr;
  }


  private int countTotalSize(int[] sizeArr, boolean incParents)
  {
    int res = 0;
    for (int i = 0; i < sizeArr.length; i++)
    {
      res += sizeArr[i];
      if (incParents)
        res += 2;
    }
    return res;
  }

  public void loadUsrNames(UsrPopSLOW usrPop, PopBuilderModel model)
  {
  }
}
