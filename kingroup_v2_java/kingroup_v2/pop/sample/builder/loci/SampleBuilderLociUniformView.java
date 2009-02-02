package kingroup_v2.pop.sample.builder.loci;
import kingroup_v2.pop.sample.PopBuilderModel;

import javax.swing.*;
import javax.swingx.text_fieldx.IntTextField;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 23/11/2005, Time: 15:31:11
 */
public class SampleBuilderLociUniformView extends SampleBuilderLociCommonView
{
  protected final static int FIELD_SIZE = 2;
  private IntTextField nLoci;
  private JLabel nLociLbl;
  private IntTextField nAlleles;
  private JLabel nAllelesLbl;

  public SampleBuilderLociUniformView(PopBuilderModel bean) {
    init();
    loadFrom(bean);
    assemble();
  }
  private void init() {
    nLoci = new IntTextField(FIELD_SIZE, 5, 1, 100);
    nLoci.setMinimumSize(nLoci.getPreferredSize());

    nLociLbl = new JLabel("loci");
    nLociLbl.setToolTipText("number of loci");

    nAlleles = new IntTextField(FIELD_SIZE, 10, 1, 100);
    nAlleles.setMinimumSize(nAlleles.getPreferredSize());

    nAllelesLbl = new JLabel("locus size");
    nAllelesLbl.setToolTipText("number of alleles at each locus");
  }
  private void assemble() {
    startRow(nLoci);
    endRow(nLociLbl);
    startRow(nAlleles);
    endRow(nAllelesLbl);
  }
  public void loadFrom(PopBuilderModel bean) {
    nLoci.setValue(bean.getNumLoci());
    nAlleles.setValue(bean.getNumAlleles());
  }
  public void loadTo(PopBuilderModel bean) {
    bean.setNumLoci(nLoci.getInput());
    bean.setNumAlleles(nAlleles.getInput());
  }
}
