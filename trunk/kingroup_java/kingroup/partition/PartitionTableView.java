package kingroup.partition;
/* Copyright (C) 2003-2004  Dr. Dmitry Konovalov.
* This code is licensed under the GPL license (see www.gnu.org) for academic,
* not-for-profit use or for use within other Open Source software (see www.opensource.org).
* See www.kingroup.org for more details.
*/
import kingroup.genetics.PartitionSearchModel;
import kingroup.genotype.Genotype;
import kingroup.model.HypothesisModel;
import kingroup.population.PopGroup;
import kingroup.project.KinGroupProjectV1;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;
public class PartitionTableView {
  private int NUM_INFO_COLUMNS = 2;
  private PartitionEngine engine_ = null;
  private PopGroup data_ = null;
  private Vector header_ = null;
  final private PartitionModel model_;
  public PartitionTableView(PartitionEngine sub) {
    engine_ = sub;
    model_ = sub.getModel();
    data_ = sub.getGenotypeData();
  }
  // AbstractTableModel
  public DefaultTableModel getDefaultTableModel() {
    DefaultTableModel model = new PrivateTableModel(getRowCount()
      , getColumnCount());
    header_ = buildHeader();
    if (header_ != null && header_.size() > 0) {
      model.setColumnIdentifiers(header_);
    }
    for (int r = 0; r < getRowCount(); r++) {
      for (int c = 0; c < getColumnCount(); c++) {
        model.setValueAt(getValueAt(r, c), r, c);
      }
    }
    return model;
  }
  private int getRowCount() {
    return engine_.getPartitionArray().length;
  }
  private int getColumnCount() {
    return data_.size() + NUM_INFO_COLUMNS;
  }
  private Object getValueAt(int row, int col) {
    if (engine_ == null
      || engine_.getPartitionArray() == null)
      return " ";
    PartitionSequence[] subgroups = engine_.getPartitionArray();
    HypothesisModel hypo = model_.getHypoPrimModel();
    if (col == 0)
      return "" + (row + 1);
    if (col == 1)
      return hypo.format(subgroups[row].getLog() - subgroups[0].getLog());
    int subCol = col - NUM_INFO_COLUMNS; // remember view may have extra columns
    PartitionSearchModel search = KinGroupProjectV1.getInstance().getKinGroupSearchModel();
    Genotype genoCol = data_.getGenotype(subCol);
    PartitionSequence seq = subgroups[row];
    String groupId = seq.getGroup(genoCol).getId();
    if (search.getDisplaySearchSequence()) {
      Genotype geno = seq.getSequence(subCol);
      String seqId = null;
      if (geno == null)
        seqId = "na";
      else
        seqId = geno.getIdView(hypo);
      return groupId + " " + seqId;
    } else
      return groupId;
  }
  private Vector buildHeader() {
    if (engine_ == null)
      return null;
    Vector header = new Vector();
    header.add("#");
    header.add("L[i]/L[1]");
    HypothesisModel hypo = model_.getHypoPrimModel();
    for (int c = 0; c < data_.size(); c++) {
      Genotype geno = data_.getGenotype(c);
      header.add(geno.getIdView(hypo));
    }
    return header;
  }
  private class PrivateTableModel extends DefaultTableModel {
    public PrivateTableModel(int row, int column) {
      super(row, column);
    }
    public boolean isCellEditable(int row, int column) {
      return false;
    }
  }
}
