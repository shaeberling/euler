package p16

import (
	"aoc18/problems/aocvm"
	c "common"
)

// --- Day 16: Chronal Classification ---
// http://adventofcode.com/2018/day/16
func Solve(input string) (string, string) {
	lines := c.SplitByNewline(input)
	samples := make([]sample, 0)
	var testProgram [][]int
	for i := range lines {
		if lines[i] == "" && lines[i+1] == "" && lines[i+2] == "" {
			testProgram = parseTestProgram(lines[i+3:])
			break
		}
		if lines[i] == "" {
			continue
		}
		if lines[i][0:6] == "Before" {
			be := c.MapStrI(c.SplitByCommaTrim(lines[i][9:19]), c.ToIntOrPanic)
			in := c.MapStrI(c.SplitByWhitespaceTrim(lines[i+1]), c.ToIntOrPanic)
			af := c.MapStrI(c.SplitByCommaTrim(lines[i+2][9:19]), c.ToIntOrPanic)
			samples = append(samples, sample{
				before: be,
				instr:  in,
				after:  af,
			})
		}
	}
	ops := aocvm.GetOperations()
	return c.ToString(solveA(samples, ops)), c.ToString(solveB(samples, ops, testProgram))
}

func solveA(samples []sample, ops map[string]aocvm.Operation) int {
	count := 0
	for _, s := range samples {
		numMatched := 0
		for _, op := range ops {
			before := s.cpBefore()
			op(s.instr[1:], before)
			if eq(before, s.after) {
				numMatched++
			}
		}
		if numMatched >= 3 {
			count++
		}
	}
	return count
}

func solveB(samples []sample, ops map[string]aocvm.Operation, program [][]int) int {
	identified := make(map[int]aocvm.Operation)
	for len(ops) > 0 {
		for _, s := range samples {
			numMatched := 0
			lastMatchedInstr := ""
			for iName, op := range ops {
				before := s.cpBefore()
				op(s.instr[1:], before)
				if eq(before, s.after) {
					lastMatchedInstr = iName
					numMatched++
				}
			}
			// If we matched only then it's clear we have a match!
			if numMatched == 1 {
				opcode := s.instr[0]
				identified[opcode] = ops[lastMatchedInstr]
				delete(ops, lastMatchedInstr)
			}
		}
	}

	// Once all instructions are identified, run the test program!
	reg := make([]int, 4)
	for _, p := range program {
		identified[p[0]](p[1:], reg)
	}
	return reg[0]
}

func parseTestProgram(lines []string) [][]int {
	result := make([][]int, len(lines))
	for i, l := range lines {
		result[i] = c.MapStrI(c.SplitByWhitespaceTrim(l), c.ToIntOrPanic)
	}
	return result
}

func eq(a []int, b []int) bool {
	for i := range a {
		if a[i] != b[i] {
			return false
		}
	}
	return true
}

// A sample parsed from the input.
type sample struct {
	before []int
	instr  []int
	after  []int
}

// Copies the 'before' state so it can be modified.
func (s *sample) cpBefore() []int {
	result := make([]int, len(s.before))
	for i, b := range s.before {
		result[i] = b
	}
	return result
}
