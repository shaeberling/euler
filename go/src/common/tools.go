package common

import (
	"os"
	"strings"
)

func SplitByCommaTrim(content string) []string {
	values := strings.FieldsFunc(content, func(c rune) bool { return c == ',' })
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
