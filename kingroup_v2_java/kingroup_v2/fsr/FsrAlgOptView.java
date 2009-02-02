package kingroup_v2.fsr;
import kingroup_v2.Kingroup;
import pattern.ucm.UCController;

import javax.swingx.panelx.GridBagView;
import java.awt.*;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 10/10/2005, Time: 11:13:32
 */
public class FsrAlgOptView extends GridBagView {
  protected FsrSpecieView specieView;
  protected DisplayOptView displayView;
  public FsrAlgOptView()
  {
    init();
  }
  private void init()
  {
    getStartRow().anchor = GridBagConstraints.NORTHWEST;    
    getEndRow().anchor = GridBagConstraints.NORTHWEST;
  }
  public void loadTo(Kingroup model) {
    specieView.loadTo(model);
    displayView.loadTo(model);
  }
  protected void loadFrom(Kingroup project) {
    specieView = new FsrSpecieView(project);
    displayView = new DisplayOptView(project);
  }
  protected void assemble() {
    startRow(specieView);
    endRow(displayView);
  }
  public void onOptionChange(UCController uc) {
    specieView.onSpecieChange(uc);
    displayView.onViewTypeChange(uc);
    displayView.onSortByGroupChange(uc);
  }
  public Dimension getMinimumSize() {
    int EXTRA_SIZE = 20;
    Dimension d = getPreferredSize();
    return new Dimension(d.width + EXTRA_SIZE, d.height + EXTRA_SIZE);
  }
}
