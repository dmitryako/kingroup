lowF = 1
lowL = size(af)
highF = lowL + 1
highL = size(jni)

hold on
grid on
plot(af(lowF:lowL, 1), af(lowF:lowL, 2), 'r^')
plot(lit2(lowF:lowL, 1), lit2(lowF:lowL, 2), 'ko')
plot(jni(lowF:highL, 1), jni(lowF:highL, 2), 'bx')
legend('AF', 'java', 'C++')

errorbar(af(lowF:lowL, 1), af(lowF:lowL, 2), af(lowF:lowL, 4), 'r^')
errorbar(lit2(lowF:lowL, 1), lit2(lowF:lowL, 2), lit2(lowF:lowL, 4), 'ko')
errorbar(jni(lowF:highL, 1), jni(lowF:highL, 2), jni(lowF:highL, 4), 'bx')
errorbar(lit(highF:highL, 1), lit(highF:highL, 2), lit(highF:highL, 4), 'ko')
ylabel('\itlog(time)')
xlabel('effective size \itn')
set(gca, 'XLim', [0, 60])
set(gca,'Layer','top')
hold off