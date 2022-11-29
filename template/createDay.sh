#!/bin/sh

DAY=$1
TITLE=$2

if [ "$DAY" -lt 10 ]; then
    DAY="0${DAY}"
fi

mkdir ../src/Day_"$DAY"/

cp -r main.kt ../src/Day_"$DAY"/main.kt

touch ../src/Day_"$DAY"/README.md
touch ../src/Day_"$DAY"/input.txt
printf "# Advent Of Code 2022 Day %s: %s\n\n## Part 1\nTBD\n\n---\n## Part 2\nTBD" "$DAY" "$TITLE" > ../src/Day_"$DAY"/README.md