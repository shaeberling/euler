package common

import "math"

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

func Sum(arr []int) (sum int) {
	for _, x := range arr {
		sum += x
	}
	return
}
