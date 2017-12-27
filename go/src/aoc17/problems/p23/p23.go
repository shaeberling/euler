package p23

import (
	c "common"
	"strconv"
)

// --- Day 23: Coprocessor Conflagration ---
// http://adventofcode.com/2017/day/23
func Solve(input string) (string, string) {
	return c.ToString(solveA(c.SplitByNewline(input))), c.ToString(solveB())
}

func solveA(input []string) int {
	numMul := 0
	regs := make(map[string]int)
	val := func(x string) int {
		n, err := strconv.ParseInt(x, 10, 64)
		if err != nil {
			return regs[x]
		}
		return int(n)
	}

	for pc := 0; pc < len(input); pc++ {
		instr := c.SplitByWhitespaceTrim(input[pc])
		switch instr[0] {
		case "set":
			regs[instr[1]] = val(instr[2])
		case "sub":
			regs[instr[1]] -= val(instr[2])
		case "mul":
			regs[instr[1]] *= val(instr[2])
			numMul++
		case "jnz":
			if val(instr[1]) != 0 {
				pc += val(instr[2]) - 1
			}
		}
	}
	return numMul
}

func solveB() int {
	// I worked out why this works here: https://goo.gl/cft9cb
	regC := 123500
	regH := 0
	for regB := 106500; regB < regC+1; regB += 17 {
		if !c.IsPrime(regB) {
			regH++
		}
	}
	return regH
}

