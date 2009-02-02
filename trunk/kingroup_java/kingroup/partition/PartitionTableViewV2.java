package kingroup.partition;
import kingroup.genotype.Genotype;
import kingroup.model.HypothesisModel;
import kingroup.population.PopGroup;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 4/03/2005, Time: 16:59:40
 */
public class PartitionTableViewV2 {
  private int NUM_INFO_COLUMNS = 0;
  private PartitionEngineV2 engine_ = null;
  private PopGroup data_ = null;
  private Vector header_ = null;
  final private PartitionModel model_;
  public PartitionTableViewV2(PartitionEngineV2 sub) {
    engine_ = sub;
    model_ = sub.getModel();
    data_ = sub.getGenotypeData();
  }
  // AbstractTableModel
  public DefaultTableModel getDefaultTableModel() {
    DefaultTableModel model = new PartitionTableViewV2.PrivateTableModel(getRowCount()
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
//      return "N/A";
    if (engine_ == null || engine_.getPartitionArray() == null)
      return " ";
    DRAlgPartitionV1[] partitions = engine_.getPartitionArray();
    HypothesisModel hypo = model_.getHypoPrimModel();
//      if (col == 0)
//         return "" + (row + 1);
//      if (col == 1)
//         return hypo.formatLog(partitions[row].getLog() - partitions[0].getLog());
    int genoIdx = col - NUM_INFO_COLUMNS; // remember view may have extra columns
    DRAlgPartitionV1 part = partitions[row];
    DRAlgClusterV1 group = part.getClusterByGenoIdxSLOW(genoIdx);
    String groupId = group.getClusterId();
    return groupId;
  }
  private Vector buildHeader() {
    if (engine_ == null)
      return null;
    Vector header = new Vector();
//      header.addLine("#");
//      header.addLine("L[i]/L[1]");
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

