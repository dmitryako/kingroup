package kingroup_v2.cervus;

import kingroup_v2.KingroupFrame;
import kingroup_v2.pop.UserGenotype;
import kingroup_v2.pop.UserLocus;
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
 * User: jc138691, Date: 27/01/2006, Time: 17:25:45
 */
public class CervusFileReader {
  private static ProjectLogger log = ProjectLogger.getLogger(CervusFileReader.class);
  public static UsrPopSLOW importFile(TextFile from, CervusFileFormat format) {
    //log.setDebug();
    JFrame frame = KingroupFrame.getInstance();
    UsrPopSLOW usrPop = makePopSampleFrom(from, format, frame); log.debug("usrPop=\n", usrPop);
    UsrAlleleFreq freq = UsrAlleleFreqFactory.calcFrom(usrPop); log.debug("freq=\n", freq);
    usrPop.setFreq(freq);
    if (isValid(usrPop, frame))
      return usrPop;
    return null;
  }
  private static boolean isValid(UsrPopSLOW pop, JFrame frame) {
    return true;
  }

  // PRE: NO COMMENTS and NO FREQ BLOCK
  public static UsrPopSLOW makePopSampleFrom(TextFile from
                                                , CervusFileFormat format, JFrame frame) {
    //log.setDebug();
    if (from == null)
      return null;
    UsrPopSLOW usr = new UsrPopSLOW();
    usr.copyFrom(format);

    boolean firstLine = true;
    for (int i = 0; i < from.size(); i++) {
      String line = from.getLine(i);              log.debug("line\n", line);

      if (firstLine) {
        firstLine = false;
        setDefaultLocusIds(usr, format);
        if (format.getFirstLine() == format.FIRST_LINE_IGNORE) {
          continue;
        }
        else if (format.getFirstLine() == format.FIRST_LINE_LOCUS_NAMES) {
          importLocusIdsFrom(usr, line, format, frame);
          String[] locusIds = usr.getLocusIds();
          log.info("locusIds="+StrVec.toString(locusIds));
          if (locusIds == null) {// first line must be the header
            String error = "Unable to import:\n"
              + "User selected the first line to be a list of locus names, e.g.\n"
              + "..., loc1a, loc1b, loc2a, loc2b, ...";
            log.severe(error);
            JOptionPane.showMessageDialog(frame, error);
            return null;
          }
          continue;
        }
      }
      UserGenotype geno = loadGenotypeFrom(line, format);
      if (geno == null) {
        return null; // error
      }
      usr.addGenotype(geno);
    }
    return usr;
  }
  public static UserGenotype loadGenotypeFrom(String from
                                              , CervusFileFormat format) {
    StringTokenizer tokens = new StringTokenizer(from, format.getColumnDelimStr(), false);
    int locusIdx = -1;
    int columnIdx = -1;
    int nLoci = format.getNumLoci();
    UserGenotype res = new UserGenotype(nLoci);
    boolean firstAllele = true;
    UserLocus locus = null;
    while (tokens.hasMoreTokens()
      && ((locusIdx + 1) < nLoci // limit num of loci
      || !firstAllele)) { // finish reading the last locus
      columnIdx++;
      String token = tokens.nextToken().trim();      log.debug("token=", token);
      if (format.getHasId() && columnIdx + 1 == format.getIdColumn()) {
        res.setId(token);
      }
      if (columnIdx + 1 < format.getFirstLocusColumn()) {
        continue; // ignore
      }
      if (firstAllele) {
        locusIdx++;
        locus = new UserLocus();
        res.set(locusIdx, locus);
      }
      if (format.isMissingDataIdentifier(token)) {
        locus.add("");
      }
      else {
        locus.add(token);
      }
      firstAllele = !firstAllele;
    }
    return res;
  }
  public static void separateDelimiters(TextFile file, CervusFileFormat format) {
    String spacerStr = " ";
    for (int i = 0; i < file.size(); i++) {
      String line = file.getLine(i);
      line = separateColumnDelimiters(line, spacerStr, format);
      file.set(i, line);
    }
  }
  public static String separateColumnDelimiters(String line, String spacerStr
                                                         , CervusFileFormat format) {
    StringBuffer buff = new StringBuffer(line);
    if (format.isColumnDelim(buff.charAt(0)))
      buff.insert(0, spacerStr);
    for (int i = 1; i < buff.length(); i++) {  // NOTE: 1
      if (format.isColumnDelim(buff.charAt(i - 1))    // >,,<
        && format.isColumnDelim(buff.charAt(i)))
        buff.insert(i, spacerStr);
    }
    return buff.toString();
  }
  public static void setDefaultLocusIds(UserPopFormat pop
                                        , CervusFileFormat format) {
    int nLoci = format.getNumLoci();
    String[] ids = new String[nLoci];
    for (int i = 0; i < ids.length; i++) {
      ids[i] = "loc" + (i+1);
    }
    pop.setLocusIds(ids);
  }
  public static void importLocusIdsFrom(UserPopFormat pop, String line
                                        , CervusFileFormat format, JFrame frame) {
    StringTokenizer tokens = new StringTokenizer(line, format.getColumnDelimStr(), false);
    ArrayList ids = new ArrayList();
    boolean isLocus = true; // starting with get
    int locusIdx = -1;
    int columnIdx = -1;
    while (tokens.hasMoreTokens() && (locusIdx + 1) < format.getNumLoci()) {
      columnIdx++;
      int columnN = columnIdx + 1;
      String token = tokens.nextToken().trim();
      if (format.getHasId() && columnN == format.getIdColumn()) {
        pop.setIdHeader(token);
      }
      if (columnN < format.getFirstLocusColumn()) {
        continue; // ignore
      }
      if (token.length() == 0) {
        String error = "Unable to import: Missing locus_id#" + (locusIdx + 1);
        token = error;
        log.severe(error);
        JOptionPane.showMessageDialog(frame, error);
      }
      if (isLocus) {
        locusIdx++;
        ids.add(token);
      }
      isLocus = !isLocus;
    }
    if (ids.isEmpty()) {
      ids.add("missing_locus_id");
    }
    pop.setLocusIds(StrVec.asArray(ids));
  }
}
