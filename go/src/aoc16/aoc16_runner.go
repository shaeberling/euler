package aoc16

import (
	"aoc16/problems/p01"
	"aoc16/problems/p02"
	"aoc16/problems/p03"
	"aoc16/problems/p04"
	"runner"
)

// Run all the AOC 2016 puzzles
func Run(dataDir string) {
	// Note: Add new puzzles here.
	puzzles := []runner.Puzzle{
		{"AOC 2016.01", "aoc/2016/day1.txt", p01.Solve, "161", "110"},
		{"AOC 2016.02", "aoc/2016/day2.txt", p02.Solve, "69642", "8CB23"},
		{"AOC 2016.03", "aoc/2016/day3.txt", p03.Solve, "1032", "1838"},
		{"AOC 2016.04", "aoc/2016/day4.txt", p04.Solve, "137896", "501"},
	}
	runner.Run(dataDir, puzzles)
}
