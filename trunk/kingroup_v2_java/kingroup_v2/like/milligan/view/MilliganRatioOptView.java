package kingroup_v2.like.milligan.view;
import kingroup_v2.Kingroup;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.like.view.KinshipRatioSimTableView;
import kingroup_v2.kinship.like.view.KinshipRatioSimView;
import kingroup_v2.kinship.view.KinshipDisplayWithLogView;
import kingroup_v2.kinship.view.KinshipRatioOptViewI;
import kingroup_v2.like.milligan.Milligan;
import kingroup_v2.like.milligan.MilliganIBD;

import javax.swing.*;
import javax.swingx.JTabbedPaneX;
import javax.swingx.panelx.GridBagView;
import java.awt.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 23/03/2006, Time: 12:45:44
 */
public class MilliganRatioOptView
  extends GridBagView
  implements KinshipRatioOptViewI
{
//  private PedigreeRatioHistView histView;
  private JTabbedPaneX tabbedPane;

  private MilliganIBDView primView;
  private int primIdx = -1;
  private JRadioButton primFocus;

  private MilliganIBDArrView nullView;
  private int nullIdx = -1;
  private JRadioButton nullFocus;

  private KinshipRatioSimView simView;
//  private int simIdx = -1;
//  private JRadioButton simFocus;

  private KinshipDisplayWithLogView showView;
//  private int showIdx = -1;
//  private JRadioButton showFocus;

  public MilliganRatioOptView(Kingroup model) {
    init();
    loadFrom(model);
    assemble();
  }
  public void setPValueEnabled(boolean b) {simView.setPValueEnabled(b);}

  public void loadFrom(Kingroup kingroup) {
    Kinship kinship = kingroup.getKinship();
    Milligan model = kingroup.getMilligan();
    MilliganIBD prim = model.getPrimIBD();
    primView = new MilliganIBDView(prim);
    nullView = new MilliganIBDArrView(model);
//    hapView = new KinshipHaploidOptView(model);
    showView = new KinshipDisplayWithLogView(kinship);
    simView = new KinshipRatioSimView(kinship);
  }
  public void loadTo(Kingroup kingroup) {
    Milligan model = kingroup.getMilligan();
    Kinship kinship = kingroup.getKinship();
    MilliganIBD prim = model.getPrimIBD();
    primView.loadTo(prim);
    nullView.loadTo(model);
    simView.loadTo(kinship);
    showView.loadTo(kinship);
  }

  private void init() {
    primFocus = new JRadioButton();
    nullFocus = new JRadioButton();
//    simFocus = new JRadioButton();
//    showFocus = new JRadioButton();
    ButtonGroup group = new ButtonGroup();
    group.add(primFocus);
    group.add(nullFocus);
//    group.add(simFocus);
//    group.add(showFocus);
    tabbedPane = new JTabbedPaneX(JTabbedPane.TOP);
//    graphView.setMinimumSize(new Dimension(200, 200));
//    graphView.setPreferredSize(new Dimension(200, 200));
  }
  private void assemble() {

    primIdx = tabbedPane.processView(primView, primIdx, primFocus.isSelected()
      , "Primary", "Primary hypothesis");
    nullIdx = tabbedPane.processView(nullView, nullIdx, nullFocus.isSelected()
      , "Null", "Null hypothesis");
//    simIdx = tabbedPane.processView(simView, simIdx, simFocus.isSelected()
//      , "Significance", "Statistical significance");
//    showIdx = tabbedPane.processView(showView, showIdx, showFocus.isSelected()
//      , "Display", "Display options");

    tabbedPane.setSelectedIndex(0);
    startRow(tabbedPane);
    startRow(showView);
    endRow(simView);
  }
  public void setRatioSimTableView(KinshipRatioSimTableView view) {
    simView.setTableView(view);
  }

  public Dimension getMinimumSize(){
    return new Dimension(300, 250);
  }
  public boolean getDisplayLog() {
    return showView.getDisplayLog();
  }
  public MilliganIBDView getPrimIBDView() {
    return primView;
  }
  public MilliganIBDArrView getNullIBDView() {
    return nullView;
  }
  public KinshipRatioSimView getRatioSimView() {
    return simView;
  }
}

