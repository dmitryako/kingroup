package kingroup_v2.pop.allele.freq;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 16/09/2005, Time: 11:05:21
 */
public class AlleleFreqPair {
  private static int N_DIGITS = 6;
  private static StringBuffer toStringBuff = new StringBuffer();
  private final String name;
  private double freq;
  public AlleleFreqPair(String alleleName, double alleleFreq) {
    name = alleleName;
    freq = alleleFreq;
  }
  public static AlleleFreqPair makeFromString(String name, String val)
  {
    NumberFormat nf = NumberFormat.getNumberInstance();
    double f = 0;
    try {
//    ParsePosition pos = new ParsePosition(0);
//    f = nf.parse(val, pos).doubleValue();
      f = nf.parse(val).doubleValue();
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
    return new AlleleFreqPair(name, f);
  }
  public String toString(String delim) {
    NumberFormat nf = NumberFormat.getNumberInstance();
    nf.setMaximumFractionDigits(N_DIGITS);
    toStringBuff.setLength(0);
    toStringBuff.append(name).append(delim).append(nf.format(freq));
    return toStringBuff.toString();
  }
  public double getFreq() {
    return freq;
  }
  public String getName() {
    return name;
  }
  public void setFreq(double freq) {
    this.freq = freq;
  }
}
