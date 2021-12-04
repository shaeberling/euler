package p15

import (
	c "s13g.com/euler/common"
)

// ---  Dueling Generators ---
// http://adventofcode.com/2017/day/15
func Solve(_ string) (string, string) {
	return c.ToString(solve(40000000, false)), c.ToString(solve(5000000, true))
}

func solve(loops int, partB bool) (count int) {
	const factorA, factorB, div = 16807, 48271, 2147483647
	a, b := 699, 124
	for i := 0; i < loops; i++ {
		for ok := true; ok; ok = a%4 != 0 && partB {
			a = (a * factorA) % div
		}
		for ok := true; ok; ok = b%8 != 0 && partB {
			b = (b * factorB) % div
		}
		const mask = 1<<16 - 1
		if a&mask == b&mask {
			count++
		}
	}
	return
}
