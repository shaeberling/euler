package aoc21

import (
	"s13g.com/euler/aoc21/p01"
	"s13g.com/euler/aoc21/p02"
	"s13g.com/euler/aoc21/p03"
	"s13g.com/euler/aoc21/p05"
	"s13g.com/euler/aoc21/p06"
	"s13g.com/euler/aoc21/p07"
	"s13g.com/euler/aoc21/p17"
	"s13g.com/euler/runner"
)

// Run all the AOC 2021 puzzles
func Run(dataDir string) {
	r := runner.InitRunner(2021, dataDir)

	r.AddPuzzle(1, p01.Solve, "1462", "1497")
	r.AddPuzzle(2, p02.Solve, "2215080", "1864715580")
	r.AddPuzzle(3, p03.Solve, "3549854", "3765399")
	// r.AddPuzzle(4, p04.Solve, "33462", "30070")
	r.AddPuzzle(5, p05.Solve, "5169", "22083")
	r.AddPuzzle(6, p06.Solve, "383160", "1721148811504")
	r.AddPuzzle(7, p07.Solve, "329389", "86397080")
	r.AddPuzzle(17, p17.Solve, "5995", "3202")

	r.Run(true)
}
