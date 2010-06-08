package jm.atom;
import jm.grid.TransLcrToR;
import stlx.valarrayx.valarray;

import javax.iox.LOG;
import javax.mathx.MathX;
import javax.vecmathx.VecMathException;
import javax.vecmathx.function.FuncVec;
import javax.vecmathx.grid.StepGrid;
import javax.vecmathx.interpolation.PolynomInterpol;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/03/2005, Time: 08:01:04
 */
//
// YkLogR(r)=r I^oo_0 r<^k/r>^(k+1) s^2 ds Rn(s)Rn'(s)
// R(r)=sqrt(y)/r * P(r); where y(r)=c+r
// YkLogR(r) = Zk(r) + I^oo_r (r/s)^(k+1) yds Pn(s) Pn'(s)
// Zk(r) = I^r_0 (s/r)^k yds Pn(s) Pn'(s)
//
// dZk/dr = -k/r Zk + PPy
// dYk/dr = (k+1)/r YkLogR - (2k+1)/r Zk
// Zk(0)=0; YkLogR(r->oo)->Zk(r)
// dZ/dr=dZ/dx 1/y; since x=ln(y)
//
// dZ/dx = -ky/r Z + PPy^2
// dY/dx = (k+1)y/r Y - (2k+1)y/r Z
//
// integrating factor: r^k    for Z
// d(r^k Z)/dx = k r^(k-1) dr/dx Z + r^k dZ/dx;  since dr/dx=y
// d(r^k Z)/dx = FF y^2 r^k
//
// Zim = (ri/rim)^k Zi + I^im_i FF y^2 * (r/rim)^k dx;  where ri=r_i, rim=r_(i+m)
// the same as in Froese-Fischer but replacing
//  exp(-mhk) with (ri/rim)^k;   and
//  r*exp(k(x-xim)) with  y^2*(r/rim)^k
//
// integrating factor: r^-(k+1)    for Y
// d(r^-(k+1) Y)/dx = -(k+1) r^(k+1) y/r Z + r^-(k+1) dZ/dx;  since dr/dx=y
// d(r^-(k+1) Y)/dx = -(2k+1)y/r r^-(k+1) Z
//
// Yim = (rim/ri)^(k+1) Yi + I^im_i Z y/r (rim/r)^(k+1) dx;  where ri=r_i, rim=r_(i+m)
// the same as in Froese-Fischer but replacing
//  exp(-mh(k+1)) with (ri/rim)^(k+1);   and
//  exp(-(k+1)*(x-xi)) with  y/r*(r/rim)^(k+1)
//
public class YkLcr {
  private final valarray f;
  private final valarray f2;
  private final valarray CR2;//(c+r)^2
  private final valarray CR;//(c+r)
  private final valarray r;
  private final valarray divR;
  private final valarray yDivR;
  private final int K;
  private final int MX;
  private final double H;
  private final double H2;
  private final double H3;
  public YkLcr(final TransLcrToR xToR, final valarray f, final valarray f2, final int K) {
    this.K = K;
    this.f = f;
    this.f2 = f2;
    r = xToR;
    CR2 = xToR.getCR2();
    CR = xToR.getCR();
    divR = xToR.getDivR();
    yDivR = xToR.getCRDivR();
    if (!(xToR.x instanceof StepGrid)) {
      String mssg = "YkLogR can only work with StepGrid";
      LOG.error(this, mssg, "");
      throw new VecMathException(mssg);
    }
    H = ((StepGrid) xToR.x).getStep();
    H2 = H / 2.0;
    H3 = H / 3.0;
    MX = Math.min(f.size(), f2.size()) / 2 * 2;//      MX = (MIN0(MAX(I),MAX(J))/2)*2                                    AATK4123
    // MX is used to trace simpson's rule errors
    //EH = DEXP(-H)   // from SUBROUTINE INIT
  }

  public FuncVec calcZk() {
    FuncVec res = new FuncVec(r, r.size());
    //      DEN = L(I) + L(J) + 3+ K                                          AATK4107
    //      FACT = (D1/(L(I)+1) + D1/(L(J)+1))/(DEN + D1)                     AATK4108
    //      A = EH**K                                                         AATK4109
    //      A2 = A*A                                                          AATK4110
    double H90 = H / 90.0;//      H90 = H/90.D0                                                     AATK4111
    //      A3 = A2*A*H90                                                     AATK4112
    //      AI = H90/A                                                        AATK4113
    //      AN = 114.D0*A*H90                                                 AATK4114
    double A34 = 34.0 * H90;//      A34 = 34.D0*H90                                                   AATK4115
    int M = 0;
    double F1 = CR2.get(M) * f.get(M) * f2.get(M);
    M++;//      F1 = RR(1)*P(1,I)*P(1,J)                                          AATK4116
    double F2 = CR2.get(M) * f.get(M) * f2.get(M);
    M++;//      F2 = RR(2)*P(2,I)*P(2,J)                                          AATK4117
    double F3 = CR2.get(M) * f.get(M) * f2.get(M);
    M++;//      F3 = RR(3)*P(3,I)*P(3,J)                                          AATK4118
    double F4 = CR2.get(M) * f.get(M) * f2.get(M);
    M++;//      F4 = RR(4)*P(4,I)*P(4,J)                                          AATK4119
    //YK(1) = F1*(D1 + Z*R(1)*FACT)/DEN                                 AATK4120
    //YK(2) = F2*(D1 + Z*R(2)*FACT)/DEN                                 AATK4121
    //YK(3) = YK(1)*A2 + H3*(F3 + D4*A*F2 + A2*F1)                      AATK4122
    res.set(0, 0);
    res.set(1, approxFirstZ(1, F2, F3));
    res.set(2, approxFirstZ(2, F2, F3)); // simpson does not work as good as this
    for (M = 5; M <= MX; M++) {//      DO 8 M = 5,MX                                                     AATK4124
      double F5 = CR2.get(M - 1) * f.get(M - 1) * f2.get(M - 1);//      F5 = (RR(M)*P(M,I))*P(M,J)                                        AATK4125

      // Zim = (ri/rim)^k Zi + I^im_i FF y^2 * (r/rim)^k dx;  where ri=r_i, rim=r_(i+m)
      // the same as in Froese-Fischer but replacing
      //  exp(-mhk) with (ri/rim)^k;   and
      //  r*exp(k(x-xim)) with  y^2*(r/rim)^k

      // Replace exp(-mhk) with (ri/rim)^k;  where m=2
      //      EH = DEXP(-H)   // from SUBROUTINE INIT
      //      A = EH**K                                                         AATK4109
      double rM2 = divR.get(M - 2);
      double A3 = MathX.pow(r.get(M - 5) * rM2, K) * H90;//      A3 = A2*A*H90                                                     AATK4112
      double A2 = MathX.pow(r.get(M - 4) * rM2, K);//      A2 = A*A                                                          AATK4110
      double AN = MathX.pow(r.get(M - 3) * rM2, K) * 114.0 * H90;//      AN = 114.D0*A*H90                                                 AATK4114
      double AI = MathX.pow(r.get(M - 1) * rM2, K) * H90;//      AI = H90/A                                                        AATK4113

      // From Froese Fischer etal Computational atomic structure 2000
      // DELTA^2 F = F2 - 2*F3 + F4
      // DELTA^4 F = F1 - 4*F2 + 6*F3 - 4*F4 + F5
      // INTL F(x)dx = h(F3 + DELTA^2 F/3 - DELTA^4 F/90) + O(h^7) =
      //  = h/90 [114*F3 + 34*(F2+F4) - F1 - F5]
      //
      //      YK(M-1) = YK(M-3)*A2 + ( AN*F3 + A34*(F4+A2*F2)-F5*AI-F1*A3)      AATK4126
      res.set(M - 2, res.get(M - 4) * A2 + (AN * F3 + A34 * (F4 + A2 * F2) - F5 * AI - F1 * A3));
      F1 = F2;//      F1 = F2                                                           AATK4127
      F2 = F3;//      F2 = F3                                                           AATK4128
      F3 = F4;//      F3 = F4                                                           AATK4129
      F4 = F5;//8     F4 = F5                                                           AATK4130
    }
    int M1 = MX - 1;//      M1 = MX - 1                                                       AATK4131
    if (f == f2 && K == 0) {//      IF (IABS(I-J)  +  IABS(K) .NE. 0) GO TO 2                         AATK4132
      //*  *****  FOR Y0(I,I) SET THE LIMIT TO 1 AND REMOVE OSCILLATIONS        AATK4134
      //*  *****  INTRODUCED BY THE USE OF SIMPSON'S RULE                       AATK4135
      //      M2 = M1 - 1                                                       AATK4137
      double C1 = 1. - res.get(M1 - 1);//      C1 = D1 - YK(M1)                                                  AATK4138
      double C2 = 1. - res.get(M1 - 2);//      C2 = D1 - YK(M2)                                                  AATK4139
      for (M = 1; M <= M1; M += 2) {//      DO 3 M = 1,M1,2                                                   AATK4140
        res.set(M - 1, res.get(M - 1) + C1);//      YK(M) = YK(M) + C1                                                AATK4141
        res.set(M, res.get(M) + C2);//3     YK(M+1) = YK(M+1) + C2                                            AATK4142
      }
    }
    int NO = r.size();
    for (M = M1 + 1; M <= NO; M++) {//2     DO 1 M = M1+1,NO                                                  AATK4143
      double A = MathX.pow(r.get(M - 2) * divR.get(M - 1), K);//A = EH**K                                                         AATK4109
      res.set(M - 1, A * res.get(M - 2));//         YK(M) = A*YK(M-1)                                              AATK4144
//         y.set(M-1, y.getLine(M-2));
    }//1     CONTINUE                                                          AATK4145
    return res;
  }
  private double approxFirstZ(int idxDest, double F2, double F3) {
    FuncVec tmp = new FuncVec(r, 3);
    int M = 0;
    tmp.set(M, 0);
    M++;
    double fx[] = {0, 0, 0};
    double rDest = r.get(idxDest);
    double A = MathX.pow(r.get(M) / rDest, K);
    fx[M] = F2 * A / CR.get(M);
    tmp.set(M, fx[M]);
    M++;
    A = MathX.pow(r.get(M) / rDest, K);
    fx[M] = F3 * A / CR.get(M);
    tmp.set(M, fx[M]);
    double b = PolynomInterpol.calcPowerSLOW(tmp, 0);
//      b = 4;
    return rDest / (b + 1) * fx[idxDest];
  }
  public FuncVec calcYk() {
    FuncVec res = calcZk(); //CALL ZK(I,J,K)                                                    AATK4062
    //A =    EH**(K+1)                                                  AATK4063
    int K1 = K + 1;
    double C = 2 * K + 1;//      C = 2*K+1                                                         AATK4064
    //      A2 = A*A                                                          AATK4065
    double H90 = C * H3 / 30.;//      H90 = C*H3/D30                                                    AATK4066
    //      A3 = A2*A*H90                                                     AATK4067
    //      AI = H90/A                                                        AATK4068
    //      AN = 114.D0*A*H90                                                 AATK4069
    double A34 = 34. * H90;//      A34 = 34.D0*H90                                                   AATK4070
    //      MX = (MIN0(MAX(I),MAX(J))/2)*2                                    AATK4071
    int M = MX - 1;
    double F1 = yDivR.get(M) * res.get(M);
    M--;//      F1 = YK(MX)*EH**K                                                 AATK4072
    double F2 = yDivR.get(M) * res.get(M);
    M--;//      F2 = YK(MX)                                                       AATK4073
    double F3 = yDivR.get(M) * res.get(M);
    M--;//      F3 = YK(MX-1)                                                     AATK4074
    double F4 = yDivR.get(M) * res.get(M);
    M--;//      F4 = YK(MX-2)                                                     AATK4075
    // NOTE!!! MX-1, MX-2, and MX-3; the last three must be set to Z, otherwise the algorithm does not work?!
    for (M = MX - 3; M >= 2; M--) {//      DO 9 M = MX-2,2,-1                                                AATK4076
      double F5 = yDivR.get(M - 2) * res.get(M - 2);//      F5 = YK(M-1)                                                      AATK4077
      double rM1 = r.get(M - 1); // NOTE K1 not K below
      double A3 = MathX.pow(rM1 * divR.get(M + 2), K1) * H90;//      A3 = A2*A*H90                                                     AATK4112
      double A2 = MathX.pow(rM1 * divR.get(M + 1), K1);//      A2 = A*A                                                          AATK4110
      double AN = MathX.pow(rM1 * divR.get(M + 0), K1) * 114.0 * H90;//      AN = 114.D0*A*H90                                                 AATK4114
      double AI = MathX.pow(rM1 * divR.get(M - 2), K1) * H90;//      AI = H90/A                                                        AATK4113

      //      YK(M) = YK(M+2)*A2 + ( AN*F3 + A34*(F4+A2*F2)-F5*AI-F1*A3)        AATK4078
      res.set(M - 1, res.get(M + 1) * A2 + (AN * F3 + A34 * (F4 + A2 * F2) - F5 * AI - F1 * A3));
      F1 = F2;//      F1 = F2                                                           AATK4079
      F2 = F3;//      F2 = F3                                                           AATK4080
      F3 = F4;//      F3 = F4                                                           AATK4081
      F4 = F5;//9     F4 = F5                                                           AATK4082
    }
    //      YK(1) = YK(3)*A2+C*H3*(F4 + D4*A*F3 + A2*F2)                      AATK4083
    res.set(0, 0); // always zero
    return res;
  }
}
/*
*AATKHF86.  GENERAL HARTREE-FOCK PROGRAM.  C.F. FISCHER.                 AATK0000
*REF. IN COMP. PHYS. COMMUN. 43 (1987) 355                               AATK0000
*     ------------------------------------------------------------------AATK0001
*                                                                       AATK0002
*     A GENERAL HARTREE-FOCK PROGRAM                                    AATK0003
*                                                                       AATK0004
*     by C. Froese Fischer                                              AATK0005
*        Vanderbilt University                                          AATK0006
*        Nashville, TN 37235 USA                                        AATK0007
*                                                                       AATK0008
*     April, 1986                                                       AATK0009
*                                                                       AATK0010
*     ------------------------------------------------------------------AATK0011
*                                                                       AATK0012
*                                                                       AATK0013
*     All comments in the program listing assume the radial function P  AATK0014
*     is the solution of an equation of the form                        AATK0015
*                                                                       AATK0016
*      P" + ( 2Z/R - Y - L(L+1)/R**2 - E)P = X + T                      AATK0017
*                                                                       AATK0018
*     where Y is called a potential function                            AATK0019
*           X is called an exchange function, and                       AATK0020
*           T includes contributions from off-diagonal energy parameter AATK0021
*                                                                       AATK0022
*     The program uses LOG(Z*R) as independent variable and             AATK0023
*                      P/SQRT(R) as dependent variable.                 AATK0024
*     As a result all equations must be transformed as described in     AATK0025
*     Sec. 6-2 and 6-4 of the book - ``The Hartree-Fock Method for      AATK0026
*     Atoms'',Wiley Interscience, 1977, by Charlotte FROESE FISCHER.    AATK0027
*     (All references to equations and sections refer to this book.)    AATK0028
*                                                                       AATK0029
*     This program is written as a double precision FORTRAN77           AATK0030
*     program.  However, on computers with a word length of 48 bits or  AATK0031
*     more it should be run as a single precision program.  Such con-   AATK0032
*     version is facilitated through the use of IMPLICIT type declar-   AATK0033
*     ations and the initialization of many double precision            AATK0034
*     constants in the INIT program.                                    AATK0035
*                                                                       AATK0036
*                                                                       AATK0037
*                                                                       AATK0051
*     ------------------------------------------------------------------AATK4046
*                 Y K F                                                 AATK4047
*     ------------------------------------------------------------------AATK4048
*                                                                       AATK4049
*               k                                                       AATK4050
*       Stores Y (i, j; r) in the array YK                              AATK4051
*                                                                       AATK4052
*                                                                       AATK4053
SUBROUTINE YKF(I,J,K,REL)                                         AATK4054
IMPLICIT DOUBLE PRECISION(A-H,O-Z)                                AATK4055
PARAMETER (NOD=220,NWFD=20)                                       AATK4056
COMMON /PARAM/H,H1,H3,CH,EH,RHO,Z,TOL,NO,ND,NWF,NP,NCFG,IB,IC,ID  AATK4057
:   ,D0,D1,D2,D3,D4,D5,D6,D8,D10,D12,D16,D30,FINE,NSCF,NCLOSD      AATK4058
COMMON /RADIAL/R(NOD),RR(NOD),R2(NOD),P(NOD,NWFD),YK(NOD),        AATK4059
:   YR(NOD),X(NOD),AZ(NWFD),L(NWFD),MAX(NWFD),N(NWFD)              AATK4060
LOGICAL REL                                                       AATK4061
CALL ZK(I,J,K)                                                    AATK4062
A =    EH**(K+1)                                                  AATK4063
C = 2*K+1                                                         AATK4064
A2 = A*A                                                          AATK4065
H90 = C*H3/D30                                                    AATK4066
A3 = A2*A*H90                                                     AATK4067
AI = H90/A                                                        AATK4068
AN = 114.D0*A*H90                                                 AATK4069
A34 = 34.D0*H90                                                   AATK4070
MX = (MIN0(MAX(I),MAX(J))/2)*2                                    AATK4071
F1 = YK(MX)*EH**K                                                 AATK4072
F2 = YK(MX)                                                       AATK4073
F3 = YK(MX-1)                                                     AATK4074
F4 = YK(MX-2)                                                     AATK4075
DO 9 M = MX-2,2,-1                                                AATK4076
F5 = YK(M-1)                                                      AATK4077
YK(M) = YK(M+2)*A2 + ( AN*F3 + A34*(F4+A2*F2)-F5*AI-F1*A3)        AATK4078
F1 = F2                                                           AATK4079
F2 = F3                                                           AATK4080
F3 = F4                                                           AATK4081
9     F4 = F5                                                           AATK4082
YK(1) = YK(3)*A2+C*H3*(F4 + D4*A*F3 + A2*F2)                      AATK4083
IF (.NOT.REL) RETURN                                              AATK4084
C = C*FINE                                                        AATK4085
DO 10 M = 1,MX                                                    AATK4086
YK(M) = YK(M) + C*P(M,I)*P(M,J)                                   AATK4087
10    CONTINUE                                                          AATK4088
RETURN                                                            AATK4089
END                                                               AATK4090
*                                                                       AATK4091
*AATKHF86.  GENERAL HARTREE-FOCK PROGRAM.  C.F. FISCHER.                 AATK0000
*REF. IN COMP. PHYS. COMMUN. 43 (1987) 355                               AATK0000
*     ------------------------------------------------------------------AATK0001
*                                                                       AATK0002
*     A GENERAL HARTREE-FOCK PROGRAM                                    AATK0003
*                                                                       AATK0004
*     by C. Froese Fischer                                              AATK0005
*        Vanderbilt University                                          AATK0006
*        Nashville, TN 37235 USA                                        AATK0007
*                                                                       AATK0008
*     April, 1986                                                       AATK0009
*                                                                       AATK0010
*     ------------------------------------------------------------------AATK0011
*                                                                       AATK0012
*                                                                       AATK0013
*     All comments in the program listing assume the radial function P  AATK0014
*     is the solution of an equation of the form                        AATK0015
*                                                                       AATK0016
*      P" + ( 2Z/R - Y - L(L+1)/R**2 - E)P = X + T                      AATK0017
*                                                                       AATK0018
*     where Y is called a potential function                            AATK0019
*           X is called an exchange function, and                       AATK0020
*           T includes contributions from off-diagonal energy parameter AATK0021
*                                                                       AATK0022
*     The program uses LOG(Z*R) as independent variable and             AATK0023
*                      P/SQRT(R) as dependent variable.                 AATK0024
*     As a result all equations must be transformed as described in     AATK0025
*     Sec. 6-2 and 6-4 of the book - ``The Hartree-Fock Method for      AATK0026
*     Atoms'',Wiley Interscience, 1977, by Charlotte FROESE FISCHER.    AATK0027
*     (All references to equations and sections refer to this book.)    AATK0028
*                                                                       AATK0029
*     This program is written as a double precision FORTRAN77           AATK0030
*     program.  However, on computers with a word length of 48 bits or  AATK0031
*     more it should be run as a single precision program.  Such con-   AATK0032
*     version is facilitated through the use of IMPLICIT type declar-   AATK0033
*     ations and the initialization of many double precision            AATK0034
*     constants in the INIT program.                                    AATK0035
*                                                                       AATK0036
*                                                                       AATK0037
*     ------------------------------------------------------------------AATK4092
*                 Z K                                                   AATK4093
*     ------------------------------------------------------------------AATK4094
*                                                                       AATK4095
*               k                                                       AATK4096
*       Stores Z (i, j; r) in the array YK.                             AATK4097
*                                                                       AATK4098
*                                                                       AATK4099
SUBROUTINE ZK(I,J,K)                                              AATK4100
PARAMETER (NOD=220,NWFD=20)                                       AATK4101
IMPLICIT DOUBLE PRECISION(A-H,O-Z)                                AATK4102
COMMON /PARAM/H,H1,H3,CH,EH,RHO,Z,TOL,NO,ND,NWF,NP,NCFG,IB,IC,ID  AATK4103
:   ,D0,D1,D2,D3,D4,D5,D6,D8,D10,D12,D16,D30,FINE,NSCF,NCLOSD      AATK4104
COMMON /RADIAL/R(NOD),RR(NOD),R2(NOD),P(NOD,NWFD),YK(NOD),        AATK4105
:   YR(NOD),X(NOD),AZ(NWFD),L(NWFD),MAX(NWFD),N(NWFD)              AATK4106
DEN = L(I) + L(J) + 3+ K                                          AATK4107
FACT = (D1/(L(I)+1) + D1/(L(J)+1))/(DEN + D1)                     AATK4108
A = EH**K                                                         AATK4109
A2 = A*A                                                          AATK4110
H90 = H/90.D0                                                     AATK4111
A3 = A2*A*H90                                                     AATK4112
AI = H90/A                                                        AATK4113
AN = 114.D0*A*H90                                                 AATK4114
A34 = 34.D0*H90                                                   AATK4115
F1 = RR(1)*P(1,I)*P(1,J)                                          AATK4116
F2 = RR(2)*P(2,I)*P(2,J)                                          AATK4117
F3 = RR(3)*P(3,I)*P(3,J)                                          AATK4118
F4 = RR(4)*P(4,I)*P(4,J)                                          AATK4119
YK(1) = F1*(D1 + Z*R(1)*FACT)/DEN                                 AATK4120
YK(2) = F2*(D1 + Z*R(2)*FACT)/DEN                                 AATK4121
YK(3) = YK(1)*A2 + H3*(F3 + D4*A*F2 + A2*F1)                      AATK4122
MX = (MIN0(MAX(I),MAX(J))/2)*2                                    AATK4123
DO 8 M = 5,MX                                                     AATK4124
F5 = (RR(M)*P(M,I))*P(M,J)                                        AATK4125
YK(M-1) = YK(M-3)*A2 + ( AN*F3 + A34*(F4+A2*F2)-F5*AI-F1*A3)      AATK4126
F1 = F2                                                           AATK4127
F2 = F3                                                           AATK4128
F3 = F4                                                           AATK4129
8     F4 = F5                                                           AATK4130
M1 = MX - 1                                                       AATK4131
IF (IABS(I-J)  +  IABS(K) .NE. 0) GO TO 2                         AATK4132
*                                                                       AATK4133
*  *****  FOR Y0(I,I) SET THE LIMIT TO 1 AND REMOVE OSCILLATIONS        AATK4134
*  *****  INTRODUCED BY THE USE OF SIMPSON'S RULE                       AATK4135
*                                                                       AATK4136
M2 = M1 - 1                                                       AATK4137
C1 = D1 - YK(M1)                                                  AATK4138
C2 = D1 - YK(M2)                                                  AATK4139
DO 3 M = 1,M1,2                                                   AATK4140
YK(M) = YK(M) + C1                                                AATK4141
3     YK(M+1) = YK(M+1) + C2                                            AATK4142
2     DO 1 M = M1+1,NO                                                  AATK4143
YK(M) = A*YK(M-1)                                              AATK4144
1     CONTINUE                                                          AATK4145
END                                                               AATK4146
*/