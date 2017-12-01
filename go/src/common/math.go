package common

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
