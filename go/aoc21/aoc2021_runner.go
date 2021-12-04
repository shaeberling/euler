package aoc21

import (
	"s13g.com/euler/aoc21/p01"
	"s13g.com/euler/runner"
)

// Run all the AOC 2018 puzzles
func Run(dataDir string) {
	// Note: Add new puzzles here.
	puzzles := []runner.Puzzle{
		{Name: "AOC 2021.01",
			Filename:  "aoc/2021/day1.txt",
			Solver:    p01.Solve,
			SolutionA: "1462",
			SolutionB: "1497"},
	}
	runner.Run(dataDir, puzzles)
}
