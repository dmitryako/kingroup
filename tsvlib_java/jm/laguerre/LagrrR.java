package jm.laguerre;
import stlx.valarrayx.valarray;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 19/03/2005, Time: 16:41:15
 */
public class LagrrR extends LagrrOrth {
  public LagrrR(valarray r, int size, int alpha, double lambda) {
    super(r, size, alpha, lambda);
//      loadLogRBasis();
  }
  //   Rn(r) = Ln(r) / r; since Ln is integrable with dr not r^2dr
//   private void loadLogRBasis() {
//      final FuncVec scale = new FuncVec(grid(), new FuncPowInt(1., -1));
//      for (int n = 0; n < size(); n++) {
//         arr_[n].scale(scale);
//      }
//   }
}
