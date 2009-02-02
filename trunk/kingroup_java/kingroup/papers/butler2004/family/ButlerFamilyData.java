package kingroup.papers.butler2004.family;
import javax.utilx.pair.IntStringPair;
import java.util.Hashtable;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 28, 2004, Time: 12:55:40 PM
 */
public class ButlerFamilyData extends Hashtable {
  private static int count = 0;
  // r = 1
  public static final int BUTLER_1x2 = count++;   // 2 full sibs
  public static final int BUTLER_1x3 = count++;   // 3 full sibs
  public static final int BUTLER_1x4 = count++;   // 4 full sibs
  public static final int BUTLER_1x5 = count++;   // 5 full sibs
  public static final int BUTLER_1x20 = count++;
  public static final int BUTLER_1x12 = count++;
  // r = 2
  public static final int BUTLER_2x1 = count++;  // 2 unrelated
  public static final int BUTLER_2x2 = count++;
  public static final int BUTLER_2x3 = count++;
  public static final int BUTLER_2x4 = count++;
  public static final int BUTLER_2x5 = count++;
  public static final int BUTLER_2x6 = count++;
  public static final int BUTLER_2x10 = count++;
  public static final int BUTLER_2x25 = count++;
  public static final int BUTLER_2x50 = count++;
  public static final int BUTLER_2x100 = count++;
  // r = 5
  public static final int BUTLER_5x1 = count++;
  public static final int BUTLER_5x2 = count++;
  public static final int BUTLER_5x4 = count++;
  public static final int BUTLER_5x10 = count++;
  public static final int BUTLER_5x20 = count++;
  public static final int BUTLER_5x40 = count++;
  // r = 3
  public static final int BUTLER_3x4 = count++;
  // r = 4
  public static final int BUTLER_4x3 = count++;
  public static final int BUTLER_4x5 = count++;
  // r = 6
  public static final int BUTLER_6x2 = count++;
  // r = 10
  public static final int BUTLER_10x1 = count++;
  public static final int BUTLER_10x2 = count++;
  public static final int BUTLER_10x3 = count++;
  public static final int BUTLER_10x5 = count++;
  public static final int BUTLER_10x10 = count++;
  public static final int BUTLER_10x20 = count++;
  public static final int BUTLER_10x50 = count++;
  public static final int BUTLER_100x50 = count++;
  public static final int BUTLER_200x50 = count++;
  // r = 12
  public static final int BUTLER_12x1 = count++;
  // r = 12
  public static final int BUTLER_20x50 = count++;
  // g = 5
  public static final int BUTLER_6x5 = count++;
  public static final int BUTLER_20x5 = count++;
  public static final int BUTLER_40x5 = count++;
  public static final int BUTLER_100x5 = count++;
  public static final int BUTLER_3x1 = count++;
  public static final int BUTLER_3_2_1 = count++;
  public static final int BUTLER_20x1 = count++;
  public static final int BUTLER_1x10 = count++;
  public static final int BUTLER_17_OF_1 = count++;
  public static final int BUTLER_50x1 = count++;
  public static final int BUTLER_25x2 = count++;
  public static final int BUTLER_1x50 = count++;
  public static final int BUTLER_5x8 = count++;
  public static final int BUTLER_25_10_10_4_1 = count++;
  public static final int BUTLER_SKEWED_5_8_1 = count++;
  public static final int BUTLER_SKEWED_5_6_2 = count++;
  public static final int BUTLER_SKEWED_5_4_3 = count++;
  public static final int BUTLER_SKEWED_5_2_4 = count++;
  public static final int BUTLER_20_10_10_5_5 = count++;
  public static final int BUTLER_30_5_5_5_5 = count++;
  public static final int BUTLER_40_5_2_2_1 = count++;
  public static final int BUTLER_20_3x5_5x2_5x1 = count++;
  public static final int BUTLER_18_8_8_3_3 = count++;
  public static final int BUTLER_23_8_8_2_1 = count++;
  public static final int BUTLER_28_3_3_3_3 = count++;
  public static final int BUTLER_46_1_1_1_1 = count++;
  public static final int BUTLER_44_1_1_1_1 = count++;
  public static final int BUTLER_25x1 = count++;
  public static final int BUTLER_5x5 = count++;
  public static final int BUTLER_12_5_5_2_1 = count++;
  public static final int BUTLER_21_1_1_1_1 = count++;
  public static final int BUTLER_200_OF_1 = count++;
  public static final int BUTLER_20_OF_10 = count++;
  public static final int BUTLER_20_OF_8 = count++;
  public static final int BUTLER_5_OF_40 = count++;
  public static final int BUTLER_5_OF_38 = count++;
  public static final int BUTLER_100_40_40_16_4 = count++;
  public static final int BUTLER_98_38_38_14_2 = count++;
  public static final int BUTLER_196_1_1_1_1 = count++;
  public static final int BUTLER_194_1_1_1_1 = count++;
  public static final int A4 = count++;
  public static final int A8 = count++;
  public static final int EQUAL_FREQ = count++;
  public static final int DNEQ = count++;
  public static final int SKEWED_BY_1 = count++;
  public static final int SKEWED_BY_2 = count++;
  public static final int SKEWED_BEYER = count++;
  private static ButlerFamilyData instance_ = new ButlerFamilyData();
  public static ButlerFamilyData getInstance() {
    return instance_;
  }

// todo: addLine the rest of families
  private ButlerFamilyData() {
    // r = 1
    put(new Integer(BUTLER_1x2)
      , new ButlerFamily(BUTLER_1x2, "1x2", makeFamilies(1, 2)));
    put(new Integer(BUTLER_1x3)
      , new ButlerFamily(BUTLER_1x3, "1x3", makeFamilies(1, 3)));
    put(new Integer(BUTLER_1x4)
      , new ButlerFamily(BUTLER_1x4, "1x4", makeFamilies(1, 4)));
    put(new Integer(BUTLER_1x5)
      , new ButlerFamily(BUTLER_1x5, "1x5", makeFamilies(1, 5)));
    put(new Integer(BUTLER_1x12)
      , new ButlerFamily(BUTLER_1x12, "1x12", makeFamilies(1, 12)));
    put(new Integer(BUTLER_1x20)
      , new ButlerFamily(BUTLER_1x20, "1x20", makeFamilies(1, 20)));

    // r = 2
    put(new Integer(BUTLER_2x1)
      , new ButlerFamily(BUTLER_2x1, "2x1", makeFamilies(2, 1)));
    put(new Integer(BUTLER_2x2)
      , new ButlerFamily(BUTLER_2x2, "2x2", makeFamilies(2, 2)));
    put(new Integer(BUTLER_2x3)
      , new ButlerFamily(BUTLER_2x3, "2x3", makeFamilies(2, 3)));
    put(new Integer(BUTLER_2x4)
      , new ButlerFamily(BUTLER_2x4, "2x4", makeFamilies(2, 4)));
    put(new Integer(BUTLER_2x5)
      , new ButlerFamily(BUTLER_2x5, "2x5", makeFamilies(2, 5)));
    put(new Integer(BUTLER_2x6)
      , new ButlerFamily(BUTLER_2x6, "2x6", makeFamilies(2, 6)));
    put(new Integer(BUTLER_2x10)
      , new ButlerFamily(BUTLER_2x10, "2x10", makeFamilies(2, 10)));
    put(new Integer(BUTLER_2x25)
      , new ButlerFamily(BUTLER_2x25, "2x25", makeFamilies(2, 25)));
    put(new Integer(BUTLER_2x50)
      , new ButlerFamily(BUTLER_2x50, "2x50", makeFamilies(2, 50)));
    put(new Integer(BUTLER_2x100)
      , new ButlerFamily(BUTLER_2x100, "2x100", makeFamilies(2, 100)));

    // r = 3
    put(new Integer(BUTLER_3x4)
      , new ButlerFamily(BUTLER_3x4, "3x4", makeFamilies(3, 4)));

    // r = 4
    put(new Integer(BUTLER_4x3)
      , new ButlerFamily(BUTLER_4x3, "4x3", makeFamilies(4, 3)));
    put(new Integer(BUTLER_4x5)
      , new ButlerFamily(BUTLER_4x5, "4x5", makeFamilies(4, 5)));

    // r = 5
    put(new Integer(BUTLER_5x1)
      , new ButlerFamily(BUTLER_5x1, "5x1", makeFamilies(5, 1)));
    put(new Integer(BUTLER_5x2)
      , new ButlerFamily(BUTLER_5x2, "5x2", makeFamilies(5, 2)));
    put(new Integer(BUTLER_5x4)
      , new ButlerFamily(BUTLER_5x4, "5x4", makeFamilies(5, 4)));
    put(new Integer(BUTLER_5x10)
      , new ButlerFamily(BUTLER_5x10, "5x10", makeFamilies(5, 10)));
    put(new Integer(BUTLER_5x20)
      , new ButlerFamily(BUTLER_5x20, "5x20", makeFamilies(5, 20)));
    put(new Integer(BUTLER_5x40)
      , new ButlerFamily(BUTLER_5x40, "5x40", makeFamilies(5, 40)));

    // r = 6
    put(new Integer(BUTLER_6x2)
      , new ButlerFamily(BUTLER_6x2, "6x2", makeFamilies(6, 2)));

    // r = 10
    put(new Integer(BUTLER_10x1)
      , new ButlerFamily(BUTLER_10x1, "10x1", makeFamilies(10, 1)));
    put(new Integer(BUTLER_10x2)
      , new ButlerFamily(BUTLER_10x2, "10x2", makeFamilies(10, 2)));
    put(new Integer(BUTLER_10x3)
      , new ButlerFamily(BUTLER_10x3, "10x3", makeFamilies(10, 3)));
    put(new Integer(BUTLER_10x5)
      , new ButlerFamily(BUTLER_10x5, "10x5", makeFamilies(10, 5)));
    put(new Integer(BUTLER_10x10)
      , new ButlerFamily(BUTLER_10x10, "10x10", makeFamilies(10, 10)));
    put(new Integer(BUTLER_10x20)
      , new ButlerFamily(BUTLER_10x20, "10x20", makeFamilies(10, 20)));
    put(new Integer(BUTLER_10x50)
      , new ButlerFamily(BUTLER_10x50, "10x50", makeFamilies(10, 50)));
    put(new Integer(BUTLER_100x50)
      , new ButlerFamily(BUTLER_100x50, "100x50", makeFamilies(100, 50)));
    put(new Integer(BUTLER_200x50)
      , new ButlerFamily(BUTLER_200x50, "200x50", makeFamilies(200, 50)));

    // r = 12
    put(new Integer(BUTLER_12x1)
      , new ButlerFamily(BUTLER_12x1, "12x1", makeFamilies(12, 1)));

    // r = 20
    put(new Integer(BUTLER_20x50)
      , new ButlerFamily(BUTLER_20x50, "20x50", makeFamilies(20, 50)));

    // g=5
    put(new Integer(BUTLER_6x5)
      , new ButlerFamily(BUTLER_6x5, "6x5", makeFamilies(6, 5)));
    put(new Integer(BUTLER_20x5)
      , new ButlerFamily(BUTLER_20x5, "20x5", makeFamilies(20, 5)));
    put(new Integer(BUTLER_40x5)
      , new ButlerFamily(BUTLER_40x5, "40x5", makeFamilies(40, 5)));
    put(new Integer(BUTLER_100x5)
      , new ButlerFamily(BUTLER_100x5, "100x5", makeFamilies(100, 5)));
    put(new Integer(BUTLER_3x1)
      , new ButlerFamily(BUTLER_3x1, "3x1", makeFamilies(3, 1)));
    put(new Integer(BUTLER_1x10)
      , new ButlerFamily(BUTLER_1x10, "1x10", makeFamilies(1, 10)));
    put(new Integer(BUTLER_20x1)
      , new ButlerFamily(BUTLER_20x1, "20x1", makeFamilies(20, 1)));
    put(new Integer(BUTLER_50x1)
      , new ButlerFamily(BUTLER_50x1, "50x1", makeFamilies(50, 1)));
    put(new Integer(BUTLER_25x2)
      , new ButlerFamily(BUTLER_25x2, "25x2", makeFamilies(25, 2)));
    put(new Integer(BUTLER_1x50)
      , new ButlerFamily(BUTLER_1x50, "1x50", makeFamilies(1, 50)));
    put(new Integer(BUTLER_17_OF_1)
      , new ButlerFamily(BUTLER_17_OF_1, "17x1", makeFamilies(17, 1)));
    put(new Integer(BUTLER_25x1)
      , new ButlerFamily(BUTLER_25x1, "25x1", makeFamilies(25, 1)));
    put(new Integer(BUTLER_5x8)
      , new ButlerFamily(BUTLER_5x8, "5 Families of 8", makeFamilies(5, 8)));
    put(new Integer(BUTLER_5x5)
      , new ButlerFamily(BUTLER_5x5, "5 Families of 5", makeFamilies(5, 5)));
    put(new Integer(BUTLER_25_10_10_4_1)
      , new ButlerFamily(BUTLER_25_10_10_4_1, "Family sizes of: 25, 10, 10, 4, 1", makeF25()));
    put(new Integer(BUTLER_23_8_8_2_1)
      , new ButlerFamily(BUTLER_23_8_8_2_1, "Family sizes of: 23, 8, 8, 2, 1", makeF23()));
    put(new Integer(BUTLER_12_5_5_2_1)
      , new ButlerFamily(BUTLER_12_5_5_2_1, "Family sizes of: 12, 5, 5, 2, 1", makeF25_12()));
    put(new Integer(BUTLER_SKEWED_5_8_1)
      , new ButlerFamily(BUTLER_SKEWED_5_8_1, "Family sizes", makeSkewed(5, 8, 1)));
    put(new Integer(BUTLER_SKEWED_5_6_2)
      , new ButlerFamily(BUTLER_SKEWED_5_6_2, "Family sizes", makeSkewed(5, 6, 2)));
    put(new Integer(BUTLER_SKEWED_5_4_3)
      , new ButlerFamily(BUTLER_SKEWED_5_4_3, "Family sizes", makeSkewed(5, 4, 3)));
    put(new Integer(BUTLER_SKEWED_5_2_4)
      , new ButlerFamily(BUTLER_SKEWED_5_2_4, "Family sizes", makeSkewed(5, 2, 4)));
    put(new Integer(BUTLER_5x10)
      , new ButlerFamily(BUTLER_5x10, "Family", makeFamilies(5, 10)));
    put(new Integer(BUTLER_20_10_10_5_5)
      , new ButlerFamily(BUTLER_20_10_10_5_5, "Family", makeF20()));
    put(new Integer(BUTLER_30_5_5_5_5)
      , new ButlerFamily(BUTLER_30_5_5_5_5, "Family", makeF30()));
    put(new Integer(BUTLER_40_5_2_2_1)
      , new ButlerFamily(BUTLER_40_5_2_2_1, "Family", makeF40()));
    put(new Integer(BUTLER_20_3x5_5x2_5x1)
      , new ButlerFamily(BUTLER_20_3x5_5x2_5x1, "Family", makeBUTLER_20_3x5_5x2_5x1()));
    put(new Integer(BUTLER_18_8_8_3_3)
      , new ButlerFamily(BUTLER_18_8_8_3_3, "Family sizes of: 18, 8, 8, 3, 3", makeF18()));
    put(new Integer(BUTLER_28_3_3_3_3)
      , new ButlerFamily(BUTLER_28_3_3_3_3, "Family sizes of: 28, 3, 3, 3, 3", makeF28()));
    put(new Integer(BUTLER_3_2_1)
      , new ButlerFamily(BUTLER_3_2_1, "Family sizes of: 3, 2, 1", makeF6_3()));
    put(new Integer(BUTLER_46_1_1_1_1)
      , new ButlerFamily(BUTLER_46_1_1_1_1, "Family sizes of: 46, 1, 1, 1, 1", makeF46()));
    put(new Integer(BUTLER_44_1_1_1_1)
      , new ButlerFamily(BUTLER_44_1_1_1_1, "Family sizes of: 44, 1, 1, 1, 1", makeF44()));
    put(new Integer(BUTLER_21_1_1_1_1)
      , new ButlerFamily(BUTLER_21_1_1_1_1, "Family sizes of: 21, 1, 1, 1, 1", makeF25_21()));
    put(new Integer(BUTLER_200_OF_1)
      , new ButlerFamily(BUTLER_200_OF_1, "200 Families of 1", makeFamilies(200, 1)));
    put(new Integer(BUTLER_20_OF_10)
      , new ButlerFamily(BUTLER_20_OF_10, "20 Families of 10", makeFamilies(20, 10)));
    put(new Integer(BUTLER_20_OF_8)
      , new ButlerFamily(BUTLER_20_OF_8, "20 Families of 8", makeFamilies(20, 8)));
    put(new Integer(BUTLER_5_OF_40)
      , new ButlerFamily(BUTLER_5_OF_40, "5 Families of 40", makeFamilies(5, 40)));
    put(new Integer(BUTLER_5_OF_38)
      , new ButlerFamily(BUTLER_5_OF_38, "5 Families of 38", makeFamilies(5, 38)));
    put(new Integer(BUTLER_100_40_40_16_4)
      , new ButlerFamily(BUTLER_100_40_40_16_4, "Family sizes of: 100, 40, 40, 16, 4", makeF100()));
    put(new Integer(BUTLER_98_38_38_14_2)
      , new ButlerFamily(BUTLER_98_38_38_14_2, "Family sizes of: 98, 38, 38, 14, 2", makeF98()));
    put(new Integer(BUTLER_196_1_1_1_1)
      , new ButlerFamily(BUTLER_196_1_1_1_1, "Family sizes of: 196, 1, 1, 1, 1", makeF196()));
    put(new Integer(BUTLER_194_1_1_1_1)
      , new ButlerFamily(BUTLER_194_1_1_1_1, "Family sizes of: 194, 1, 1, 1, 1", makeF194()));
  }
  final public ButlerFamily getFamily(int id) {
    Object obj = get(new Integer(id));
    if (obj == null)
      return null;
    return (ButlerFamily) obj;
  }
  public IntStringPair[] getIntStringPairs() {
    IntStringPair[] res = new IntStringPair[size()];
    Object[] keys = keySet().toArray();
    for (int i = 0; i < size(); i++) {
      ButlerFamily family = (ButlerFamily) get(keys[i]);
      res[i] = new IntStringPair(family.getId(), family.toString());
    }
    return res;
  }
  private Integer[] makeFamilies(int num, int size) {
    Integer res[] = new Integer[num];
    for (int i = 0; i < res.length; i++)
      res[i] = new Integer(size);
    return res;
  }
  private Integer[] makeSkewed(int r, int u, int skew) {
    Integer res[] = new Integer[r];
    int curr = u;
    for (int i = 0; i < res.length; i++) {
      res[i] = new Integer(curr);
      curr += skew;
    }
    return res;
  }
  private Integer[] makeF25() {
    final int SIZE = 5;
    Integer res[] = new Integer[SIZE];
    int i = 0;
    res[i++] = new Integer(25);
    res[i++] = new Integer(10);
    res[i++] = new Integer(10);
    res[i++] = new Integer(4);
    res[i++] = new Integer(1);
    return res;
  }
  private Integer[] makeF20() {
    final int SIZE = 5;
    Integer res[] = new Integer[SIZE];
    int i = 0;
    res[i++] = new Integer(20);
    res[i++] = new Integer(10);
    res[i++] = new Integer(10);
    res[i++] = new Integer(5);
    res[i++] = new Integer(5);
    return res;
  }
  private Integer[] makeF18() {
    final int SIZE = 5;
    Integer res[] = new Integer[SIZE];
    int i = 0;
    res[i++] = new Integer(18);
    res[i++] = new Integer(8);
    res[i++] = new Integer(8);
    res[i++] = new Integer(3);
    res[i++] = new Integer(3);
    return res;
  }
  private Integer[] makeF30() {
    final int SIZE = 5;
    Integer res[] = new Integer[SIZE];
    int i = 0;
    res[i++] = new Integer(30);
    res[i++] = new Integer(5);
    res[i++] = new Integer(5);
    res[i++] = new Integer(5);
    res[i++] = new Integer(5);
    return res;
  }
  private Integer[] makeF40() {
    final int SIZE = 5;
    Integer res[] = new Integer[SIZE];
    int i = 0;
    res[i++] = new Integer(40);
    res[i++] = new Integer(5);
    res[i++] = new Integer(2);
    res[i++] = new Integer(2);
    res[i++] = new Integer(1);
    return res;
  }
  private Integer[] makeBUTLER_20_3x5_5x2_5x1() {
    final int SIZE = 14;
    Integer res[] = new Integer[SIZE];
    int i = 0;
    res[i++] = new Integer(20);
    res[i++] = new Integer(5);
    res[i++] = new Integer(5);
    res[i++] = new Integer(5);
    res[i++] = new Integer(2);
    res[i++] = new Integer(2);
    res[i++] = new Integer(2);
    res[i++] = new Integer(2);
    res[i++] = new Integer(2);
    res[i++] = new Integer(1);
    res[i++] = new Integer(1);
    res[i++] = new Integer(1);
    res[i++] = new Integer(1);
    res[i++] = new Integer(1);
    return res;
  }
  private Integer[] makeF28() {
    final int SIZE = 5;
    Integer res[] = new Integer[SIZE];
    int i = 0;
    res[i++] = new Integer(28);
    res[i++] = new Integer(3);
    res[i++] = new Integer(3);
    res[i++] = new Integer(3);
    res[i++] = new Integer(3);
    return res;
  }
  private Integer[] makeF100() {
    final int SIZE = 5;
    Integer res[] = new Integer[SIZE];
    int i = 0;
    res[i++] = new Integer(100);
    res[i++] = new Integer(40);
    res[i++] = new Integer(40);
    res[i++] = new Integer(16);
    res[i++] = new Integer(4);
    return res;
  }
  private Integer[] makeF98() {
    final int SIZE = 5;
    Integer res[] = new Integer[SIZE];
    int i = 0;
    res[i++] = new Integer(98);
    res[i++] = new Integer(38);
    res[i++] = new Integer(38);
    res[i++] = new Integer(14);
    res[i++] = new Integer(2);
    return res;
  }
  private Integer[] makeF196() {
    final int SIZE = 5;
    Integer res[] = new Integer[SIZE];
    int i = 0;
    res[i++] = new Integer(196);
    res[i++] = new Integer(1);
    res[i++] = new Integer(1);
    res[i++] = new Integer(1);
    res[i++] = new Integer(1);
    return res;
  }
  private Integer[] makeF194() {
    final int SIZE = 5;
    Integer res[] = new Integer[SIZE];
    int i = 0;
    res[i++] = new Integer(194);
    res[i++] = new Integer(1);
    res[i++] = new Integer(1);
    res[i++] = new Integer(1);
    res[i++] = new Integer(1);
    return res;
  }
  private Integer[] makeF23() {
    final int SIZE = 5;
    Integer res[] = new Integer[SIZE];
    int i = 0;
    res[i++] = new Integer(23);
    res[i++] = new Integer(8);
    res[i++] = new Integer(8);
    res[i++] = new Integer(2);
    res[i++] = new Integer(1);
    return res;
  }
  private Integer[] makeF25_12() {
    final int SIZE = 5;
    Integer res[] = new Integer[SIZE];
    int i = 0;
    res[i++] = new Integer(12);
    res[i++] = new Integer(5);
    res[i++] = new Integer(5);
    res[i++] = new Integer(2);
    res[i++] = new Integer(1);
    return res;
  }
  private Integer[] makeF6_3() {
    final int SIZE = 3;
    Integer res[] = new Integer[SIZE];
    int i = 0;
    res[i++] = new Integer(3);
    res[i++] = new Integer(2);
    res[i++] = new Integer(1);
    return res;
  }
  private Integer[] makeF46() {
    final int SIZE = 5;
    Integer res[] = new Integer[SIZE];
    int i = 0;
    res[i++] = new Integer(46);
    res[i++] = new Integer(1);
    res[i++] = new Integer(1);
    res[i++] = new Integer(1);
    res[i++] = new Integer(1);
    return res;
  }
  private Integer[] makeF25_21() {
    final int SIZE = 5;
    Integer res[] = new Integer[SIZE];
    int i = 0;
    res[i++] = new Integer(21);
    res[i++] = new Integer(1);
    res[i++] = new Integer(1);
    res[i++] = new Integer(1);
    res[i++] = new Integer(1);
    return res;
  }
  private Integer[] makeF44() {
    final int SIZE = 5;
    Integer res[] = new Integer[SIZE];
    int i = 0;
    res[i++] = new Integer(44);
    res[i++] = new Integer(1);
    res[i++] = new Integer(1);
    res[i++] = new Integer(1);
    res[i++] = new Integer(1);
    return res;
  }
  public static int getDefaultFamilyType() {
    return BUTLER_50x1;
  }
}
