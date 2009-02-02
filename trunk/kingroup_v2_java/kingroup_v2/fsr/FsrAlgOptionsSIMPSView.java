package kingroup_v2.fsr;
import kingroup_v2.Kingroup;
import pattern.ucm.UCController;
import pattern.ucm.AdapterUCCToALThread;

import javax.swing.*;
import javax.swingx.panelx.GridBagView;
import javax.swingx.text_fieldx.IntTextField;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 7/10/2005, Time: 09:07:17
 */
public class FsrAlgOptionsSIMPSView extends FsrAlgOptView {
  private IntTextField loopBreaker;
  private IntTextField nIter;
  private static final int FIELD_SIZE = 7;
  public FsrAlgOptionsSIMPSView() {

  }
  public FsrAlgOptionsSIMPSView(Kingroup model) {
    init();
    loadFrom(model);
    assemble("SIMPSON");
  }
  protected void init() {
    nIter = new IntTextField(FIELD_SIZE, 100, 1000000);
    nIter.setToolTipText("[100, 1E+6]");

    loopBreaker = new IntTextField(FIELD_SIZE, -1, 10000);
    loopBreaker.setToolTipText("[-1, 1E+4] Set to -1 to disable");
  }
  public void loadTo(Kingroup model) {
    super.loadTo(model);
    model.setSimpsAlgNumIter(nIter.getInput());
  }
  protected void loadFrom(Kingroup from) {
    super.loadFrom(from);
    nIter.setValue(from.getSimpsAlgNumIter());
    loopBreaker.setValue(from.getSimpsAlgLoopBreaker());
  }
  protected void assemble(String title) {
    endRow(specieView);
    endRow(displayView);
    GridBagView panel = new GridBagView(title);
    JLabel label = new JLabel("iterations");
    label.setToolTipText("maximum number of iterations");
    panel.startRow(label);
    panel.endRow(nIter);
    label = new JLabel("loop breaker");
    label.setToolTipText("iterative process stops if the same partition persists for this number of iterations");
    panel.startRow(label);
    panel.endRow(loopBreaker);
    endRow(panel);
  }
  public void onOptionChange(UCController uc) {
    super.onOptionChange(uc);
    nIter.addActionListener(new AdapterUCCToALThread(uc));
  }
}
