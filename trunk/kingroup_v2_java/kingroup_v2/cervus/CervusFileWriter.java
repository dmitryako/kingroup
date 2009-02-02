package kingroup_v2.cervus;
import kingroup_v2.pop.UserGenotype;
import kingroup_v2.pop.sample.usr.UsrPopSLOW;
import tsvlib.project.ProjectLogger;

import javax.iox.TextFile;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 25/01/2006, Time: 12:31:21
 */
public class CervusFileWriter
{
  private static ProjectLogger log = ProjectLogger.getLogger(CervusFileWriter.class.getName());
  private static final String DELIM = ", ";

  public static void write(TextFile to
    , UsrPopSLOW from, CervusFileFormat format)
  {
    StringBuffer buff = new StringBuffer();
    for (int i = 0; i < from.size(); i++)
    {
      buff.setLength(0);
      UserGenotype geno = from.getGenotype(i);
      buff.append(geno.getId()).append(DELIM);
      for (int L = 0; L < from.getNumLoci(); L++)
      {
        buff.append(from.getAllele(i, L, from.MAT));
        buff.append(DELIM);
        buff.append(from.getAllele(i, L, from.PAT));
        buff.append(DELIM);
      }
      to.addLine(buff.toString());
    }
  }
}
