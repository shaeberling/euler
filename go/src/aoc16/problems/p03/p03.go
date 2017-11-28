package p03

import (
	"common"
	"fmt"
	"log"
)

// --- Day 3: Squares With Three Sides ---
// http://adventofcode.com/2016/day/3
func Solve(input string) (string, string) {
	return solveA(input), solveB(input)
}

func solveA(input string) string {
	triangles := common.SplitByNewline(input)
	return fmt.Sprintf("%d", validTriangles(triangles))
}

func solveB(input string) string {
	matrix := common.ParseMatrix(input)
	triangles := turnMatrix(matrix)
	return fmt.Sprintf("%d", validTriangles(triangles))
}

func validTriangles(triangles []string) int {
	possible := 0
	for _, t := range triangles {
		numStrs := common.SplitByWhitespaceTrim(t)
		if len(numStrs) != 3 {
			log.Fatalf("illegal triangle definition '%s'", t)
			return -1
		}
		nums := common.ParseIntArray(numStrs)
		max := common.Max3(nums[0], nums[1], nums[2])
		if (max * 2) < common.Sum(nums) {
			possible += 1
		}
	}
	return possible
}

// Takes a matrix from the input and turns it around, expecting 3 values per input row.
func turnMatrix(matrix [][]string) []string {
	numRows := len(matrix)
	numCols := len(matrix[0]) // Should all be the same
	result := make([]string, numRows)
	idx := 0
	for c := 0; c < numCols; c += 1 {
		for r := 0; r < numRows; r += 3 {
			result[idx] = fmt.Sprintf("%s %s %s", matrix[r][c], matrix[r+1][c], matrix[r+2][c])
			idx += 1
		}
	}
	return result
}
