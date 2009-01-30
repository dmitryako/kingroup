% f = load('wf/y.csv')
% f = load('wf/coulP1s.csv')
f = load('wf/Y_0_1s_test.csv')
x = f(:, 2)
t = f(:, 3)

f = load('wf/Y_0_1s.csv')
z = f(:, 3)

f = load('wf/logCR.csv')
r = f(:, 3)
% hold on
% plot(x, r, '-r')
% t = t - z
plot(x, t, '-r')
hold
% plot(x, z, ':r')
% plot(x, r, '--r')
% set(gca, 'YLim', [0, 1])
% set(gca, 'YLim',  [-1, 30])
% title('A. Full siblings, resolution = 1')
% xlabel('Log likelihood')
% ylabel('Frequency (%)')
% legend('\itFull sibs (R_m=R_p=0.5)', 'Unrelated (R_m=R_p=0)', 0)
% grid on
%
%
