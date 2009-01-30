package jm.atom;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 10, 2004, Time: 11:48:14 AM
 */
/* y=1-exp(-r);
*  r=-ln(1-y);
*  1/r = -1/ln(1-y)
* dr = dy/(1-y);
* dy/dr=exp(-r)=(1-y);
* d^2y/dr^2 = -exp(-r) = -(1-y);
* dU(r)/dr = dU(y)/dy  * dy/dr = (1-y) dU/dy;
* d^2U(r)/dr^2=d^2U(y)/dy^2 * (dy/dr)^2 + dU/dy * d^2y/dr^2=
*  = d^2U(y)/dy^2 * (1-y)^2 - dU/dy * (1-y);

Version I
r2 H R(r) = r^2[ (-1/2)(d^2R/dr^2 + 2/r dR/dr -L(L+1)/r^2 R) + Z/r R] =
= (-1/2)(r^2 d^2R/dr^2 + 2r dR/dr -L(L+1) R) +Z*r R
INTEGRAL(0,oo)  r^2 dr Rn'(r) H Rn(r) = delta_n'n

R(r) = SUM_n C_n Q_n(y)
INTEGRAL(0, 1) dy Q_n'(y) H Q_n(y) =

Version II
// After r*Rn(r) = Un(r)
//                 1 d^2    L(L+1)   Z
// Hamiltonian = - - --   + ------ + - = H(U)
//                 2 dr^2    2r^2    r
// oo
// |  dr Un'(r) H Un(r) = delta_n'n
// o
U(r) = SUM_n C_n Q_n(y)
INTEGRAL(0, 1) dy Q_n'(y) H Q_n(y) =
= 1/2 * INTEGRAL(0, 1) dy Q_n'(y) [-d^2/dr^2 + L(L+1)/r^2 ] Q_n(y) =
= 1/2 * INTEGRAL(0, 1) dy Q_n'(y) [-d^2Q(y)/dy^2 * (1-y)^2 + dQ/dy * (1-y) + L(L+1)/r^2 Q(y)] =

SUM dy Q (1-y)^2 d^2Q/dy^2 + SUM dy d(Q(1-y)^2)/dy * dQ/dy = Q(1-y)^2 * dQ/dy @(1)-(0)
*/
public class SlaterIntegralsP {
  // P - for Legendra
}
