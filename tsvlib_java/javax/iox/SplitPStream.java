package javax.iox;
import java.io.PrintStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.ArrayList;
/**
 * Copyright dmitry.konovalov@jcu.edu.au Date: 15/09/2008, Time: 09:42:02
 */
public class SplitPStream extends PrintStream {
  private ArrayList<PrintStream> arr = new ArrayList<PrintStream>();
  public SplitPStream(OutputStream from) {
    super(from);
  }

  public void add(PrintStream ps) {
    arr.add(ps);
  }
//  public void println(Object s) { super.println(s); for (PrintStream ps : arr)  ps.println(s); }
//  public void print(Object s)   { super.print(s);   for (PrintStream ps : arr)  ps.print(s); }
//  public void println(String s) { super.println(s); for (PrintStream ps : arr)  ps.println(s); }
//  public void print(String s)   { super.print(s);   for (PrintStream ps : arr)  ps.print(s); }
  public void close()           { super.close();    for (PrintStream ps : arr)  ps.close(); }
  public void flush()           { super.flush();    for (PrintStream ps : arr)  ps.flush(); }
  public void write(int b)      { super.write(b);   for (PrintStream ps : arr)  ps.write(b); }
  public void write(byte[] b)  throws IOException { super.write(b);    for (PrintStream ps : arr)  ps.write(b); }
  public void write(byte[] b, int off, int len)   { super.write(b, off, len);    for (PrintStream ps : arr)  ps.write(b, off, len); }
}