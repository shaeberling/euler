package p19

import (
	c "common"
	"fmt"
)

// --- Day 19: Go With The Flow ---
// http://adventofcode.com/2018/day/19
func Solve(input string) (string, string) {
	lines := c.SplitByNewline(input)
	return c.ToString(solveA(parse(lines))), c.ToString(optimizedB())
}

type operation struct {
	name   string
	params []int
}

func (op operation) String() string {
	return fmt.Sprintf("%s %v", op.name, op.params)
}

func parse(lines []string) ([]operation, int) {
	fcReg := c.ToIntOrPanic(c.SplitByWhitespaceTrim(lines[0])[1])
	instrs := make([]operation, len(lines)-1)
	for i := 1; i < len(lines); i++ {
		split := c.SplitByWhitespaceTrim(lines[i])
		instrs[i-1] = operation{
			name:   split[0],
			params: []int{c.ToIntOrPanic(split[1]), c.ToIntOrPanic(split[2]), c.ToIntOrPanic(split[3])},
		}
	}

	return instrs, fcReg
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

func solveA(ops []operation, fcReg int) int {
	instrs := make(map[string]instruction, 0)
	instrs["addr"] = iAddr
	instrs["addi"] = iAddi
	instrs["mulr"] = iMulr
	instrs["muli"] = iMuli
	instrs["banr"] = iBanr
	instrs["bani"] = iBani
	instrs["borr"] = iBorr
	instrs["bori"] = iBori
	instrs["setr"] = iSetr
	instrs["seti"] = iSeti
	instrs["gtir"] = iGtir
	instrs["gtri"] = iGtri
	instrs["gtrr"] = iGtrr
	instrs["eqir"] = iEqir
	instrs["eqri"] = iEqri
	instrs["eqrr"] = iEqrr

	reg := make([]int, 6)
	for reg[fcReg] < len(ops) {
		op := ops[reg[fcReg]]
		instrs[op.name](op.params, reg)
		reg[fcReg]++
	}
	return reg[0]
}

// Interface for all instructions (Copied fro Day 16)
type instruction func(op []int, reg []int)

func iAddr(op []int, reg []int) { reg[op[2]] = reg[op[0]] + reg[op[1]] }
func iAddi(op []int, reg []int) { reg[op[2]] = reg[op[0]] + op[1] }
func iMulr(op []int, reg []int) { reg[op[2]] = reg[op[0]] * reg[op[1]] }
func iMuli(op []int, reg []int) { reg[op[2]] = reg[op[0]] * op[1] }
func iBanr(op []int, reg []int) { reg[op[2]] = reg[op[0]] & reg[op[1]] }
func iBani(op []int, reg []int) { reg[op[2]] = reg[op[0]] & op[1] }
func iBorr(op []int, reg []int) { reg[op[2]] = reg[op[0]] | reg[op[1]] }
func iBori(op []int, reg []int) { reg[op[2]] = reg[op[0]] | op[1] }
func iSetr(op []int, reg []int) { reg[op[2]] = reg[op[0]] }
func iSeti(op []int, reg []int) { reg[op[2]] = op[0] }
func iGtir(op []int, reg []int) { reg[op[2]] = bin(op[0] > reg[op[1]]) }
func iGtri(op []int, reg []int) { reg[op[2]] = bin(reg[op[0]] > op[1]) }
func iGtrr(op []int, reg []int) { reg[op[2]] = bin(reg[op[0]] > reg[op[1]]) }
func iEqir(op []int, reg []int) { reg[op[2]] = bin(op[0] == reg[op[1]]) }
func iEqri(op []int, reg []int) { reg[op[2]] = bin(reg[op[0]] == op[1]) }
func iEqrr(op []int, reg []int) { reg[op[2]] = bin(reg[op[0]] == reg[op[1]]) }

func bin(a bool) int {
	if a {
		return 1
	} else {
		return 0
	}
}
