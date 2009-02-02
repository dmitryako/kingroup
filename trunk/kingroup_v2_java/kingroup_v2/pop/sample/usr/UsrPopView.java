package kingroup_v2.pop.sample.usr;
import pattern.mvc.MVCTableView;

import javax.swing.*;
import javax.swingx.tablex.ReadOnlyTableModel;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 16/09/2005, Time: 13:47:06
 */
public class UsrPopView extends MVCTableView
{
  public static final String ID = "id";
  public static final String GROUP = "group";
  public static final String MID = "mid";
  public static final String PID = "pid";

  private UsrPopSLOW pop;
  private String[][] rowData;// loci are in columns

  public UsrPopView(UsrPopSLOW pop) {
    this.pop = pop;
    JTable view = makeView();
    assemble(view);
  }
  public JTable makeView(//KinshipFileFormat format
                         ) {
    int firstLocus = getFirstLocusIdx();
    int nCols = pop.getNumLoci() + firstLocus;
    int n = pop.size();
    rowData = new String[n][nCols]; // loci are in columns
    for (int i = 0; i < n; i++) {
      for (int L = 0; L < pop.getNumLoci(); L++) {
//        rowData[i][firstLocus + L] = geno.get(L).toString();
        rowData[i][firstLocus + L] = pop.getLocus(i, L).toString();
      }
      int idx = getIdIdx();
      if (pop.getHasId()) {
        rowData[i][idx] = pop.getId(i);
      } else { // DISPLAY A DEFAULT ID
        rowData[i][idx] = ID + (i + 1);
      }
      idx = getGroupIdIdx();
      if (idx != -1) {
        rowData[i][idx] = pop.getGroupId(i);
      }
      idx = getMatIdIdx();
      if (idx != -1) {
        rowData[i][idx] = pop.getMatId(i);
      }
      idx = getPatIdIdx();
      if (idx != -1) {
        rowData[i][idx] = pop.getPatId(i);
      }
    }
    String[] columnNames = makeHeader(pop);
    return new JTable(new ReadOnlyTableModel(rowData, columnNames));
  }
  public String getId(int i) {
    return rowData[i][getIdIdx()];
  }
  public String getGroupId(int i) {
    int idx = getGroupIdIdx();
    if (idx == -1) {
      return "";
    }
    return rowData[i][idx];
  }
  public String[] makeHeader(UsrPopSLOW pop//, KinshipFileFormat format
                             ) {
    int firstLocus = getFirstLocusIdx();
    int nCols = pop.getNumLoci() + firstLocus;
    String[] res = new String[nCols];
    for (int i = 0; i < pop.getNumLoci(); i++) {
      String locus = pop.getLocusId(i);
      res[firstLocus + i] = locus;
    }
    int idx = getIdIdx();
    if (idx != -1) {
      res[idx] = ID; // NOTE! user must see if id column is actually the id column
    }
    idx = getGroupIdIdx();
    if (idx != -1) {
      res[idx] = GROUP; // NOTE! user must see if GROUP column is actually the GROUP column
    }
    idx = getMatIdIdx();
    if (idx != -1) {
        res[idx] = MID;
    }
    idx = getPatIdIdx();
    if (idx != -1) {
        res[idx] = PID;
    }
    return res;
  }
  public int getFirstLocusIdx() {
    int firstLocus = 0;
    //    if (pop.getHasId())
    // NOTE!!! always display an getId
    firstLocus++;
    if (pop.getHasGroupId())
      firstLocus++;
    if (pop.getHasPatId())
      firstLocus++;
    if (pop.getHasMatId())
      firstLocus++;
    return firstLocus;
  }
  public int getIdIdx() {
    // NOTE!!! always display an getId
    if (!pop.getHasId())
      return getFirstLocusIdx() - 1; // JUST BEFORE THE FIRST LOCUS COLUMN
    int res = 0;
    if (pop.getHasGroupId())
      res++;
    return res;
  }
  public int getGroupIdIdx() {
    if (!pop.getHasGroupId())
      return -1;
    int res = 0;
    return res;
  }
  public int getMatIdIdx() {
    if (!pop.getHasMatId())
      return -1;
    int res = 0;
    if (pop.getHasId())
      res++;
    if (pop.getHasGroupId())
      res++;
    return res;
  }
  public int getPatIdIdx() {
    if (!pop.getHasPatId())
      return -1;
    int res = 0;
    if (pop.getHasId())
      res++;
    if (pop.getHasGroupId())
      res++;
    if (pop.getHasMatId())
      res++;
    return res;
  }
  public UsrPopSLOW getUserPop() {
    return pop;
  }
}
