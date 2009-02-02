package kingroup_v2.pop.sample.usr;
import kingroup_v2.pop.UserGenotype;
import kingroup_v2.pop.UserLocus;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.allele.freq.UsrAlleleFreq;
import kingroup_v2.pop.allele.freq.UsrAlleleFreqFactory;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.builder.PopBuilder;
import kingroup_v2.pop.sample.builder.SysPopBuilderFactory;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import javax.utilx.arrays.IntVec;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/09/2005, Time: 10:54:27
 */
public class UsrPopFactory
 {
  private static ProjectLogger log = ProjectLogger.getLogger(UsrPopFactory.class.getName());

  public static UsrPopSLOW shuffle(UsrPopSLOW from)
  {
    int[] rnd = IntVec.makeRandIdxArr(from.size());
    UsrPopSLOW res = new UsrPopSLOW();
    res.copyFrom(from);
    res.setLocusIds(from.getLocusIds());
    res.setFreq(from.getFreq());
    for (int i = 0; i < rnd.length; i++)
    {
      res.addGenotype(from.getGenotype(rnd[i]));
    }
    return res;
  }

   public static UsrPopSLOW makeUsrPopFrom(SysPop sys)
   {
//    log.info("sys formatLog=\n" + sys.toString());
     int n = sys.size();
     UsrPopSLOW usr = new UsrPopSLOW();
     usr.copyFrom(sys);
     usr.setHasId(true);
     usr.setHasGroupId(true);

     SysAlleleFreq freq = sys.getFreq();
     UsrAlleleFreq usrFreq = UsrAlleleFreqFactory.makeUsrAlleleFreqFrom(freq);
     usr.setLocusIds(usrFreq.getLocusIds());
     usr.setFreq(usrFreq);
     for (int i = 0; i < n; i++) {
       UserGenotype geno = makeGenotypeFrom(i, sys);
       usr.addGenotype(geno);
     }
//    log.info("user formatLog=\n" + usr.toString());
     return usr;
   }
   public static UsrPopSLOW makeUsrPopFrom(SysPop newSysPop, UsrAlleleFreq oldUsrFreq
     , PopBuilderModel model)
   {
     PopBuilder builder = SysPopBuilderFactory.makeBuilder(model);

//    log.info("sys formatLog=\n" + sys.toString());
     int n = newSysPop.size();
     UsrPopSLOW usrPop = new UsrPopSLOW();
     usrPop.copyFrom(newSysPop);
     usrPop.setHasId(true);
     usrPop.setHasGroupId(true);

     SysAlleleFreq sysFreq = newSysPop.getFreq();
     usrPop.setLocusIds(oldUsrFreq.getLocusIds());
     usrPop.setFreq(oldUsrFreq);
     for (int i = 0; i < n; i++) {
       UserGenotype geno = makeGenotypeFrom(i, newSysPop, oldUsrFreq);
       usrPop.addGenotype(geno);
     }

     builder.loadUsrNames(usrPop, model);
//    log.info("user formatLog=\n" + usr.toString());
     return usrPop;
   }

   private static UserGenotype makeGenotypeFrom(int i, SysPop sys)
   {
     UserGenotype usr = new UserGenotype();
     usr.setGroupId("g"+Integer.toString(sys.getGroupId(i)+1));
     usr.setId(usr.getGroupId() + "_" + Integer.toString(sys.getIdIdx(i)+1));
     for (int L = 0; L < sys.getNumLoci(); L++)
     {
       int m = sys.getAllele(i, L, sys.MAT);
       int p = sys.getAllele(i, L, sys.PAT);
       UserLocus locus = new UserLocus();
       if (m != -1)
         locus.add(Integer.toString(m+1));
       if (p != -1)
         locus.add(Integer.toString(p+1));
       usr.add(locus);
     }
     return usr;
   }
   private static UserGenotype makeGenotypeFrom(int i, SysPop sys, UsrAlleleFreq oldUsrFreq)
   {
     UserGenotype usr = new UserGenotype();
     usr.setGroupId("g"+Integer.toString(sys.getGroupId(i)+1));
     usr.setId(usr.getGroupId() + "_" + Integer.toString(sys.getIdIdx(i)+1));
     for (int L = 0; L < sys.getNumLoci(); L++)
     {
       int m = sys.getAllele(i, L, sys.MAT);
       int p = sys.getAllele(i, L, sys.PAT);
       UserLocus locus = new UserLocus();
       if (m != -1)
         locus.add(oldUsrFreq.get(L, m).getName());
       if (p != -1)
         locus.add(oldUsrFreq.get(L, p).getName());
       usr.add(locus);
     }
     return usr;
   }


  public static UsrPopSLOW[] toGroupArray(UsrPopSLOW pop) {
//    // log.info("from pop =\n" + pop.toString());
    UserPopGroupFinder finder = new UserPopGroupFinder(pop);
//    // log.info("finder = " + finder.toString());
    UsrPopSLOW[] res = new UsrPopSLOW[finder.size()];
    if (finder.size() == 1) {
      res[0] = pop;
      return res;
    }
    for (int g = 0; g < finder.size(); g++) {
      res[g] = new UsrPopSLOW();
      res[g].shallowCopyFrom(pop);
      res[g].resetGenotypes();
    }
    for (int i = 0; i < pop.size(); i++) {
      String groupName = pop.getGroupId(i);
      int g = finder.getGroupIdx(groupName);
//      log.info("res["+g+"]=\n"+res[g]);
      res[g].addGenotype(pop.getGenotype(i));
      res[g].setName(groupName);
//      log.info("res["+g+"]=\n"+res[g]);
    }
    return res;
  }
}
