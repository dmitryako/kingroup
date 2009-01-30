package tomsk.io.pdb;
import java.util.ArrayList;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 8/05/2007, 16:05:02
 */
public class PDBModel extends ArrayList<PDBAtom>
{
  private PDBHeader header;
  private PDBTitle title;

  public PDBModel() {
    init();
  }

  private void init() {
    header = new PDBHeader("N/A");
    title = new PDBTitle("N/A");
  }

  public void setHeader(PDBHeader h)
  {
    if (header == null) {
      header = h;
    }
    else {
      header.append(h);
    }
  }

  public PDBHeader getHeader()
  {
    return header;
  }

  public void setTitle(PDBTitle t)
  {
    if (header == null) {
      title = t;
    }
    else {
      title.append(t);
    }
  }

  public PDBTitle getTitle()
  {
    return title;
  }

}
