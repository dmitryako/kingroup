w = load('bspline_k0_i0.csv')
xA_1 = w(:, 2)
A_1 = w(:, 3)
w = load('bspline_k0_i1.csv')
xA_2 = w(:, 2)
A_2 = w(:, 3)
w = load('bspline_k0_i2.csv')
xA_3 = w(:, 2)
A_3 = w(:, 3)
w = load('bspline_k0_i3.csv')
xA_4 = w(:, 2)
A_4 = w(:, 3)
w = load('bspline_k0_i4.csv')
xA_5 = w(:, 2)
A_5 = w(:, 3)

w = load('bspline_k1_i0.csv')
xB_1 = w(:, 2)
B_1 = w(:, 3)
w = load('bspline_k1_i1.csv')
xB_2 = w(:, 2)
B_2 = w(:, 3)
w = load('bspline_k1_i2.csv')
xB_3 = w(:, 2)
B_3 = w(:, 3)
w = load('bspline_k1_i3.csv')
xB_4 = w(:, 2)
B_4 = w(:, 3)
w = load('bspline_k1_i4.csv')
xB_5 = w(:, 2)
B_5 = w(:, 3)

w = load('bspline_k2_i0.csv')
xC_1 = w(:, 2)
C_1 = w(:, 3)
w = load('bspline_k2_i1.csv')
xC_2 = w(:, 2)
C_2 = w(:, 3)
w = load('bspline_k2_i2.csv')
xC_3 = w(:, 2)
C_3 = w(:, 3)
w = load('bspline_k2_i3.csv')
xC_4 = w(:, 2)
C_4 = w(:, 3)
w = load('bspline_k2_i4.csv')
xC_5 = w(:, 2)
C_5 = w(:, 3)
w = load('bspline_k2_i5.csv')
xC_6 = w(:, 2)
C_6 = w(:, 3)

w = load('bspline_k3_i0.csv')
xD_1 = w(:, 2)
D_1 = w(:, 3)
w = load('bspline_k3_i1.csv')
xD_2 = w(:, 2)
D_2 = w(:, 3)
w = load('bspline_k3_i2.csv')
xD_3 = w(:, 2)
D_3 = w(:, 3)
w = load('bspline_k3_i3.csv')
xD_4 = w(:, 2)
D_4 = w(:, 3)
w = load('bspline_k3_i4.csv')
xD_5 = w(:, 2)
D_5 = w(:, 3)
w = load('bspline_k3_i5.csv')
xD_5 = w(:, 2)
D_5 = w(:, 3)

%
%
subplot(2,2,1)
plot (xA_1, A_1, 'k-')
hold
plot (xA_2, A_2, 'k--')
plot (xA_3, A_3, 'k:')
plot (xA_4, A_4, 'k-.')
plot (xA_5, A_5, 'k-')
set(gca, 'YLim', [0, 1.2])
title('A.')

subplot(2,2,2)
plot (xB_1, B_1, 'k-')
hold
plot (xB_2, B_2, 'k--')
plot (xB_3, B_3, 'k:')
plot (xB_4, B_4, 'k-.')
plot (xB_5, B_5, 'k-')
set(gca, 'YLim', [0, 1.2])
title('B.')
%
subplot(2,2,3)
plot (xC_1, C_1, 'k-')
hold
plot (xC_2, C_2, 'k--')
plot (xC_3, C_3, 'k:')
plot (xC_4, C_4, 'k-.')
plot (xC_5, C_5, 'k-')
plot (xC_6, C_6, 'k--')
set(gca, 'YLim', [0, 1.2])
title('C.')
%
% subplot(2,2,4)
% plot (xD_1, D_1, 'k-')
% hold
% plot (xD_2, D_2, 'k--')
% plot (xD_3, D_3, 'k:')
% plot (xD_4, D_4, 'k-.')
% plot (xD_5, D_5, 'k-.')
% plot (xD_6, D_6, 'k-.')
% set(gca, 'YLim', [0, 1.2])
% title('D.')
%
% subplot(2,2,2)
% plot (xC, C0, 'k-')
% hold
% plot (xCn, Cn, 'k--')
% set(gca, 'XLim', [-4, 4])
% set(gca, 'YLim', [-5, 10])
% xlabel('\itx')
% legend('\itF_0(x)', '\itF_1_9(x)')
% title('B.')
% %
% subplot(2,2,3)
% plot (xDR, DR0, 'k-')
% hold
% plot (xDRn, DRn, 'k--')
% set(gca, 'XLim', [-4, 4])
% set(gca, 'YLim', [-20, 40])
% ylabel('First derivative')
% xlabel('\it\rho')
% legend('\itP''_0(\rho)', '\itP''_1_9(\rho)')
% title('C.')
% %
% subplot(2,2,4)
% plot (xDC, DC0, 'k-')
% hold
% plot (xDCn, DCn, 'k--')
% set(gca, 'XLim', [-4, 4])
% set(gca, 'YLim', [-20, 40])
% xlabel('\itx')
% legend('\itF''_0(x)', '\itF''_1_9(x)')
% title('D.')
% 
