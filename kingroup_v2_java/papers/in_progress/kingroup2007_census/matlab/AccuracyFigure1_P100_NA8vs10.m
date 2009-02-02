tmp = load('misprint0/distance_P100_50x1_NA10.txt')
x = tmp(:, 1)
f50 = 100 - tmp(:, 2)
tmp = load('misprint0/distance_P100_50x1_NA4.txt')
xb = tmp(:, 1)
f50b = 100 - tmp(:, 2)

tmp = load('misprint0/distance_P100_5x10S_NA10.txt')
f5 = 100 - tmp(:, 2)
tmp = load('misprint0/distance_P100_5x10S_NA4.txt')
f5b = 100 - tmp(:, 2)
% f5_err = tmp(:, 4)
 
tmp = load('misprint0/distance_P100_25S_NA10.txt')
f25 = 100 - tmp(:, 2)
tmp = load('misprint0/distance_P100_25S_NA4.txt')
f25b = 100 - tmp(:, 2)
 
tmp = load('misprint0/distance_P100_46S_NA10.txt')
f46 = 100 - tmp(:, 2)
tmp = load('misprint0/distance_P100_46S_NA4.txt')
f46b = 100 - tmp(:, 2)

CensusFigure_NAx