n = int(input())
x = []

for i in range(1,n+1):
	y = []
	for j in range(0,n):
		y.append(1/(i+j))
	if (i == 1):
		y.append(1)
	else:
		y.append(0)
	x.append(y)

for i in range(n):
	for j in range(n):
		print("{:.10f}".format(x[i][j]), end=" ")
	print(str(x[i][n]))