package kingroup_v2.fsr.accuracy;
import kingroup_v2.Kingroup;
import kingroup_v2.fsr.DisplayOptView;
import kingroup_v2.fsr.FsrSpecieView;
import kingroup_v2.pop.sample.builder.groups.PopBuilderFullSibView;
import pattern.ucm.UCController;

import javax.swing.*;
import javax.swingx.panelx.GridBagView;
import java.awt.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 25/11/2005, Time: 09:53:39
 */
public class FsrAccuracyOptionsView extends GridBagView {
  protected PopBuilderFullSibView builderView;
  protected FsrSpecieView specieView;
  protected DisplayOptView displayView;
  protected AccuracyOptionsView accView;

  public FsrAccuracyOptionsView(Kingroup project)
  {
    init();
    loadFrom(project);
  }
  private void init() {
    getStartRow().anchor = GridBagConstraints.NORTHWEST;
    getEndRow().anchor = GridBagConstraints.NORTHWEST;
  }
  public void loadTo(Kingroup project) {
    builderView.loadTo(project.getPopBuilder());
    specieView.loadTo(project);
    displayView.loadTo(project);
    accView.loadTo(project);
  }
  protected void loadFrom(Kingroup project) {
    builderView = new PopBuilderFullSibView(project.getPopBuilder());
    specieView = new FsrSpecieView(project);
    displayView = new DisplayOptView(project);
    accView = new AccuracyOptionsView(project);
  }
  protected void assemble() {
    endRow(builderView);

    startRow(specieView);
    startRow(displayView);
    startRow(accView);
    endRow(new JPanel());
  }
  public void onOptionChange(UCController uc) {
//    specieView.onSpecieChange(uc);
//    displayView.onDisplayViewChange(uc);
//    displayView.onSortByGroupChange(uc);
  }
  public Dimension getMinimumSize() {
    int EXTRA_SIZE = 20;
    Dimension d = getPreferredSize();
    return new Dimension(d.width + EXTRA_SIZE, d.height + EXTRA_SIZE);
  }

  public void setAccurError(double accError)
  {
    accView.setAccurError(accError);
  }

  public void setAccurErrorStdDev(double stdDev)
  {
    accView.setAccurErrorStdDev(stdDev);
  }
}