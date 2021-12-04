package aoc21

import (
	"s13g.com/euler/aoc21/p01"
	"s13g.com/euler/aoc21/p02"
	"s13g.com/euler/aoc21/p03"
	"s13g.com/euler/runner"
)

// Run all the AOC 2021 puzzles
func Run(dataDir string) {
	r := runner.InitRunner(2021, dataDir)

	r.AddPuzzle(1, p01.Solve, "1462", "1497")
	r.AddPuzzle(2, p02.Solve, "2215080", "1864715580")
	r.AddPuzzle(3, p03.Solve, "3549854", "3765399")
	// r.AddPuzzle(1, p04.Solve, "33462", "30070")

	r.Run(true)
}
