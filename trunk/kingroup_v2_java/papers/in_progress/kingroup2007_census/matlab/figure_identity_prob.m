fs = [0.5 0.5];
u = [0.0 0.0];
po = [1.0 0.0];
c = [1.0 1.0];
t = [0.0 0.5
    0.5, 0.0 
    1.0, 0.5];
hold on

f = u
plot(f(:, 1), f(:, 2), 'r*')
% hold
f = fs
plot(f(:, 1), f(:, 2), 'r+')
f = po
plot(f(:, 1), f(:, 2), 'rv')
f = c
plot(f(:, 1), f(:, 2), 'bx')
f = t
plot(f(:, 1), f(:, 2), 'ro')
set(gca, 'XLim', [0, 1])
set(gca, 'YLim', [0, 1])
% title('5x10S')
ylabel('\itR_p')
xlabel('\itR_m')
legend('unrelated', 'full-siblings', 'parent-offspring', 'census', 2)
% grid on
%
hold off