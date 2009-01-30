package javax.textx;
import java.text.DecimalFormat;
import java.text.NumberFormat;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 21/09/2005, Time: 12:09:38
 */
public class FractionDigitsModel {
  public static final int THIS_IDX_IS_SCIENTIFIC = 8;
  private static float[] arr = {0, 0.1f, 0.12f, 0.123f, 0.1234f, 0.12345f, 0.123456f, 0.1234567f, 0.123f};
  private int selectedIdx;
  private final static String SCIENTIFIC_FORMAT = "0.###E0";
  private final static DecimalFormat scientific = new DecimalFormat(SCIENTIFIC_FORMAT);
  public String[] getFormats() {
    String formats[] = new String[arr.length];
    NumberFormat nf = NumberFormat.getNumberInstance();
    int i = 0;
    for (; i < arr.length - 1; i++) {
      nf.setMaximumFractionDigits(i);
      formats[i] = nf.format(arr[i]);
    }
    nf.setMaximumFractionDigits(3);
    formats[i] = nf.format(arr[i]) + "E";
    return formats;
  }

//  public int getMaxFractionDigits() {
//    return maxFractionDigits;
//  }
//
//  public void setMaxFractionDigits(int maxFractionDigits) {
//    this.maxFractionDigits = maxFractionDigits;
//  }
  public int getSelectedIdx() {
    return selectedIdx;
  }
  public void setSelectedIdx(int selectedIdx) {
    this.selectedIdx = selectedIdx;
  }
  public void loadDefault() {
    selectedIdx = 4;
  }
  public String format(double d) {
    if (getSelectedIdx() == THIS_IDX_IS_SCIENTIFIC)
      return scientific.format(d);
    NumberFormat nf = NumberFormat.getNumberInstance();
    nf.setMaximumFractionDigits(getSelectedIdx());
    nf.setGroupingUsed(false);
    return nf.format(d);
  }
  public String toString() {
    return "selectedIdx = " + selectedIdx + ", arr[selectedIdx] = " + arr[selectedIdx];
  }
}
