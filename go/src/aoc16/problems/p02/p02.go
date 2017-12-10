package p02

import (
	"common"
	"fmt"
)

// --- Day 2: Bathroom Security ---
// http://adventofcode.com/2016/day/2
func Solve(input string) (string, string) {
	return solveA(input), solveB(input)
}

func solveA(input string) string {
	x := 1
	y := 1
	instructions := common.SplitByNewline(input)
	var resultA string
	for _, instr := range instructions {
		for _, c := range instr {
			if c == 'L' && x > 0 {
				x -= 1
			}
			if c == 'R' && x < 2 {
				x += 1
			}
			if c == 'U' && y > 0 {
				y -= 1
			}
			if c == 'D' && y < 2 {
				y += 1
			}
		}
		num := (y * 3) + x + 1
		resultA += fmt.Sprintf("%d", num)
	}
	return resultA
}

func solveB(input string) string {
	pad := "__1___234_56789_ABC___D__"
	x := 0
	y := 2
	instructions := common.SplitByNewline(input)
	var resultB string
	for _, instr := range instructions {
		for _, c := range instr {
			prevX := x
			prevY := y
			if c == 'L' && x > 0 {
				x -= 1
			}
			if c == 'R' && x < 4 {
				x += 1
			}
			if c == 'U' && y > 0 {
				y -= 1
			}
			if c == 'D' && y < 4 {
				y += 1
			}

			pos := y*5 + x
			if pad[pos] == '_' {
				x = prevX
				y = prevY
			}
		}
		resultB += string(pad[y*5+x])
	}
	return resultB
}
