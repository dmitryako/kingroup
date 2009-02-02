package kingroup.model;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 17, 2004, Time: 9:28:34 AM
 */
public class KnownPedigreeModel {
  public static int MANUAL_INPUT = 0;
  public static int UNRELATED = 1;
  public static int FULL_SIBLINGS = 2;
  public static int HALF_SIBLINGS = 3;
  public static int PARENT_OFFSPRING = 4;
  public static int FULL_HAPLOSIBS = 5;
  public static int COUSINS = 6;
  public static int HAPLO_COISINS = 7;
  public static int CENSUS = 8;
  private static String pedigrees_[]
    = {"Manual Input"
    , "Unrelated"                   // 0,0
    , "Full siblings"               // 0.5, 0.5
    , "Half siblings"    // 0.5, 0
    //, "Half siblings (maternal)"    // 0.5, 0
    //, "Half siblings (paternal)"    // 0, 0.5
    , "Parent-offspring"            // 1, 0
    //, "Mother-offspring"            // 1, 0
    //, "Father-offspring"            // 0, 1
    , "Full sisters (haplodiploid)" // 0.5, 1
    , "Cousins"          // 0.25, 0
    //, "Cousins (paternal)"          // 0, 0.25
    , "Cousins (haplodiploid)"      // 0.375, 0
    , "Census"      // 0.999, 0.999
  };
  private static float relRm_[]
    = {-1f, 0f, 0.5f, 0.5f, 1.0f, 0.5f, 0.25f, 0.375f, 0.999f};
  // with maternal half-sib
  //= {-1.0f, 0.0f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 0.5f, 0.25f, 0.0f, 0.375f};
  private static float relRp_[]
    = {-1f, 0f, 0.5f, 0f, 1.0f, 1.0f, 0f, 0f, 0.999f};
  // with maternal half-sib
  //= {-1.0f, 0.0f, 0.5f, 0.0f, 0.5f, 0.0f, 1.0f, 1.0f, 0.0f,  0.25f, 0.0f};
  public static void load(int from, KinshipIBDModelV1 to) {
    if (from >= relRm_.length
      || from < 0)
      from = UNRELATED;
    to.setComplex(false);
    to.setRm(relRm_[from]);
    to.setRp(relRp_[from]);
  }
  final public static boolean isValidType(int pedigree) {
    return (pedigree >= 0 && pedigree < pedigrees_.length);
  }
  final public static String getPedigreeName(int pedigree) {
    if (!isValidType(pedigree))
      return "n/a";
    return pedigrees_[pedigree];
  }
  final public static float getPedigreeRm(int pedigree) {
    if (!isValidType(pedigree))
      return 0;
    return relRm_[pedigree];
  }
  final public static float getPedigreeRp(int pedigree) {
    if (!isValidType(pedigree))
      return 0;
    return relRp_[pedigree];
  }
}
