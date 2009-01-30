package tomsk.io.pdb;
import tsvlib.project.ProjectLogger;

import javax.iox.TableReader;
import javax.iox.TextFile;

/**
 * Protein Data Bank (PDB) reader
 * Copyright: www.DmitryKonovalov.org, jc138691, 8/05/2007, 13:28:31
 */
public class PDBReader
{
  private static final ProjectLogger log = ProjectLogger.getLogger(TableReader.class);
  private static int RECORD_TYPE_START_IDX = 0;
  private static int RECORD_TYPE_END_IDX = 6;
  private static final String REMARK_STR = "REMARK";
  private static final int HEADER_START_IDX = 10;
  private static final int TITLE_START_IDX = 10;
  private static final String END_STR = "END";

  private static final int ATOM_SERIAL_START_IDX = 6;
  private static final int ATOM_SERIAL_END_IDX = 11;
  private static final int ATOM_NAME_START_IDX = 12;
  private static final int ATOM_NAME_END_IDX = 16;

  private static final int ATOM_X_START_IDX = 30;
  private static final int ATOM_X_END_IDX = 38;
  private static final int ATOM_Y_START_IDX = 38;
  private static final int ATOM_Y_END_IDX = 46;
  private static final int ATOM_Z_START_IDX = 46;
  private static final int ATOM_Z_END_IDX = 54;

  public static PDBModel makeFromFile(TextFile from, boolean showError)
  {
    log.trace("makeFromFile");
    log.trace("fileModel = \n", from);
    PDBModel res = new PDBModel();

    for (int r = 0; r < from.size(); r++) {
      String line = from.getLine(r).trim();
      if (line == null  || line.length() == 0)
        continue;
      String type = getRecordType(line);
      if (isEnd(type)) {
        log.trace("isEnd==true");
        break;
      }
      if (ignore(type)) {
        log.trace("ignore==true");
        continue;
      }

      PDBHeader h = readHeader(type, line);
      if (h != null) {
        res.setHeader(h);
        continue;
      }

      PDBTitle t = readTitle(type, line);
      if (t != null) {
        res.setTitle(t);
        continue;
      }

      PDBAtom a = readAtom(type, line);
      if (a != null) {
        res.add(a);
        continue;
      }
    }
    return res;
  }

  private static boolean isEnd(String type)
  {
    if (type.equals(END_STR))
      return true;
    return false;
  }

  private static PDBHeader readHeader(String type, String line)
  {
    if (!type.equals(PDBHeader.TYPE))
      return null;
    String text = line.substring(HEADER_START_IDX, line.length());
    log.trace("HEADER=[", text, "]");
    return new PDBHeader(text);
  }

  private static PDBAtom readAtom(String type, String line)
  {
    if (!type.equals(PDBAtom.TYPE)
    && !type.equals(PDBHetAtom.TYPE))
      return null;

    PDBAtom res = new PDBAtom();

    String str = line.substring(ATOM_SERIAL_START_IDX, ATOM_SERIAL_END_IDX).trim();
    log.trace("atom serial num=[", str, "]");
    int serialNum = Integer.parseInt(str);
    res.setSerialNum(serialNum);

    str = line.substring(ATOM_NAME_START_IDX, ATOM_NAME_END_IDX).trim();
    log.trace("atom name=[", str, "]");
    res.setName(str);

    str = line.substring(ATOM_X_START_IDX, ATOM_X_END_IDX).trim();
    log.trace("x=[", str, "]");
    double d = Double.parseDouble(str);
    res.setX(d);

    str = line.substring(ATOM_Y_START_IDX, ATOM_Y_END_IDX).trim();
    log.trace("y=[", str, "]");
    d = Double.parseDouble(str);
    res.setY(d);

    str = line.substring(ATOM_Z_START_IDX, ATOM_Z_END_IDX).trim();
    log.trace("z=[", str, "]");
    d = Double.parseDouble(str);
    res.setZ(d);

    return res;
  }

  private static PDBTitle readTitle(String type, String line)
  {
    if (!type.equals(PDBTitle.TYPE))
      return null;
    String text = line.substring(TITLE_START_IDX, line.length());
    log.trace("TITLE=[", text, "]");
    return new PDBTitle(text);
  }

  private static boolean ignore(String type)
  {
    if (type.equals(REMARK_STR))
      return true;
    return false;
  }

  private static String getRecordType(String line)
  {
    log.trace("line=[", line, "]");
    int endIdx = Math.min(line.length(), RECORD_TYPE_END_IDX);
    String type = line.substring(RECORD_TYPE_START_IDX, endIdx);
    log.trace("RECORD TYPE=[", type, "]");
    return type.trim();
  }
}
