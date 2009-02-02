package kingroup_v2.pop.allele.freq;
import kingroup_v2.KingroupFrame;
import kingroup_v2.pop.UserLocus;
import kingroup_v2.pop.sample.usr.UsrPopSLOW;
import tsvlib.project.ProjectLogger;

import javax.swing.*;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 28/01/2006, Time: 10:03:12
 */
public class UsrAlleleFreqFactory
{
  private static ProjectLogger log = ProjectLogger.getLogger(UsrAlleleFreqFactory.class.getName());
  private static boolean REPORT_NOT_FOUND_ERROR = true;

  public static UsrAlleleFreq makeUsrAlleleFreqFrom(SysAlleleFreq sys)
  {
//    log.info("freq sys formatLog=\n" + sys);
    int nLoci = sys.getNumLoci();
    String locusIds[] = makeLocusIds(sys);
    UsrAlleleFreq usr = new UsrAlleleFreq(locusIds);
    for (int L = 0; L < nLoci; L++) {
      int locusSize = sys.getLocusSize(L);
      for (int i = 0; i < locusSize; i++) {
        double freq = sys.getFreq(L, i);
        AlleleFreqPair allele = new AlleleFreqPair(""+(i+1), freq);
        usr.add(L, allele);
      }
    }
//    log.info("freq usr formatLog=\n" + usr);
    return usr;
  }
  private static String[] makeLocusIds(SysAlleleFreq sys)
  {
    int nLoci = sys.getNumLoci();
    String[] res = new String[nLoci];
    for (int L = 0; L < res.length; L++) {
      res[L] = "loc" + (L +1);
    }
    return res;
  }


  public static UsrAlleleFreq calcFrom(UsrPopSLOW from)
  {
    UsrAlleleFreq res = new UsrAlleleFreq(from.getLocusIds());
    for (int L = 0; L < from.getNumLoci(); L++)
    {
      TreeMap<String, Integer> strMap = new TreeMap<String, Integer>();
      TreeMap<String, Integer> intMap = new TreeMap<String, Integer>();
      for (int i = 0; i < from.size(); i++)
      {
        UserLocus locus = from.getLocus(i, L);
        for (int a = 0; a < locus.size(); a++)
        {
          String strKey = locus.getAllele(a);
          if (strKey == null || strKey.length() == 0) {  // [dak080331] ignore empty alleles 
            continue;
          }
          try{
            Integer.parseInt(strKey);
            Integer count = intMap.get(strKey);
            if (count == null)
              count = new Integer(0);
            // [dk20060614] DO NOT use int value, since 080 would become 80.
            intMap.put(strKey, new Integer(count.intValue()+1));
          }
          catch(NumberFormatException ex)
          {
            Integer count = strMap.get(strKey);
            if (count == null)
              count = new Integer(0);
            strMap.put(strKey, new Integer(count.intValue()+1));
          }
        }
      }

      // integer values first
      for (Iterator<String> it = intMap.keySet().iterator(); it.hasNext(); )
      {
        String intKey = it.next();
        Integer count = intMap.get(intKey);
        AlleleFreqPair pair = new AlleleFreqPair(intKey.toString(), count.doubleValue());
        res.add(L, pair);
      }

      for (Iterator<String> it = strMap.keySet().iterator(); it.hasNext(); )
      {
        String strKey = it.next();
        Integer count = strMap.get(strKey);
        AlleleFreqPair pair = new AlleleFreqPair(strKey, count.doubleValue());
        res.add(L, pair);
      }
    }
    return res;
  }

  public static UsrAlleleFreq deepCopyFrom(UsrAlleleFreq from) {
    UsrAlleleFreq res = new UsrAlleleFreq(from.getLocusIds());
    for (int L = 0; L < from.getNumLoci(); L++)
    {
      int locusSize = from.getLocusSize(L);
      for (int a = 0; a < locusSize; a++) {
        AlleleFreqPair oldPair = from.get(L, a);
        AlleleFreqPair newPair = new AlleleFreqPair(oldPair.getName(), oldPair.getFreq());
        res.add(L, newPair);
      }
    }
    return res;
  }

  public static UsrAlleleFreq subtract(UsrAlleleFreq from
    , UsrAlleleFreq freq) {
    UsrAlleleFreq res = UsrAlleleFreqFactory.deepCopyFrom(from);
    for (int L = 0; L < res.getNumLoci(); L++)
    {
//      TreeMap<String, AlleleFreqPair> mapFrom = makeMap(L, from);
      TreeMap<String, AlleleFreqPair> map = makeMap(L, res);
      for (int a = 0; a < freq.getLocusSize(L); a++)
      {
        AlleleFreqPair pair = freq.get(L, a);
        AlleleFreqPair oldPair = map.get(pair.getName());
        if (oldPair == null)
        {
          if (REPORT_NOT_FOUND_ERROR)
          {
            REPORT_NOT_FOUND_ERROR = false;
            String error = "Internal error: allele name mismatch.";
            log.severe(error);
            JFrame frame = KingroupFrame.getInstance();
            JOptionPane.showMessageDialog(frame, error);
          }
          return null;
        }
        double oldFreq = oldPair.getFreq();
        oldPair.setFreq(oldFreq - pair.getFreq());
//        if ((float)oldPair.getFreq() == 0.0f)
//        {
//          AlleleFreqPair fromPair = mapFrom.get(pair.getName());
//           //NOTE!!! This is how KINSHIP does it
//          oldPair.setFreq(fromPair.getFreq());
//        }
      }
    }
    return res;
  }

  public static TreeMap<String, AlleleFreqPair> makeMap(int L, UsrAlleleFreq freq) {
    TreeMap<String, AlleleFreqPair> map = new TreeMap<String, AlleleFreqPair>();
    for (int a = 0; a < freq.getLocusSize(L); a++)
    {
      AlleleFreqPair pair = freq.get(L, a);
      map.put(pair.getName(), pair);
    }
    return map;
  }
}
