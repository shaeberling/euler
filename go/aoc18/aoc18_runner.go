package aoc18

import (
	"s13g.com/euler/aoc18/p01"
	"s13g.com/euler/aoc18/p02"
	"s13g.com/euler/aoc18/p03"
	"s13g.com/euler/aoc18/p04"
	"s13g.com/euler/aoc18/p05"
	"s13g.com/euler/aoc18/p06"
	"s13g.com/euler/aoc18/p07"
	"s13g.com/euler/aoc18/p08"
	"s13g.com/euler/aoc18/p09"
	"s13g.com/euler/aoc18/p10"
	"s13g.com/euler/aoc18/p11"
	"s13g.com/euler/aoc18/p12"
	"s13g.com/euler/aoc18/p13"
	"s13g.com/euler/aoc18/p14"
	"s13g.com/euler/aoc18/p15"
	"s13g.com/euler/aoc18/p16"
	"s13g.com/euler/aoc18/p17"
	"s13g.com/euler/aoc18/p18"
	"s13g.com/euler/aoc18/p19"
	"s13g.com/euler/aoc18/p20"
	"s13g.com/euler/runner"
)

// Run all the AOC 2018 puzzles
func Run(dataDir string) {
	r := runner.InitRunner(2018, dataDir)

	r.AddPuzzle(1, p01.Solve, "416", "56752")
	r.AddPuzzle(2, p02.Solve, "8715", "fvstwblgqkhpuixdrnevmaycd")
	r.AddPuzzle(3, p03.Solve, "110546", "819")
	r.AddPuzzle(4, p04.Solve, "4716", "117061")
	r.AddPuzzle(5, p05.Solve, "9238", "4052")
	r.AddPuzzle(6, p06.Solve, "3894", "39398")
	r.AddPuzzle(7, p07.Solve, "IJLFUVDACEHGRZPNKQWSBTMXOY", "1072")
	r.AddPuzzle(8, p08.Solve, "38567", "24453")
	r.AddPuzzle(9, p09.Solve, "380705", "3171801582")
	r.AddPuzzle(10, p10.Solve, "ZNNRZJXP", "10418")
	r.AddPuzzle(11, p11.Solve, "21,77", "224,222,27")
	r.AddPuzzle(12, p12.Solve, "3051", "1300000000669")
	r.AddPuzzle(13, p13.Solve, "82,104", "121,22")
	r.AddPuzzle(14, p14.Solve, "5992684592", "20181148")
	r.AddPuzzle(15, p15.Solve, "190012", "34364")
	r.AddPuzzle(16, p16.Solve, "517", "667")
	r.AddPuzzle(17, p17.Solve, "31383", "25376")
	r.AddPuzzle(18, p18.Solve, "466312", "176782")
	r.AddPuzzle(19, p19.Solve, "960", "10750428")
	r.AddPuzzle(20, p20.Solve, "3568", "8475")
	// r.AddPuzzle(21, p21.Solve, "n/a", "n/a")
	// r.AddPuzzle(22, p22.Solve, "n/a", "n/a")
	// r.AddPuzzle(23, p23.Solve, "396", "n/a")
	// r.AddPuzzle(24, p24.Solve, "n/a", "n/a")
	// r.AddPuzzle(25, p25.Solve, "305", "n/a")

	r.Run()
}
