package javax.swingx.tablex;
import pattern.ucm.UCController;
import tsvlib.project.ProjectLogger;

import javax.swing.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 21/09/2005, Time: 16:40:51
 */
public class UCClearTable implements UCController {
  private static ProjectLogger log = ProjectLogger.getLogger(UCClearTable.class.getName());
  private TableViewWithOpt tableView;
  public UCClearTable(TableViewWithOpt likeView) {
    this.tableView = likeView;
  }
  public boolean run() {
//    log.info("called");
    tableView.setTableView(new JTable());
    tableView.assembleWithOpt(tableView.getOrientation());
    return true;
  }
}
