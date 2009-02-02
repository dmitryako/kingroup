package kingroup_v2.kinship.view;
import pattern.ucm.UCController;
import tsvlib.project.ProjectLogger;

import javax.swingx.panelx.GridBagView;
import java.awt.*;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/09/2005, Time: 08:06:19
 */
public abstract class KinshipOptionsView extends GridBagView {
  private static ProjectLogger log = ProjectLogger.getLogger(KinshipOptionsView.class.getName());
  protected KinshipHaploidOptView hapView;
  protected KinshipDisplayWithLogView showView;
  public KinshipOptionsView() {
    init();
  }
  public Dimension getMinimumSize() {
//    log.info("curr min size = " + super.getMinimumSize());
//    log.info("curr pref size = " + super.getPreferredSize());
    int EXTRA_SIZE = 10;
    Dimension d = getPreferredSize();
    return new Dimension(d.width + EXTRA_SIZE, d.height + EXTRA_SIZE);
  }
  public void init() {
    //setBorder(BorderFactory.createLoweredBevelBorder());
    getStartRow().anchor = GridBagConstraints.NORTHWEST;
  }
  public void onByGroupChange(UCController uc) {
    showView.onByGroupChange(uc);
  }
  public void onDisplayLogsChange(UCController uc) {
    showView.changeDisplayLogs(uc);
  }
  public void onTreatHaploidChange(UCController uc) {
    hapView.onTreatHaploidChange(uc);
  }
}
