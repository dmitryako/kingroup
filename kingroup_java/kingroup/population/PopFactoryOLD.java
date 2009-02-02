package kingroup.population;
import kingroup.genotype.GenotypeGroup;
import kingroup.partition.DRAlgClusterV1;
import kingroup.partition.DRAlgPartitionV1;
import kingroup.partition.PartitionSequence;

/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 15, 2004, Time: 12:18:51 PM
 */
public class PopFactoryOLD {
  public static OldPop makeFrom(PartitionSequence s) {
    OldPop toRet = new OldPop();
    GenotypeGroup fromGroup;
    PopGroup toGroup;
    for (int i = 0; i < s.size(); i++) {
      fromGroup = s.getGenotypeGroup(i);
      toGroup = new PopGroup();
      toGroup.setId(fromGroup.getId());
      for (int j = 0; j < fromGroup.size(); j++) {
        toGroup.addGenotype(fromGroup.getGenotype(j));
      }
      toRet.addGroup(toGroup);
    }
    return toRet;
  }
  public static OldPop makeFrom(DRAlgPartitionV1 part) {
    OldPop toRet = new OldPop();
    DRAlgClusterV1 fromGroup;
    PopGroup toGroup;
    for (int i = 0; i < part.size(); i++) {
      fromGroup = part.getCluster(i);
      toGroup = new PopGroup();
      toGroup.setId(fromGroup.getClusterId());
      for (int j = 0; j < fromGroup.size(); j++) {
        int genoIdx = fromGroup.get(j);
        toGroup.addGenotype(part.getGenotype(genoIdx));
      }
      toRet.addGroup(toGroup);
    }
    return toRet;
  }

//  public static OldPop makeShuffled(OldPop from)
//  {
//    int[] rnd = IntVec.makeRandomIdxArr(from.size());
//    OldPop res = new OldPop(from.getAlleleFreq());
//    for (int i = 0; i < rnd.length; i++)
//    {
//      res.addGenotype(from.getGenotype(rnd[i]));
//    }
//    return res;
//  }

}
