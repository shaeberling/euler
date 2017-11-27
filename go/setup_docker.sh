#!/bin/bash

export PS1='\[\e[0;31m\]\u\[\e[m\]\[\e[1;37m\]:\[\e[m\]\[\e[4;37m\]mac\[\e[m\] \[\e[1;33m\]\w\[\e[m\] \[\e[1;32m\]\$ \[\e[m\]\[\e[1;37m\]'
alias ll='ls -la'

export GOPATH=/usr/src/myapp
aoc () {
  go run src/main/main.go  /usr/eulerdata/
}
export -f aoc

