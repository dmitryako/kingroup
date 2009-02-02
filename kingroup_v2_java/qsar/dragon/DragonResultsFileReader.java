package qsar.dragon;
import javax.iox.TextFile;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;
import tsvlib.project.ProjectLogger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/10/2006, Time: 12:27:44
 */
public class DragonResultsFileReader
{
  protected final static ProjectLogger log = ProjectLogger.getAnonymousLogger();
  private static int DESCRIPTOR_NAME_LINE_IDX = 2;
  private static int DESCRIPTOR_LINE_IDX = 3;
  private static int DBG_COUNT_MAX = 10;
  private static String MOL_ID;
  public static String DRAGON_ERROR_STR = "-999";

  public static DragonResultsFile read(File file) {
    if (!file.isFile())
      return null;

    TextFile from = new TextFile();
    from.setFileName(file.getName());
    from.read(file, null);

    DragonResultsFile res = new DragonResultsFile();
    res.setNames(loadNames(from.getLine(DESCRIPTOR_NAME_LINE_IDX)));
    res.setArr(loadArr(from.getLine(DESCRIPTOR_LINE_IDX)));
    res.setMolId(MOL_ID); // MOL_ID is set in loadArr
    res.setMolName(loadMolName(file.getName()));
    return res;
  }

  private static String loadMolName(String fileName)
  {
    return fileName.substring(0, fileName.indexOf('_'));
  }

  private static ArrayList<String> loadNames(String line)
  {
//    log.info("line=" + line);
    StringTokenizer tokens = new StringTokenizer(line, "\t", false);
    ArrayList<String> res = new ArrayList<String>();

    // IGNORE FIRST two
    String token = tokens.nextToken();
    token = tokens.nextToken();

    int dbg_count = 0;
    while (tokens.hasMoreTokens()) {
      token = tokens.nextToken().trim();
//      if (dbg_count++ < DBG_COUNT_MAX)
//        log.info("token=" + token);
      res.add(token);
    }
    return res;
  }
  private static ArrayList<Double> loadArr(String line)
  {
//    log.info("line=" + line);
    StringTokenizer tokens = new StringTokenizer(line, "\t", false);
    ArrayList<Double> res = new ArrayList<Double>();

    // IGNORE FIRST two
    String token = tokens.nextToken();
    MOL_ID = tokens.nextToken(); // !!! Loading MOL_ID  !!!!!

    int dbg_count = 0;
    while (tokens.hasMoreTokens()) {
      token = tokens.nextToken().trim();
      if (token.equals(DRAGON_ERROR_STR))
        token = "0";
//      if (dbg_count++ < DBG_COUNT_MAX)
//        log.info("token=" + token);
      try {
        Double val = new Double(token);
        res.add(val);
      }
      catch (NumberFormatException e) {
        String error = "Unable to import double from text=[" + token.trim() + "]";
        log.severe(error);
        JOptionPane.showMessageDialog(null, error);
        return null;
      }
    }
    return res;
  }
}
