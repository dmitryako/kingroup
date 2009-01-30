package javax.swingx;
/** JKinship    Copyright (C) 2003  Dr.D.A.Konovalov
 */
import javax.langx.MemoryInfo;
import javax.swing.*;
import java.awt.*;

public class ProgressWnd extends ProgressMonitor {
  private static ProgressWnd instance = null;
  private int maxVal = 100;
  private int minVal = 0;
  private MemoryInfo memory = new MemoryInfo();
  private static Component parent = null;
  public static ProgressWnd getInstance() {
    if (instance != null)
      return instance;
    instance = new ProgressWnd(parent);
    return instance;
  }
  public void close() {
    super.close();
    instance = null;
  }
  synchronized public boolean isCanceled(int curr_val, int min_val, int max_val) {
    if (isCanceled())
      return true;
    if (minVal != min_val)
      setMinimum(min_val);
    if (maxVal != max_val)
      setMaximum(max_val);
    setNote(memory.getStatus());
    setProgress(curr_val);
    return false;
  }
  public ProgressWnd(Component frame) {
    super(frame, "Monitor", "", 1, 100);
    setMillisToPopup(1000);  // default is 2000
  }
  public ProgressWnd(Component frame, String name) {
    super(frame, name, "", 1, 100);
    setMillisToPopup(1000);  // default is 2000
  }
  public static ProgressWnd makeInstance(Component newframe) {
    parent = newframe;
    instance = new ProgressWnd(parent);
    return instance;
  }
  public static void setInstance(ProgressWnd v) {
    instance = v;
  }
}
