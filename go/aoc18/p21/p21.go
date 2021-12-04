package p21

import (
	"s13g.com/euler/aoc18/aocvm"
	c "s13g.com/euler/common"
)

// --- Day 21: Chronal Conversion ---
// http://adventofcode.com/2018/day/21
func Solve(input string) (string, string) {
	lines := c.SplitByNewline(input)
	return c.ToString(solveA(aocvm.ParseProgramWithIP(lines))), ""
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
