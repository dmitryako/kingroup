package kingroup.papers.butler2004.family;
import kingroup.papers.butler2004.ButlerPopBuilderModel;

import javax.swingx.IntStringPairComboBox;
import javax.utilx.pair.IntStringPair;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 29, 2004, Time: 8:16:35 AM
 */
public class ButlerFamilyStructureView extends IntStringPairComboBox {
  public ButlerFamilyStructureView(ButlerPopBuilderModel model) {
    super(ButlerFamilyData.getInstance().getIntStringPairs());
    setByInt(model.getFamilyType());
  }
  public void loadTo(ButlerPopBuilderModel model) {
    IntStringPair pair = (IntStringPair) getSelectedItem();
    if (pair != null)
      model.setFamilyType(pair.getInt());
  }
}
