package kingroup_v2.kinship;
import kingroup_v2.KingroupFrame;
import kingroup_v2.pop.UserGenotype;
import kingroup_v2.pop.UserLocus;
import kingroup_v2.pop.allele.freq.AlleleFreqPair;
import kingroup_v2.pop.allele.freq.UsrAlleleFreq;
import kingroup_v2.pop.allele.freq.UsrAlleleFreqFactory;
import kingroup_v2.pop.sample.usr.UserPopFormat;
import kingroup_v2.pop.sample.usr.UsrPopSLOW;
import tsvlib.project.ProjectLogger;

import javax.iox.TextFile;
import javax.swing.*;
import javax.utilx.arrays.StrVec;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 15/09/2005, Time: 14:13:18
 */
public class KinshipFileReader
{
  private static ProjectLogger log = ProjectLogger.getLogger(KinshipFileReader.class);

  public static UsrPopSLOW importFile(TextFile from, KinshipFileFormat format)
  {
    JFrame frame = KingroupFrame.getInstance();     //    log.info("from=\n" + from);
    TextFile noComm = removeComments(from, format); //    log.info("no comments=\n" + noComm);
    TextFile popBlock = noComm;
    UsrAlleleFreq freq = null;
    if (format.getFreqSource() == format.FREQ_SOURCE_FILE) {
      TextFile freqBlock = getFreqBlock(noComm, format); //      log.info("freq block=\n" + freqBlock);
      freq = makeFreqFrom(freqBlock, format, frame);     //      log.info("freq =\n" + freq);
      if (freq == null) {
        String error = "Unable to import frequency block.";
        log.severe(error);
        JOptionPane.showMessageDialog(frame, error);
        return null;
      }
      popBlock = removeFreqBlock(noComm, format);   //      log.info("pop block=\n" + popBlock);
    }
    UsrPopSLOW usrPop = makePopSampleFrom(popBlock, format, frame); //    log.info("usrPop=\n" + usrPop);
    if (format.getFreqSource() == format.FREQ_SOURCE_CALC
      || format.getFreqSource() == format.FREQ_SOURCE_BIAS)
      freq = UsrAlleleFreqFactory.calcFrom(usrPop);   //    log.info("freq=\n" + freq);
    usrPop.setFreq(freq);
    if (isValid(usrPop, frame))
      return usrPop;
    return null;
  }

  private static boolean isValid(UsrPopSLOW pop, JFrame frame)
  {
    UsrAlleleFreq freq = pop.getFreq();
    String[] freqIds = freq.getLocusIds();
    String[] popIds = pop.getLocusIds();
    if (freqIds.length != popIds.length) {
      String error = "Unable to import:\nFrequency block has " + freqIds.length + " locus names while\n"
        + "genotypes block has " + popIds.length + ".";
      log.severe(error);
      JOptionPane.showMessageDialog(frame, error);
      return false;
    }
    for (int i = 0; i < freqIds.length; i++) {
      if (!freqIds[i].equals(popIds[i])) {
        String error = "Unable to import:\nThe frequency block locus #" + (i + 1) + " has name=[" + freqIds[i]
          + "]   while\n"
          + "the same locus #" + i + " in the genotypes block has name=[" + popIds[i] + "].";
        log.severe(error);
        JOptionPane.showMessageDialog(frame, error);
        return false;
      }
    }
    return true;
  }

  // PRE: NO COMMENTS and FREQ BLOCK FIRST
  public static TextFile removeFreqBlock(TextFile from
    , KinshipFileFormat format)
  {
    boolean freqBlock = true;
    TextFile res = new TextFile();
    for (int i = 0; i < from.size(); i++) {
      String line = from.getLine(i);
      if (freqBlock) {
        if (isEndOfAlleleFreqs(line, format)) {
          freqBlock = false;
        }
        continue;
      } else {
        res.addLine(line);
      }
    }
    return res;
  }

  // PRE: NO COMMENTS
  public static TextFile getFreqBlock(TextFile from, KinshipFileFormat format)
  {
    TextFile res = new TextFile();
    for (int i = 0; i < from.size(); i++) {
      String line = from.getLine(i);
      if (isEndOfAlleleFreqs(line, format)) {
        return res;
      }
      res.addLine(line);
    }
    return null;
  }

  // PRE: NO COMMENTS and FREQ BLOCK ONLY
  public static UsrAlleleFreq makeFreqFrom(TextFile from
    , KinshipFileFormat format, JFrame frame)
  {
//    log.setLevel(Level.ALL);
    if (from == null)
      return null;
    UsrAlleleFreq res = null;
    boolean firstValidLine = true;
    for (int i = 0; i < from.size(); i++) {
      String line = from.getLine(i);
//      log.info("line="+line);
      if (firstValidLine) {
        firstValidLine = false;
        String[] locusIds = importFreqLocusIdsFrom(line, format, frame);
//        log.info("locusIds="+StrVec.toString(locusIds));
        if (locusIds == null) {// first line must be the header
          String error = "Unable to import allele frequencies:\n"
            + "The first non-comment line of the FREQUENCY block must be a list of locus names, e.g.\n"
            + "loc1, , loc2, , ...";
          log.severe(error);
          JOptionPane.showMessageDialog(frame, error);
          return null;
        }
        res = new UsrAlleleFreq(locusIds);
        continue;
      }
      if (!loadFreqFrom(res, line, format, frame)) {
        String error = "Unable to import allele frequencies from line:\n" + line;
        log.severe(error);
        JOptionPane.showMessageDialog(frame, error);
        return null; // error
      }
    }
    return res;
  }

  // PRE: NO COMMENTS and NO FREQ BLOCK
  public static UsrPopSLOW makePopSampleFrom(TextFile from
    , KinshipFileFormat format, JFrame frame)
  {
//    log.setLevel(Level.ALL);
    if (from == null)
      return null;
    UsrPopSLOW usr = new UsrPopSLOW();
    usr.copyFrom(format);
    boolean firstValidLine = true;
    for (int i = 0; i < from.size(); i++) {
      String line = from.getLine(i);
//      log.info("line="+line);
      if (firstValidLine) {
        firstValidLine = false;
        importLocusIdsFrom(usr, line, format, frame);
        String[] locusIds = usr.getLocusIds();           // log.info("locusIds="+StrVec.toString(locusIds));
        if (locusIds == null) {// first line must be the header
          String error = "Unable to import:\n"
            + "The first non-comment line of the GENOTYPES block must be a list of locus names, e.g.\n"
            + "loc1, , loc2, , ...";
          log.severe(error);
          JOptionPane.showMessageDialog(frame, error);
          return null;
        }
        continue;
      }
      UserGenotype geno = loadGenotypeFrom(line, format);
      if (geno == null) {
        return null; // error
      }
      usr.addGenotype(geno);
    }
    return usr;
  }

  private static boolean loadFreqFrom(UsrAlleleFreq freq, String from
    , KinshipFileFormat format, JFrame frame)
  {
    StringTokenizer tokens = new StringTokenizer(from, format.getColumnDelimStr(), false);
    boolean isLocus = true; // starting with get
    int locusIdx = -1;
    int colIdx = -1;
    String alleleName = null;
//    double alleleFreq = 0;
    while (tokens.hasMoreTokens()) {
      colIdx++;
      String token = tokens.nextToken().trim();
      if (isLocus) {
        locusIdx++;
        if (locusIdx >= format.getNumLoci()) {
          return true;
        }
        alleleName = token;
        if (alleleName.length() == 0) { // THIS IS NOT AN ERROR
          if (tokens.hasMoreTokens()) {
            tokens.nextToken();  // skip next column
            isLocus = false;
          }
//          String error = "Unable to import frequency block:\nMissing allele id at column #" + (colIdx +1)
//            + ", locus #" + (locusIdx+1);
//          alleleName = error;
//          log.severe(error);
//          JOptionPane.showMessageDialog(frame, error);
//          return false;
        }
      } else {
        if (token.length() == 0) {
          String error = "Unable to import frequency block:\nMissing frequence at line #" + (colIdx + 1)
            + ", locus #" + (locusIdx + 1);
//          alleleFreq = 0;
          log.severe(error);
          JOptionPane.showMessageDialog(frame, error);
          return false;
        }
        AlleleFreqPair pair = AlleleFreqPair.makeFromString(alleleName, token);
        if (pair != null)
          freq.add(locusIdx, pair);
        else {
          String error = "Unable to import frequency from text=[" + token.trim() + "] at line #" + (colIdx + 1)
            + ", get #" + (locusIdx + 1);
//          alleleFreq = 0;
          log.severe(error);
          JOptionPane.showMessageDialog(frame, error);
          return false;
        }
      }
      isLocus = !isLocus;
    }

    // CHECK
    for (int L = 0; L < freq.getNumLoci(); L++) {
      if (freq.getLocusSize(L) == 0) {
        String error = "Unable to import:\nLocus #" + (L + 1) + " is empty\n"
          + "Check that\n"
          + "\t1. the selected column delimiter matches the delimiter used in the file.";
//        alleleFreq = 0;
        log.severe(error);
        JOptionPane.showMessageDialog(frame, error);
        return false;
      }
    }
    return true;
  }

  public static UserGenotype loadGenotypeFrom(String from
    , KinshipFileFormat format)
  {
    StringTokenizer tokens = new StringTokenizer(from, format.getColumnDelimStr(), false);
    int locusIdx = -1;
    int columnIdx = -1;
    int nLoci = format.getNumLoci();
    UserGenotype res = new UserGenotype(nLoci);
    while (tokens.hasMoreTokens() && (locusIdx + 1) < nLoci) {
      columnIdx++;
      String token = tokens.nextToken().trim();
      if (format.getHasGroupId() && columnIdx + 1 == format.getGroupIdColumn()) {
        res.setGroupId(token);
      }
      if (format.getHasId() && columnIdx + 1 == format.getIdColumn()) {
        res.setId(token);
      }
      if (format.getHasMatId() && columnIdx + 1 == format.getMatIdColumn()) {
        res.setMatId(token);
      }
      if (format.getHasPatId() && columnIdx + 1 == format.getPatIdColumn()) {
        res.setPatId(token);
      }
      if (columnIdx + 1 < format.getFirstLocusColumn()) {
        continue; // ignore
      }
      locusIdx++;
      UserLocus locus = loadLocusFrom(token, format);
      res.set(locusIdx, locus);
    }
    return res;
  }

  public static UserLocus loadLocusFrom(String from, KinshipFileFormat format)
  {
    StringTokenizer tokens = new StringTokenizer(from, format.getAlleleDelimStr(), false);
    UserLocus res = new UserLocus();
    while (tokens.hasMoreTokens()) {
      String allele = tokens.nextToken().trim();
//      if (allele.length() == 0) {
//        continue;
//      }
      res.add(allele);
    }
    return res;
  }

  private static boolean isEndOfAlleleFreqs(String line, KinshipFileFormat format)
  {
    StringTokenizer tokens = new StringTokenizer(line, format.getColumnDelimStr(), false);
    if (!tokens.hasMoreTokens())
      return false;
    String next = tokens.nextToken().trim();
    return format.isFreqsEndMarker(next);
  }

  public static void separateDelimiters(TextFile file, KinshipFileFormat format)
  {
//    log.info("file=\n" + file);
    String spacerStr = " ";
    for (int i = 0; i < file.size(); i++) {
      String line = file.getLine(i);
      if (format.isComment(line))
        continue;
      line = separateColumnAndAlleleDelimiters(line, spacerStr, format);
      file.set(i, line);
    }
//    log.info("new file=\n" + file);
  }

  public static String separateColumnAndAlleleDelimiters(String line, String spacerStr
    , KinshipFileFormat format)
  {
    StringBuffer buff = new StringBuffer(line);
    if (format.isColumnDelim(buff.charAt(0))
      || format.isAlleleDelim(buff.charAt(0)))
      buff.insert(0, spacerStr);
    for (int i = 1; i < buff.length(); i++) {  // NOTE: 1
      if (format.isColumnDelim(buff.charAt(i - 1))    // >,,<
        && format.isColumnDelim(buff.charAt(i)))
        buff.insert(i, spacerStr);
      if (format.isColumnDelim(buff.charAt(i - 1))    // >,/<
        && format.isAlleleDelim(buff.charAt(i)))
        buff.insert(i, spacerStr);
      if (format.isAlleleDelim(buff.charAt(i - 1))  // >/,<
        && format.isColumnDelim(buff.charAt(i)))
        buff.insert(i, spacerStr);
      if (format.isAlleleDelim(buff.charAt(i - 1))      // >//<
        && format.isAlleleDelim(buff.charAt(i)))
        buff.insert(i, spacerStr);
    }
    return buff.toString();
  }

  public static String[] importFreqLocusIdsFrom(String line, KinshipFileFormat format
    , JFrame frame)
  {
//    log.setLevel(Level.ALL);
    StringTokenizer tokens = new StringTokenizer(line, format.getColumnDelimStr(), false);
    ArrayList ids = new ArrayList();
    boolean isLocus = true; // starting with get
    int locusIdx = -1;
    while (tokens.hasMoreTokens() && (locusIdx + 1) < format.getNumLoci()) {
      String token = tokens.nextToken().trim();
      if (isLocus) {
        locusIdx++;
        if (token.length() == 0) {
          String error = "Unable to import:\nMissing locus_id#" + (locusIdx + 1)
            + " in the FREQUENCY block.";
          token = error;
          log.severe(error);
          JOptionPane.showMessageDialog(frame, error);
          return null;
        }
        ids.add(token);
      }
      isLocus = !isLocus;
    }
    if (ids.isEmpty()) {
      ids.add("missing_locus_id");
    }
    return StrVec.asArray(ids);
  }

  public static void importLocusIdsFrom(UserPopFormat pop, String line
    , KinshipFileFormat format, JFrame frame)
  {
    StringTokenizer tokens = new StringTokenizer(line, format.getColumnDelimStr(), false);
    ArrayList ids = new ArrayList();
    int locusIdx = -1;
    int columnIdx = -1;
    while (tokens.hasMoreTokens() && (locusIdx + 1) < format.getNumLoci()) {
      columnIdx++;
      int columnN = columnIdx + 1;
      String token = tokens.nextToken().trim();
      if (format.getHasId() && columnN == format.getIdColumn()) {
        pop.setIdHeader(token);
      } else if (format.getHasGroupId() && columnN == format.getGroupIdColumn()) {
        pop.setGroupIdHeader(token);
      } else if (format.getHasMatId() && columnN == format.getMatIdColumn()) {
        pop.setMatIdHeader(token);
      } else if (format.getHasPatId() && columnN == format.getPatIdColumn()) {
        pop.setPatIdHeader(token);
      }
      if (columnN < format.getFirstLocusColumn()) {
        continue; // ignore
      }
      locusIdx++;
      if (token.length() == 0) {
        String error = "Unable to import: Missing locus_id#" + (locusIdx + 1);
        token = error;
        log.severe(error);
        JOptionPane.showMessageDialog(frame, error);
      }
      ids.add(token);
    }
    if (ids.isEmpty()) {
      ids.add("missing_locus_id");
    }
    pop.setLocusIds(StrVec.asArray(ids));
  }

  public static TextFile removeComments(TextFile from, KinshipFileFormat format)
  {
    TextFile res = new TextFile();
    for (int i = 0; i < from.size(); i++) {
      String line = from.getLine(i);
      if (format.isComment(line)) {
        continue;
      }
      res.addLine(line);
    }
    return res;
  }

  public static void replaceTabDelims(TextFile file, KinshipFileFormat format)
  {
    if (format.getColumnDelim() != '\t')
      return;
    for (int i = 0; i < file.size(); i++) {
      String line = file.getLine(i);
      if (format.isComment(line))
        continue;
      line = line.replace("\t", " ; ");
      file.set(i, line);
    }
  }
}
