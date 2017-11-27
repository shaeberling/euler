package main

import (
	"aoc16"
	"os"
	"common"
	"log"
)

func main() {
	if len(os.Args) != 2 {
		log.Fatalln("missing argument to data directory\n")
		return
	}

	dirname := os.Args[1]
	if !common.IsDirectory(dirname) {
		log.Fatalf("directory does not exist '%s'\n", dirname)
		return
	}
	aoc16.Run(dirname)
}
