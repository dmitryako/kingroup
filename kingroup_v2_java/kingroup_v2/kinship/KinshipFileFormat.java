package kingroup_v2.kinship;
import kingroup_v2.io.ImportPopOptions;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 15/09/2005, Time: 12:22:04
 */
public class KinshipFileFormat extends ImportPopOptions {
  private char commentLineMarker;
  private String freqBlockEndMarker;
  private char alleleDelim;
  private int groupIdColumn;
  private int matIdColumn;
  private int patIdColumn;
  private String SPACER = " ";

  public void loadDefault() {
    super.loadDefault();
    setFreqBlockEndMarker("end");
    setCommentLineMarker('*');
    setAlleleDelim('/');
    setColumnDelim(',');
    setHasGroupId(true);
    setGroupIdColumn(1);
    setHasId(true);
    setIdColumn(2);
    setHasMatId(false);
    setMatIdColumn(3);
    setHasPatId(false);
    setPatIdColumn(4);
    setFirstLocusColumn(3);
    setNumLoci(1);
    setFreqSource(FREQ_SOURCE_FILE);
    setFreqUserNorm(true);
  }

  public boolean isComment(String line) {
    if (line == null || line.length() < 1)
      return true;
    String s2 = line.trim();
    if (s2.length() == 0)
      return true;
    return (getCommentLineMarker() == s2.charAt(0));
  }
  public boolean isColumnDelim(char c) {
    return getColumnDelim() == c;
  }
  public boolean isAlleleDelim(char c) {
    return getAlleleDelim() == c;
  }
  public boolean isFreqsEndMarker(String token) {
    if (token == null || token.length() == 0)
      return false;
    if (token.equals(getFreqBlockEndMarker()))
      return true;
    return false;
  }
  public String getUserColumnDelim() {
    return Character.toString(getColumnDelim()) + SPACER;
  }
  public String getUserColumnDelimName() {
    if (getColumnDelim() == '\t')
      return "tab";
    if (getColumnDelim() == ',')
      return "comma";
    if (getColumnDelim() == ';')
      return "semicolon";
    return Character.toString(getColumnDelim());
  }
  public String getAlleleDelimStr() {
    return Character.toString(getAlleleDelim());
  }


  public String getFreqBlockEndMarker() {
    return freqBlockEndMarker;
  }
  public void setFreqBlockEndMarker(String s) {
    freqBlockEndMarker = s;
  }
  public char getCommentLineMarker() {
    return commentLineMarker;
  }
  public void setCommentLineMarker(char s) {
    commentLineMarker = s;
  }
  public char getAlleleDelim() {
    return alleleDelim;
  }
  public void setAlleleDelim(char s) {
    alleleDelim = s;
  }
  public int getGroupIdColumn() {
    return groupIdColumn;
  }
  public void setGroupIdColumn(int i) {
    groupIdColumn = i;
  }
  public int getMatIdColumn() {
    return matIdColumn;
  }
  public void setMatIdColumn(int i) {
    matIdColumn = i;
  }
  public int getPatIdColumn() {
    return patIdColumn;
  }
  public void setPatIdColumn(int i) {
    patIdColumn = i;
  }
}