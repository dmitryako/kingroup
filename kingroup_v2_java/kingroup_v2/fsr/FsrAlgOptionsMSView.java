package kingroup_v2.fsr;
import kingroup_v2.Kingroup;
import pattern.ucm.UCController;
import pattern.ucm.AdapterUCCToALThread;

import javax.swingx.text_fieldx.IntField;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 10/10/2005, Time: 11:06:15
 */
public class FsrAlgOptionsMSView extends FsrAlgOptView {
  private IntField wSize;
  private static final int FIELD_SIZE = 3;
  public FsrAlgOptionsMSView(Kingroup model) {
    init();
    loadFrom(model);
    assemble();
  }
  private void init() {
    wSize = new IntField(FIELD_SIZE, 1, 100);
    wSize.setToolTipText("window size [1, 100]");
  }
  public void loadTo(Kingroup model) {
    super.loadTo(model);
    model.setMsAlgWindSize(wSize.getInput());
  }
  protected void loadFrom(Kingroup from) {
    super.loadFrom(from);
    wSize.setValue(from.getMsAlgWindSize());
  }
  protected void assemble() {
    endRow(specieView);
    endRow(displayView);
    endRow(wSize);
  }
  public void onOptionChange(UCController uc) {
    super.onOptionChange(uc);
    wSize.addActionListener(new AdapterUCCToALThread(uc));
  }
}
