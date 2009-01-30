f = load('wf/Y_0_2p_test.csv')
x = f(:, 2)
t = f(:, 3)

f = load('wf/Y_0_2p.csv')
z = f(:, 3)

% t = t - z
plot(x, t, '-r')
% hold
% plot(x, r, '-r')
