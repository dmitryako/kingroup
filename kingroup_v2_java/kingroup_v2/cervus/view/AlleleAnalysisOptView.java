package kingroup_v2.cervus.view;
import kingroup_v2.cervus.AlleleAnalysisByRow;
import kingroup_v2.cervus.Cervus;
import tsvlib.project.ProjectLogger;

import javax.swing.*;
import javax.swingx.panelx.GridBagView;
import java.awt.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 3/03/2006, Time: 10:03:12
 */
public class AlleleAnalysisOptView extends GridBagView {
  private static ProjectLogger log = ProjectLogger.getLogger(AlleleAnalysisOptView.class.getName());
  private JCheckBox lociByCol = new JCheckBox("Loci by columns");

  public AlleleAnalysisOptView(Cervus model)
  {
    init();
    loadFrom(model);
    AlleleAnalysisByRow a = new AlleleAnalysisByRow();
    assemble(a);
  }

  private void assemble(AlleleAnalysisByRow a)
  {
    endRow(lociByCol);
    for (int i = 0; i < a.getNumVals(); i++)
    {
      startRow(new JLabel(a.getValName(i)));
      endRow(new JLabel(a.getValTip(i)));
    }
  }

  private void loadFrom(Cervus model)
  {
    lociByCol.setSelected(model.getLociByCol());
  }

  public Dimension getMinimumSize() {
//    log.info("curr min size = " + super.getMinimumSize());
//    log.info("curr pref size = " + super.getPreferredSize());
    int EXTRA_SIZE = 10;
    Dimension d = getPreferredSize();
    return new Dimension(d.width + EXTRA_SIZE, d.height + EXTRA_SIZE);
  }
//  public abstract void loadTo(Kinship model);
//  protected abstract void loadFrom(Kinship model, PopFormat pop);
  public void init() {
    //setBorder(BorderFactory.createLoweredBevelBorder());
    getStartRow().anchor = GridBagConstraints.NORTHWEST;
  }

  public void loadTo(Cervus model)
  {
    model.setLociByCol(lociByCol.isSelected());
  }
}