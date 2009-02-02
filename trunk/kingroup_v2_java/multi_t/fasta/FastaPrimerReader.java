package multi_t.fasta;
import javax.iox.TextFile;
import multi_t.pcr.Primer;
import multi_t.MultiT;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 20/11/2006, Time: 16:02:09
 */
public class FastaPrimerReader
{
  private static int count = 0;
  private static final int FWD_LBL = count++;
  private static final int FWD_SEQ = count++;
  private static final int REV_LBL = count++;
  private static final int REV_SEQ = count++;

  public static void read(TextFile file, MultiT project)
  {
    Primer fwd = project.getFwdPrimer();
    fwd.setLbl("");
    fwd.setSeq("");

    Primer rev = project.getRevPrimer();
    rev.setLbl("");
    rev.setSeq("");

    if (file.size() <= FWD_SEQ)
      return;
    fwd.setLbl(file.getLine(FWD_LBL).trim());
    fwd.setSeq(file.getLine(FWD_SEQ).trim());

    if (file.size() <= REV_SEQ)
      return;

    rev.setLbl(file.getLine(REV_LBL).trim());
    rev.setSeq(file.getLine(REV_SEQ).trim());
  }
}
