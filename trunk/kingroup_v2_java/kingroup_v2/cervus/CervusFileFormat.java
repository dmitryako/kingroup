package kingroup_v2.cervus;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 25/01/2006, Time: 12:33:05
 */
public class CervusFileFormat extends CervusFileBean {
  private static final String SPACER = " ";
  public boolean isColumnDelim(char c) {
    return getColumnDelim() == c;
  }
  public String getColumnDelimStr() {
    return Character.toString(getColumnDelim());
  }
  public String getUserColumnDelim() {
    return Character.toString(getColumnDelim()) + SPACER;
  }

  public boolean isMissingDataIdentifier(String token) {
    if (token == null  || token.length() == 0)
      return true;
    if (token.equals("*"))
      return true;
    if (token.equals("?"))
      return true;
    if (token.equals("0"))
      return true;
    return false;
  }
}
