package jm.atom;
import jm.grid.WFQuadrLcr;
import stlx.FastLoop;
import stlx.valarrayx.valarray;

import javax.vecmathx.function.FuncVec;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 17/03/2005, Time: 17:20:56
 */
// from F.Fisher "The hartree-fock method for atoms", p18
//                     oo oo
// R^k(a, b; a2, b2) = I  I  dr ds    P(a; r) P(a2; r) U^k(r,s) P(b; s) P(b2; s)
//                     0  0
// = INTL dr P(a; r) P(a2; r) 1/r Y(b, b2, K, r); NOTE 1/r!!!
//  U^k(r,s) = y^k/x^(k+1)   y = min(r,s)   x = max(r,s)
public class RkLcr {
  public static double calc(WFQuadrLcr w, valarray a, valarray b, valarray a2, valarray b2, int K) {
    FuncVec yk = new YkLcr(w.getLogCRToR(), b, b2, K).calcYk();
    double res = FastLoop.dot(a, a2, yk, w.getWithCR2DivR());
    return res;
//      return 0;
  }
}

/*
template <class F>
class TRk : public CIntegralPowExp {
public:
   typedef F func_type;
   TRk(const CWeights& w
      , const F& correction // this is w correction
      , const F& a,  const F& b
      , const F& a2, const F& b2
      , const CL& k) {

      TYk<F> yk(b, b2, k);
      //TBD: make it generic for other function types
      CIntegralPowExp tmp(w, a, a2, yk, correction);
      res(tmp.res());
   }
};

typedef TRk<CFuncPowExp>       CRk;
typedef shared_ptr<tomsk::CRk> CRkH; */