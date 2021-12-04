package main

import (
	"log"
	"os"

	"s13g.com/euler/aoc21"
	"s13g.com/euler/common"
)

func main() {
	if len(os.Args) != 2 {
		log.Fatalln("missing argument to data directory")
		return
	}

	dirname := os.Args[1]
	if !common.IsDirectory(dirname) {
		log.Fatalf("directory does not exist '%s'\n", dirname)
		return
	}

	f, err := os.OpenFile("aoc.log", os.O_RDWR|os.O_CREATE, 0660)
	if err != nil {
		log.Fatalf("cannot open file '%v'", err)
	}
	defer f.Close()
	log.SetOutput(f)

	// aoc16.Run(dirname)
	// aoc17.Run(dirname)
	// aoc18.Run(dirname)
	aoc21.Run(dirname)
	//stairs.Stairs()
}
