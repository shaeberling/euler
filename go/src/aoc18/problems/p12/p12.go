package p12

import (
	c "common"
)

// --- Day 12: Subterranean Sustainability ---
// http://adventofcode.com/2018/day/12
func Solve(input string) (string, string) {
	lines := c.SplitByNewline(input)
	state := []rune(lines[0][15:])
	rules := make(map[string]rune)
	for _, l := range lines[2:] {
		r := l[:5]
		rules[r] = rune(l[9])
	}
	return c.ToString(solveIt(state, rules, 20)), c.ToString(solveIt(state, rules, 50000000000))
}

func solveIt(state []rune, rules map[string]rune, gen int) int {
	offset, numMoved, prevState := 0, 0, ""
	for i := 1; i <= gen; i++ {
		state, numMoved = trimWell(state)
		stateStr := string(state)
		if stateStr == prevState {
			// We found a loop! Fast forward...
			offset += (gen - i + 2) * numMoved
			break
		}
		offset += numMoved
		prevState = stateStr
		state = iterate(state, rules)
	}
	// Calculate the final sum based on offset.
	sumA := 0
	for i, r := range state {
		if r == '#' {
			sumA += (i + offset)
		}
	}
	return sumA
}

func iterate(state []rune, rules map[string]rune) []rune {
	result := make([]rune, len(state))
	result[0] = '.'
	result[1] = '.'
	for i := 0; i < len(state)-4; i++ {
		toMatch := state[i : i+5]
		if val, ok := rules[string(toMatch)]; ok {
			result[i+2] = val
		} else {
			result[i+2] = '.'
		}
	}
	result[len(result)-1] = '.'
	result[len(result)-2] = '.'
	return result
}

// We want at least four empty pots on each side for rules to match
func trimWell(state []rune) ([]rune, int) {
	firstPot, lastPot := -1, -1
	for i, p := range state {
		if firstPot == -1 && p == '#' {
			firstPot = i
		}
		if p == '#' {
			lastPot = i
		}
	}
	numToAdd := c.Max(0, lastPot-(len(state)-4))
	for i := 0; i < numToAdd; i++ {
		state = append(state, '.')
	}

	numMoved := 0
	if firstPot < 4 {
		numMoved = -4
		state = append([]rune{'.', '.', '.', '.'}, state...)
	} else if firstPot > 4 {
		numMoved = firstPot - 4
		state = state[numMoved:]
	}
	return state, numMoved
}
