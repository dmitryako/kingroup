package javax.iox;
import tsvlib.project.ProjectLogger;

import javax.stats.Stats;
import javax.stats.StatsRes;
import javax.utilx.DoubleArrayByInt;
import javax.utilx.arrays.vec.Vec;
import java.awt.geom.Point2D;
import java.io.*;
import java.util.Iterator;
import java.util.Set;

public class LOG
{
  private static ProjectLogger log = ProjectLogger.getLogger(LOG.class.getName());

  private static boolean traceOn_ = false;
  private static boolean errorOn_ = true;
  private static boolean toFileOn_ = false;
  private static String traceFileName = "defTrace.log";
  public static void setTrace(boolean v) {
    traceOn_ = v;
  }
  public static boolean isTraceOn() {
    return traceOn_;
  }
  public static void setError(boolean v) {
    errorOn_ = v;
  }
  public static void setToFile(boolean v) {
    toFileOn_ = v;
  }
  public static void setTraceFileName(String v) {
    traceFileName = v;
  }
  // PRIVATEs
  // If toFileOn_ is true then write all Trace data to a log file, appending the data to the end
  static private void writeToFile(Object ob, String s) {
    try {
      File fD = new File(traceFileName);
      PrintWriter out = new PrintWriter(new FileOutputStream(fD, true));
      if (ob == null)
        out.println(" [" + s + "]");
      else
        out.println(ob.getClass().getName() + " [" + s + "]");
      out.close();
    } catch (IOException e) {
    }
  }
  static private void print(Object from, String s) {
    if (from == null)
      System.out.println(" " + s);
    else
      System.out.println(from.getClass().getName() + " " + s);
  }
  static public void trace(Object from, String s) {
    if (!traceOn_) return;
    if (toFileOn_) writeToFile(from, s);
    print(from, s);
  }
  static public void report(Object from, Object obj) {
    print(from, obj.toString());
  }
  static private void error(Object from, String s) {
    if (!errorOn_) return;
    if (toFileOn_) writeToFile(from, "ERROR: " + s);
    print(from, "ERROR: " + s);
  }
  // ob+ob
  static public void trace(Object from, Object ob, Object ob2) {
    if (!traceOn_) return;
    if (ob2 == null)
      trace(from, ob.toString() + "null");
    else
      trace(from, ob.toString() + ob2);
  }
  static public void throwError(Object from, Object ob, Object ob2) {
    error(from, ob, ob2);
    throw new ArithmeticException(ob.toString() + ob2.toString());
  }
  static public void error(Object from, Object ob, Object ob2) {
    if (!errorOn_) return;
    if (ob2 == null)
      error(from, ob.toString() + "null");
    else
      error(from, ob.toString() + ob2);
  }
  // ob+ob[]
  static public void traceArray(Object from, String name, Object[] arr) {
    if (!traceOn_) return;
    for (int i = 0; i < arr.length; i++)
      traceArrayAt(from, name, i, arr);
  }
  static public void traceArrayAt(Object from, String name, int i, Object[] arr) {
    if (!traceOn_) return;
    trace(from, name + "[" + i + "]=" + arr[i]);
  }
  static public void traceArrayAt(Object from, String name, int i, double[] arr) {
    if (!traceOn_) return;
    trace(from, name + "[" + i + "]=" + arr[i]);
  }
  // ob+...+ob
  static public void trace(Object from, Object ob, Object ob2, Object ob3) {
    if (!traceOn_) return;
    trace(from, ob.toString() + ob2 + ob3);
  }
  static public void trace(Object from, Object ob, Object ob2, Object ob3, Object ob4) {
    if (!traceOn_) return;
    trace(from, ob.toString() + ob2 + ob3 + ob4);
  }
  static public void trace(Object from, Object ob, Object ob2, Object ob3, Object ob4, Object ob5) {
    if (!traceOn_) return;
    trace(from, ob.toString() + ob2 + ob3 + ob4 + ob5);
  }
  // ob+char,int,double,etc
  static public void trace(Object from, Object ob, boolean c) {
    if (!traceOn_) return;
    trace(from, ob.toString() + c);
  }
  static public void trace(Object from, Object ob, char c) {
    if (!traceOn_) return;
    trace(from, ob.toString() + c);
  }
  static public void trace(Object from, Object ob, int c) {
    if (!traceOn_) return;
    trace(from, ob.toString() + c);
  }
  static public void trace(Object from, Object ob, double c) {
    if (!traceOn_) return;
    trace(from, ob.toString() + c);
  }
  // ob+ob+char,int,double,etc
  static public void trace(Object from, Object ob, Object ob2, boolean d) {
    if (!traceOn_) return;
    trace(from, ob.toString() + ob2 + d);
  }
  static public void trace(Object from, Object ob, Object ob2, char d) {
    if (!traceOn_) return;
    trace(from, ob.toString() + ob2 + d);
  }
  static public void trace(Object from, Object ob, Object ob2, int d) {
    if (!traceOn_) return;
    trace(from, ob.toString() + ob2 + d);
  }
  static public void trace(Object from, Object ob, Object ob2, double d) {
    if (!traceOn_) return;
    trace(from, ob.toString() + ob2 + d);
  }
  // ob+ob+ob+char,int,double,etc
  static public void trace(Object from, Object ob, Object ob2, Object ob3, boolean d) {
    if (!traceOn_) return;
    trace(from, ob.toString() + ob2 + ob3 + d);
  }
  static public void trace(Object from, Object ob, Object ob2, Object ob3, char d) {
    if (!traceOn_) return;
    trace(from, ob.toString() + ob2 + ob3 + d);
  }
  static public void trace(Object from, Object ob, Object ob2, Object ob3, int d) {
    if (!traceOn_) return;
    trace(from, ob.toString() + ob2 + ob3 + d);
  }
  static public void trace(Object from, Object ob, Object ob2, Object ob3, double d) {
    if (!traceOn_) return;
    trace(from, ob.toString() + ob2 + ob3 + d);
  }
  public static void saveToFile(double[] a, double[] a2, double[] a3
    , String dirName, String fileName) {
    File dir = new File(dirName);
    dir.mkdir();
    saveToFile(a, a2, a3, dirName + File.separator + fileName);
  }
  public static void saveToFile(double[] a, double[] a2, double[] a3, double[] a4
    , String dirName, String fileName) {
    File dir = new File(dirName);
    dir.mkdir();
    saveToFile(a, a2, a3, a4, dirName + File.separator + fileName);
  }
  public static void saveToFile(double[] a, double[] a2
    , String dirName, String fileName) {
    File dir = new File(dirName);
    dir.mkdir();
    saveToFile(a, a2, dirName + File.separator + fileName);
  }
  public static void saveToFile(double[] a
    , String dirName, String fileName) {
    File dir = new File(dirName);
    dir.mkdir();
    saveToFile(a, dirName + File.separator + fileName);
  }
  public static void saveToFile(double[][] mtrx
    , String dirName, String fileName) {
    File dir = new File(dirName);
    dir.mkdir();
    saveToFile(mtrx, dirName + File.separator + fileName);
  }
  public static void saveToFile(double[] a, double[] a2, String fileName) {
    PrintWriter out = null;
    int size = Math.min(a.length, a2.length);
    try {
      out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
      for (int i = 0; i < size; i++) {
        double v = 0;
        if (i < a.length)
          v = a[i];
        double v2 = 0;
        if (i < a2.length)
          v2 = a2[i];
        out.println("" + i + ", " + v + ", " + v2);
      }
    }
    catch (IOException e) {
      javax.iox.LOG.error(null, "unable write to a file [", fileName);
      return;
    }
    finally {
      if (out != null)
        out.close();
    }
  }
  public static void saveToFile(double[] a, String fileName) {
    PrintWriter out = null;
    int size = a.length;
    try {
      out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
      for (int i = 0; i < size; i++) {
        double v = 0;
        if (i < a.length)
          v = a[i];
        out.println("" + i + ", " + v);
      }
    }
    catch (IOException e) {
      javax.iox.LOG.error(null, "unable write to a file [", fileName);
      return;
    }
    finally {
      if (out != null)
        out.close();
    }
  }
  public static void saveToFile(double[][] m, String fileName) {
    PrintWriter out = null;
    try {
      out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
      for (int r = 0; r < m.length; r++) {
        String s = Vec.toCSV(m[r]);
        out.println(s);
      }
    }
    catch (IOException e) {
      javax.iox.LOG.error(null, "unable write to a file [", fileName);
      return;
    }
    finally {
      if (out != null)
        out.close();
    }
  }
  public static void saveToFile(double[] a, double[] a2, double[] a3, String fileName) {
    PrintWriter out = null;
    try {
      out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
      for (int i = 0; i < a.length; i++) {
        out.println("" + i
          + ", " + a[i]
          + ", " + a2[i]
          + ", " + a3[i]
        );
      }
    }
    catch (IOException e) {
      javax.iox.LOG.error(null, "unable write to a file [", fileName);
      return;
    }
    finally {
      if (out != null)
        out.close();
    }
  }
  public static void saveToFile(double[] a, double[] a2, double[] a3, double[] a4, String fileName) {
    PrintWriter out = null;
    try {
      out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
      for (int i = 0; i < a.length; i++) {
        out.println("" + i
          + ", " + a[i]
          + ", " + a2[i]
          + ", " + a3[i]
          + ", " + a4[i]
        );
      }
    }
    catch (IOException e) {
      javax.iox.LOG.error(null, "unable write to a file [", fileName);
      return;
    }
    finally {
      if (out != null)
        out.close();
    }
  }
  public static void saveToFile(String txt, String fileName) {
    PrintWriter out = null;
    try {
      out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
      out.print(txt);
    }
    catch (IOException e) {
      javax.iox.LOG.error(null, "unable write to a file [", fileName);
      return;
    }
    finally {
      if (out != null)
        out.close();
    }
  }
  public static void saveToFile(DoubleArrayByInt data, String dirName, String fileName) {
    String output = dirName;
    File dir = new File(output);
    dir.mkdir();
    saveToFile(data, output + File.separator + fileName);
  }
  public static void saveToFile(String txt, String dirName, String fileName) {
    String output = dirName;
    File dir = new File(output);
    dir.mkdir();
    saveToFile(txt, output + File.separator + fileName);
  }
  public static void saveBoxplot(DoubleArrayByInt data, String dirName, String fileName) {
    String output = dirName;
    File dir = new File(output);
    dir.mkdir();
    saveBoxplot(data, output + File.separator + fileName);
  }
  public static void saveToFile(DoubleArrayByInt data, String fileName) {
    log.info("saveToFile(" + fileName);

    PrintWriter out = null;
    try {
      out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
//         out.println("key, mean, mean, stddev, median, q1, q3, max, maxreg, min, minreg, size");
      Set keys = data.keySet();
      for (Iterator it = keys.iterator(); it.hasNext();) {
        int key = ((Integer) it.next()).intValue();
        double[] arr = data.getArray(key);
        StatsRes stats = new Stats(arr);
//            BoxPlotItem item = new BoxPlotItem(arr);
        out.print(key);
        out.print(", ");
        out.print(stats.getMean());
        out.print(", ");
        out.print(stats.getS());
        out.print(", ");
//            out.print(item.getMedian());
//            out.print(", ");
//            out.print(item.getQ1());
//            out.print(", ");
//            out.print(item.getQ3());
//            out.print(", ");
//            out.print(item.getMaxOutlier());
//            out.print(", ");
//            out.print(item.getMaxRegularValue());
//            out.print(", ");
//            out.print(item.getMinOutlier());
//            out.print(", ");
//            out.print(item.getMinRegularValue());
//            out.print(", ");
        out.println(arr.length);
      }
    }
    catch (IOException e) {
      error(null, "unable write to a file [", fileName);
      return;
    }
    finally {
      if (out != null)
        out.close();
    }
  }
  public static void saveBoxplot(DoubleArrayByInt data, String fileName) {
//      NumberFormat nf = NumberFormat.getNumberInstance();
//      nf.setMaxFractionDigits();
//      return nf.format(d);
    PrintWriter out = null;
    try {
      out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
      Set keys = data.keySet();
      for (Iterator it = keys.iterator(); it.hasNext();) {
        int key = ((Integer) it.next()).intValue();
        double[] arr = data.getArray(key);
//            LOG.trace(null, DoubleArr.toCSV(arr));
//            out.print(key);
//            out.print(", ");
//            out.println(arr.length);
//            out.print(", ");
        out.println(Vec.toCSV(arr));
      }
    }
    catch (IOException e) {
      error(null, "unable write to a file [", fileName);
      return;
    }
    finally {
      if (out != null)
        out.close();
    }
  }
  public static void saveToFile(Point2D.Double[] arr, String name) {
    PrintWriter out = null;
    try {
      out = new PrintWriter(new BufferedWriter(new FileWriter(name)));
      for (int i = 0; i < arr.length; i++) {
        out.print(arr[i].getX());
        out.print(", ");
        out.println(arr[i].getY());
      }
    }
    catch (IOException e) {
      error(null, "unable write to a file [", name);
      return;
    }
    finally {
      if (out != null)
        out.close();
    }
  }
}