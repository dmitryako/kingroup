package kingroup.genotype;
/* Copyright (C) 2003-2004  Dr. Dmitry Konovalov.
* This code is licensed under the GPL license (see www.gnu.org) for academic,
* not-for-profit use or for use within other Open Source software (see www.opensource.org).
* See www.kingroup.org for more details.
*/
import kingroup.model.HypothesisModel;
import kingroup.model.KinshipFileModelV1;
import kingroup.project.KinGroupProjectV1;
import kingroup.util.FastId;
public class Genotype extends Loci
  implements Comparable {
  public static final int NOT_SET = -1;
  // see also System.arraycopy(...)
  static public void arraycopy(Genotype[] src, Genotype[] dest, int len) {
    int size = Math.min(Math.min(dest.length, src.length), len);
    for (int i = 0; i < size; i++)
      dest[i] = src[i];
  }
  // NOTE!!! group_ and id_ is not unique!!!
  // unique key is needed
  private int key_ = NOT_SET; // any unique (within a population) key set
  private FastId group_ = new FastId();
  private boolean isHaploid_ = false;
  private boolean isDiploid_ = false;
  //[041214dk] Making sure only GenotypeFactory can create a Genotype
  protected Genotype() {
    super();
  }
  //[041214dk] DO NOT DUPLICATE!!! There should be only one instance of each individual!!!
  //   final public Genotype duplicate() {
  // [041215dmitry] NOTE!!!! can only be called from GenotypeFactory
  // TODO: Is there a better way? Currently GenotypeFactory extends Genotype (only to gain access to this method).
  protected void setUniqueKey(int key) {
    key_ = key;
    if (getId() == null)
      setId(Integer.toString(key_));
  }
  // [040126-common] Allow for biologically imposible mix of haploid and diploid loci.
  public boolean isHaploid() {
    return isHaploid_;
  }
  public boolean isDiploid() {
    return isDiploid_;
  }
  public void setHaploid(boolean val) {
    isHaploid_ = val;
  }
  public void setDiploid(boolean val) {
    isDiploid_ = val;
  }
  public boolean equals(Genotype gen) {
    if (this == gen)
      return true;
    return key_ == gen.key_;
  }
  public String getIdView(HypothesisModel hypo) {
    StringBuffer buf = new StringBuffer();
    KinshipFileModelV1 format = KinGroupProjectV1.getInstance().getKinshipFormatModel();
    buf.append(getId());
    if (format.getHasGroupIdColumn()
      && hypo.getHeaderWithGroupId()) {
      buf.append(format.getNotColumnDelimiter());
      buf.append(getGroupId());
    }
    return buf.toString();
  }
  public String getGroupId() {
    return group_.getId();
  }
  public void setGroupId(String id) {
    group_.setId(id);
  }
  public String getView(int i) {
    if (i < 0 || i >= getViewSize())
      return "";
    switch (i) {
      case 0:
        return group_.getId();
      case 1:
        return getId();
      default:
        String delim = KinGroupProjectV1.getInstance().getKinshipFormatModel().getAlleleDelimiters();
        return getLocus(i - 2).toStringBrief(delim);
    }
  }
  public int getViewSize() {
    return getNumLoci() + 2; // extra space for group getId and getId
  }
  public String toString() {
    StringBuffer res = new StringBuffer();
    res.append(key_);
    res.append(", ").append(group_.getId()).append(", ");
    res.append(super.toString());
    return res.toString();
  }
  final public int compareTo(Object o) {
    if (equals(o))
      return 0;
    return key_ - ((Genotype) o).key_;
  }
  public int getUniqueKey() {
    return key_;
  }
}
