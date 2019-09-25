import math
# f(x) = (x**2 + x**0.5)/(e**x + x)

n = int(raw_input())

x = 0.0
hasil = 0.0
while(x<=2.01):
	hasil = (x**2 + math.sqrt(x))/(math.exp(x) + x)
	print x, round(hasil, 6)
	x += 2.0/n
