package kingroup_v2.pop.sample.builder.groups;
import kingroup_v2.pop.sample.PopBuilderModel;

import javax.swing.*;
import javax.swingx.panelx.GridBagView;
import javax.swingx.text_fieldx.IntTextField;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 7/05/2006, Time: 11:30:06
 */
public class PopBuilderHalfSibOptView    extends GridBagView
  implements PopBuilderViewI
{
  protected final static int FIELD_SIZE = 2;
  protected IntTextField nSires;
  protected JLabel nSiresLbl;
  protected IntTextField nDams;
  protected JLabel nDamsLbl;
  protected IntTextField nOffspring;
  protected JLabel nOffspringLbl;

//  public PopBuilderHalfSibOptView() {
//
//  }
  public PopBuilderHalfSibOptView(PopBuilderModel bean) {
    init();
    loadFrom(bean);
    assemble();
  }

  protected void init() {
    nSires = new IntTextField(FIELD_SIZE, 1, 50);
    nSires.setMinimumSize(nSires.getPreferredSize());
    nSiresLbl = new JLabel("Sires");
    nSiresLbl.setToolTipText("number of sires");

    nDams = new IntTextField(FIELD_SIZE, 1, 50);
    nDams.setMinimumSize(nDams.getPreferredSize());
    nDamsLbl = new JLabel("Dams");
    nDamsLbl.setToolTipText("number of dams within sire");

    nOffspring = new IntTextField(FIELD_SIZE, 1, 50);
    nOffspring.setMinimumSize(nOffspring.getPreferredSize());
    nOffspringLbl = new JLabel("Offspring");
    nOffspringLbl.setToolTipText("number of offspring within dam");
  }

  private void assemble() {
    startRow(nSires);
    endRow(nSiresLbl);
    startRow(nDams);
    endRow(nDamsLbl);
    startRow(nOffspring);
    endRow(nOffspringLbl);
  }
  public void loadFrom(PopBuilderModel model) {
    nSires.setValue(model.getNumSires());
    nDams.setValue(model.getNumDams());
    nOffspring.setValue(model.getNumOffspring());
  }
  public void loadTo(PopBuilderModel model) {
    model.setBuilderType(PopBuilderModel.HALF_SIB_BUILDER);
    model.setNumSires(nSires.getInput());
    model.setNumDams(nDams.getInput());
    model.setNumOffspring(nOffspring.getInput());
  }
}
