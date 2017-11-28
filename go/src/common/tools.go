package common

import (
	"os"
	"strings"
	"strconv"
	"log"
)

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

func ParseMatrix(content string) [][]string {
	rows := SplitByNewline(content)
	matrix := make([][]string, len(rows))

	for i, row := range rows {
		matrix[i] = SplitByWhitespaceTrim(row)
	}
	return matrix
}

func ParseIntArray(array []string) []int {
	result := make([]int, len(array))
	for i, str := range array {
		v, err := strconv.ParseInt(str, 10, 64)
		if err != nil {
			log.Fatalf("Cannot parse value '%s", str)
			return []int{}
		}
		result[i] = int(v)
	}
	return result
}

func SplitByCommaTrim(content string) []string {
	values := strings.FieldsFunc(content, func(c rune) bool { return c == ',' })
	result := make([]string, len(values))
	for i, v := range values {
		result[i] = strings.TrimSpace(v)
	}
	return result
}

func SplitByWhitespaceTrim(content string) []string {
	values := strings.Fields(content)
	result := make([]string, len(values))
	for i, v := range values {
		result[i] = strings.TrimSpace(v)
	}
	return result
}

func SplitByNewline(content string) []string {
	values := strings.FieldsFunc(content, func(c rune) bool { return c == '\n' })
	result := make([]string, len(values))
	for i, v := range values {
		result[i] = strings.TrimSpace(v)
	}
	return result
}

func IsDirectory(filename string) bool {
	if fileInfo, err := os.Stat(filename); os.IsNotExist(err) {
		return false
	} else {
		return fileInfo.IsDir()
	}
}

func IsRegularFile(filename string) bool {
	if fileInfo, err := os.Stat(filename); os.IsNotExist(err) {
		return false
	} else {
		return !fileInfo.IsDir() && fileInfo.Mode().IsRegular()
	}
}
