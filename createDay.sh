#!/bin/sh

PROVIDED_DAY=$1
TITLE=$2

if [ "$PROVIDED_DAY" -lt 10 ]; then
    DAY="0${PROVIDED_DAY}"
else
    DAY="$PROVIDED_DAY"
fi

mkdir -p ./src/main/kotlin/
mkdir -p ./src/inputs

touch ./src/inputs/Day_${DAY}.txt

cp -R ./template ./src/main/kotlin/day_"$DAY"

sed -i "s/package template/package day_$DAY/; s/input.txt/Day_$DAY.txt/" "./src/main/kotlin/day_$DAY/Main.kt"

printf "# Advent Of Code 2022 Day %s: %s\n\n## Part 1\nTBD\n\n---\n## Part 2\nTBD" "$DAY" "$TITLE" > ./src/main/kotlin/day_"$DAY"/README.md

printf "\n[**Day %s**](/src/main/kotlin/day_%s/)\n - [AdventOfCode](https://adventofcode.com/2022/day/%s)" "$PROVIDED_DAY" "$DAY" "$PROVIDED_DAY" >> README.md

curl --cookie "session=$AOC_COOKIE" https://adventofcode.com/2022/day/"$PROVIDED_DAY"/input > ./src/inputs/Day_"$DAY".txt
