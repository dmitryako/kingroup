package kingroup.papers.butler2004.freq;
import kingroup.papers.butler2004.ButlerPopBuilderModel;

import javax.swingx.IntStringPairComboBox;
import javax.utilx.pair.IntStringPair;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 28, 2004, Time: 12:26:34 PM
 */
public class ButlerAllelicFreqView extends IntStringPairComboBox {
  public ButlerAllelicFreqView(ButlerPopBuilderModel model) {
    super(ButlerAllelicFreqData.getInstance().getIntStringPairs());
    setByInt(model.getAllelicFreqType());
  }
  public void loadTo(ButlerPopBuilderModel model) {
    IntStringPair pair = (IntStringPair) getSelectedItem();
    if (pair != null)
      model.setAllelicFreqType(pair.getInt());
  }
}
