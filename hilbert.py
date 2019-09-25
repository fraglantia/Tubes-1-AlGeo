from gmpy2 import *

get_context().precision=1000

n = int(input())
x = []

for i in range(1,n+1):
	y = []
	for j in range(0,n):
		y.append(mpfr(1)/mpfr(i+j))
	if (i == 1):
		y.append(1)
	else:
		y.append(0)
	x.append(y)

for i in range(n):
	for j in range(n):
		print("{:.30f}".format(x[i][j]), end=" ")
	print(str(x[i][n]))