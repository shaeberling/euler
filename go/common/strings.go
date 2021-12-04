package common

import (
	"bufio"
	"fmt"
	"log"
	"math/big"
	"sort"
	"strconv"
	"strings"
)

func ToIntOrPanic(content string) int {
	v, err := strconv.ParseInt(content, 10, 64)
	if err != nil {
		log.Fatalf("Cannot parse %v", err.Error())
	}
	return int(v)
}

// Trim and parse Int.
func TrInt(content string) int {
	return ToIntOrPanic(strings.TrimSpace(content))
}

func ToString(v int) string {
	return fmt.Sprintf("%d", v)
}

func ToStringBig(v *big.Int) string {
	return fmt.Sprintf("%v", v)
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

func SplitTrim(content string, char rune) []string {
	values := strings.FieldsFunc(content, func(c rune) bool { return c == char })
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
	var result []string
	sc := bufio.NewScanner(strings.NewReader(content))
	for sc.Scan() {
		result = append(result, sc.Text())
	}
	return result
}

func SortChars(str string) string {
	s := strings.Split(str, "")
	sort.Strings(s)
	return strings.Join(s, "")
}
