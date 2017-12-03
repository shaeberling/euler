package common

import (
	"math"
	"fmt"
)

func MinMaxArr(nums []int) (int, int) {
	min := math.MaxInt64
	max := math.MinInt64
	for _, n := range nums {
		if n < min {
			min = n
		}
		if n > max {
			max = n
		}
	}
	return min, max
}

func MinMax(a int, b int) (int, int) {
	if a < b {
		return a, b
	} else {
		return b, a
	}
}

func Max3(a, b, c int) int {
	return Max(Max(a, b), c)
}

func Max(a, b int) int {
	if a > b {
		return a
	}
	return b
}
func Min(a, b int) int {
	if a < b {
		return a
	}
	return b
}

func MinA(arr []int) int {
	min := math.MaxInt64
	for _, v := range arr {
		if v < min {
			min = v
		}
	}
	return min
}

func Sum(arr []int) (sum int) {
	for _, x := range arr {
		sum += x
	}
	return
}

func Abs(n int) int {
	if n < 0 {
		return -n
	}
	return n
}

// A grid that can also have negative indices.
type Grid struct {
	values map[string]int
}

func NewGrid() *Grid {
	g := new(Grid)
	g.values = make(map[string]int)
	return g
}

func toKey(x, y int) string { return fmt.Sprintf("%d,%d", x, y) }

func (g *Grid) Set(x int, y int, value int) {
	g.values[toKey(x, y)] = value
}

func (g *Grid) Get(x int, y int) int {
	return g.values[toKey(x, y)]
}
