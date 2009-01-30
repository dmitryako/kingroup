package javax.iox;
import tsvlib.project.ProjectLogger;

import javax.swing.*;
import javax.swingx.ProgressWnd;
import javax.langx.SysProp;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 14/09/2005, Time: 12:02:15
 */
public class TextFile {
  private static ProjectLogger log = ProjectLogger.getLogger(TextFile.class.getName());
  private final ArrayList<String> lines;
  private String fileName;
  private ProgressWnd progress = null;

  public TextFile() {
    lines = new ArrayList<String>();
  }
  public void setFileName(String v) {
    fileName = v;
  }
  public String getFileName() {
    return fileName;
  }
  public void read(File file, JFrame frame) {
    if (file == null) {
      String error = "Unable to read from null file.";
      log.severe(error);
      JOptionPane.showMessageDialog(frame, error);
    }
    String path = null;
    try {
      path = file.getCanonicalPath();
      BufferedReader from = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
      String s = null;
      while ((s = from.readLine()) != null) lines.add(s);
    }
    catch (IOException e) {
      String error = e.toString()
        + "\nwhile reading from " + file.getName();
      lines.add(error);
      log.severe(error);
      JOptionPane.showMessageDialog(frame, error);
    }
  }
  public String toString() {
    StringBuffer buff = new StringBuffer();
    for (int i = 0; i < size(); i++) {
      if (i < size() - 1 // show last 
        && i >= ProjectLogger.MAX_N_ROWS_TO_STRING) {
        if (i == ProjectLogger.MAX_N_ROWS_TO_STRING)
          buff.append(" ... see ProjectLogger.MAX_N_ROWS_TO_STRING ").append(SysProp.EOL);
        continue;
      }
      String line = getLine(i);
      if (line.length() < ProjectLogger.MAX_LEN_TO_STRING)
        buff.append(line).append(SysProp.EOL);
      else {
        buff.append(line.substring(0, ProjectLogger.MAX_LEN_TO_STRING));
        buff.append(" ...").append(SysProp.EOL);
      }
    }
    return buff.toString();
  }
  // type safe access
  public String getLine(int i) {
    return lines.get(i);
  }
  public void addLine(String s) {
    lines.add(s);
  }
  public Object[] toArray() {
    return lines.toArray();
  }
  public int size() {
    return lines.size();
  }
  public void set(int i, String line) {
    lines.set(i, line);
  }
  public void write(File file, JFrame frame) {
    if (file == null) {
      String error = "Unable to write to null file.";
      log.severe(error);
      JOptionPane.showMessageDialog(frame, error);
    }
    try {
      BufferedWriter to = new BufferedWriter(new OutputStreamWriter(
        new FileOutputStream(file)));
      for (int i = 0; i < lines.size(); i++) {
        to.write(getLine(i));
        to.write(SysProp.EOL);
      }
      to.flush();
    }
    catch (IOException e) {
      String error = e.toString()
        + "\nwhile writing to " + file.getName();
      lines.add(error);
      log.severe(error);
      JOptionPane.showMessageDialog(frame, error);
    }
  }

  public void addLines(String lines)
  {
    StringTokenizer tokens = new StringTokenizer(lines, "\n", false);
    while (tokens.hasMoreTokens()) {
      addLine(tokens.nextToken().trim());
    }
  }

  public void trim()
  {
    for (Iterator<String> it = lines.iterator(); it.hasNext();) {
      String line = it.next();
      if (line.trim().length() == 0)
        it.remove();
    }
  }

}
