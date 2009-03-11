package kingroup_v2.pop.allele.freq;
import kingroup_v2.Kingroup;
import kingroup_v2.KingroupFrame;
import kingroup_v2.fsr.DisplayOptView;
import kingroup_v2.view.KingroupViewI;
import pattern.ucm.AdapterUCCToALThread;
import pattern.ucm.UCShowHelpMssg;
import tsvlib.project.ProjectFrame;
import tsvlib.project.ProjectLogger;

import javax.swing.*;
import javax.swingx.panelx.GridBagView;
import javax.swingx.text_fieldx.IntField;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: ESHFreeUser
 * Date: 7/06/2006
 * Time: 13:26:41
 * To change this template use File | Settings | File Templates.
 */
public class KonHegFreqAlgOptView extends GridBagView implements KingroupViewI {
  private static ProjectLogger log = ProjectLogger.getLogger(KonHegFreqAlgOptView.class);

  private IntField nIter;
  private static final int FIELD_SIZE = 7;
  private static final int MIN_N_ITER = 10;
  private static final int MAX_N_ITER = 1000000;

  protected DisplayOptView displayView;

  public KonHegFreqAlgOptView(Kingroup model) {
    init();
    loadFrom(model);
    assemble();
  }

  private void assemble()                  {
    endRow(displayView);
    GridBagView panel = new GridBagView("Algorithm");
    JLabel label = new JLabel("iterations");
    label.setToolTipText("number of iterations");
    panel.startRow(label);
    JButton bttn = ProjectFrame.makeHelpButton();
    bttn.addActionListener(new AdapterUCCToALThread(new UCShowHelpMssg(
      KonHegFreqAlg.REFERENCE, KingroupFrame.getInstance())));
    panel.endRow(bttn);
    panel.endRow(nIter);

    endRow(panel);
  }

  public void loadFrom(Kingroup from)  {
    displayView = new DisplayOptView(from);
    int n = from.getKonHegAlgNumIter();
    nIter.setValue(n);
  }

  public Dimension getMinimumSize() {
//    log.info("curr min size = " + super.getMinimumSize());
//    log.info("curr pref size = " + super.getPreferredSize());
    int EXTRA_SIZE = 10;
    Dimension d = getPreferredSize();
    return new Dimension(d.width + EXTRA_SIZE, d.height + EXTRA_SIZE);
  }
  public void init() {
    nIter = new IntField(FIELD_SIZE, MIN_N_ITER, MAX_N_ITER);
    nIter.setToolTipText("[min="+MIN_N_ITER+", max="+ MAX_N_ITER+"]");

    //setBorder(BorderFactory.createLoweredBevelBorder());
    getStartRow().anchor = GridBagConstraints.NORTHWEST;
  }

  public void loadTo(Kingroup model)
  {
    displayView.loadTo(model);
    model.setKonHegAlgNumIter(nIter.getInput());
  }
}

