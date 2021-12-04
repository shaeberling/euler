package p19

import (
	"fmt"

	"s13g.com/euler/aoc18/aocvm"
	c "s13g.com/euler/common"
)

// --- Day 19: Go With The Flow ---
// http://adventofcode.com/2018/day/19
func Solve(input string) (string, string) {
	lines := c.SplitByNewline(input)
	return c.ToString(solveA(aocvm.ParseProgramWithIP(lines))), c.ToString(optimizedB())
}

func optimizedB() int {
	const E = 10551293
	result := 0
	for i := 1; i <= E; i++ {
		if E%i == 0 {
			result += i
		}
	}
	return result
}

// This is for documentation only. After manually decompiling
// the code, this is what it's actually executing. As you can see
// It adds all the factors of 10551293 (which is assembled in the
// second half of the code at the beginning). It would run for
// 10551293^2 iterations, which of course would take forever.
// See optimizedB() above for how to do the same thing a lot more
// efficiently.
func decompiledB() int {
	r := []int{0, 1, 1, 1, 10551293}
	for r[2] = 1; r[2] <= r[4]; r[2]++ {
		for r[1] = 1; r[1] <= r[4]; r[1]++ {
			if r[2]*r[1] == r[4] {
				fmt.Printf("(%d * %d) ", r[2], r[1])
				r[0] = r[0] + r[2]
			}
		}
	}
	fmt.Print("\n\n")
	return r[0]
}

func solveA(instrs []aocvm.Instruction, fcReg int) int {
	ops := aocvm.GetOperations()
	reg := make([]int, 6)
	for reg[fcReg] < len(instrs) {
		instr := instrs[reg[fcReg]]
		ops[instr.Name](instr.Params, reg)
		reg[fcReg]++
	}
	return reg[0]
}
