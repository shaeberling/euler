package p19

import (
	"strings"

	"s13g.com/euler/common"
)

// --- Day 19: A Series of Tubes ---
// http://adventofcode.com/2017/day/19
func Solve(input string) (string, string) {
	resultA, resultB := solveA(strings.Split(input, "\n"))
	return resultA, common.ToString(resultB)
}

func solveA(p []string) (string, int) {
	x, y, dX, dY := strings.Index(p[0], "|"), 0, 0, 1
	resultA, resultB := "", 0

	isBad := func(newX, newY int) bool {
		return newX < 0 || newY < 0 || len(p) <= newY || len(p[newY]) <= newX || p[newY][newX] == ' '
	}

	for ; !isBad(x, y); x, y, resultB = x+dX, y+dY, resultB+1 {
		if p[y][x] >= 'A' && p[y][x] <= 'Z' {
			resultA += string(p[y][x])
		}

		if p[y][x] == '+' {
			if dX != 0 {
				dX = 0
				if !isBad(x, y+1) {
					dY = 1
				} else if !isBad(x, y-1) {
					dY = -1
				}
			} else {
				dY = 0
				if !isBad(x+1, y) {
					dX = 1
				} else if !isBad(x-1, y) {
					dX = -1
				}
			}
		}
	}
	return resultA, resultB
}
