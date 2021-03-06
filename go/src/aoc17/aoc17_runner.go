package aoc17

import (
	"aoc17/problems/p01"
	"aoc17/problems/p02"
	"aoc17/problems/p03"
	"aoc17/problems/p04"
	"aoc17/problems/p05"
	"aoc17/problems/p06"
	"aoc17/problems/p07"
	"aoc17/problems/p08"
	"aoc17/problems/p09"
	"aoc17/problems/p10"
	"aoc17/problems/p11"
	"aoc17/problems/p12"
	"aoc17/problems/p13"
	"aoc17/problems/p14"
	"aoc17/problems/p15"
	"aoc17/problems/p16"
	"aoc17/problems/p17"
	"aoc17/problems/p18"
	"aoc17/problems/p19"
	"aoc17/problems/p20"
	"aoc17/problems/p21"
	"aoc17/problems/p22"
	"aoc17/problems/p23"
	"aoc17/problems/p24"
	"aoc17/problems/p25"
	"runner"
)

// Run all the AOC 2017 puzzles
func Run(dataDir string) {
	// Note: Add new puzzles here.
	puzzles := []runner.Puzzle{
		{"AOC 2017.01", "aoc/2017/day1.txt", p01.Solve, "995", "1130"},
		{"AOC 2017.02", "aoc/2017/day2.txt", p02.Solve, "48357", "351"},
		{"AOC 2017.02.hs", "aoc/2017/day2.txt", p02.SolveHs, "48357", "351"},
		{"AOC 2017.03", "aoc/2017/day3.txt", p03.Solve, "371", "369601"},
		{"AOC 2017.03.big", "aoc/2017/day3.txt", p03.SolveBig, "371", "369601"},
		{"AOC 2017.04", "aoc/2017/day4.txt", p04.Solve, "451", "223"},
		{"AOC 2017.05", "aoc/2017/day5.txt", p05.Solve, "387096", "28040648"},
		{"AOC 2017.06", "aoc/2017/day6.txt", p06.Solve, "6681", "2392"},
		{"AOC 2017.07", "aoc/2017/day7.txt", p07.Solve, "dgoocsw", "1275"},
		{"AOC 2017.08", "aoc/2017/day8.txt", p08.Solve, "6343", "7184"},
		{"AOC 2017.09", "aoc/2017/day9.txt", p09.Solve, "21037", "9495"},
		{"AOC 2017.10", "aoc/2017/day10.txt", p10.Solve, "7888", "decdf7d377879877173b7f2fb131cf1b"},
		{"AOC 2017.11", "aoc/2017/day11.txt", p11.Solve, "722", "1551"},
		{"AOC 2017.12", "aoc/2017/day12.txt", p12.Solve, "175", "213"},
		{"AOC 2017.13", "aoc/2017/day13.txt", p13.Solve, "3184", "3878062"},
		{"AOC 2017.14", "aoc/2017/day14.txt", p14.Solve, "8140", "1182"},
		{"AOC 2017.15", "aoc/2017/day15.txt", p15.Solve, "600", "313"},
		{"AOC 2017.16", "aoc/2017/day16.txt", p16.Solve, "nlciboghjmfdapek", "nlciboghmkedpfja"},
		{"AOC 2017.17", "aoc/2017/day17.txt", p17.Solve, "1506", "39479736"},
		{"AOC 2017.18", "aoc/2017/day18.txt", p18.Solve, "7071", "8001"},
		{"AOC 2017.19", "aoc/2017/day19.txt", p19.Solve, "RYLONKEWB", "16016"},
		{"AOC 2017.20", "aoc/2017/day20.txt", p20.Solve, "300", "502"},
		{"AOC 2017.21", "aoc/2017/day21.txt", p21.Solve, "152", "1956174"},
		{"AOC 2017.22", "aoc/2017/day22.txt", p22.Solve, "5575", "2511991"},
		{"AOC 2017.23", "aoc/2017/day23.txt", p23.Solve, "3969", "917"},
		{"AOC 2017.24", "aoc/2017/day24.txt", p24.Solve, "1656", "1642"},
		{"AOC 2017.25", "aoc/2017/day25.txt", p25.Solve, "4385", "Done :-)"},
	}
	runner.Run(dataDir, puzzles)
}
