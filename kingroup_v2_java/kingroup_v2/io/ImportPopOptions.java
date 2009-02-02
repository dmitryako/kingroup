package kingroup_v2.io;

import kingroup_v2.pop.sample.PopFormat;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 1/02/2006, Time: 12:39:05
 */
public class ImportPopOptions extends PopFormat {
  public final static String DEFAULT_GROUP_ID = "g1";
  public final static int FREQ_SOURCE_FILE = 0;
  public final static int FREQ_SOURCE_CALC = 1;
  public final static int FREQ_SOURCE_BIAS = 2;
  private int freqSource;
  private char columnDelim;
  private int firstLocusColumn;
  private int numLoci;
  private int idColumn;
  private boolean freqUserNorm;

  public void loadDefault() {
    super.loadDefault();
    setColumnDelim('\t');
    setHasGroupId(true);
    setHasId(true);
    setIdColumn(2);
    setHasMatId(false);
    setHasPatId(false);
    setFirstLocusColumn(3);
    setNumLoci(1);
    setFreqSource(FREQ_SOURCE_FILE);
    setFreqUserNorm(true);
  }
  public String getColumnDelimStr() {
    return Character.toString(getColumnDelim());
  }
  public char getColumnDelim() {
    return columnDelim;
  }
  public void setColumnDelim(char s) {
    columnDelim = s;
  }
  public int getIdColumn() {
    return idColumn;
  }
  public void setIdColumn(int i) {
    idColumn = i;
  }
  public int getFirstLocusColumn() {
    return firstLocusColumn;
  }
  public void setFirstLocusColumn(int i) {
    firstLocusColumn = i;
  }
  public int getNumLoci() {
    return numLoci;
  }
  public void setNumLoci(int i) {
    numLoci = i;
  }
  public int getFreqSource() {
    return freqSource;
  }
  public void setFreqSource(int b) {
    freqSource = b;
  }

  public boolean getFreqUserNorm() {
    return freqUserNorm;
  }

  public void setFreqUserNorm(boolean freqUserNorm) {
    this.freqUserNorm = freqUserNorm;
  }
}
