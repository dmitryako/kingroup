package kingroup_v2.cervus;

import kingroup_v2.io.ImportPopOptions;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/01/2006, Time: 17:10:29
 */
public class CervusFileBean extends ImportPopOptions {
  public static final int FIRST_LINE_LOCUS_NAMES = 0;
  public static final int FIRST_LINE_DATA = 1;
  public static final int FIRST_LINE_IGNORE = 2;
  private int firstLine;

  public void loadDefault() {
    super.loadDefault();
    setHasGroupId(false);
    setFirstLine(FIRST_LINE_LOCUS_NAMES);

    setColumnDelim('\t');
    setHasId(true);
    setIdColumn(1);
    setHasMatId(false);
    setHasPatId(false);
    setFirstLocusColumn(2);
    setNumLoci(1);
    setFreqSource(FREQ_SOURCE_CALC);
  }
  public void setFirstLine(int firstLine) {
    this.firstLine = firstLine;
  }

  public int getFirstLine() {
    return firstLine;
  }
}