FS = load('likelihoods/fullsibs.txt')
UN = load('likelihoods/unrelated.txt')
N = length(FS(:,1))
norm = 100 / N

x = -100:0

hold on

subplot(2,2,1)
f = hist(FS(:, 2), x) * norm
f2 = hist(FS(:, 3), x) * norm
plot(x, f, 'r-', x, f2, 'k:')
set(gca, 'XLim', [-70, -40])
set(gca, 'YLim',  [-1, 30])
title('A. Full siblings, resolution = 1')
xlabel('Log likelihood')
ylabel('Frequency (%)')
% legend('\itFull sibs (R_m=R_p=0.5)', 'Unrelated (R_m=R_p=0)', 'Parent-Offspring (R_m=1, R_p=0)', 0)
% legend('\itFull sibs (R_m=R_p=0.5)', 'Unrelated (R_m=R_p=0)', 0)
 legend('\itCensus', 'Unrelated', 1)
% grid on
%
subplot(2,2,3)
f = hist(UN(:, 2), x)* norm
f2 = hist(UN(:, 3), x)* norm
plot(x, f, 'r-', x, f2, 'k:')
set(gca, 'XLim', [-70, -40])
set(gca, 'YLim',  [-1, 30])
title('C. Unrelated, resolution = 1')
xlabel('Log likelihood')
ylabel('Frequency (%)')
%

x = -100:0.1:0

subplot(2,2,2)
f = hist(FS(:, 2), x)* norm
f2 = hist(FS(:, 3), x)* norm
plot(x, f, 'r-', x, f2, 'k:')
set(gca, 'XLim', [-70, -40])
set(gca, 'YLim', [-1, 30])
title('B. Full siblings, resolution = 0.1')
xlabel('Log likelihood')
ylabel('Frequency (%)')
% legend('\itFull sibs (R_m=R_p=0.5)', 'Unrelated (R_m=R_p=0)', 'Parent-Offspring (R_m=1, R_p=0)', 0)
% grid on
%
subplot(2,2,4)
f = hist(UN(:, 2), x)* norm
f2 = hist(UN(:, 3), x)* norm
plot(x, f, 'r-', x, f2, 'k:')
set(gca, 'XLim', [-70, -40])
set(gca, 'YLim', [-1, 30])
title('D. Unrelated, resolution = 0.1')
xlabel('Log likelihood')
ylabel('Frequency (%)')
% grid on
%
hold off