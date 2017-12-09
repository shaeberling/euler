package p09

import (
	"common"
)

// --- Day 9: Stream Processing ---
// http://adventofcode.com/2017/day/8
func Solve(input string) (string, string) {
	solutionA, solutionB := solve(input)
	return common.ToString(solutionA), common.ToString(solutionB)
}

func solve(stream string) (int, int) {
	var sum, garbageCount, nestingLevel int
	garbage := false
	for i := 0; i < len(stream); i++ {
		if stream[i] == '!' {
			i++
			continue
		}
		if garbage {
			if stream[i] == '>' {
				garbage = false
			} else {
				garbageCount++
			}
			continue
		}
		switch stream[i] {
		case '<':
			garbage = true
		case '{':
			nestingLevel++
		case '}':
			sum += nestingLevel
			nestingLevel--
		}
	}
	return sum, garbageCount
}
