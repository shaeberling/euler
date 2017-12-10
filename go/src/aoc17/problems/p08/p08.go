package p08

import (
	c "common"
	"log"
	"math"
	"strings"
)

// --- Day 8: I Heard You Like Registers ---
// http://adventofcode.com/2017/day/8
func Solve(input string) (string, string) {
	solutionA, solutionB := solve(c.SplitByNewline(input))
	return c.ToString(solutionA), c.ToString(solutionB)
}

func solve(instructions []string) (int, int) {
	// Contains the register values.
	reg := make(map[string]int)

	// Defines functions for each conditional instruction.
	funcs := make(map[string]func(string, int) bool)
	funcs["=="] = func(r string, n int) bool { return reg[r] == n }
	funcs["!="] = func(r string, n int) bool { return reg[r] != n }
	funcs[">="] = func(r string, n int) bool { return reg[r] >= n }
	funcs["<="] = func(r string, n int) bool { return reg[r] <= n }
	funcs[">"] = func(r string, n int) bool { return reg[r] > n }
	funcs["<"] = func(r string, n int) bool { return reg[r] < n }

	// Will hold solution B, which asks for the highest value ever held.
	maxEver := math.MinInt64
	// Go through each instruction, check condition and apply the increment or
	// decrement if the condition holds true.
	for _, inst := range instructions {
		split := strings.Split(inst, " if ")
		if len(split) != 2 {
			log.Fatalf("Invalid format: '%s'.", inst)
		}
		condition := c.SplitByWhitespaceTrim(split[1])
		if len(condition) != 3 {
			log.Fatalf("Invalid condition: '%s'.", split[1])
		}

		// If the condition holds, apply the inc/dec operation.
		if funcs[condition[1]](condition[0], c.ToIntOrPanic(condition[2])) {
			operation := c.SplitByWhitespaceTrim(split[0])
			if len(operation) != 3 {
				log.Fatalf("Invalid operation format: '%s'.", split[0])
			}
			register := operation[0]
			num := c.ToIntOrPanic(operation[2])
			if operation[1] == "inc" {
				reg[register] += num
			} else if operation[1] == "dec" {
				reg[register] -= num
			} else {
				log.Fatalf("Unknown instruction: '%s'.", operation[1])
			}
			if reg[register] > maxEver {
				maxEver = reg[register]
			}
		}
	}
	// Find the largest register in the end.
	max := math.MinInt64
	for _, v := range reg {
		if v > max {
			max = v
		}
	}
	return max, maxEver
}
