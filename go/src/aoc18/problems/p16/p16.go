package p16

import (
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
	instrs := make(map[string]instruction, 0)
	instrs["iAddr"] = iAddr
	instrs["iAddi"] = iAddi
	instrs["iMulr"] = iMulr
	instrs["iMuli"] = iMuli
	instrs["iBanr"] = iBanr
	instrs["iBani"] = iBani
	instrs["iBorr"] = iBorr
	instrs["iBori"] = iBori
	instrs["iSetr"] = iSetr
	instrs["iSeti"] = iSeti
	instrs["iGtir"] = iGtir
	instrs["iGtri"] = iGtri
	instrs["iGtrr"] = iGtrr
	instrs["iEqir"] = iEqir
	instrs["iEqri"] = iEqri
	instrs["iEqrr"] = iEqrr
	return c.ToString(solveA(samples, instrs)), c.ToString(solveB(samples, instrs, testProgram))
}

func solveA(samples []sample, instrs map[string]instruction) int {
	count := 0
	for _, s := range samples {
		numMatched := 0
		for _, inst := range instrs {
			before := s.cpBefore()
			inst(s.instr, before)
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

func solveB(samples []sample, instrs map[string]instruction, program [][]int) int {
	identified := make(map[int]instruction)
	for len(instrs) > 0 {
		for _, s := range samples {
			numMatched := 0
			lastMatchedInstr := ""
			for iName, inst := range instrs {
				before := s.cpBefore()
				inst(s.instr, before)
				if eq(before, s.after) {
					lastMatchedInstr = iName
					numMatched++
				}
			}
			// If we matched only then it's clear we have a match!
			if numMatched == 1 {
				opcode := s.instr[0]
				identified[opcode] = instrs[lastMatchedInstr]
				delete(instrs, lastMatchedInstr)
			}
		}
	}

	// Once all instructions are identified, run the test program!
	reg := make([]int, 4)
	for _, p := range program {
		instr := identified[p[0]]
		instr(p, reg)
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

// Interface for all instructions.
type instruction func(op []int, reg []int)

func iAddr(op []int, reg []int) { reg[op[3]] = reg[op[1]] + reg[op[2]] }
func iAddi(op []int, reg []int) { reg[op[3]] = reg[op[1]] + op[2] }
func iMulr(op []int, reg []int) { reg[op[3]] = reg[op[1]] * reg[op[2]] }
func iMuli(op []int, reg []int) { reg[op[3]] = reg[op[1]] * op[2] }
func iBanr(op []int, reg []int) { reg[op[3]] = reg[op[1]] & reg[op[2]] }
func iBani(op []int, reg []int) { reg[op[3]] = reg[op[1]] & op[2] }
func iBorr(op []int, reg []int) { reg[op[3]] = reg[op[1]] | reg[op[2]] }
func iBori(op []int, reg []int) { reg[op[3]] = reg[op[1]] | op[2] }
func iSetr(op []int, reg []int) { reg[op[3]] = reg[op[1]] }
func iSeti(op []int, reg []int) { reg[op[3]] = op[1] }
func iGtir(op []int, reg []int) { reg[op[3]] = bin(op[1] > reg[op[2]]) }
func iGtri(op []int, reg []int) { reg[op[3]] = bin(reg[op[1]] > op[2]) }
func iGtrr(op []int, reg []int) { reg[op[3]] = bin(reg[op[1]] > reg[op[2]]) }
func iEqir(op []int, reg []int) { reg[op[3]] = bin(op[1] == reg[op[2]]) }
func iEqri(op []int, reg []int) { reg[op[3]] = bin(reg[op[1]] == op[2]) }
func iEqrr(op []int, reg []int) { reg[op[3]] = bin(reg[op[1]] == reg[op[2]]) }

func bin(a bool) int {
	if a {
		return 1
	} else {
		return 0
	}
}
