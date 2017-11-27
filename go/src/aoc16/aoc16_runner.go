package aoc16

import (
	"aoc16/problems/p01"
	"runner"
	"aoc16/problems/p02"
)

// Run all the AOC 2016 puzzles
func Run(dataDir string) {
	// Note: Add new puzzles here.
	puzzles := []runner.Puzzle{
		{"AOC 2016.01", "aoc/2016/day1.txt", p01.Solve, "161", "110"},
		{"AOC 2016.02", "aoc/2016/day2.txt", p02.Solve, "69642", "8CB23"},
	}
	runner.Run(dataDir, puzzles)
}
