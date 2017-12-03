package common

// Map processes the values of an array by applying the given function.
func Map(arr []int, f func(int) (int)) []int {
	result := make([]int, len(arr))
	for i, v := range arr {
		result[i] = f(v)
	}
	return result
}
