package qsar.bench.descriptors.LFER;
import pattern.ucm.UCController;
import qsar.bench.QBenchFrame;

import javax.swing.*;
import javax.swingx.tablex.TableWithApplyUI;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 1/04/2007, 11:04:43
 */
public class LferPlattsTableView extends TableWithApplyUI {
  private final LferPlattsOptView optView;
  public LferPlattsTableView(LferPlattsOptView optView, UCController runOnApply) {
    super(optView, runOnApply);
    setFrame(QBenchFrame.getInstance());
    this.optView = optView;
    this.setButtonText("Calculate");
  }
  public void assemble(JPanel v) {
    setOptView(v);
    assembleWithOpt(JSplitPane.HORIZONTAL_SPLIT);
  }
}
