#!/bin/sh

SCRIPTPATH="$( cd "$(dirname "$0")" ; pwd -P )"

docker run --rm -it -v "$PWD":/usr/src/myapp -v "${SCRIPTPATH}/../data":/usr/eulerdata -w /usr/src/myapp golang
