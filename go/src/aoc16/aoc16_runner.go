package aoc16

import (
	"runner"
	"aoc16/problems"
)

// Run all the AOC 2016 puzzles
func Run(dataDir string) {
	// Note: Add new puzzles here.
	puzzles := []runner.Puzzle{
		{"AOC 2016.01", "aoc/2016/day1.txt", problems.Solve01, "161", "110"},
		{"AOC 2016.02", "aoc/2016/day2.txt", problems.Solve02, "69642", "8CB23"},
	}
	runner.Run(dataDir, puzzles)
}
