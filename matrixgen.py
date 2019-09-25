import random

# RANDOM MATRIX NxN
# -100 - 100

n = int(raw_input())

m = ''

for i in range(n):
	for j in range(n):
		m += str(random.randint(-100, 100)) + " "

	m += '\n'

print m