package qsar.papers.chem2007_LogBB;
import javax.iox.StrMtrxReader;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 21/02/2007, Time: 14:21:00
 */
public class LogBBRegressPCMDCommon  extends LogBB_2007_paper
{
  protected static int N_COLS_IGNORE_PCMD_INPUT = 1;

  protected void remove999(String[][] txt)
  {
    for (int i = 0; i < txt.length; i++)
      remove999(txt[i]);
  }

  protected void remove999(String[] line)
  {
//    log.info("\nstring line\n=" + StrVec.toString(line));
    for (int i = 0; i < line.length; i++) {
      String s = line[i];
      if (s.indexOf("999") == 0)
        line[i] = s.replaceFirst("999", "0");
      if (s.indexOf("-999") == 0)
        line[i] = s.replaceFirst("-999", "0");
    }
  }

  protected String[][] loadPCMDNames(String fileName)
  {
    String[][] res = new StrMtrxReader("\t").readMtrx(-1, -1, N_COLS_IGNORE_PCMD_INPUT, fileName);
    return res;
  }

}
