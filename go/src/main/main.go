package main

import (
	"aoc16"
	"aoc17"
	"common"
	"log"
	"os"
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
	aoc17.Run(dirname)
}
