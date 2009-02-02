package kingroup.papers.butler2004;
import kingroup.papers.butler2004.family.ButlerFamilyData;
import kingroup.population.SmithPopBuilderModel;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 17, 2004, Time: 4:08:21 PM
 */
public class ButlerPopBuilderModel extends SmithPopBuilderModel {
  final public static String REFERENCE = "Butler et al Mol.Ecology(2004) 13, 1589-1600";
  private int familyType;
  private int allelicFreqType;
  public void loadDefaults() {
    familyType = ButlerFamilyData.getDefaultFamilyType();
    allelicFreqType = ButlerFamilyData.EQUAL_FREQ;
    setNumAlleles(4);
    setNumLoci(4);
    setIncParents(false);
  }
  public void copyTo(ButlerPopBuilderModel model) {
    super.copyTo(model);
    model.setAllelicFreqType(getAllelicFreqType());
    model.setFamilyType(getFamilyType());
    model.setIncParents(this.getIncParents());
  }
  final public int getFamilyType() {
    return familyType;
  }
  final public void setFamilyType(int familyType) {
    this.familyType = familyType;
  }
  final public int getAllelicFreqType() {
    return allelicFreqType;
  }
  final public void setAllelicFreqType(int allelicFreqType) {
    this.allelicFreqType = allelicFreqType;
  }
}
