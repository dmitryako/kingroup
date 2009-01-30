package jm.angular;
import javax.mathx.MathX;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 21/03/2005, Time: 15:41:29
 */
public class Wign3j {
  private static double R1 = 1;//   PARAMETER (R1 = 1, HF = R1/2)
  private static double HF = R1 / 2;
//   DIMENSION U(0:202)
  private static double U[] = {0., 0., 0., 0., .6931471805599453, 0.,
    1.791759469228055, 0., 3.178053830347946, 0., 4.787491742782046, 0.,
    6.579251212010101, 0., 8.525161361065414, 0., 10.60460290274525, 0.,
    12.80182748008147, 0., 15.10441257307552, 0., 17.50230784587389, 0.,
    19.98721449566189, 0., 22.55216385312342, 0., 25.19122118273868, 0.,
    27.89927138384089, 0., 30.67186010608067, 0., 33.50507345013689, 0.,
    36.39544520803305, 0., 39.33988418719949, 0., 42.33561646075349, 0.,
    45.38013889847691, 0., 48.47118135183522, 0., 51.60667556776437, 0.,
    54.78472939811232, 0., 58.00360522298052, 0., 61.261701761002, 0.,
    64.55753862700633, 0., 67.88974313718153, 0., 71.25703896716801, 0.,
    74.65823634883016, 0., 78.09222355331531, 0., 81.55795945611504, 0.,
    85.05446701758152, 0., 88.58082754219768, 0., 92.13617560368709, 0.,
    95.7196945421432, 0., 99.33061245478743, 0., 102.9681986145138, 0.,
    106.6317602606435, 0., 110.3206397147574, 0., 114.0342117814617, 0.,
    117.7718813997451, 0., 121.5330815154386, 0., 125.3172711493569, 0.,
    129.1239336391272, 0., 132.9525750356163, 0., 136.8027226373264, 0.,
    140.6739236482343, 0., 144.5657439463449, 0., 148.477766951773, 0.,
    152.4095925844974, 0., 156.3608363030788, 0., 160.3311282166309, 0.,
    164.3201122631952, 0., 168.3274454484277, 0., 172.3527971391628, 0.,
    176.3958484069974, 0., 180.4562914175438, 0., 184.5338288614495, 0.,
    188.6281734236716, 0., 192.7390472878449, 0., 196.86618167289, 0.,
    201.0093163992815, 0., 205.1681994826412, 0., 209.3425867525368, 0.,
    213.5322414945633, 0., 217.7369341139542, 0., 221.9564418191303, 0.,
    226.1905483237276, 0., 230.439043565777, 0., 234.7017234428183, 0.,
    238.9783895618343, 0., 243.2688490029827, 0., 247.5729140961869, 0.,
    251.8904022097232, 0., 256.2211355500095, 0., 260.5649409718632, 0.,
    264.9216497985528, 0., 269.2910976510198, 0., 273.6731242856937, 0.,
    278.0675734403661, 0., 282.4742926876304, 0., 286.893133295427, 0.,
    291.3239500942703, 0., 295.7666013507606, 0., 300.2209486470141, 0.,
    304.6868567656687, 0., 309.1641935801469, 0., 313.6528299498791, 0.,
    318.1526396202093, 0., 322.6634991267262, 0., 327.1852877037752, 0.,
    331.7178871969285, 0., 336.2611819791985, 0., 340.815058870799, 0.,
    345.3794070622669, 0., 349.9541180407702, 0., 354.5390855194408, 0.,
    359.1342053695754, 0., 363.7393755555635, 0., 368.3544960724047};
  public static double calc(float A1, float B1, float C1, float X1, float Y1, float Z1) {//FUNCTION calcConfigEnergy(A1,B1,C1,X1,Y1,Z1)
    //IMPLICIT DOUBLE PRECISION (A-H,O-Z)
    //LOGICAL LCG,LJN,LRC
    //LCG=.FALSE.
    return calc(false, A1, B1, C1, X1, Y1, Z1);
  }
  public static double calc2L(int IA, int IB, int IC, int IX, int IY, int IZ) {
    return calc(false, IA, IB, IC, IX, IY, IZ);
  }
  public static double calcWign6j(float A1, float B1, float C1, float X1, float Y1, float Z1) {
    //            LJN=.FALSE.
    //            LRC=.FALSE.
    return calcWign6j(false, false, A1, B1, C1, X1, Y1, Z1);
  }
  public static double calcClebsh(float A1, float B1, float C1, float X1, float Y1, float Z1) {//ENTRY calcClebsh(A1,B1,C1,X1,Y1,Z1)
    //LCG=.TRUE.
    return calc(true, A1, B1, C1, X1, Y1, Z1);
  }
  // 2*L as ints
  public static double calcClebsh2L(int IA, int IB, int IC, int IX, int IY, int IZ) {
    return calc2L(true, IA, IB, IC, IX, IY, IZ);
  }
  private static double calc(boolean LCG, float A1, float B1, float C1, float X1, float Y1, float Z1) {
    int IA = Math.round(2 * A1); //        IA=NINT(2*A1)
    int IB = Math.round(2 * B1);//        IB=NINT(2*B1)
    int IC = Math.round(2 * C1);//        IC=NINT(2*C1)
    int IX = Math.round(2 * X1);//        IX=NINT(2*X1)
    int IY = Math.round(2 * Y1);//        IY=NINT(2*Y1)
    int IZ = Math.round(2 * Z1);//        IZ=NINT(2*Z1)
    return calc2L(LCG, IA, IB, IC, IX, IY, IZ);
  }
  private static double calcWign6j(boolean LJN, boolean LRC, float A1, float B1, float C1, float X1, float Y1, float Z1) {
    int IA = Math.round(2 * A1); //        IA=NINT(2*A1)
    int IB = Math.round(2 * B1);//        IB=NINT(2*B1)
    int IC = Math.round(2 * C1);//        IC=NINT(2*C1)
    int IX = Math.round(2 * X1);//        IX=NINT(2*X1)
    int IY = Math.round(2 * Y1);//        IY=NINT(2*Y1)
    int IZ = Math.round(2 * Z1);//        IZ=NINT(2*Z1)
    return calcWign6j2L(LJN, LRC, IA, IB, IC, IX, IY, IZ);
  }
  private static double calc2L(boolean LCG, int IA, int IB, int IC, int IX, int IY, int IZ) {
    double F = 0;
    double H = 0;//7 H=0
    //IF(IA .LT. 0 .OR. IB .LT. 0 .OR. IC .LT. 0) GO TO 99
    if (IA < 0 || IB < 0 || IC < 0) return H;
    if (MathX.mod(IA + IB + IC, 2) != 0) return H;//IF(MOD(IA+IB+IC,2) .NE. 0) GO TO 99
    int JX = Math.abs(IX);//        JX=ABS(IX)
    int JY = Math.abs(IY);//        JY=ABS(IY)
    int JZ = Math.abs(IZ);//        JZ=ABS(IZ)
    //IF(IA .LT. JX .OR. IB .LT. JY .OR. IC .LT. JZ) GO TO 99
    if (IA < JX || IB < JY || IC < JZ) return H;
    //IF(MOD(IA+JX,2) .NE. 0 .OR. MOD(IB+JY,2) .NE. 0) GOTO 99
    if (MathX.mod(IA + JX, 2) != 0 || MathX.mod(IB + JY, 2) != 0) return H;
    if (MathX.mod(IC + JZ, 2) != 0) return H;//IF(MOD(IC+JZ,2) .NE. 0) GO TO 99
    int J0;
    if (LCG) {//        IF(LCG) THEN
      IZ = -IZ;//         IZ=-IZ
      J0 = 0;//         J0=0
      F = Math.sqrt((IC + 1) * R1);//         F=SQRT((IC+1)*R1)
    } else {//        ELSE
      J0 = IA - IB - IZ;//         J0=IA-IB-IZ
      F = 1;//         F=1
    }//        ENDIF
    //IF(IX+IY+IZ .NE. 0 .OR. MOD(J0,2) .NE. 0) GO TO 99
    if (IX + IY + IZ != 0 || MathX.mod(J0, 2) != 0) return H;
    int K0 = IA + IB + IC + 2;//        K0=IA+IB+IC+2
    int K1 = IA + IB - IC;//        K1=IA+IB-IC
    int K2 = IA - IB + IC;//        K2=IA-IB+IC
    int K3 = IB + IC - IA;//        K3=IB+IC-IA
    //IF(K1 .LT. 0 .OR. K2 .LT. 0 .OR. K3 .LT. 0) GO TO 99
    if (K1 < 0 || K2 < 0 || K3 < 0) return H;
    int K4 = IA + IX;//    K4=IA+IX
    int K5 = IB + IY;//    K5=IB+IY
    int K6 = IC + IZ;//    K6=IC+IZ
    int K7 = IA - IX;//    K7=IA-IX
    int K8 = IB - IY;//         K8=IB-IY
    int K9 = IC - IZ;//         K9=IC-IZ
    int K10 = IB - IC - IX;//         K10=IB-IC-IX
    int K11 = IA - IC + IY;//         K11=IA-IC+IY
    int KA = MathX.max(0, K10, K11);//        KA=MAX(0,K10,K11)
    int KZ = MathX.min(K1, K5, K7);//        KZ=MIN(K1,K5,K7)
    //     W=HF*(U(K1)+U(K2)+U(K3)+U(K4)+U(K5)+U(K6)+U(K7)+U(K8)+U(K9)-U(K0))
    double W = HF * (U[K1] + U[K2] + U[K3] + U[K4] + U[K5] + U[K6] + U[K7] + U[K8] + U[K9] - U[K0]);
    double S = 0;//        S=0
    double Q = MathX.pow(-1, (KA + J0) / 2);//        Q=(-1)**((KA+J0)/2)
    for (int K = KA; K <= KZ; K += 2) {//        DO 1 K = KA,KZ,2
      //  S=S+Q*EXP(W-(U(K)+U(K1-K)+U(K5-K)+U(K7-K)+U(K-K10)+U(K-K11)))
      S += Q * Math.exp(W - (U[K] + U[K1 - K] + U[K5 - K] + U[K7 - K] + U[K - K10] + U[K - K11]));
      Q = -Q;//      1 Q=-Q
    }
    H = F * S;//        H=F*S
    return H;//        GO TO 99
  }
  private static double calcWign6j2L(boolean LJN, boolean LRC
    , int IA, int IB, int IC
    , int IX, int IY, int IZ) {
    //      #if defined(CERNLIB_DOUBLE)
    //            ENTRY DWIG6J(A1,B1,C1,X1,Y1,Z1)
    //      #endif
    //      #if !defined(CERNLIB_DOUBLE)
    //            ENTRY RWIG6J(A1,B1,C1,X1,Y1,Z1)
    //      #endif
    //            LJN=.FALSE.
    //            LRC=.FALSE.
    //            A=A1
    //            B=B1
    //            C=C1
    //            X=X1
    //            Y=Y1
    //            Z=Z1
    //            GO TO 9
    //      #if defined(CERNLIB_DOUBLE)
    //            ENTRY DRACAW(A1,B1,C1,X1,Y1,Z1)
    //      #endif
    //      #if !defined(CERNLIB_DOUBLE)
    //            ENTRY RRACAW(A1,B1,C1,X1,Y1,Z1)
    //      #endif
    //            LJN=.FALSE.
    //            LRC=.TRUE.
    //            GO TO 8
    //      #if defined(CERNLIB_DOUBLE)
    //            ENTRY DJAHNU(A1,B1,C1,X1,Y1,Z1)
    //      #endif
    //      #if !defined(CERNLIB_DOUBLE)
    //            ENTRY RJAHNU(A1,B1,C1,X1,Y1,Z1)
    //      #endif
    //            LJN=.TRUE.
    //            LRC=.FALSE.
    //          8 A=A1
    //            B=B1
    //            C=Y1
    //            X=X1
    //            Y=C1
    //            Z=Z1
    double H = 0;//          9 H=0
    //            IA=NINT(2*A)
    //            IB=NINT(2*B)
    //            IC=NINT(2*C)
    //           IF(IA .LT. 0 .OR. IB .LT. 0 .OR. IC .LT. 0) GO TO 99
    if (IA < 0 || IB < 0 || IC < 0) return H;
    //            IX=NINT(2*X)
    //            IY=NINT(2*Y)
    //            IZ=NINT(2*Z)
    //            IF(IX .LT. 0 .OR. IY .LT. 0 .OR. IZ .LT. 0) GO TO 99
    if (IX < 0 || IY < 0 || IZ < 0) return H;
    int IABC = IA + IB + IC;//            IABC=IA+IB+IC
    int IAYZ = IA + IY + IZ;//            IAYZ=IA+IY+IZ
    //            IF(MOD(IABC,2) .NE. 0 .OR. MOD(IAYZ,2) .NE. 0) GOTO 99
    if (MathX.mod(IABC, 2) != 0 || MathX.mod(IAYZ, 2) != 0) return H;
    int IXBZ = IX + IB + IZ;//            IXBZ=IX+IB+IZ
    int IXYC = IX + IY + IC;//            IXYC=IX+IY+IC
    //            IF(MOD(IXBZ,2) .NE. 0 .OR. MOD(IXYC,2) .NE. 0) GOTO 99
    if (MathX.mod(IXBZ, 2) != 0 || MathX.mod(IXYC, 2) != 0) return H;
    int K1 = IA + IB - IC;//            K1=IA+IB-IC
    int K2 = IA - IB + IC;//            K2=IA-IB+IC
    int K3 = IB + IC - IA;//            K3=IB+IC-IA
    //            IF(K1 .LT. 0 .OR. K2 .LT. 0 .OR. K3 .LT. 0) GO TO 99
    if (K1 < 0 || K2 < 0 || K3 < 0) return H;
    int K4 = IA + IY - IZ;//            K4=IA+IY-IZ
    int K5 = IA - IY + IZ;//            K5=IA-IY+IZ
    int K6 = IY + IZ - IA;//            K6=IY+IZ-IA
    //            IF(K4 .LT. 0 .OR. K5 .LT. 0 .OR. K6 .LT. 0) GO TO 99
    if (K4 < 0 || K5 < 0 || K6 < 0) return H;
    int K7 = IX + IB - IZ;//            K7=IX+IB-IZ
    int K8 = IX - IB + IZ;//            K8=IX-IB+IZ
    int K9 = IB + IZ - IX;//            K9=IB+IZ-IX
    //            IF(K7 .LT. 0 .OR. K8 .LT. 0 .OR. K9 .LT. 0) GO TO 99
    if (K7 < 0 || K8 < 0 || K9 < 0) return H;
    int K10 = IX + IY - IC;//            K10=IX+IY-IC
    int K11 = IX - IY + IC;//            K11=IX-IY+IC
    int K12 = IY + IC - IX;//            K12=IY+IC-IX
    //            IF(K10 .LT. 0 .OR. K11 .LT. 0 .OR. K12 .LT. 0) GO TO 99
    if (K10 < 0 || K11 < 0 || K12 < 0) return H;
    int IABXY = IA + IB + IX + IY;//            IABXY=IA+IB+IX+IY
    int IBCYZ = IB + IC + IY + IZ;//            IBCYZ=IB+IC+IY+IZ
    int ICAZX = IC + IA + IZ + IX;//            ICAZX=IC+IA+IZ+IX
    int KA = MathX.max(IABC, IAYZ, IXBZ, IXYC);//            KA=MAX(IABC,IAYZ,IXBZ,IXYC)
    int KZ = MathX.min(IABXY, IBCYZ, ICAZX);//            KZ=MIN(IABXY,IBCYZ,ICAZX)
    int J1 = KA;//            J1=KA
    if (LRC || LJN) J1 = KA + IABXY;//            IF(LRC .OR. LJN) J1=KA+IABXY
    //     W=HF*(U(K1)+U(K2)+U(K3)-U(IABC+2)+U(K4)+U(K5)+U(K6)-U(IAYZ+2)+
    double W = HF * (U[K1] + U[K2] + U[K3] - U[IABC + 2] + U[K4] + U[K5] + U[K6] - U[IAYZ + 2] +
      //1      U(K7)+U(K8)+U(K9)-U(IXBZ+2)+U(K10)+U(K11)+U(K12)-U(IXYC+2))
      U[K7] + U[K8] + U[K9] - U[IXBZ + 2] + U[K10] + U[K11] + U[K12] - U[IXYC + 2]);
    double S = 0;//            S=0
    double Q = MathX.pow(-1, J1 / 2);//            Q=(-1)**(J1/2)
    for (int K = KA; K <= KZ; K += 2) {//            DO 2 K = KA,KZ,2
      //S=S+Q*EXP(W+U(K+2)-(U(K-IABC)+U(K-IAYZ)+U(K-IXBZ)+U(K-IXYC)+
      S = S + Q * Math.exp(W + U[K + 2] - (U[K - IABC] + U[K - IAYZ] + U[K - IXBZ] + U[K - IXYC] +
        //1            U(IABXY-K)+U(IBCYZ-K)+U(ICAZX-K)))
        U[IABXY - K] + U[IBCYZ - K] + U[ICAZX - K]));
      Q = -Q;//          2 Q=-Q
    }
    H = S;//            H=S
    //            IF(LJN) H=SQRT(((IC+1)*(IZ+1))*R1)*H
    if (LJN) H = Math.sqrt(((IC + 1) * (IZ + 1)) * R1) * H;
    return H;//            RETURN
  }
}

/*
*
* $Id: Wign3j.java,v 1.4 2008/07/16 00:16:38 jc138691 Exp $
*
* $Log: Wign3j.java,v $
* Revision 1.4  2008/07/16 00:16:38  jc138691
* no message
*
* Revision 1.3  2005/10/06 18:00:19  jc138691
* Maternal and paternal ids are working
*
* Revision 1.2  2005/05/16 05:42:00  jc138691
* no message
*
* Revision 1.1.1.1  2005/05/12 05:51:42  jc138691
* Initilizing Repository.
*
* Revision 1.3  2005/03/23 01:20:28  jc138691
* no message
*
* Revision 1.2  2005/03/22 07:23:06  jc138691
* no message
*
* Revision 1.1  2005/03/21 06:45:11  jc138691
* no message
*
* Revision 1.1.1.1  1996/04/01 15:01:48  mclareni
* Mathlib gen
*
*
#include "gen/pilot.h"
#if defined(CERNLIB_DOUBLE)
      FUNCTION calcConfigEnergy(A1,B1,C1,X1,Y1,Z1)
      IMPLICIT DOUBLE PRECISION (A-H,O-Z)
#endif
#if !defined(CERNLIB_DOUBLE)
      FUNCTION RWIG3J(A1,B1,C1,X1,Y1,Z1)
#include "gen/imp64.inc"
#endif
      LOGICAL LCG,LJN,LRC

      DIMENSION U(0:202)

      PARAMETER (R1 = 1, HF = R1/2)

      DATA U(0),U(2),(U(2*N-1),N=1,101) /103*0/
      DATA U(  4),U(  6) /6.931471805599453D-01, 1.791759469228055D+00/
      DATA U(  8),U( 10) /3.178053830347946D+00, 4.787491742782046D+00/
      DATA U( 12),U( 14) /6.579251212010101D+00, 8.525161361065414D+00/
      DATA U( 16),U( 18) /1.060460290274525D+01, 1.280182748008147D+01/
      DATA U( 20),U( 22) /1.510441257307552D+01, 1.750230784587389D+01/
      DATA U( 24),U( 26) /1.998721449566189D+01, 2.255216385312342D+01/
      DATA U( 28),U( 30) /2.519122118273868D+01, 2.789927138384089D+01/
      DATA U( 32),U( 34) /3.067186010608067D+01, 3.350507345013689D+01/
      DATA U( 36),U( 38) /3.639544520803305D+01, 3.933988418719949D+01/
      DATA U( 40),U( 42) /4.233561646075349D+01, 4.538013889847691D+01/
      DATA U( 44),U( 46) /4.847118135183522D+01, 5.160667556776437D+01/
      DATA U( 48),U( 50) /5.478472939811232D+01, 5.800360522298052D+01/
      DATA U( 52),U( 54) /6.126170176100200D+01, 6.455753862700633D+01/
      DATA U( 56),U( 58) /6.788974313718153D+01, 7.125703896716801D+01/
      DATA U( 60),U( 62) /7.465823634883016D+01, 7.809222355331531D+01/
      DATA U( 64),U( 66) /8.155795945611504D+01, 8.505446701758152D+01/
      DATA U( 68),U( 70) /8.858082754219768D+01, 9.213617560368709D+01/
      DATA U( 72),U( 74) /9.571969454214320D+01, 9.933061245478743D+01/
      DATA U( 76),U( 78) /1.029681986145138D+02, 1.066317602606435D+02/
      DATA U( 80),U( 82) /1.103206397147574D+02, 1.140342117814617D+02/
      DATA U( 84),U( 86) /1.177718813997451D+02, 1.215330815154386D+02/
      DATA U( 88),U( 90) /1.253172711493569D+02, 1.291239336391272D+02/
      DATA U( 92),U( 94) /1.329525750356163D+02, 1.368027226373264D+02/
      DATA U( 96),U( 98) /1.406739236482343D+02, 1.445657439463449D+02/
      DATA U(100),U(102) /1.484777669517730D+02, 1.524095925844974D+02/
      DATA U(104),U(106) /1.563608363030788D+02, 1.603311282166309D+02/
      DATA U(108),U(110) /1.643201122631952D+02, 1.683274454484277D+02/
      DATA U(112),U(114) /1.723527971391628D+02, 1.763958484069974D+02/
      DATA U(116),U(118) /1.804562914175438D+02, 1.845338288614495D+02/
      DATA U(120),U(122) /1.886281734236716D+02, 1.927390472878449D+02/
      DATA U(124),U(126) /1.968661816728900D+02, 2.010093163992815D+02/
      DATA U(128),U(130) /2.051681994826412D+02, 2.093425867525368D+02/
      DATA U(132),U(134) /2.135322414945633D+02, 2.177369341139542D+02/
      DATA U(136),U(138) /2.219564418191303D+02, 2.261905483237276D+02/
      DATA U(140),U(142) /2.304390435657770D+02, 2.347017234428183D+02/
      DATA U(144),U(146) /2.389783895618343D+02, 2.432688490029827D+02/
      DATA U(148),U(150) /2.475729140961869D+02, 2.518904022097232D+02/
      DATA U(152),U(154) /2.562211355500095D+02, 2.605649409718632D+02/
      DATA U(156),U(158) /2.649216497985528D+02, 2.692910976510198D+02/
      DATA U(160),U(162) /2.736731242856937D+02, 2.780675734403661D+02/
      DATA U(164),U(166) /2.824742926876304D+02, 2.868931332954270D+02/
      DATA U(168),U(170) /2.913239500942703D+02, 2.957666013507606D+02/
      DATA U(172),U(174) /3.002209486470141D+02, 3.046868567656687D+02/
      DATA U(176),U(178) /3.091641935801469D+02, 3.136528299498791D+02/
      DATA U(180),U(182) /3.181526396202093D+02, 3.226634991267262D+02/
      DATA U(184),U(186) /3.271852877037752D+02, 3.317178871969285D+02/
      DATA U(188),U(190) /3.362611819791985D+02, 3.408150588707990D+02/
      DATA U(192),U(194) /3.453794070622669D+02, 3.499541180407702D+02/
      DATA U(196),U(198) /3.545390855194408D+02, 3.591342053695754D+02/
      DATA U(200),U(202) /3.637393755555635D+02, 3.683544960724047D+02/

      LCG=.FALSE.
      GO TO 7

#if defined(CERNLIB_DOUBLE)
      ENTRY calcClebsh(A1,B1,C1,X1,Y1,Z1)
#endif
#if !defined(CERNLIB_DOUBLE)
      ENTRY RCLEBG(A1,B1,C1,X1,Y1,Z1)
#endif
      LCG=.TRUE.

    7 H=0
      IA=NINT(2*A1)
      IB=NINT(2*B1)
      IC=NINT(2*C1)
      IX=NINT(2*X1)
      IY=NINT(2*Y1)
      IZ=NINT(2*Z1)
      IF(IA .LT. 0 .OR. IB .LT. 0 .OR. IC .LT. 0) GO TO 99
      IF(MOD(IA+IB+IC,2) .NE. 0) GO TO 99
      JX=ABS(IX)
      JY=ABS(IY)
      JZ=ABS(IZ)
      IF(IA .LT. JX .OR. IB .LT. JY .OR. IC .LT. JZ) GO TO 99
      IF(MOD(IA+JX,2) .NE. 0 .OR. MOD(IB+JY,2) .NE. 0) GOTO 99
      IF(MOD(IC+JZ,2) .NE. 0) GO TO 99
      IF(LCG) THEN
       IZ=-IZ
       J0=0
       F=SQRT((IC+1)*R1)
      ELSE
       J0=IA-IB-IZ
       F=1
      ENDIF
      IF(IX+IY+IZ .NE. 0 .OR. MOD(J0,2) .NE. 0) GO TO 99
      K0=IA+IB+IC+2
      K1=IA+IB-IC
      K2=IA-IB+IC
      K3=IB+IC-IA
      IF(K1 .LT. 0 .OR. K2 .LT. 0 .OR. K3 .LT. 0) GO TO 99
      K4=IA+IX
      K5=IB+IY
      K6=IC+IZ
      K7=IA-IX
      K8=IB-IY
      K9=IC-IZ
      K10=IB-IC-IX
      K11=IA-IC+IY
      KA=MAX(0,K10,K11)
      KZ=MIN(K1,K5,K7)
      W=HF*(U(K1)+U(K2)+U(K3)+U(K4)+U(K5)+U(K6)+U(K7)+U(K8)+U(K9)-U(K0))
      S=0
      Q=(-1)**((KA+J0)/2)
      DO 1 K = KA,KZ,2
      S=S+Q*EXP(W-(U(K)+U(K1-K)+U(K5-K)+U(K7-K)+U(K-K10)+U(K-K11)))
    1 Q=-Q
      H=F*S
      GO TO 99

#if defined(CERNLIB_DOUBLE)
      ENTRY DWIG6J(A1,B1,C1,X1,Y1,Z1)
#endif
#if !defined(CERNLIB_DOUBLE)
      ENTRY RWIG6J(A1,B1,C1,X1,Y1,Z1)
#endif

      LJN=.FALSE.
      LRC=.FALSE.
      A=A1
      B=B1
      C=C1
      X=X1
      Y=Y1
      Z=Z1
      GO TO 9

#if defined(CERNLIB_DOUBLE)
      ENTRY DRACAW(A1,B1,C1,X1,Y1,Z1)
#endif
#if !defined(CERNLIB_DOUBLE)
      ENTRY RRACAW(A1,B1,C1,X1,Y1,Z1)
#endif

      LJN=.FALSE.
      LRC=.TRUE.
      GO TO 8

#if defined(CERNLIB_DOUBLE)
      ENTRY DJAHNU(A1,B1,C1,X1,Y1,Z1)
#endif
#if !defined(CERNLIB_DOUBLE)
      ENTRY RJAHNU(A1,B1,C1,X1,Y1,Z1)
#endif

      LJN=.TRUE.
      LRC=.FALSE.
    8 A=A1
      B=B1
      C=Y1
      X=X1
      Y=C1
      Z=Z1

    9 H=0
      IA=NINT(2*A)
      IB=NINT(2*B)
      IC=NINT(2*C)
      IF(IA .LT. 0 .OR. IB .LT. 0 .OR. IC .LT. 0) GO TO 99
      IX=NINT(2*X)
      IY=NINT(2*Y)
      IZ=NINT(2*Z)
      IF(IX .LT. 0 .OR. IY .LT. 0 .OR. IZ .LT. 0) GO TO 99
      IABC=IA+IB+IC
      IAYZ=IA+IY+IZ
      IF(MOD(IABC,2) .NE. 0 .OR. MOD(IAYZ,2) .NE. 0) GOTO 99
      IXBZ=IX+IB+IZ
      IXYC=IX+IY+IC
      IF(MOD(IXBZ,2) .NE. 0 .OR. MOD(IXYC,2) .NE. 0) GOTO 99
      K1=IA+IB-IC
      K2=IA-IB+IC
      K3=IB+IC-IA
      IF(K1 .LT. 0 .OR. K2 .LT. 0 .OR. K3 .LT. 0) GO TO 99
      K4=IA+IY-IZ
      K5=IA-IY+IZ
      K6=IY+IZ-IA
      IF(K4 .LT. 0 .OR. K5 .LT. 0 .OR. K6 .LT. 0) GO TO 99
      K7=IX+IB-IZ
      K8=IX-IB+IZ
      K9=IB+IZ-IX
      IF(K7 .LT. 0 .OR. K8 .LT. 0 .OR. K9 .LT. 0) GO TO 99
      K10=IX+IY-IC
      K11=IX-IY+IC
      K12=IY+IC-IX
      IF(K10 .LT. 0 .OR. K11 .LT. 0 .OR. K12 .LT. 0) GO TO 99
      IABXY=IA+IB+IX+IY
      IBCYZ=IB+IC+IY+IZ
      ICAZX=IC+IA+IZ+IX
      KA=MAX(IABC,IAYZ,IXBZ,IXYC)
      KZ=MIN(IABXY,IBCYZ,ICAZX)
      J1=KA
      IF(LRC .OR. LJN) J1=KA+IABXY
      W=HF*(U(K1)+U(K2)+U(K3)-U(IABC+2)+U(K4)+U(K5)+U(K6)-U(IAYZ+2)+
     1      U(K7)+U(K8)+U(K9)-U(IXBZ+2)+U(K10)+U(K11)+U(K12)-U(IXYC+2))
      S=0
      Q=(-1)**(J1/2)
      DO 2 K = KA,KZ,2
      S=S+Q*EXP(W+U(K+2)-(U(K-IABC)+U(K-IAYZ)+U(K-IXBZ)+U(K-IXYC)+
     1            U(IABXY-K)+U(IBCYZ-K)+U(ICAZX-K)))
    2 Q=-Q
      H=S
      IF(LJN) H=SQRT(((IC+1)*(IZ+1))*R1)*H

   99 calcConfigEnergy=H
      RETURN
      END
*/