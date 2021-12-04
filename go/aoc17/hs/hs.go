package hs

import (
	"log"
	"math"
	"strconv"

	"s13g.com/euler/common"
)

// Mp maps a string to a slice, splitting my new-line
func Mp(str string) []string {
	return common.SplitByNewline(str)
}

// Mps maps a string slice to a 2d slice, splitting each line by whitespace.
func Mps(str []string) [][]string {
	result := make([][]string, len(str))
	for i, line := range str {
		split := common.SplitByWhitespaceTrim(line)
		result[i] = make([]string, len(split))
		for j, v := range split {
			result[i][j] = v
		}
	}
	return result
}

// Mpi maps a 2d slice to an integer slice, applying the given function to each row.
func Mpii(mtrx [][]string, f func([]string) int) []int {
	result := make([]int, len(mtrx))
	for i, line := range mtrx {
		result[i] = f(line)
	}
	return result
}

// Cross-map all values, apply to function and return a single int
func Xmpi(str []string, f func(int, int) int) []int {
	var result []int
	for i := 0; i < len(str); i++ {
		for j := 0; j < len(str); j++ {
			if i != j {
				result = append(result, f(common.ToIntOrPanic(str[i]), common.ToIntOrPanic(str[j])))
			}
		}
	}
	return result
}

func Sum(arr []int) (sum int) {
	for _, v := range arr {
		sum += v
	}
	return
}

func Min(arr []string) int {
	min := math.MaxInt64
	for _, v := range arr {
		vn, err := strconv.Atoi(v)
		if err != nil {
			log.Fatalf("Not a number: '%s'.", v)
		}
		if vn < min {
			min = vn
		}
	}
	return min
}

func Max(arr []string) int {
	max := math.MinInt64
	for _, v := range arr {
		vn, err := strconv.Atoi(v)
		if err != nil {
			log.Fatalf("Not a number: '%s'.", v)
		}
		if vn > max {
			max = vn
		}
	}
	return max
}
