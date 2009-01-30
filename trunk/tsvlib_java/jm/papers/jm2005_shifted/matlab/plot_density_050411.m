w = load('He_zeta_density.csv')
x = w(:, 2)
D = w(:, 3)
w = load('He_ground_density_4.csv')
x_2 = w(:, 2)
D_2 = w(:, 3)
w = load('He_ground_density_10.csv')
x_3 = w(:, 2)
D_3 = w(:, 3)
%
%
% subplot(2,2,1)
plot (x, D, 'k-')
hold
plot (x_2, D_2, 'k--')
plot (x_3, D_3, 'k:')
set(gca, 'XLim', [-4, 4])
% set(gca, 'YLim', [-5, 10])
% ylabel('Laguerre basis')
% xlabel('\it\rho')
% legend('\itP_0(\rho)', '\itP_1_9(\rho)')
% title('A.')
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
