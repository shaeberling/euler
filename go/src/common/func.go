package common

// Maps the values of an array by applying the given function.
func Map(arr []int, f func(int) int) []int {
	result := make([]int, len(arr))
	for i, v := range arr {
		result[i] = f(v)
	}
	return result
}

// Maps the values of an array by applying the given function.
func MapIStr(arr []int, f func(int) string) string {
	result := ""
	for _, v := range arr {
		result += f(v)
	}
	return result
}

// Finds the first index that is 'true' and returns it. -1 if none exists.
func FindFirstTrue(data map[int]bool) int {
	for n, v := range data {
		if v {
			return n
		}
	}
	return -1
}

func CountRunesIf(arr []rune, r rune) (sum int) {
	for _, c := range arr {
		if c == r {
			sum++
		}
	}
	return
}
