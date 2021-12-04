package p25

import (
	"fmt"
	"strings"

	c "s13g.com/euler/common"
)

type turing struct {
	state string
	pos   int
	tape  map[string]bool
}

func newMachine(state string) *turing {
	result := new(turing)
	result.state = state
	result.tape = make(map[string]bool)
	return result
}
func (machine *turing) get() int {
	v := machine.tape[fmt.Sprintf("%d", machine.pos)]
	if v {
		return 1
	} else {
		return 0
	}
}
func (machine *turing) set(v bool) {
	machine.tape[fmt.Sprintf("%d", machine.pos)] = v
}
func (machine *turing) inc(v int) {
	machine.pos += v
}
func (machine *turing) numOn() int {
	sum := 0
	for _, v := range machine.tape {
		if v {
			sum++
		}
	}
	return sum
}

type rule struct {
	instr [2]instruction
}

type instruction struct {
	writeValue   bool
	moveDir      int
	continueWith string
}

func parseInput(input string) (map[string]*rule, string, int) {
	lines := c.SplitByNewline(input)
	start := strings.Split(lines[0], "Begin in state ")[1][0:1]
	steps := c.ToIntOrPanic(c.SplitByWhitespaceTrim(lines[1])[5])
	rules := make(map[string]*rule, 0)

	for i := 2; i < len(lines); i += 9 {
		state := strings.Split(lines[i], "In state ")[1][0:1]
		rule := new(rule)
		parseInstruction(&rule.instr[0], i+2, lines)
		parseInstruction(&rule.instr[1], i+6, lines)
		rules[state] = rule
	}

	return rules, start, steps
}

func parseInstruction(instr *instruction, i int, lines []string) {
	writeValue := lines[i][len(lines[i])-2:][0:1]
	instr.writeValue = writeValue == "1"
	direction := c.SplitByWhitespaceTrim(lines[i+1])[6]
	if direction == "right." {
		instr.moveDir = 1
	} else {
		instr.moveDir = -1
	}
	instr.continueWith = c.SplitByWhitespaceTrim(lines[i+2])[4][0:1]
}

// --- Day 25: The Halting Problem ---
// http://adventofcode.com/2017/day/25
func Solve(input string) (string, string) {
	return c.ToString(solveA(parseInput(input))), "Done :-)"
}

func solveA(rules map[string]*rule, start string, iteration int) int {
	machine := newMachine(start)
	machine.state = start
	for i := 0; i < iteration; i++ {
		instr := rules[machine.state].instr[machine.get()]
		machine.set(instr.writeValue)
		machine.inc(instr.moveDir)
		machine.state = instr.continueWith
	}
	return machine.numOn()
}
