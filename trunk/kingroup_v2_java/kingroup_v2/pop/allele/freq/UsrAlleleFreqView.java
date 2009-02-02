package kingroup_v2.pop.allele.freq;
import pattern.mvc.MVCTableView;

import javax.swing.*;
import javax.swingx.tablex.ReadOnlyTableModel;
import javax.utilx.arrays.StrMtrx;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 16/09/2005, Time: 12:49:16
 */
public class UsrAlleleFreqView extends MVCTableView {
  private UsrAlleleFreq userAlleleFreq;
  public UsrAlleleFreqView(UsrAlleleFreq model, String delim) {
    userAlleleFreq = model;
    JTable view = makeView(model, delim);
    assemble(view);
  }
  private JTable makeView(UsrAlleleFreq freq, String delim) {
    int nL = freq.getNumLoci();
    int nA = freq.getMaxNumAlleles();
    String[][] rowData = new String[nA][nL]; // loci are in columns
    String[] columnNames = new String[nL];
    StrMtrx.set(rowData, delim);
    for (int L = 0; L < nL; L++) {
      columnNames[L] = freq.getLocusId(L) + delim;
      for (int a = 0; a < nA; a++) {
        AlleleFreqPair pair = freq.get(L, a);
        if (pair == null)
          continue;
        rowData[a][L] = pair.toString(delim);
      }
    }
    return new JTable(new ReadOnlyTableModel(rowData, columnNames));
  }
  public UsrAlleleFreq getUserAlleleFreq() {
    return userAlleleFreq;
  }
}
