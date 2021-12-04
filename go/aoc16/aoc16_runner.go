package aoc16

import (
	"s13g.com/euler/aoc16/p01"
	"s13g.com/euler/aoc16/p02"
	"s13g.com/euler/aoc16/p03"
	"s13g.com/euler/aoc16/p04"
	"s13g.com/euler/runner"
)

// Run all the AOC 2016 puzzles
func Run(dataDir string) {
	r := runner.InitRunner(2016, dataDir)

	r.AddPuzzle(1, p01.Solve, "161", "110")
	r.AddPuzzle(2, p02.Solve, "69642", "8CB23")
	r.AddPuzzle(3, p03.Solve, "1032", "1838")
	r.AddPuzzle(4, p04.Solve, "137896", "501")

	r.Run(false)
}
