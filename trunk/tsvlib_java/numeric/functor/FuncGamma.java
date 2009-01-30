package numeric.functor;
import netlib.math.complex.Complex;

import javax.iox.LOG;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 2, 2004, Time: 4:27:06 PM
 */
public class FuncGamma implements FuncCmplx {
  static private final Complex ONE = new Complex(1., 0);
  // METHOD-I
  static private final double CK[] = {
    0.5772156649015329
    , -0.6558780715202538
    , -0.0420026350340952
    , 0.1665386113822915
    , -0.0421977345555443
    , -0.0096219715278770
    , 0.0072189432466630
    , -0.0011651675918591
    , -0.0002152416741149
    , 0.0001280502823882
    , -0.0000201348547807
    , -0.0000012504934821
    , 0.0000011330272320
    , -0.0000002056338417
    , 0.0000000061160950
    , 0.0000000050020075
    , -0.0000000011812746
    , 0.0000000001043427
    , 0.0000000000077823
    , -0.0000000000036968
    , 0.0000000000005100
    , -0.0000000000000206
    , -0.0000000000000054
    , 0.0000000000000014
    , 0.0000000000000001
  };
  public Complex apply_slow(final Complex z) {
    Complex res = z;
    Complex zk = z;
    for (int k = 0; k < CK.length; k++) {
      zk = zk.mul(z);
      res = res.add(zk.mul(CK[k]));
    }
    return ONE.div(res);
  }
  ;
  // METHOD-II IS MORE ACCURATE!!!!!!!!
  static private final double C1 = 2.50662827463100050;
  static private final double C[] = {
    41.624436916439068
    , -51.224241022374774
    , 11.338755813488977
    , -0.747732687772388
    , 0.008782877493061
    , -0.000001899030264
    , 0.000000001946335
    , -0.000000000199345
    , 0.000000000008433
    , 0.000000000001486
    , -0.000000000000806
    , 0.000000000000293
    , -0.000000000000102
    , 0.000000000000037
    , -0.000000000000014
    , 0.000000000000006
  };
  public Complex apply(double r) {
    return apply(new Complex(r, 0));
  }
  public Complex apply(Complex Z) {
    Complex V, F, H;
    Complex U = new Complex(Z);
    double X = U.real();
// IF(GIMAG(U) .EQ. 0   .AND. -ABS(X) .EQ. INT(X)) THEN
    if (U.imag() == 0 && -Math.abs(X) == (double) ((int) X)) {
      F = new Complex();
      H = new Complex();
      LOG.error(this, "ARGUMENT EQUALS NON-POSITIVE INTEGER = ", "" + X);
    } else {
      if (X >= 1) {
        F = new Complex(1, 0);
        V = new Complex(U);
      } else if (X >= 0) {
        F = ONE.div(U);               // F = 1   / U
        V = ONE.add(U);               // V = 1   + U
      } else {
        F = new Complex(1, 0);        // F = 1
        V = ONE.sub(U);               // V = 1   - U
      }
      H = new Complex(1., 0.);         // H = 1;
      Complex S = new Complex(C[0], 0);// S = C(0)
      for (int k = 1; k < C.length; k++) {
        //       H = ((V - K)             / (V + (K - 1))) * H
        H = V.sub(new Complex(k, 0)).div(V.add(new Complex(k - 1, 0))).mul(H);
        S = S.add(H.scale(C[k]));     // S = S + C(K) * H
      }
      H = V.add(4.5);                  //    H = V + (4 + HF)
      //    H = C1 * EXP((V - HF) * LOG(H) - H) * S
      Complex V_logH = H.log().mul(V.add(-0.5));
      H = S.mul(V_logH.sub(H).exp()).mul(C1);
      //    IF(X .LT. 0) H = PI / (SIN(PI * U) * H)
      if (X < 0) {
        //H = PI / (sin(PI * U) * H);
        H = new Complex(Math.PI, 0).div(U.mul(Math.PI).sin().mul(H));
      }
    }
    return F.mul(H);
  }
}

// from cern/gen.f
/*      FUNCTION WGAMMA(Z)
IMPLICIT DOUBLE PRECISION (A-H,O-Z)
Complex*16
+  WGAMMA
Complex*16
+       Z,U,V,F,H,S
CHARACTER NAME*(*)
CHARACTER*80 ERRTXT
PARAMETER (NAME = 'CGAMMA/WGAMMA')
DIMENSION C(0:15)

PARAMETER (Z1 = 1, HF = Z1/2)


DATA PI /3.14159 26535 89793 24D0/
DATA C1 /2.50662 82746 31000 50D0/

DATA C( 0) / 41.62443 69164 39068D0/
DATA C( 1) /-51.22424 10223 74774D0/
DATA C( 2) / 11.33875 58134 88977D0/
DATA C( 3) / -0.74773 26877 72388D0/
DATA C( 4) /  0.00878 28774 93061D0/
DATA C( 5) / -0.00000 18990 30264D0/
DATA C( 6) /  0.00000 00019 46335D0/
DATA C( 7) / -0.00000 00001 99345D0/
DATA C( 8) /  0.00000 00000 08433D0/
DATA C( 9) /  0.00000 00000 01486D0/
DATA C(10) / -0.00000 00000 00806D0/
DATA C(11) /  0.00000 00000 00293D0/
DATA C(12) / -0.00000 00000 00102D0/
DATA C(13) /  0.00000 00000 00037D0/
DATA C(14) / -0.00000 00000 00014D0/
DATA C(15) /  0.00000 00000 00006D0/

DOUBLE PRECISION
+      GREAL,GIMAG,XARG,YARG
Complex*16
+      ZARG,GCONJG,GCMPLX
GREAL( ZARG)=DREAL( ZARG)
GIMAG( ZARG)=DIMAG( ZARG)
GCONJG(ZARG)=DCONJG(ZARG)
GCMPLX(XARG,YARG)=DCMPLX(XARG,YARG)

U=Z
X=U
IF(GIMAG(U) .EQ. 0 .AND. -ABS(X) .EQ. INT(X)) THEN
F=0
H=0
WRITE(ERRTXT,101) X
CALL MTLPRT(NAME,'C305.1',ERRTXT)
ELSE
IF(X .GE. 1) THEN
F=1
V=U
ELSEIF(X .GE. 0) THEN
F=1/U
V=1+U
ELSE
F=1
V=1-U
END IF
H=1
S=C(0)
DO 1 K = 1,15
H=((V-K)/(V+(K-1)))*H
1  S=S+C(K)*H
H=V+(4+HF)
H=C1*EXP((V-HF)*LOG(H)-H)*S
IF(X .LT. 0) H=PI/(SIN(PI*U)*H)
ENDIF
WGAMMA=F*H
RETURN
101 FORMAT('ARGUMENT EQUALS NON-POSITIVE INTEGER = ',1P,E15.1)
END*/
