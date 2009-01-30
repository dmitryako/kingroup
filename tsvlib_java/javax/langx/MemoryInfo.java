package javax.langx;
/** JKinship    Copyright (C) 2003  Dr.D.A.Konovalov
 */
import java.text.NumberFormat;
public class MemoryInfo {
  private StringBuffer buff = new StringBuffer();
  private Runtime runtime = Runtime.getRuntime();
  private final static double SCALE = 1.e-6;
  private NumberFormat FORMAT = NumberFormat.getNumberInstance();
  public MemoryInfo() {
//    System.gc();
    FORMAT.setMaximumFractionDigits(1);
  }
  public String getStatus() {
    buff.setLength(0);
    buff.append("Free/Total memory [MB]: ");
    buff.append(toStringFreeMemory()).append("/");
    buff.append(toStringTotalMemory());
//        + " / " + nf_.formatLog((double) runtime.maxMemory() * SCALE)
    return buff.toString();
  }
  public String toString() {
    FORMAT.setMinimumFractionDigits(1);
    return "Java virtual machine:"
      + " \nFree Memory = " + toStringFreeMemory()
      + " \nTotal Memory = " + toStringTotalMemory();
  }
  public String toStringFreeMemory() {
    return FORMAT.format((double) runtime.freeMemory() * SCALE) + "MB";
  }
  public String toStringTotalMemory() {
    return FORMAT.format((double) runtime.totalMemory() * SCALE) + "MB";
  }
}