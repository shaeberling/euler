package aoc17

import (
	"s13g.com/euler/aoc17/p01"
	"s13g.com/euler/aoc17/p02"
	"s13g.com/euler/aoc17/p03"
	"s13g.com/euler/aoc17/p04"
	"s13g.com/euler/aoc17/p05"
	"s13g.com/euler/aoc17/p06"
	"s13g.com/euler/aoc17/p07"
	"s13g.com/euler/aoc17/p08"
	"s13g.com/euler/aoc17/p09"
	"s13g.com/euler/aoc17/p10"
	"s13g.com/euler/aoc17/p11"
	"s13g.com/euler/aoc17/p12"
	"s13g.com/euler/aoc17/p13"
	"s13g.com/euler/aoc17/p14"
	"s13g.com/euler/aoc17/p15"
	"s13g.com/euler/aoc17/p16"
	"s13g.com/euler/aoc17/p17"
	"s13g.com/euler/aoc17/p18"
	"s13g.com/euler/aoc17/p19"
	"s13g.com/euler/aoc17/p20"
	"s13g.com/euler/aoc17/p21"
	"s13g.com/euler/aoc17/p22"
	"s13g.com/euler/aoc17/p23"
	"s13g.com/euler/aoc17/p24"
	"s13g.com/euler/aoc17/p25"
	"s13g.com/euler/runner"
)

// Run all the AOC 2017 puzzles
func Run(dataDir string) {
	r := runner.InitRunner(2017, dataDir)

	r.AddPuzzle(1, p01.Solve, "995", "1130")
	r.AddPuzzle(2, p02.Solve, "48357", "351")
	r.AddPuzzle(2, p02.SolveHs, "48357", "351")
	r.AddPuzzle(3, p03.Solve, "371", "369601")
	r.AddPuzzle(3, p03.SolveBig, "371", "369601")
	r.AddPuzzle(4, p04.Solve, "451", "223")
	r.AddPuzzle(5, p05.Solve, "387096", "28040648")
	r.AddPuzzle(6, p06.Solve, "6681", "2392")
	r.AddPuzzle(7, p07.Solve, "dgoocsw", "1275")
	r.AddPuzzle(8, p08.Solve, "6343", "7184")
	r.AddPuzzle(9, p09.Solve, "21037", "9495")
	r.AddPuzzle(10, p10.Solve, "7888", "decdf7d377879877173b7f2fb131cf1b")
	r.AddPuzzle(11, p11.Solve, "722", "1551")
	r.AddPuzzle(12, p12.Solve, "175", "213")
	r.AddPuzzle(13, p13.Solve, "3184", "3878062")
	r.AddPuzzle(14, p14.Solve, "8140", "1182")
	r.AddPuzzle(15, p15.Solve, "600", "313")
	r.AddPuzzle(16, p16.Solve, "nlciboghjmfdapek", "nlciboghmkedpfja")
	r.AddPuzzle(17, p17.Solve, "1506", "39479736")
	r.AddPuzzle(18, p18.Solve, "7071", "8001")
	r.AddPuzzle(19, p19.Solve, "RYLONKEWB", "16016")
	r.AddPuzzle(20, p20.Solve, "300", "502")
	r.AddPuzzle(21, p21.Solve, "152", "1956174")
	r.AddPuzzle(22, p22.Solve, "5575", "2511991")
	r.AddPuzzle(23, p23.Solve, "3969", "917")
	r.AddPuzzle(24, p24.Solve, "1656", "1642")
	r.AddPuzzle(25, p25.Solve, "4385", "Done :-)")

	r.Run()
}
