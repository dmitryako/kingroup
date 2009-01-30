f = load('wf/Z_1_1s_test.csv')
t = f(:, 3)

f = load('wf/Z_1_1s.csv')
z = f(:, 3)
x = f(:, 2)

t = t - z
plot(x, t, '-r')
hold
% plot(x, z, ':r')
