package common

import "os"

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
