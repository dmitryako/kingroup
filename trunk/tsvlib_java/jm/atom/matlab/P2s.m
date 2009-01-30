f = load('wf/P2s_test.csv')
t = f(:, 3)

f = load('wf/P2s_sqrtCR.csv')
z = f(:, 3)

f = load('wf/logCR.csv')
x = f(:, 2)
r = f(:, 3)
% t = t - z
plot(x, t, '-r')
hold
plot(x, z, ':r')
% hold off