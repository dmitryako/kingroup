package kingroup_v2.cervus.tools;

import javax.iox.TextFile;

import javax.utilx.pair.StringPair;
import tsvlib.project.ProjectLogger;
import java.util.StringTokenizer;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/01/2006, Time: 09:20:39
 */
public class CervusParentageReader {
  private static ProjectLogger log = ProjectLogger.getLogger(CervusParentageReader.class.getName());
  public static CervusParentageData importFile(TextFile from, CervusParentageFileFormat format) {
    if (from == null)
      return null;

    CervusParentageData res = new CervusParentageData();
    TextFile parents = new TextFile();
    boolean firstLine = true;
    for (int i = 0; i < from.size(); i++) {
      String line = from.getLine(i);
//      log.info("line="+line);

      if (firstLine) { // ignore first line
        firstLine = false;
        continue;
      }
      StringPair p = loadParentFrom(line, format);
      if (p == null)
        continue;
      res.add(p);
      parents.addLine(p.toCSV());
    }
    res.setInputData(parents);
    return res;
  }
  public static StringPair loadParentFrom(String from
                                          , CervusParentageFileFormat format) {
    StringTokenizer tokens = new StringTokenizer(from, format.getColumnDelimStr(), false);
    int columnIdx = -1;
    String a = null;
    String b = null;
    while (tokens.hasMoreTokens()) {
      columnIdx++;
      String token = tokens.nextToken().trim();
      if (columnIdx + 1 == format.getIdColumn()) {
        a = token;
      }
      if (columnIdx + 1 == format.getParentColumn()) {
        b = token;
      }
      if (a != null  && b != null) {
        if (a.length() == 0  ||  b.length() == 0)
          return null;
        return new StringPair(a, b);
      }
    }
    return null;
  }
}

