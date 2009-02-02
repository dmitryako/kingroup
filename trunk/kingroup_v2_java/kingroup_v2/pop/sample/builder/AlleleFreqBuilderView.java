package kingroup_v2.pop.sample.builder;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.builder.alleles.PopBuilderAllelesView;
import kingroup_v2.pop.sample.builder.loci.PopBuilderLociView;

import javax.swingx.panelx.GridBagView;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 4/05/2006, Time: 16:17:18
 */
public class AlleleFreqBuilderView  extends GridBagView
{
  private PopBuilderLociView lociView;
  private PopBuilderAllelesView allelesView;

  public AlleleFreqBuilderView(PopBuilderModel bean) {
    init();
    loadFrom(bean);
    assemble();
  }
  private void loadFrom(PopBuilderModel bean)
  {
    lociView = new PopBuilderLociView(bean);
    allelesView = new PopBuilderAllelesView(bean);
  }
  public void loadTo(PopBuilderModel bean)
  {
    lociView.loadTo(bean);
    allelesView.loadTo(bean);
  }
  private void init() {
  }
  private void assemble() {
    startRow(lociView);
    endRow(allelesView);
  }
}
