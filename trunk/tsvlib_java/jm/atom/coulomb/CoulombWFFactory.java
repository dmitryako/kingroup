package jm.atom.coulomb;
import numeric.functor.Func;
import stlx.valarrayx.valarray;

import javax.mathx.MathX;
import javax.vecmathx.function.FuncVec;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 14/03/2005, Time: 14:42:03
 */
public class CoulombWFFactory {
  // from p.190 of Condon & Odabasi "Atomic Structure"
  // all INTL P^2 dr = 1;  INTL PP' dr = 0.
  public static FuncVec makeP1s(valarray r, double z) {
    return new FuncVec(r, new functorP1s(z));
  }
  public static FuncVec makeP2s(valarray r, double z) {
    return new FuncVec(r, new functorP2s(z));
  }
  public static FuncVec makeP2p(valarray r, double z) {
    return new FuncVec(r, new functorP2p(z));
  }
  public static FuncVec makeP3s(valarray r, double z) {
    return new FuncVec(r, new functorP3s(z));
  }
  public static FuncVec makeP3p(valarray r, double z) {
    return new FuncVec(r, new functorP3p(z));
  }
  public static FuncVec makeP3d(valarray r, double z) {
    return new FuncVec(r, new functorP3d(z));
  }
  public static FuncVec makeY_0_1s(valarray r) {
    return new FuncVec(r, new functorY_0_1s());
  }
  public static FuncVec makeY_0_2s(valarray r) {
    return new FuncVec(r, new functorY_0_2s());
  }
  public static FuncVec makeY_0_2p(valarray r) {
    return new FuncVec(r, new functorY_0_2p());
  }
  public static FuncVec makeY_2_2p(valarray r) {
    return new FuncVec(r, new functorY_2_2p());
  }
  // Z(r, K) = INTL_0^r (s/r)^K * f(s)*f(s) ds
  public static FuncVec makeZ_0_1s(valarray r) {
    return new FuncVec(r, new functorZ_0_1s());
  }
  public static FuncVec makeZ_1_1s(valarray r) {
    return new FuncVec(r, new functorZ_1_1s());
  }
}

// Y^0(1s, 1s; r) = 1-exp(-2r)*(1+r)
class functorY_0_1s implements Func {
  public double calc(double r) {
    //return 1. - exp(-2. * x) * (1. + x);
    return 1. - Math.exp(-2. * r) * (1. + r);
  }
}
class functorZ_0_1s implements Func {
  // = INTL_0^r 4 s^2 exp(-2s) ds
  // INTL r^2 exp(ax) dx = exp(ax) (r^2/a - 2x/a^2 + 2/a^3)
  // a = -2
  public double calc(double r) {
    double a = -2;
    double ex = Math.exp(a * r);
    return 4. * ex * (r * r / a - 2. * r / (a * a)) + (ex - 1) * 8. / (a * a * a);
  }
}
class functorZ_1_1s implements Func {
  // = INTL_0^r 4 s/r * s^2 exp(-2s) ds
  // INTL x^3 exp(ax) dx = exp(ax)/a (x^3 - 3x^2/a + 6x/a^2 - 6/a^3)
  // a = -2
  public double calc(double r) {
    if (r == 0)
      return 0;
    double a = -2;
    double ex = Math.exp(a * r);
    double d2 = 24. / (r * MathX.pow(a, 4));
    double d = 4. * ex / a * (r * r - 3. * r / a + 6. / (a * a));
    return d + (1. - ex) * d2;
  }
}
class functorY_0_2s implements Func {
  public double calc(double r) {
    //return 1. - exp(-r) * (1. + 3./4 * r + 1./4 * r * r + 1./8 * pow(r, 3));
    return 1. - Math.exp(-r) * (1. + 3. / 4 * r + 1. / 4 * r * r + 1. / 8 * MathX.pow(r, 3));
  }
}
class functorY_0_2p implements Func {
  public double calc(double r) {
    //return 1. - exp(-r) * (1. + 3./4 * r + 1./4 * r * r + 1./24 * pow(r, 3));
    return 1. - Math.exp(-r) * (1. + 3. / 4 * r + 1. / 4 * r * r + 1. / 24 * MathX.pow(r, 3));
  }
}
class functorY_2_2p implements Func {
  public double calc(double r) {
    if (r == 0)
      return 0;
    //return 30./(r * r) * (1. - exp(-r) * (1. + r + 1./2 * r * r
    //   + 1./6 * pow(r, 3) + 1./24 * pow(r, 4) + 1./144 * pow(r, 5)));
//      return 30./(r * r) * (1. - Math.exp(-r) * (1. + r + 1./2 * r * r
//         + 1./6 * MathX.pow(r, 3) + 1./24 * MathX.pow(r, 4) + 1./144 * MathX.pow(r, 5)));
    return 30. / r * ((1. - Math.exp(-r)) / r - Math.exp(-r) * (1. + 1. / 2 * r
      + 1. / 6 * MathX.pow(r, 2) + 1. / 24 * MathX.pow(r, 3) + 1. / 144 * MathX.pow(r, 4)));
  }
}
class functorP1s implements Func {
  protected final double z;
  public functorP1s(final double z) {
    this.z = z;
  }
  // INTL P^2 dr = 1.
  public double calc(double r) {
    if (r <= 0)
      return 0;
    double d = r * z;
    return 2. * Math.sqrt(z) * Math.exp(-d) * d;
  }
}
class functorP2s extends functorP1s {
  public functorP2s(final double z) {
    super(z);
  }
  // INTL P^2 dr = 1.
  public double calc(double r) {
    if (r <= 0)
      return 0;
    double d = r * z * 0.5;
    return Math.sqrt(2 * z) * Math.exp(-d) * d * (1.0 - d);
  }
}
class functorP3s extends functorP1s {
  public functorP3s(final double z) {
    super(z);
  }
  public double calc(double r) {
    if (r <= 0)
      return 0;
    double d = r * z / 3.;
    return Math.sqrt(4. * z / 3.) * Math.exp(-d) * d * (1.0 - 2 * d + 2. * d * d / 3.);
  }
}
class functorP2p extends functorP1s {
  public functorP2p(final double z) {
    super(z);
  }
  public double calc(double r) {
    if (r <= 0)
      return 0;
    double d = r * z * 0.5;
    return Math.sqrt(2 * z / 3.) * Math.exp(-d) * d * d;
  }
}
class functorP3p extends functorP1s {
  public functorP3p(final double z) {
    super(z);
  }
  public double calc(double r) {
    if (r <= 0)
      return 0;
    double d = r * z / 3.;
    return 8. / 3. * Math.sqrt(z / 6.) * Math.exp(-d) * d * d * (1.0 - 0.5 * d);
  }
}
class functorP3d extends functorP1s {
  public functorP3d(final double z) {
    super(z);
  }
  public double calc(double r) {
    if (r <= 0)
      return 0;
    double d = r * z / 3.;
    return 4. / 3. * Math.sqrt(z / 30.) * Math.exp(-d) * d * d * d;
  }
}

/*
// J-Matrix v5
// by Dmitry Konovalov www.it.jcu.edu.au/~dmitry
//
#if !defined(ATOMIC_YK_H)
#define      ATOMIC_YK_H

#include "../debug/Test.h"
#include "FuncShell.h"
#include "../cpp/Functors.h"

namespace tomsk {

// from F.Fisher "The hartree-fock method for atoms"
// p236
// Y^0(1s, 1s; r) = 1-exp(-2r)*(1+r)
struct FY_0_1s : public CFunctor {
   virtual double operator()(double x) const {
      return 1. - exp(-2. * x) * (1. + x);
   }
};
struct FY_0_2s : public CFunctor {
   virtual double operator()(double x) const {
      return 1. - exp(-x) * (1. + 3./4 * x + 1./4 * x * x + 1./8 * pow(x, 3));
   }
};
struct FY_0_2p : public CFunctor {
   virtual double operator()(double x) const {
      return 1. - exp(-x) * (1. + 3./4 * x + 1./4 * x * x + 1./24 * pow(x, 3));
   }
};
struct FY_2_2p : public CFunctor {
   virtual double operator()(double x) const {
      return 30./(x * x) * (1. - exp(-x) * (1. + x + 1./2 * x * x
         + 1./6 * pow(x, 3) + 1./24 * pow(x, 4) + 1./144 * pow(x, 5)));
   }
};*/

