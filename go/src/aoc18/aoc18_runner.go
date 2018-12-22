package aoc18

import (
	"aoc18/problems/p01"
	"aoc18/problems/p02"
	"aoc18/problems/p03"
	"aoc18/problems/p04"
	"aoc18/problems/p05"
	"aoc18/problems/p06"
	"aoc18/problems/p07"
	"aoc18/problems/p08"
	"aoc18/problems/p09"
	"aoc18/problems/p10"
	"aoc18/problems/p11"
	"aoc18/problems/p12"
	"aoc18/problems/p13"
	"aoc18/problems/p14"
	"aoc18/problems/p15"
	"aoc18/problems/p16"
	"aoc18/problems/p17"
	"aoc18/problems/p18"
	"aoc18/problems/p19"
	"aoc18/problems/p20"
	"runner"
)

// Run all the AOC 2018 puzzles
func Run(dataDir string) {
	// Note: Add new puzzles here.
	puzzles := []runner.Puzzle{
		{"AOC 2018.01", "aoc/2018/day1.txt", p01.Solve, "416", "56752"},
		{"AOC 2018.02", "aoc/2018/day2.txt", p02.Solve, "8715", "fvstwblgqkhpuixdrnevmaycd"},
		{"AOC 2018.03", "aoc/2018/day3.txt", p03.Solve, "110546", "819"},
		{"AOC 2018.04", "aoc/2018/day4.txt", p04.Solve, "4716", "117061"},
		{"AOC 2018.05", "aoc/2018/day5.txt", p05.Solve, "9238", "4052"},
		{"AOC 2018.06", "aoc/2018/day6.txt", p06.Solve, "3894", "39398"},
		{"AOC 2018.07", "aoc/2018/day7.txt", p07.Solve, "IJLFUVDACEHGRZPNKQWSBTMXOY", "1072"},
		{"AOC 2018.08", "aoc/2018/day8.txt", p08.Solve, "38567", "24453"},
		{"AOC 2018.09", "aoc/2018/day9.txt", p09.Solve, "380705", "3171801582"},
		{"AOC 2018.10", "aoc/2018/day10.txt", p10.Solve, "ZNNRZJXP", "10418"},
		{"AOC 2018.11", "aoc/2018/day11.txt", p11.Solve, "21,77", "224,222,27"},
		{"AOC 2018.12", "aoc/2018/day12.txt", p12.Solve, "3051", "1300000000669"},
		{"AOC 2018.13", "aoc/2018/day13.txt", p13.Solve, "82,104", "121,22"},
		{"AOC 2018.14", "aoc/2018/day14.txt", p14.Solve, "5992684592", "20181148"},
		{"AOC 2018.15", "aoc/2018/day15.txt", p15.Solve, "190012", "34364"},
		{"AOC 2018.16", "aoc/2018/day16.txt", p16.Solve, "517", "667"},
		{"AOC 2018.17", "aoc/2018/day17.txt", p17.Solve, "31383", "25376"},
		{"AOC 2018.18", "aoc/2018/day18.txt", p18.Solve, "466312", "176782"},
		{"AOC 2018.19", "aoc/2018/day19.txt", p19.Solve, "960", "10750428"},
		{"AOC 2018.20", "aoc/2018/day20.txt", p20.Solve, "3568", "8475"},
		// {"AOC 2018.21", "aoc/2018/day21.txt", p21.Solve, "n/a", "n/a"},
		// {"AOC 2018.22", "aoc/2018/day22.txt", p22.Solve, "n/a", "n/a"},
		// {"AOC 2018.23", "aoc/2018/day23.txt", p23.Solve, "n/a", "n/a"},
		// {"AOC 2018.24", "aoc/2018/day24.txt", p24.Solve, "n/a", "n/a"},
		// {"AOC 2018.25", "aoc/2018/day25.txt", p25.Solve, "n/a", "n/a"},
	}
	runner.Run(dataDir, puzzles)
}
