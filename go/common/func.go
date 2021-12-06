package common

// Map maps the values of an array by applying the given function.
func Map(arr []int, f func(int) int) []int {
	result := make([]int, len(arr))
	for i, v := range arr {
		result[i] = f(v)
	}
	return result
}

// MapStrI maps the values of an array by applying the given function.
func MapStrI(arr []string, f func(string) int) []int {
	result := make([]int, len(arr))
	for i, v := range arr {
		result[i] = f(v)
	}
	return result
}

// MapIStr maps the values of an array by applying the given function.
func MapIStr(arr []int, f func(int) string) string {
	result := ""
	for _, v := range arr {
		result += f(v)
	}
	return result
}

// FindFirstTrue finds the first index that is 'true' and returns it. -1 if none exists.
func FindFirstTrue(data map[int]bool) int {
	for n, v := range data {
		if v {
			return n
		}
	}
	return -1
}

func CountMappedIntsIf(arr map[int]int, i int) (sum int) {
	for _, c := range arr {
		if c == i {
			sum++
		}
	}
	return
}

func CountRunesIf(arr []rune, r rune) (sum int) {
	for _, c := range arr {
		if c == r {
			sum++
		}
	}
	return
}

func Contains(arr []string, str string) bool {
	for _, s := range arr {
		if s == str {
			return true
		}
	}
	return false
}

func MapStrIntContains(m map[string]int, str string) bool {
	_, exists := m[str]
	return exists
}
