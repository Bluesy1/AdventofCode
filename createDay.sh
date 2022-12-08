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

touch ./2022/inputs/Day_"${DAY}".txt

cp -R ./template ./2022/main/day_"$DAY"

sed -i "s/package template/package day_${DAY}_2022/; s/input.txt/Day_$DAY.txt/" "./2022/main/day_$DAY/Main.kt"

printf "# Advent Of Code 2022 Day %s: %s\n\n## Part 1\nTBD\n\n---\n## Part 2\nTBD" "$DAY" "$TITLE" > ./2022/main/day_"$DAY"/README.md

printf "\n[**Day %s: %s**](main/day_%s/) - [AdventOfCode](https://adventofcode.com/2022/day/%s)\n" "$PROVIDED_DAY" "$TITLE" "$DAY" "$PROVIDED_DAY" >> ./2022/README.md

curl --cookie "session=$AOC_COOKIE" https://adventofcode.com/2022/day/"$PROVIDED_DAY"/input > ./src/inputs/Day_"$DAY".txt
