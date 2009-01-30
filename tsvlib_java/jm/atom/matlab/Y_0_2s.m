f = load('wf/Y_0_2s_test.csv')
x = f(:, 2)
t = f(:, 3)

f = load('wf/Y_0_2s.csv')
z = f(:, 3)

f = load('wf/logCR.csv')
r = f(:, 3)
% t = t - z
plot(x, t, '-r')
% hold
% plot(x, r, '-r')
