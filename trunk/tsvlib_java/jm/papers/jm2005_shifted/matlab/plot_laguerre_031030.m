n = 120
w = load('LaguerreLogR_n0.csv')
xR = w(1:n, 2)
R0 = w(1:n, 3)
w = load('LaguerreLogR_n4.csv')
Rn = w(1:n, 3)
w = load('LaguerreLogR_n0_deriv.csv')
xDR = w(1:n, 2)
DR0 = w(1:n, 3)
w = load('LaguerreLogR_n4_deriv.csv')
DRn = w(1:n, 3)

w = load('LaguerreLogCR_n0.csv')
xC = w(1:n, 2)
C0 = w(1:n, 3)
w = load('LaguerreLogCR_n4.csv')
Cn = w(1:n, 3)
w = load('LaguerreLogCR_n0_deriv.csv')
xDC = w(1:n, 2)
DC0 = w(1:n, 3)
w = load('LaguerreLogCR_n4_deriv.csv')
DCn = w(1:n, 3)
%
%SUBPLOT('position',[left bottom width height]) creates an
%    axis at the specified position in normalized coordinates (in 
%    in the range from 0.0 to 1.0).
%gap = 0.1;
%left = gap;
%left2 = 0.5 + gap;
%bottom = gap;
%width = 0.5 - gap;
%height = 1 - 2 * gap;
%SUBPLOT('position', [left bottom width height])
%SUBPLOT('position', [0.05 0.05 0.5 0.5])
%
subplot(2,2,1)
plot (xR, R0, 'k-', xR, Rn, 'k--')
ylabel('Radial basis functions')
legend('\itP_0(\rho)', '\itP_4(\rho)')
%
subplot(2,2,3)
plot (xDR, DR0, 'k-', xDR, DRn, 'k--')
% plot (w(1:n, 2), w(1:n, 6),  'k-', w(1:n, 2), w(1:n, 13), 'k--')
% legend('\itF^B_2(\rmln\it(r))', '\itF^B_9(\rmln\it(r))')%
% ylabel('Radial basis functions')
% xlabel('Radial distance, \itr\rm (a.u.)')
%
subplot(2,2,2)
plot (xC, C0, 'k-', xC, Cn, 'k--')
% plot (w(1:n, 3), w(1:n, 4), 'k-', w(1:n, 3), w(1:n, 5), 'k--')
% legend('\itF^B_0(\rho)', '\itF^B_1(\rho)')
%
subplot(2,2,4)
plot (xDC, DC0, 'k-', xDC, DCn, 'k--')
% plot (w(1:n, 3), w(1:n, 6),  'k-', w(1:n, 3), w(1:n, 13), 'k--')
% legend('\itF^B_2(\rho)', '\itF^B_9(\rho)')%
% xlabel('\it\rho = \rmln\it(r)')

