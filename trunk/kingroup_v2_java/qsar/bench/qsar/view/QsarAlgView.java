package qsar.bench.qsar.view;
import pattern.ucm.UCController;
import qsar.bench.QBenchFrame;

import javax.swing.*;
import javax.swingx.tablex.TableWithApplyUI;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 9/03/2007, 13:50:53
 */
public class QsarAlgView extends TableWithApplyUI {
  private final QsarAlgOptView optView;
  public QsarAlgView(QsarAlgOptView optView, UCController runOnApply) {
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
