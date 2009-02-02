package kingroup.model;
import kingroup.genotype.Genotype;
import kingroup.genotype.GenotypeFactory;
import kingroup.genotype.Locus;
import kingroup.util.FastIdArray;

import javax.swing.*;
import javax.utilx.arrays.StrVec;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;
/**
 * JKinship    Copyright (C) 2003  Dr.D.A.Konovalov
 * <p/>
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation;
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details (file license_gpl.txt).
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program (file license_gpl.txt); if not, see http://www.gnu.org .
 *
 * @see www.it.jcu.edu.au/kinship for full JKinship Licensing and more details
 *      or contact Dmitry.Konovalov@jcu.edu.au
 */
public class KinshipFileModelV1 {
  public final static String DEFAULT_GROUP_ID = "g1";
  public final static int ALLELE_FREQ_FILE = 0;
  public final static int ALLELE_FREQ_CALC = 1;
  public final static int ALLELE_FREQ_BIAS = 2;
  private int alleleFreqs;
  private String commentLineMarkers;
  private String freqsEndMarker;
  private String alleleDelimiters;
  private String columnDelimiters;
  private boolean hasGroupIdColumn;
  private int groupIdColumn;
  private boolean hasIdColumn;
  private int idColumn;
  private boolean hasMatIdColumn;
  private int matIdColumn;
  private boolean hasPatIdColumn;
  private int patIdColumn;
  private int locusColumn;
  private boolean hasMaxNumLoci;
  private int maxNumLoci;
  public KinshipFileModelV1() {
  }
  public KinshipFileModelV1(KinshipFileModelV1 from) {
    copyFrom(from);
  }
  public void copyFrom(KinshipFileModelV1 from) {
    if (from == null)
      return;
    setFreqsEndMarker(from.getFreqsEndMarker());
    setCommentLineMarkers(from.getCommentLineMarkers());
    setAlleleDelimiters(from.getAlleleDelimiters());
    setColumnDelimiters(from.getColumnDelimiters());
    setHasGroupIdColumn(from.getHasGroupIdColumn());
    setGroupIdColumn(from.getGroupIdColumn());
    setHasIdColumn(from.getHasIdColumn());
    setIdColumn(from.getIdColumn());
    setHasPatIdColumn(from.getHasPatIdColumn());
    setPatIdColumn(from.getPatIdColumn());
    setHasMatIdColumn(from.getHasMatIdColumn());
    setMatIdColumn(from.getMatIdColumn());
    setLocusColumn(from.getLocusColumn());
    setHasMaxNumLoci(from.getHasMaxNumLoci());
    setMaxNumLoci(from.getMaxNumLoci());
    setAlleleFreqs(from.getAlleleFreqs());
  }
  public void loadDefaults() {
    setFreqsEndMarker("end");
    setCommentLineMarkers("*");
    setAlleleDelimiters("/");
    //fformat.setColumnDelim("\t;,");
    setColumnDelimiters(",\t");
    setHasGroupIdColumn(true);
    setGroupIdColumn(1);
    setHasIdColumn(true);
    setIdColumn(2);
    setLocusColumn(3);
    setHasMaxNumLoci(true);
    setMaxNumLoci(1);
    setAlleleFreqs(this.ALLELE_FREQ_FILE);
  }
  public boolean ignoreInputLine(String s) {
    if (s == null || s.length() < 1)
      return true;
    String s2 = s.trim();
    if (s2.length() == 0)
      return true;
    if (isCommentLine(s2.charAt(0)))
      return true;
    return false;
  }
  public boolean isEndOfAlleleFreqs(String s) {
    if (s == null)
      return false;
    if (s.length() < 1)
      return false;
    StringTokenizer st = new StringTokenizer(s, getColumnDelimiters());
    if (!st.hasMoreTokens())
      return false;
    String next = st.nextToken().trim();
    if (next.equals(getFreqsEndMarker()))
      return true;
    return false;
  }
  public String[] importLocusIdsFrom(String s) {
    StringTokenizer st = new StringTokenizer(s, getColumnDelimiters(), true);
    ArrayList ids = new ArrayList();
    int column = 1 - getLocusColumn(); // NOTE!!! when locus_id = 1, col=0
    while (st.hasMoreTokens()) {
      column++;
      String locusId = st.nextToken();
      if (isColumnDelimiter(locusId)) {// case: >,<
        locusId = "missing_locus_id_" + column; // expecting an getId, got nothing
      } else {
        if (locusId.trim().length() == 0) {
          locusId = "missing_locus_id_" + column; // getId is white space
        }
        if (st.hasMoreTokens())   // case: >something,<
          st.nextToken();   // MUST be a delim!!!
      }
      if (column > 0) {// start from the the first get column
        if (getHasMaxNumLoci() && column > getMaxNumLoci())
          continue;
        ids.add(locusId.trim());
      }
    }
    if (ids.isEmpty()) {
      ids.add("missing_locus_id");
    }
    return StrVec.asArray(ids);
  }
  // loads the header titles for the allelle UsrPopFactory information
  public String[] importFreqLocusIdsFrom(String s) {
    StringTokenizer st = new StringTokenizer(s, getColumnDelimiters(), true);
    ArrayList ids = new ArrayList();
    int column = 0;
    boolean isLocus = true; // starting with get
    while (st.hasMoreTokens()) {
      if (isLocus)
        column++;
      String locusId = st.nextToken();
      if (isColumnDelimiter(locusId)) // case: >,<
        locusId = "missing_freq_id_" + column; // expecting an getId, got nothing
      else {
        if (locusId.trim().length() == 0)
          locusId = "missing_freq_id_" + column; // getId is white space
        if (st.hasMoreTokens())    // case: >something,<
          st.nextToken();   // MUST be a delim!!!
      }
      if (isLocus) {
        if (column > 0 && getHasMaxNumLoci() && column > getMaxNumLoci())
          continue;
        ids.add(locusId.trim());
      }
      isLocus = !isLocus;
    }
    if (ids.isEmpty()) {
      ids.add("missing_freq_id");
    }
    return StrVec.asArray(ids);
  }
  public String[] importLocusFrom(String s) {
    StringTokenizer st = new StringTokenizer(s, getAlleleDelimiters(), true);
    ArrayList ids = new ArrayList();
    while (st.hasMoreTokens()) {
      String id = st.nextToken();
      if (isAlleleDelimiter(id)) // case: >,<
        id = ""; // expecting an getId, got nothing. DO NOT create an Allele
      else {
        if (st.hasMoreTokens())    // case: >something,<
          st.nextToken();   // MUST be a delim!!!
      }
      id = id.trim();
      if (id.length() > 0)
        ids.add(id);
    }
    if (ids.isEmpty())
      return null;
    return StrVec.asArray(ids);
  }
  //
  // PRE: input string must be in the "nice" formatLog:
  // e.g. " , , , " not ",,,"
  public Genotype importGenotypeFrom(String s, FastIdArray locusIds) {
//      Genotype geno = new Genotype();
    Genotype geno = GenotypeFactory.getInstance().makeGenotype();
    StringTokenizer st = new StringTokenizer(s, getColumnDelimiters());
    Vector v = new Vector();
    while (st.hasMoreTokens()) {
      v.add(st.nextToken().trim());
    }
    if (v.size() < 1)
      return null;

    // GROUP ID
    int groupIdx = getGroupIdColumn() - 1;// group getId is from 1
    boolean hasGroupId = getHasGroupIdColumn();
    if (hasGroupId && groupIdx >= 0 && v.size() > groupIdx)
      geno.setGroupId((String) v.get(groupIdx));

    // INDIVIDUAL ID
    int idIdx = getIdColumn() - 1;// getId is from 1
    boolean hasId = getHasIdColumn();
    if (hasId && idIdx >= 0 && v.size() > idIdx)
      geno.setId((String) v.get(idIdx));
    int firstIdx = getLocusColumn() - 1;
    int numLoci = 0;
    int maxNumLoci = getMaxNumLoci();
    boolean limitNumLoci = getHasMaxNumLoci();
    for (int i = firstIdx; i < v.size(); i++) {
      String[] ids = importLocusFrom((String) v.get(i));
      Locus locus = new Locus(ids);
      if (limitNumLoci && numLoci >= maxNumLoci)
        continue; // ignore the rest
      numLoci++; // keep count
      geno.add(locus);

      // STORE get getId's as part of each get.
      int locusIdx = numLoci - 1; // current get index
      if (locusIds != null && locusIdx < locusIds.size())
        locus.setId(locusIds.get(locusIdx));
      else
        locus.setId("ERROR");
    }
    return geno;
  }
  // The freqsEndMarker property
  public String getFreqsEndMarker() {
    return freqsEndMarker;
  }
  public void setFreqsEndMarker(String s) {
    freqsEndMarker = s;
  }
  public char getPreferredCommentMarker() {
    String delims = getCommentLineMarkers();
    char delim = '*'; // preferred
    if (!isCommentLine(delim)
      && delims != null && delims.length() > 0)
      delim = delims.charAt(0);
    return delim;
  }
  public boolean isCommentLine(char c) {
    if (commentLineMarkers == null)
      return false;
    return (commentLineMarkers.indexOf(c) != -1);
  }
  public String getCommentLineMarkers() {
    return commentLineMarkers;
  }
  public void setCommentLineMarkers(String s) {
    commentLineMarkers = s;
  }
  public String getAlleleDelimiters() {
    return alleleDelimiters;
  }
  public void setAlleleDelimiters(String s) {
    alleleDelimiters = s;
  }
  public boolean isAlleleDelimiter(char c) {
    if (alleleDelimiters == null)
      return false;
    return (alleleDelimiters.indexOf(c) != -1);
  }
  private boolean isAlleleDelimiter(String src) {
    if (src.length() == 1                        // case: >,<
      && isAlleleDelimiter(src.charAt(0))) // CHECK for delimiters
      return true;
    return false;
  }
  public boolean isValidAlleleDelimiter(String text, JPanel view) {
    if (text.length() > 1) {
      if (view != null)
        JOptionPane.showMessageDialog(view, "Allele delimiter must be only one character.");
      return false;
    }
    char c = text.charAt(0);
    if (Character.isLetterOrDigit(c)) {
      if (view != null)
        JOptionPane.showMessageDialog(view, "Allele delimiter cannot be a number or a letter");
      return false;
    }
    if (isColumnDelimiter(c)) { //NOTE: checking against user input
      if (view != null)
        JOptionPane.showMessageDialog(view
          , "Allele delimiter must be different from other delimiters");
      return false;
    }
    return true;
  }
  public String getColumnDelimiters() {
    return columnDelimiters;
  }
  public void setColumnDelimiters(String s) {
    columnDelimiters = s;
  }
  public boolean isColumnDelimiter(char c) {
    if (columnDelimiters == null)
      return false;
    return (columnDelimiters.indexOf(c) != -1);
  }
  private boolean isColumnDelimiter(String src) {
    if (src.length() == 1                        // case: >,<
      && isColumnDelimiter(src.charAt(0))) // CHECK for delimiters
      return true;
    return false;
  }
  public char getPreferredColumnDelimiter() {
    if (isColumnDelimiter(',')) {
      return ',';
    }
    if (isColumnDelimiter('\t')) {
      return '\t';
    }
    return ';';
  }
//   public char getPreferredColumnDelimiter() {
//      String delims = getColumnDelim();
//      char delim = ','; // preferred delimiter
//      if (!isColumnDelimiter(delim)  &&  delims != null  &&  delims.length() > 0){
//         for(int i =0; i < delims.length(); i++) {
//            char c = delims.charAt(i);
//            if(c  == ';' || c == ':' || c == '|') {
//               delim = c;
//               break;
//            }
//            else{ delim = ';'; }
//         }
//      }
//      return delim;
//   }
  public String delimiterToString(char c) {
    switch (c) {
      case '\t' :
        return "Tab";
      case ',' :
        return "Comma";
      case ';' :
        return "Semicolon";
      default :
        return "NA";
    }
  }
  public char getNotColumnDelimiter() {
    char options[] = {'@', '#', '!', '$'};
    String delims = getColumnDelimiters();
    for (int i = 0; i < options.length; i++) {
      char delim = options[i];
      if (!isColumnDelimiter(delim))
        return delim;
    }
    return ' ';
  }
  public void addColumnDelimiter(char c) {
    int i = columnDelimiters.indexOf(c);
    if (i != -1) // must be in and it's in
      return;
    else // must be there but it's not
      columnDelimiters += c;
  }
  public boolean getHasGroupIdColumn() {
    return hasGroupIdColumn;
  }
  public int getGroupIdColumn() {
    return groupIdColumn;
  }
  public void setHasGroupIdColumn(boolean b) {
    hasGroupIdColumn = b;
  }
  public void setGroupIdColumn(int i) {
    groupIdColumn = i;
  }
  public boolean getHasIdColumn() {
    return hasIdColumn;
  }
  public int getIdColumn() {
    return idColumn;
  }
  public void setHasIdColumn(boolean b) {
    hasIdColumn = b;
  }
  public void setIdColumn(int i) {
    idColumn = i;
  }
  public boolean getHasMatIdColumn() {
    return hasMatIdColumn;
  }
  public int getMatIdColumn() {
    return matIdColumn;
  }
  public void setHasMatIdColumn(boolean b) {
    hasMatIdColumn = b;
  }
  public void setMatIdColumn(int i) {
    matIdColumn = i;
  }
  public boolean getHasPatIdColumn() {
    return hasPatIdColumn;
  }
  public int getPatIdColumn() {
    return patIdColumn;
  }
  public void setHasPatIdColumn(boolean b) {
    hasPatIdColumn = b;
  }
  public void setPatIdColumn(int i) {
    patIdColumn = i;
  }
  public int getLocusColumn() {
    return locusColumn;
  }
  public void setLocusColumn(int i) {
    locusColumn = i;
  }
  public boolean getHasMaxNumLoci() {
    return hasMaxNumLoci;
  }
  public int getMaxNumLoci() {
    return maxNumLoci;
  }
  public void setHasMaxNumLoci(boolean b) {
    hasMaxNumLoci = b;
  }
  public void setMaxNumLoci(int i) {
    maxNumLoci = i;
  }
  // The alleleFreqs property (getAllele frequencies are included in the input data file)
  public int getAlleleFreqs() {
    return alleleFreqs;
  }
  public void setAlleleFreqs(int b) {
    alleleFreqs = b;
  }
}

