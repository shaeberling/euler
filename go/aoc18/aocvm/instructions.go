package aocvm

import (
	c "common"
	"fmt"
)

// Instruction is the common interface for all instructions.
type Instruction struct {
	Name   string
	Params []int
}

func (instr Instruction) String() string {
	return fmt.Sprintf("%s %v", instr.Name, instr.Params)
}

// ParseProgramWithIP parses a program that has an '#ip' definition on the first line.
func ParseProgramWithIP(lines []string) ([]Instruction, int) {
	fcReg := c.ToIntOrPanic(c.SplitByWhitespaceTrim(lines[0])[1])
	instrs := make([]Instruction, len(lines)-1)
	for i := 1; i < len(lines); i++ {
		split := c.SplitByWhitespaceTrim(lines[i])
		instrs[i-1] = Instruction{
			Name:   split[0],
			Params: []int{c.ToIntOrPanic(split[1]), c.ToIntOrPanic(split[2]), c.ToIntOrPanic(split[3])},
		}
	}
	return instrs, fcReg
}
