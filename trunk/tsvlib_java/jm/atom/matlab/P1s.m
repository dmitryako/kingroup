f = load('wf/P1s_test.csv')
t = f(:, 3)

f = load('wf/P1s_sqrtCR.csv')
z = f(:, 3)

f = load('wf/logCR.csv')
x = f(:, 2)
r = f(:, 3)
% t = t - z
plot(x, t, '-r')
hold
plot(x, z, ':r')
% set(gca, 'XLim', [0, 6])
% set(gca, 'YLim',  [-1, 30])
% title('A. Full siblings, resolution = 1')
% xlabel('Log likelihood')
% ylabel('Frequency (%)')
% legend('\itFull sibs (R_m=R_p=0.5)', 'Unrelated (R_m=R_p=0)', 0)
% grid on
%
%
hold off