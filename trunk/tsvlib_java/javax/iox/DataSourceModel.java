package javax.iox;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
public class DataSourceModel {
  private ArrayList lines_ = new ArrayList();
  private int start_ = 0;
  public DataSourceModel(DataSourceModel from) {
    copyFrom(from);
  }
  private void copyFrom(DataSourceModel from) {
    setFileName(from.getFileName());
    for (int i = 0; i < from.size(); i++) {
      lines_.add(from.get(i));
    }
  }
  public DataSourceModel(File file) {
    read(file);
    setFileName(file.getName());
  }
  public void setStartIdx(int s) {
    if (s < 0) {
      start_ = 0;
      return;
    }
    start_ = s;
  }
  public int getStartIdx() {
    return start_;
  }
  public int size() {
    if (lines_ == null)
      return 0;
    return lines_.size();
  }
  public String get(int i) {
    if (lines_ == null)
      return "";
    if (lines_.isEmpty())
      return "";
    if (i < 0 || i >= size())
      return "";
    return (String) lines_.get(i);
  }
  public void add(String s) {
    lines_.add(s);
  }
  public void set(int index, String s) {
    lines_.set(index, s);
  }
  public DefaultListModel getDefaultListModel() {
    DefaultListModel model = new DefaultListModel();
    for (int i = 0; i < size(); i++)
      model.addElement(get(i));
    return model;
  }
  private String fileName_;
  public void setFileName(String v) {
    fileName_ = v;
  }
  public String getFileName() {
    return (fileName_);
  }
  public void read(File file) {
    String path = null;
    try {
      path = file.getCanonicalPath();
      BufferedReader from = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
      String s = null;
      while ((s = from.readLine()) != null) add(s);
    }
    catch (IOException e) {
      String error = "" + e + " while reading from " + path;
      add(error);
    }
  }
  public void replace(char c, String delim) {
    for (int idx = 0; idx < size(); idx++) {
      String s = get(idx);
      if (s.indexOf(c) == -1)
        continue;
      StringBuffer buff = new StringBuffer(s);
      int i = -1;
      while ((i = buff.indexOf("\t")) != -1) {
        buff.replace(i, i + 1, delim);
      }
      set(idx, buff.toString());
    }
  }
}
