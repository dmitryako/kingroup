package kingroup_v2.kinship.view;

import kingroup_v2.Kingroup;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.view.KingroupViewI;
import pattern.ucm.UCController;

import javax.swingx.panelx.GridBagView;
import java.awt.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 26/01/2006, Time: 16:50:55
 */
public class PairwiseROptView
  extends GridBagView
  implements KingroupViewI
{
//  private static ProjectLogger log = ProjectLogger.getLogger(PairwiseROptView.class.getName());
  protected PairwiseREstimatorView rView;
  protected KinshipSortOptView sortView;
  protected KinshipRDisplayOptView showView;
  protected KinshipAlleleFreqOptView freqView;

  public PairwiseROptView(Kingroup model) {
    init();
    loadFrom(model);
    assemble();
  }
  public Dimension getMinimumSize() {
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
  public void onPValueChange(UCController uc) {
    showView.onPValueChange(uc);
  }
  public void loadTo(Kingroup model) {
    Kinship kinship = model.getKinship();
    rView.loadTo(model);
    freqView.loadTo(kinship);
    showView.loadTo(kinship);
    sortView.loadTo(kinship);
    kinship.setTreatHaploid(Kinship.HAPLOID_IGNORE);
  }
  public void loadFrom(Kingroup kingroup) {
    Kinship kinship = kingroup.getKinship();
    freqView = new KinshipAlleleFreqOptView(kinship);
    showView = new KinshipRDisplayOptView(kinship);
    sortView = new KinshipSortOptView(kinship);
    rView = new PairwiseREstimatorView(kingroup);
  }
  protected void assemble() {
    startRow(rView);
    startRow(freqView);
    startRow(showView);
    endRow(sortView);
  }

  public String getReference()
  {
    return rView.getReference();
  }
}