package p14

import (
	"fmt"
	"strconv"

	"s13g.com/euler/aoc17/p10"

	c "s13g.com/euler/common"
)

// --- Day 14: Disk Defragmentation ---
// http://adventofcode.com/2017/day/14
func Solve(input string) (string, string) {
	solutionA, solutionB := solveA(input)
	return c.ToString(solutionA), c.ToString(solutionB)
}

func solveA(input string) (int, int) {
	field := make([][]rune, 128)
	numUsed := 0
	for i := 0; i < 128; i++ {
		hash := p10.KnotHash(fmt.Sprintf("%s-%d", input, i))
		field[i] = toHexToBinary(hash)
		numUsed += c.CountRunesIf(field[i], '1')
	}
	return numUsed, findRegions(field)
}

func findRegions(field [][]rune) (num int) {
	for x := 0; x < 128; x++ {
		for y := 0; y < 128; y++ {
			if field[x][y] == '1' {
				num++
				markAll(x, y, field)
			}
		}
	}
	return num
}

func markAll(x int, y int, field [][]rune) {
	if x < 0 || x > 127 || y < 0 || y > 127 || field[x][y] == '0' {
		return
	}
	field[x][y] = '0'
	markAll(x-1, y, field)
	markAll(x+1, y, field)
	markAll(x, y-1, field)
	markAll(x, y+1, field)
}

func toHexToBinary(hex string) []rune {
	result := ""
	for _, c := range hex {
		ui, _ := strconv.ParseUint(string(c), 16, 16)
		result += fmt.Sprintf("%04b", ui)
	}
	return []rune(result)
}
