tmp = load('figure2_5_10_AF.txt')
AF5_10 = tmp(:, 2)
AF5_10e = tmp(:, 4)
tmp = load('figure2_5_10_java.txt')
J5_10 = tmp(:, 2)
J5_10e = tmp(:, 4)

tmp = load('figure2_5_40_AF.txt')
AF5_40 = tmp(:, 2)
AF5_40e = tmp(:, 4)
tmp = load('figure2_5_40_java.txt')
J5_40 = tmp(:, 2)
J5_40e = tmp(:, 4)

tmp = load('figure2_10_10_AF.txt')
x = tmp(:, 1)
AF10_10 = tmp(:, 2)
AF10_10e = tmp(:, 4)
tmp = load('figure2_10_10_java.txt')
J10_10 = tmp(:, 2)
J10_10e = tmp(:, 4)

tmp = load('figure2_20_10_AF.txt')
AF20_10 = tmp(:, 2)
AF20_10e = tmp(:, 4)
tmp = load('figure2_20_10_java.txt')
J20_10 = tmp(:, 2)
J20_10e = tmp(:, 4)

hold on

subplot(2,2,1)
f = AF5_10
fe = AF5_10e
f2 = J5_10
f2e = J5_10e
plot(x, f, 'r^', x, f2, 'ko')
hold on
errorbar(x, f, fe, 'r^')
hold on
errorbar(x, f2, f2e, 'ko')
set(gca, 'XLim', [4, 16])
set(gca, 'YLim', [-2, 14])
title('\itA(5,10)')
xlabel('\itx')
ylabel('\itlog(time)')
% grid on
%
subplot(2,2,2)
f = AF5_40
f2 = J5_40
fe = AF5_40e
f2e = J5_40e
plot(x, f, 'r^', x, f2, 'ko')
set(gca, 'XLim', [4, 16])
set(gca, 'YLim', [-2, 14])
title('\itA(5,40)')
xlabel('\itx')
ylabel('\itlog(time)')
legend('AF', 'java', 1)
hold on
errorbar(x, f, fe, 'r^')
hold on
errorbar(x, f2, f2e, 'ko')
% grid on
%
subplot(2,2,3)
f = AF10_10
f2 = J10_10
fe = AF10_10e
f2e = J10_10e
plot(x, f, 'r^', x, f2, 'ko')
set(gca, 'XLim', [4, 16])
set(gca, 'YLim', [-2, 14])
title('\itA(10,10)')
xlabel('\itx')
ylabel('\itlog(time)')
hold on
errorbar(x, f, fe, 'r^')
hold on
errorbar(x, f2, f2e, 'ko')
% grid on
%
subplot(2,2,4)
f = AF20_10
f2 = J20_10
fe = AF20_10e
f2e = J20_10e
plot(x, f, 'r^', x, f2, 'ko')
set(gca, 'XLim', [4, 16])
set(gca, 'YLim', [-2, 14])
title('\itA(20,10)')
xlabel('\itx')
ylabel('\itlog(time)')
hold on
errorbar(x, f, fe, 'r^')
hold on
errorbar(x, f2, f2e, 'ko')
% grid on
%
hold off